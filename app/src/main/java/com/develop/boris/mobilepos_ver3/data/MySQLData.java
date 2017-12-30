package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by user on 2017/11/12.
 */

public interface MySQLData {

  public void fromCursor(Cursor cursor);

  public ContentValues toContentValues();

  public String getName();
}
