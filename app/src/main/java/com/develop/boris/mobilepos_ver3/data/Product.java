package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by user on 2017/11/11.
 */

public class Product implements MySQLData {
  private int id;
  private String name;
  private String model;
  private long barcode;
  private String warehouse;
  private String category;
  private int categoryId;
  private int qty;
  private int safeQty;
  private String unit;
  private int unitPrice;
  private String picPath;
  private String createDate;
  private byte[] icon;

  // flag for adapter
  private boolean checkedStatus;

  public void fromCursorOrigin(Cursor cursor){
    this.id = cursor.getInt(cursor.getColumnIndex("p_id"));
    this.name = cursor.getString(cursor.getColumnIndex("p_name"));
    this.model = cursor.getString(cursor.getColumnIndex("p_model"));
    this.barcode = cursor.getLong(cursor.getColumnIndex("barcode"));
    this.unit = cursor.getString(cursor.getColumnIndex("p_unit"));
    this.unitPrice = cursor.getInt(cursor.getColumnIndex("p_price"));
    this.warehouse = cursor.getString(cursor.getColumnIndex("p_warehouse"));
    this.categoryId = cursor.getInt(cursor.getColumnIndex("p_category"));
    this.qty = cursor.getInt(cursor.getColumnIndex("p_qty"));
    this.picPath = cursor.getString(cursor.getColumnIndex("pic_path"));
    this.createDate = cursor.getString(cursor.getColumnIndex("p_create_date"));
    this.safeQty = cursor.getInt(cursor.getColumnIndex("p_safe_qty"));
  }
  
  
  @Override
  public void fromCursor(Cursor cursor) {
//    Log.i("Cursor", cursor.getColumnName(12));
    this.id = cursor.getInt(cursor.getColumnIndex("p_id"));
    this.name = cursor.getString(cursor.getColumnIndex("p_name"));
    this.model = cursor.getString(cursor.getColumnIndex("p_model"));
    this.barcode = cursor.getLong(cursor.getColumnIndex("barcode"));
    this.unit = cursor.getString(cursor.getColumnIndex("p_unit"));
    this.unitPrice = cursor.getInt(cursor.getColumnIndex("p_price"));
    this.warehouse = cursor.getString(cursor.getColumnIndex("p_warehouse"));
    this.category = cursor.getString(cursor.getColumnIndex("cate_name"));
    this.categoryId = cursor.getInt(cursor.getColumnIndex("p_category"));
    this.qty = cursor.getInt(cursor.getColumnIndex("p_qty"));
    this.picPath = cursor.getString(cursor.getColumnIndex("pic_path"));
    this.createDate = cursor.getString(cursor.getColumnIndex("p_create_date"));
    this.safeQty = cursor.getInt(cursor.getColumnIndex("p_safe_qty"));
//    this.icon = cursor.getBlob(cursor.getColumnIndex("p_icon"));
  }

  @Override
  public ContentValues toContentValues() {
    ContentValues data = new ContentValues();
    data.put("p_name", this.name);
    data.put("p_model", this.model);
    data.put("barcode", this.barcode);
    data.put("p_warehouse", this.warehouse);
    data.put("p_qty", this.qty);
    data.put("p_unit", this.unit);
    data.put("p_price", this.unitPrice);
    data.put("p_category", this.categoryId);
    data.put("p_safe_qty", this.safeQty);
    // 建立時間應為建立資料的時間，後續再新增欄位記錄前次修改時間
//    data.put("P_create_date", this.createDate);//建立時間讓資料庫自動生成
//    data.put("p_icon", this.icon);
    return data;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public long getBarcode() {
    return barcode;
  }

  public void setBarcode(long barcode) {
    this.barcode = barcode;
  }

  public String getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(String warehouse) {
    this.warehouse = warehouse;
  }

  public String getCategory() {
    return category;
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

  public int getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(int unitPrice) {
    this.unitPrice = unitPrice;
  }

  public String getPicPath() {
    return picPath;
  }

  public void setPicPath(String picPath) {
    this.picPath = picPath;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public boolean getCheckedStatus() {
    return checkedStatus;
  }

  public void setCheckedStatus(boolean checkedStatus) {
    this.checkedStatus = checkedStatus;
  }

  public byte[] getIcon() {
    return icon;
  }

  public void setIcon(byte[] icon) {
    this.icon = icon;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public int getSafeQty() {
    return safeQty;
  }

  public void setSafeQty(int safeQty) {
    this.safeQty = safeQty;
  }
}
