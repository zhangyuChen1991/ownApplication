package com.cc.test.sqldemo.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
     *
     * @param sql sql语句
     */
    public boolean doSql(String sql) {
        try {
            SQLiteDatabase db = sqlHelper.getWritableDatabase();
            db.execSQL(sql);
            db.close();
            //insert into table ("列名1",itemNum,exceptedRst) values("值1",0,"vvvYes");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 执行sql语句命令 实现增、删、改等功能
     *
     * @param sql    sql语句
     * @param params 参数
     */
    public boolean doSql(String sql, Object[] params) {

        try {
            SQLiteDatabase db = sqlHelper.getWritableDatabase();
            db.execSQL(sql, params);
            db.close();
            //"update " + table + " set exceptedRst = ?,realRst = ?,isPass = ? where pageNum = ? and itemNum = ?;", new Object[]{}
            //"delete from " + table + " where pageNum = ? and itemNum = ?;", new Integer[]{pageNum, itemNum}
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 插入数据
     *
     * @param table
     * @param values
     * @return
     */
    public boolean insertData(String table, ContentValues values) {
        boolean ret;
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        long result = db.insert(table, "nullColumn", values);
        db.close();
        return result == -1 ? false : true;
    }



    /**
     *  删除所有数据
     * @param table 表名
     * @return
     */
    public boolean deleteAllDatas(String table) {
        try {
            SQLiteDatabase db = sqlHelper.getWritableDatabase();
            db.execSQL("delete from  " + table + " where 1=1;");
            db.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查找数据
     *
     * @return
     */
    public Cursor seleteDatas(String sql, String[] params) {
        Cursor cursor = null;//"select * from " + table + " where pageNum = ? and itemNum =?;"
        try {
            SQLiteDatabase db = sqlHelper.getReadableDatabase();

            cursor = db.rawQuery(sql, params);
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    /**
     * 取出所有数据
     *
     * @return
     */
    public Cursor getAllDatas(String table) {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = sqlHelper.getReadableDatabase();
            cursor = db.rawQuery("select * from  " + table + " ;", null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }
}
