package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/14.
 */

public class OrderDAO extends DataDAO<Order> {
  public OrderDAO(Context ctx) {
    super(ctx);
  }

  @Override
  public ArrayList getAllData() {
    ArrayList<Order> list = new ArrayList<>();
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT * FROM orders";
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        Order data = new Order();
        data.fromCursor(cursor);
        list.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    return list;
  }

  @Override
  public ArrayList<Order> getDataByKeyword(String keyword) {
    ArrayList<Order> ret = new ArrayList<>();
    Log.i("SEARCH_RESULT", "" + ret.size());
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String query = "SELECT * FROM orders WHERE o_id like '%" + keyword + "%'";
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      do {
        Order data = new Order();
        data.fromCursor(cursor);
        ret.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    Log.i("SEARCH_RESULT", "" + ret.size());
    return ret;
  }

  @Override
  public Order getDataById(int id) {
    Order ret = null;
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT * FROM orders WHERE o_id =" + id;
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      Order data = new Order();
      data.fromCursor(cursor);
      ret = data;
    }
    db.close();
    return ret;
  }

  @Override
  public void insert(Order data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    // 資料先存到ContentValues裡面
    db.insert("orders", null, data.toContentValues());
    db.close();
  }

  @Override
  public void update(Order data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.update("orders", data.toContentValues(), "o_id=?", new String[]{data.getId() + ""});
    db.close();
  }

  @Override
  public void delete(int id) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.delete("orders", "o_id=?", new String[]{id + ""});
    db.close();
  }
}
