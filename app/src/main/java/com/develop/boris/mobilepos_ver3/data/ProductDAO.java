package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/14.
 */

public class ProductDAO extends DataDAO<Product> {

  public ProductDAO(Context ctx) {
    super(ctx);
  }

  @Override
  public ArrayList<Product> getAllData() {
    ArrayList<Product> list = new ArrayList<>();
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT * FROM products JOIN categories ON p_category = cate_id ORDER BY p_category, p_name";
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        Product data = new Product();
        data.fromCursor(cursor);
        list.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    return list;
  }

  @Override
  public ArrayList<Product> getDataByKeyword(String name) {
    ArrayList<Product> ret = new ArrayList<>();
    Log.i("SEARCH_RESULT", "" + ret.size());
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String query = "SELECT * FROM products WHERE p_name like '%" + name + "%';";
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      do {
        Product data = new Product();
        data.fromCursor(cursor);
        ret.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    Log.i("SEARCH_RESULT", "" + ret.size());
    return ret;
  }

  @Override
  public Product getDataById(int id) {
    Product ret = null;
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String query = "SELECT * FROM products JOIN categories ON p_category = cate_id WHERE p_id = " + id;
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      Product data = new Product();
      data.fromCursor(cursor);
      ret = data;
    }
    db.close();
    return ret;
  }

  @Override
  public void insert(Product data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.insert("products", null, data.toContentValues());
    db.close();
  }

  @Override
  public void update(Product data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.update("products", data.toContentValues(), "p_id=?", new String[]{data.getId() + ""});
    db.close();
  }

  @Override
  public void delete(int id) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.delete("products", "p_id=?", new String[]{id + ""});
    db.close();
  }

  //用條碼搜尋商品
  public ArrayList<Product> getDataByBarcode(long barcode) {
    ArrayList<Product> ret = new ArrayList<>();

    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String query = "SELECT * FROM products WHERE barcode = " + barcode;
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      do {
        Product data = new Product();
        data.fromCursorOrigin(cursor);
        ret.add(data);
      } while (cursor.moveToNext());
    }
    db.close();


    Log.i("SEARCH_RESULT", "" + ret.size());
    return ret;
  }


  // 庫存不足商品
  public ArrayList<Product> getAlertData() {
    ArrayList<Product> list = new ArrayList<>();
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT * FROM	products WHERE p_qty < p_safe_qty";
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        Product data = new Product();
        data.fromCursorOrigin(cursor);
        list.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    return list;
  }
}
