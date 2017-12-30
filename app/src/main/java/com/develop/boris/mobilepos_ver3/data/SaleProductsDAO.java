package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/6.
 */

public class SaleProductsDAO extends DataDAO<SaleProducts> {

  public SaleProductsDAO(Context ctx) {
    super(ctx);
  }

  // 取得單筆交易的所有商品資料
  public ArrayList<SaleProducts> getAllProductsInTheSale(long id) {
    ArrayList<SaleProducts> list = new ArrayList<>();
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT products.p_name AS 'product name', " +
            "Sale_products.p_qty AS 'Qty', " +
            "products.p_unit AS 'unit', " +
            "Sale_products.p_price AS 'price' " +
            "FROM Sale_products " +
            "JOIN products " +
            "ON Sale_products.p_id=products.p_id " +
            "WHERE sa_id=" + id;

    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        SaleProducts data = new SaleProducts();
        data.fromCursor(cursor);
        list.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    return list;
  }

  @Override
  public ArrayList<SaleProducts> getAllData() {
    return null;
  }

  @Override
  public ArrayList<SaleProducts> getDataByKeyword(String keyword) {
    return null;
  }

  @Override
  public SaleProducts getDataById(int id) {
    return null;
  }

  @Override
  public void insert(SaleProducts data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.insert("Sale_products", null, data.toContentValues());
    db.close();
  }

  @Override
  public void update(SaleProducts data) {

  }

  @Override
  public void delete(int id) {

  }
}
