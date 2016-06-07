package com.cc.test.sqldemo.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

/**
 * Created by zhangyu on 2016-06-07 10:28.
 */
public class SqlHelper extends SQLiteOpenHelper {
    String createTableOrder;//建表命令

    public SqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String createTableOrder) {
        super(context, name, factory, version);
        this.createTableOrder = createTableOrder;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (TextUtils.isEmpty(createTableOrder))
            throw new IllegalArgumentException("SqlHelper createTableOrder建表命令为空");
        else
            db.execSQL(createTableOrder);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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
