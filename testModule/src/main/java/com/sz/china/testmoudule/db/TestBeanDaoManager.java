package com.sz.china.testmoudule.db;

import android.content.Context;
import android.util.Log;

import com.sz.china.testmoudule.application.BaseApplication;
import com.sz.china.testmoudule.bean.DaoMaster;
import com.sz.china.testmoudule.bean.DaoSession;
import com.sz.china.testmoudule.bean.GreenDaoTestBean;
import com.sz.china.testmoudule.bean.GreenDaoTestBeanDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 使用GreenDao  与GreenDaoTestBean数据类对应匹配的表管理类
 * Created by zhangyu on 2016/9/19.
 */
public class TestBeanDaoManager {
    private static final String TAG = "TestBeanDaoManager";
    static Context context = BaseApplication.context;
    private static String dbName = "TestDataBase";//数据库名
    private static DaoMaster.DevOpenHelper openhelper;


    private static class SingleHolder {
        private static final TestBeanDaoManager instance = new TestBeanDaoManager();
    }

    //单例
    public static TestBeanDaoManager getInstance(){
        return SingleHolder.instance;
    }

    /**
     * 创建数据库
     */
    public static void createDataBase(){
        openhelper = new DaoMaster.DevOpenHelper(context, dbName);
    }

    private GreenDaoTestBeanDao getReadableDao(){
        DaoMaster daomaster = new DaoMaster(openhelper.getReadableDb());
        DaoSession daosession = daomaster.newSession();
        GreenDaoTestBeanDao gdtbDao = daosession.getGreenDaoTestBeanDao();
        return gdtbDao;
    }

    private GreenDaoTestBeanDao getWritableDao(){
        DaoMaster daomaster = new DaoMaster(openhelper.getWritableDb());
        DaoSession daosession = daomaster.newSession();
        GreenDaoTestBeanDao gdtbDao = daosession.getGreenDaoTestBeanDao();
        return gdtbDao;
    }

    /**
     * 插入数据
     * @param greenDaoTestBean 待插入的数据bean
     * @return 新插入数据的rowId
     */
    public long insert(GreenDaoTestBean greenDaoTestBean){
        return getWritableDao().insert(greenDaoTestBean);
    }
    /**
     * 省略封装：
     * 批量插入 getWritableDao().insertInTx();
     * 具体参数使用查询API
     */

    /**
     * 删除数据
     * @param greenDaoTestBean 待删除的数据bean
     */
    public void delete(GreenDaoTestBean greenDaoTestBean){
         getWritableDao().delete(greenDaoTestBean);
    }

    /**
     * 通过主键来删除对应数据
     * @param id 主键值 通过GreenDaoTestBeanDao.getId()获取
     */
    public void deleteByKey(Long id){
        getWritableDao().deleteByKey(id);
    }

    /**
     * 省略封装:
     * 批量删除  getWritableDao().deleteInTx(..);
     * 批量根据主键删除   getWritableDao().deleteByKeyInTx();
     * 具体参数使用查询API
     */

    /**
     * 更新数据bean
     * bean必须设置主键 否则无法找到替换位置，更新会失败
     * @param greenDaoTestBean 更新后数据bean
     */
    public boolean update(GreenDaoTestBean greenDaoTestBean){
        if(greenDaoTestBean.getId() == null)
            return false;

        getWritableDao().update(greenDaoTestBean);
        return true;
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<GreenDaoTestBean> queryAll(){
        QueryBuilder<GreenDaoTestBean> queryBuilder = getGDTBeanQueryBuilder();

        List<GreenDaoTestBean> queryList = queryBuilder.list();//gdtbDao.queryRaw("value1",gdtBean.getValue1());
        for (GreenDaoTestBean bean : queryList)
            Log.d(TAG, bean.toString());
        return queryList;
    }

    /**
     * 根据number查询数据
     * @return
     */
    public List<GreenDaoTestBean> queryDataByNumber(long number){
        QueryBuilder<GreenDaoTestBean> queryBuilder = getGDTBeanQueryBuilder();
        queryBuilder.where(GreenDaoTestBeanDao.Properties.Number.eq(number));

        List<GreenDaoTestBean> queryList = queryBuilder.list();
        for (GreenDaoTestBean bean : queryList)
            Log.d(TAG, bean.toString());
        return queryList;
    }

    private QueryBuilder<GreenDaoTestBean> getGDTBeanQueryBuilder() {
        DaoMaster daomaster = new DaoMaster(openhelper.getReadableDb());
        DaoSession daosession = daomaster.newSession();
        return daosession.queryBuilder(GreenDaoTestBean.class);
    }


    /*
    嵌套条件的例子：获取名为“Joe”出生在1970年10月份或1970年之后的用户。假如用户的生日分成年月日三个字段，
    这样便可换一种方式来表示上面的查询：名字是“Joe” AND (生日年份大于1970 OR(生日年份是1970  AND 月份大于等于10))。
QueryBuilder qb = userDao.queryBuilder();
qb.where(Properties.FirstName.eq("Joe"),
qb.or(Properties.YearOfBirth.gt(1970),
qb.and(Properties.YearOfBirth.eq(1970), Properties.MonthOfBirth.ge(10))));
List youngJoes = qb.list();

    * */

}
