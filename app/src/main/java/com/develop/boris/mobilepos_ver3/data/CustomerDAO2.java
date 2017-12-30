package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/8.
 */

public class CustomerDAO2 extends DataDAO<Customer> {
//　變數寫在父類別
//  private Context ctx;
//  private static final String DB_NAME = "posDB.db";
    
    // 用建構子與畫面做關聯
    public CustomerDAO2(Context ctx) {
        super(ctx);
//    this.ctx = ctx;
    }
    
    
    @Override
    public ArrayList<Customer> getAllData() {
        ArrayList<Customer> list = new ArrayList<>();
        
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String sql = "SELECT * FROM customers ORDER BY plate";
        
        Cursor cursor = db.rawQuery(sql, null);
        
        if (cursor.moveToFirst()) {
            do {
                Customer data = new Customer();
                data.fromCursor(cursor);
                list.add(data);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }
    
    @Override
    public ArrayList<Customer> getDataByKeyword(String keyword) {
        ArrayList<Customer> ret = new ArrayList<>();
        Log.i("SEARCH_RESULT", "" + ret.size());
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String query = "SELECT * FROM customers WHERE c_name like '%" + keyword + "%' OR plate like '%" + keyword + "%'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Customer data = new Customer();
                data.fromCursor(cursor);
                ret.add(data);
            } while (cursor.moveToNext());
        }
        db.close();
        Log.i("SEARCH_RESULT", "" + ret.size());
        return ret;
    }
    
    @Override
    public Customer getDataById(int id) {
        Customer ret = null;
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String sql = "SELECT * FROM customers WHERE c_id =" + id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            Customer data = new Customer();
            data.fromCursor(cursor);
            ret = data;
        }
        db.close();
        return ret;
    }
    
    @Override
    public void insert(Customer data) {
        SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
        // 資料先存到ContentValues裡面
        db.insert("customers", null, data.toContentValues());
        db.close();
    }
    
    @Override
    public void update(Customer data) {
        SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
        db.update("customers", data.toContentValues(), "c_id=?", new String[]{data.getId() + ""});
        db.close();
    }
    
    @Override
    public void delete(int id) {
        SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
        db.delete("customers", "c_id=?", new String[]{id + ""});
        db.close();
    }
    
    public Customer getLastOne() {
        Customer ret = null;
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String query = "SELECT * FROM customers ORDER BY c_id DESC LIMIT 1;";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Customer data = new Customer();
            data.fromCursor(cursor);
            ret = data;
        }
        db.close();
        return ret;
    }
}
