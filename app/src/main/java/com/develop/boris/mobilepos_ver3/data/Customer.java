package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by user on 2017/10/31.
 */

public class Customer implements MySQLData {
  private int id;
  private String name;
  private String plate;
  private int phone;
  private int tel;
  private String createDate;
  private byte[] icon;

  // 從cursor取得資料內容
  @Override
  public void fromCursor(Cursor cursor) {
    this.id = cursor.getInt(cursor.getColumnIndex("c_id"));
    this.name = cursor.getString(cursor.getColumnIndex("c_name"));
    this.plate = cursor.getString(cursor.getColumnIndex("plate"));
    this.phone = cursor.getInt(cursor.getColumnIndex("c_phone"));
    this.tel = cursor.getInt(cursor.getColumnIndex("c_tel"));
    this.createDate = cursor.getString(cursor.getColumnIndex("c_create_date"));
  }

  //ContentValues用來紀錄數據組，可用ContentResolver處理數據
  @Override
  public ContentValues toContentValues() {
    ContentValues data = new ContentValues();
    data.put("c_name", this.name);
    data.put("plate", this.plate);
    data.put("c_phone", this.phone);
    data.put("c_tel", this.tel);
//    data.put("c_create_date", this.createDate);//讓DB自動生成
    return data;
  }

  @Override
  public String toString() {
    String str = String.format("Plate:%s", this.plate);
    return str;
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

  public String getPlate() {
    return plate;
  }

  public void setPlate(String plate) {
    this.plate = plate;
  }

  public int getPhone() {
    return phone;
  }

  public void setPhone(int phone) {
    this.phone = phone;
  }

  public int getTel() {
    return tel;
  }

  public void setTel(int tel) {
    this.tel = tel;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public byte[] getIcon() {
    return icon;
  }

  public void setIcon(byte[] icon) {
    this.icon = icon;
  }
}
