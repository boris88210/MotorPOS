package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by user on 2017/12/4.
 */

public class Sale implements MySQLData {
    
    private long id;
    private String date;
    private int salesman;
    private int customer;
    private String customerName;
    private int consumeType;
    private String consumeTypeName;
    private int totalPrice;
    private int mileage;
    private int isComplete;
    private byte[] icon;

    public void fromCursorForHistory(Cursor cursor) {// getAll，無對應資料名稱
        this.id = cursor.getLong(cursor.getColumnIndex("sa_id"));
        this.date = cursor.getString(cursor.getColumnIndex("sa_date"));
        this.salesman = cursor.getInt(cursor.getColumnIndex("salesman"));
        this.customer = cursor.getInt(cursor.getColumnIndex("sa_c_id"));
        this.consumeType = cursor.getInt(cursor.getColumnIndex("consume_type"));
        this.consumeTypeName = cursor.getString(cursor.getColumnIndex("ct_name"));
        this.totalPrice = cursor.getInt(cursor.getColumnIndex("total_price"));
        this.mileage = cursor.getInt(cursor.getColumnIndex("mileage"));
        this.isComplete = cursor.getInt(cursor.getColumnIndex("is_complete"));
    }

    public void fromCursor(Cursor cursor, int i) {// getAll，無對應資料名稱
        this.id = cursor.getLong(cursor.getColumnIndex("sa_id"));
        this.date = cursor.getString(cursor.getColumnIndex("sa_date"));
        this.salesman = cursor.getInt(cursor.getColumnIndex("salesman"));
        this.customer = cursor.getInt(cursor.getColumnIndex("sa_c_id"));
        this.consumeType = cursor.getInt(cursor.getColumnIndex("consume_type"));
        this.totalPrice = cursor.getInt(cursor.getColumnIndex("total_price"));
        this.mileage = cursor.getInt(cursor.getColumnIndex("mileage"));
        this.isComplete = cursor.getInt(cursor.getColumnIndex("is_complete"));
    }
    
    @Override
    public void fromCursor(Cursor cursor) {//getById，有對應資料名稱
        this.id = cursor.getLong(cursor.getColumnIndex("sa_id"));
        this.date = cursor.getString(cursor.getColumnIndex("sa_date"));
        this.salesman = cursor.getInt(cursor.getColumnIndex("salesman"));
        this.customer = cursor.getInt(cursor.getColumnIndex("sa_c_id"));
        String customerName = cursor.getString(cursor.getColumnIndex("name"));
        String plate = cursor.getString(cursor.getColumnIndex("plate"));
        this.customerName = customerName + " " + plate;
        this.consumeType = cursor.getInt(cursor.getColumnIndex("consume_type"));
        this.consumeTypeName = cursor.getString(cursor.getColumnIndex("type"));
        this.totalPrice = cursor.getInt(cursor.getColumnIndex("total_price"));
    }
    
    @Override
    public ContentValues toContentValues() {
        ContentValues data = new ContentValues();
        data.put("sa_id",this.id);
//    data.put("sa_date", this.date); // 日期讓自料庫自行生成
//        data.put("salesman", this.salesman);
        data.put("sa_c_id", this.customer);
        data.put("consume_type", this.consumeType);
        data.put("total_price", this.totalPrice);
        data.put("mileage", this.mileage);
        return data;
    }
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public int getSalesman() {
        return salesman;
    }
    
    public void setSalesman(int salesman) {
        this.salesman = salesman;
    }
    
    public int getCustomer() {
        return customer;
    }
    
    public void setCustomer(int customer) {
        this.customer = customer;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public int getConsumeType() {
        return consumeType;
    }
    
    public void setConsumeType(int consumeType) {
        this.consumeType = consumeType;
    }
    
    public String getConsumeTypeName() {
        return consumeTypeName;
    }
    
    public int getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public int getMileage() {
        return mileage;
    }
    
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
    
    public int getIsComplete() {
        return isComplete;
    }
    
    public void setIsComplete(int isComplete) {
        this.isComplete = isComplete;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    @Override
    public String getName() {
        return null;
    }
}
