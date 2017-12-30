package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/4.
 */

public class SaleDAO extends DataDAO<Sale> {
    
    public SaleDAO(Context ctx) {
        super(ctx);
    }
    
    @Override
    public ArrayList<Sale> getAllData() {
        ArrayList<Sale> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String sql = "SELECT * FROM sales ORDER BY is_complete ,sa_date DESC";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Sale data = new Sale();
                data.fromCursor(cursor, 0);
                list.add(data);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }
    
    @Override
    public ArrayList<Sale> getDataByKeyword(String keyword) {
        return null;
    }
    
    @Override
    public Sale getDataById(int id) {
        Sale ret = null;
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String query = "SELECT sa_id," +
                "sa_date," +
                "salesman," +
                "sa_c_id, " +
                "customers.c_name AS 'name', " +
                "customers.plate AS 'plate', " +
                "Consume_type.ct_name AS 'type', " +
                "consume_type, " +
                "total_price, " +
                "mileage " +
                "FROM sales " +
                "JOIN consume_type " +
                "ON consume_type=ct_id " +
                "JOIN customers " +
                "ON sa_c_id=c_id " +
                "WHERE sa_id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Sale data = new Sale();
            data.fromCursor(cursor);
            ret = data;
        }
        db.close();
        return ret;
    }


    public Sale getDataById(long id) {
        Sale ret = null;
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String query = "SELECT sa_id," +
                "sa_date," +
                "salesman," +
                "sa_c_id, " +
                "customers.c_name AS 'name', " +
                "customers.plate AS 'plate', " +
                "Consume_type.ct_name AS 'type', " +
                "consume_type, " +
                "total_price, " +
                "mileage " +
                "FROM sales " +
                "JOIN consume_type " +
                "ON consume_type=ct_id " +
                "JOIN customers " +
                "ON sa_c_id=c_id " +
                "WHERE sa_id=" + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Sale data = new Sale();
            data.fromCursor(cursor);
            ret = data;
        }
        db.close();
        return ret;
    }
    
    @Override
    public void insert(Sale data) {
        SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
        db.insert("sales", null, data.toContentValues());
        db.close();
    }
    
    @Override
    public void update(Sale data) {
    
    }
    
    @Override
    public void delete(int id) {
    
    }
    
    public ArrayList<Sale> getDataByCustomerId(int id) {
        ArrayList<Sale> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String sql = "SELECT\n" +
                "\t* \n" +
                "FROM\n" +
                "\tsales\n" +
                "\tJOIN Consume_type \n" +
                "\tON sales.consume_type = Consume_type.ct_id \n" +
                "WHERE\n" +
                "\tsa_c_id=" + id + "\n" +
                "ORDER BY\n" +
                "\tsa_date DESC";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Sale data = new Sale();
                data.fromCursorForHistory(cursor);
                list.add(data);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }
    
    public Sale getLastOneByDate(String date) {
        Sale ret = null;
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String query = "SELECT * FROM sales WHERE sa_id LIKE '" + date + "%' ORDER BY sa_id DESC LIMIT 1;";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Sale data = new Sale();
            data.fromCursor(cursor, 0);
            ret = data;
        }
        db.close();
        return ret;
    }
    
    
}
