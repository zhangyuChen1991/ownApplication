package com.cc.test.sqldemo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by zhangyu on 2016-06-07 11:08.
 */
public class SqlManager {
    SqlHelper sqlHelper;

    public SqlManager(Context context, String name, String createTableOrder) {
        sqlHelper = new SqlHelper(context, name, null, 1, createTableOrder);
    }

    /**
     * 执行sql语句命令 实现增、删、改等功能
     * @param sql sql语句
     */
    public void doSql(String sql){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
        //insert into table ("列名1",itemNum,exceptedRst) values("值1",0,"vvvYes");
        //"update " + table + " set exceptedRst = ?,realRst = ?,isPass = ? where pageNum = ? and itemNum = ?;", new Object[]{}
        //"delete from " + table + " where pageNum = ? and itemNum = ?;", new Integer[]{pageNum, itemNum}
    }

    /**
     * 执行sql语句命令 实现增、删、改等功能
     * @param sql sql语句
     * @param params 参数
     */
    public void doSql(String sql,Object[] params){
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.execSQL(sql,params);
        db.close();
    }

    /**
     * 插入数据
     *
     * @param table
     * @param values
     * @return
     */
    public boolean insertData(String table, ContentValues values) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.insert(table, "nullColumn", values);
        db.close();
        return false;
    }

    //删除所有数据
    public void deleteAllDatas(String table) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.execSQL("delete from  " + table + " where 1=1;");
        db.close();
    }


    /**
     * 查找数据
     *
     * @return
     */
    public Cursor seleteDatas(String sql, String[] params) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql,params);//"select * from " + table + " where pageNum = ? and itemNum =?;"
        cursor.close();
        db.close();
        return cursor;//mark
    }

    /**
     * 取出所欲数据
     *
     * @return
     */
    public Cursor getAllDatas(String table) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from  " + table + " ;", null);
        db.close();
        return cursor;//mark
    }
}
