package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.widget.LinearLayout;

/**
 * Created by user on 2017/12/6.
 */

public class SaleProducts implements MySQLData {
    
    private long id;
    private int productId;
    private String name;
    private int qty;
    private String unit;
    private int price;
    private LinearLayout layout;
    
    
    @Override
    public void fromCursor(Cursor cursor) {
        this.name = cursor.getString(cursor.getColumnIndex("product name"));
        this.qty = cursor.getInt(cursor.getColumnIndex("Qty"));
        this.unit = cursor.getString(cursor.getColumnIndex("unit"));
        this.price = cursor.getInt(cursor.getColumnIndex("price"));
    }
    
    @Override
    public ContentValues toContentValues() {
        ContentValues data = new ContentValues();

        data.put("sa_id", this.id);
        data.put("p_id", this.productId);
        data.put("p_qty", this.qty);
        data.put("p_price", this.price);
        return data;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        SaleProducts sp = (SaleProducts) obj;
        return this.name.equals(sp.name);
    }
    
    @Override
    public int hashCode() {
        char[] arrray = this.name.toCharArray();
        int sum = 0;
        int j = 1;
        for (int i : arrray) {
            sum += i * j;
        }
        return sum;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getQty() {
        return qty;
    }
    
    public void setQty(int qty) {
        this.qty = qty;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }
}
