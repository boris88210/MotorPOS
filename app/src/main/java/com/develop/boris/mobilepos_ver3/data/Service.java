package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by user on 2017/12/14.
 */

public class Service implements MySQLData {

  private int id;
  private String name;
  private int serviceType;
  private boolean isPressed = false;

  @Override
  public void fromCursor(Cursor cursor) {
    this.id = cursor.getInt(cursor.getColumnIndex("service_id"));
    this.name = cursor.getString(cursor.getColumnIndex("service_name"));
    this.serviceType = cursor.getInt(cursor.getColumnIndex("service_type_id"));
  }

  @Override
  public ContentValues toContentValues() {
    ContentValues data = new ContentValues();
    data.put("service_name", this.name);
    data.put("service_type_id", this.serviceType);
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

  public int getServiceType() {
    return serviceType;
  }

  public void setServiceType(int serviceType) {
    this.serviceType = serviceType;
  }

  public boolean isPressed() {
    return isPressed;
  }

  public void setPressed(boolean pressed) {
    isPressed = pressed;
  }
}
