package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/13.
 */

public class ConsumeTypeDAO extends DataDAO<ConsumeType> {
    public ConsumeTypeDAO(Context ctx) {
        super(ctx);
    }
    
    @Override
    public ArrayList<ConsumeType> getAllData() {
        ArrayList<ConsumeType> list = new ArrayList<>();
        SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
        String sql = "SELECT * FROM Consume_type";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                ConsumeType data = new ConsumeType();
                data.fromCursor(cursor);
                list.add(data);
            } while (cursor.moveToNext());
        }
        return list;
    }
    
    @Override
    public ArrayList<ConsumeType> getDataByKeyword(String keyword) {
        return null;
    }
    
    @Override
    public ConsumeType getDataById(int id) {
        return null;
    }
    
    @Override
    public void insert(ConsumeType data) {
        SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
        db.insert("Consume_type", null, data.toContentValues());
        db.close();
    }
    
    @Override
    public void update(ConsumeType data) {
        SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
        db.update("Consume_type", data.toContentValues(), "ct_id=?", new String[]{data.getId() + ""});
        db.close();
    }
    
    @Override
    public void delete(int id) {
    
    }


}
