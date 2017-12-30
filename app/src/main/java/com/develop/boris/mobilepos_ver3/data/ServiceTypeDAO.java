package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/14.
 */

public class ServiceTypeDAO extends DataDAO<ServiceType> {
    
    public ServiceTypeDAO(Context ctx) {
        super(ctx);
    }
    
    @Override
    public ArrayList<ServiceType> getAllData() {
        ArrayList<ServiceType> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String sql = "SELECT * FROM Service_type";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                ServiceType data = new ServiceType();
                data.fromCursor(cursor);
                list.add(data);
            } while (cursor.moveToNext());
        }
        db.close();
        return list;
    }
    
    @Override
    public ArrayList<ServiceType> getDataByKeyword(String keyword) {
        return null;
    }
    
    @Override
    public ServiceType getDataById(int id) {
        return null;
    }
    
    @Override
    public void insert(ServiceType data) {
        SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
        db.insert("Service_type", null, data.toContentValues());
        db.close();
    }
    
    @Override
    public void update(ServiceType data) {
        SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
        db.update("Service_type", data.toContentValues(), "service_type_id=?", new String[]{data.getId() + ""});
        db.close();
    }
    
    @Override
    public void delete(int id) {
    
    }
    
    
    
}
