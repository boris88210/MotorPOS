package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by user on 2017/11/14.
 */

public class Category implements MySQLData {
    private int id;
    private String name;
    
    @Override
    public void fromCursor(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex("cate_id"));
        this.name = cursor.getString(cursor.getColumnIndex("cate_name"));
    }
    
    @Override
    public ContentValues toContentValues() {
        ContentValues data = new ContentValues();
        data.put("cate_name", this.name);
        return data;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
