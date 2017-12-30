package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/14.
 */

public class ContactDAO extends DataDAO<Contact> {
  public ContactDAO(Context ctx) {
    super(ctx);
  }

  @Override
  public ArrayList getAllData() {
    ArrayList<Contact> list = new ArrayList<>();
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT * FROM contscts";
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        Contact data = new Contact();
        data.fromCursor(cursor);
        list.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    return list;
  }

  @Override
  public ArrayList<Contact> getDataByKeyword(String name) {
    return null;
  }

  @Override
  public Contact getDataById(int id) {
    return null;
  }

  @Override
  public void insert(Contact data) {

  }

  @Override
  public void update(Contact data) {

  }

  @Override
  public void delete(int id) {

  }
}
