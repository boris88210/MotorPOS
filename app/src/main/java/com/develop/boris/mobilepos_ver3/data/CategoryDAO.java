package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/14.
 */

public class CategoryDAO extends DataDAO<Category> {

  public CategoryDAO(Context ctx) {
    super(ctx);
  }

  @Override
  public ArrayList getAllData() {
    ArrayList<Category> list = new ArrayList<>();
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT * FROM categories";
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        Category data = new Category();
        data.fromCursor(cursor);
        list.add(data);
      } while (cursor.moveToNext());
    }
    return list;
  }

  @Override
  public ArrayList<Category> getDataByKeyword(String name) {
    return null;
  }

  @Override
  public Category getDataById(int id) {
    return null;
  }

  @Override
  public void insert(Category data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.insert("categories", null, data.toContentValues());
    db.close();
  }

  @Override
  public void update(Category data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.update("categories", data.toContentValues(), "cate_id=?", new String[]{data.getId() + ""});
    db.close();
  }

  @Override
  public void delete(int id) {

  }
}
