package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by user on 2017/11/14.
 */

public class Order implements MySQLData {
  private int id;
  private String createDate;
  private String eta;
  private String completeDate;

  @Override
  public void fromCursor(Cursor cursor) {
    this.id = cursor.getInt(cursor.getColumnIndex("o_id"));
    this.createDate = cursor.getString(cursor.getColumnIndex("o_create_date"));
    this.eta = cursor.getString(cursor.getColumnIndex("o_eta"));
    this.completeDate = cursor.getString(cursor.getColumnIndex("o_complete_date"));
  }

  @Override
  public ContentValues toContentValues() {
    ContentValues data = new ContentValues();
    data.put("o_id", this.id);
    data.put("o_create_date", this.createDate);
    data.put("o_eta", this.eta);
    data.put("o_complete_date", this.completeDate);
    return data;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public String getEta() {
    return eta;
  }

  public void setEta(String eta) {
    this.eta = eta;
  }

  public String getCompleteDate() {
    return completeDate;
  }

  public void setCompleteDate(String completeDate) {
    this.completeDate = completeDate;
  }

  @Override
  public String getName() {
    return null;
  }
}
