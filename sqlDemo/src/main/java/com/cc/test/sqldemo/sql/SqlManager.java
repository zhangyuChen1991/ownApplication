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

    // 删除数据
    public void deleteDatas(String table, int pageNum, int itemNum) {   //mark
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.execSQL("delete from " + table + " where pageNum = ? and itemNum = ?;", new Integer[]{pageNum, itemNum});
        db.close();
    }

    //删除所有数据
    public void deleteAllDatas(String table) {
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.execSQL("delete from  " + table + " where 1=1;");
        db.close();
    }

    // 修改数据
    public void updateDatas(String table) {  //mark
        SQLiteDatabase db = sqlHelper.getWritableDatabase();

        db.execSQL("update " + table + " set exceptedRst = ?,realRst = ?,isPass = ? where pageNum = ? and itemNum = ?;", new Object[]{});
        db.close();
    }

    /**
     * 查找数据
     *
     * @param pageNum
     * @param itemNum
     * @return
     */
    public List<Object> seleteDatas(String table, int pageNum, int itemNum) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + table + " where pageNum = ? and itemNum =?;", new String[]{String.valueOf(pageNum), String.valueOf(itemNum)});
        while (cursor.moveToNext()) {
            cursor.getInt(cursor.getColumnIndex("pageNum"));
            cursor.getInt(cursor.getColumnIndex("itemNum"));
            cursor.getInt(cursor.getColumnIndex("isPass"));
            cursor.getString(cursor.getColumnIndex("exceptedRst"));
            cursor.getString(cursor.getColumnIndex("realRst"));

        }
        cursor.close();
        db.close();
        return null;//mark
    }

    /**
     * 取出所欲数据
     *
     * @return
     */
    public List<Object> getAllDatas(String table) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from  " + table + " ;", null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                cursor.getInt(cursor.getColumnIndex("pageNum"));
                cursor.getInt(cursor.getColumnIndex("itemNum"));
                cursor.getInt(cursor.getColumnIndex("isPass"));
                cursor.getString(cursor.getColumnIndex("exceptedRst"));
                cursor.getString(cursor.getColumnIndex("realRst"));
            }
            cursor.close();
            db.close();
        }

        return null;//mark
    }
}
