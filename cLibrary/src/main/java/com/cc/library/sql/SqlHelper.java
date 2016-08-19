package com.cc.library.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by zhangyu on 2016-06-07 10:28.
 */
public class SqlHelper extends SQLiteOpenHelper {
    private String createTableOrder;//建表命令 //"create table tableName (id integer primary key autoincrement,pageNum int,itemNum int,isPass int,notPassInfo varchar(300))"
    private  String dbName;     //数据库名
    private  Context context;

    public SqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
        this.dbName = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (TextUtils.isEmpty(createTableOrder))
            Log.e("SqlHelper","SqlHelper createTableOrder建表命令为空");
        else
            db.execSQL(createTableOrder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 删除数据库
     * @return
     */
    public boolean deleteDatabase() {
        return context.deleteDatabase(dbName);
    }

    /**
     * 设置建表命令
     *
     * @param createTableOrder
     */
    public void setCreateTableOrder(String createTableOrder) {
        this.createTableOrder = createTableOrder;
    }
}
