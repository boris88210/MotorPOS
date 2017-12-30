package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/14.
 */

public class SupplierDAO extends DataDAO<Supplier> {

  public SupplierDAO(Context ctx) {
    super(ctx);
  }

  @Override
  public ArrayList getAllData() {
    ArrayList<Supplier> list = new ArrayList<>();
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT * FROM suppliers";
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        Supplier data = new Supplier();
        data.fromCursor(cursor);
        list.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    return list;
  }

  @Override
  public ArrayList<Supplier> getDataByKeyword(String keyword) {
    ArrayList<Supplier> ret = new ArrayList<>();
    Log.i("SEARCH_RESULT", "" + ret.size());
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String query = "SELECT * FROM suppliers WHERE s_name like '%" + keyword + "%' OR s_tel like '%"
            + keyword + "%' OR s_phone like '%" + keyword + "%'";
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      do {
        Supplier data = new Supplier();
        data.fromCursor(cursor);
        ret.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    Log.i("SEARCH_RESULT", "" + ret.size());
    return ret;
  }

  @Override
  public Supplier getDataById(int id) {
    Supplier ret = null;
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT * FROM suppliers WHERE s_id =" + id;
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      Supplier data = new Supplier();
      data.fromCursor(cursor);
      ret = data;
    }
    db.close();
    return ret;
  }

  @Override
  public void insert(Supplier data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    // 資料先存到ContentValues裡面
    db.insert("suppliers", null, data.toContentValues());
    db.close();
  }

  @Override
  public void update(Supplier data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.update("suppliers", data.toContentValues(), "s_id=?", new String[]{data.getId() + ""});
    db.close();
  }

  @Override
  public void delete(int id) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.delete("suppliers", "s_id=?", new String[]{id + ""});
    db.close();
  }
}
