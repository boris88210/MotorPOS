package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by user on 2017/11/14.
 */

public class Contact implements MySQLData {
  private int id;
  private int tel;
  private int phone;
  private String name;
  private String line;
  private String createDate;

  @Override
  public void fromCursor(Cursor cursor) {
    this.id = cursor.getInt(cursor.getColumnIndex("s_c_id"));
    this.tel = cursor.getInt(cursor.getColumnIndex("s_c_tel"));
    this.phone = cursor.getInt(cursor.getColumnIndex("s_c_phone"));
    this.name = cursor.getString(cursor.getColumnIndex("s_c_name"));
    this.line = cursor.getString(cursor.getColumnIndex("s_c_line"));
    this.createDate = cursor.getString(cursor.getColumnIndex("s_c_create_date"));
  }

  @Override
  public ContentValues toContentValues() {
    ContentValues data = new ContentValues();
    data.put("s_c_name", this.name);
    data.put("s_c_tel", this.tel);
    data.put("s_c_phone", this.phone);
    data.put("s_c_line", this.line);
    data.put("s_c_create_date", this.createDate);
    return data;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getTel() {
    return tel;
  }

  public void setTel(int tel) {
    this.tel = tel;
  }

  public int getPhone() {
    return phone;
  }

  public void setPhone(int phone) {
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }
}
