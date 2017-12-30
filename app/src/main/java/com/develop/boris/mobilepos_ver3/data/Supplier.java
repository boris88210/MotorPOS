package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by user on 2017/11/14.
 */

public class Supplier implements MySQLData {
  private int id;
  private String name;
  private int tel;
  private int phone;
  private int fax;
  private String mail;
  private String address;
  private String createDate;
  private byte[] icon;

  @Override
  public void fromCursor(Cursor cursor) {
    this.id = cursor.getInt(cursor.getColumnIndex("s_id"));
    this.tel = cursor.getInt(cursor.getColumnIndex("s_tel"));
    this.phone = cursor.getInt(cursor.getColumnIndex("s_phone"));
    this.fax = cursor.getInt(cursor.getColumnIndex("s_fax"));
    this.name = cursor.getString(cursor.getColumnIndex("s_name"));
    this.mail = cursor.getString(cursor.getColumnIndex("s_mail"));
    this.address = cursor.getString(cursor.getColumnIndex("s_address"));
    this.createDate = cursor.getString(cursor.getColumnIndex("s_create_date"));
  }

  @Override
  public ContentValues toContentValues() {
    ContentValues data = new ContentValues();
    data.put("s_tel", this.tel);
    data.put("s_phone", this.phone);
    data.put("s_fax", this.fax);
    data.put("s_name", this.name);
    data.put("s_mail", this.mail);
    data.put("s_address", this.address);
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

  public int getFax() {
    return fax;
  }

  public void setFax(int fax) {
    this.fax = fax;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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
