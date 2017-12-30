package com.develop.boris.mobilepos_ver3.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/14.
 */

public class ServiceDAO extends DataDAO<Service> {

  public ServiceDAO(Context ctx) {
    super(ctx);
  }

  @Override
  public ArrayList<Service> getAllData() {
    ArrayList<Service> list = new ArrayList<>();
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT * FROM Services";
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        Service data = new Service();
        data.fromCursor(cursor);
        list.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    return list;
  }

  @Override
  public ArrayList<Service> getDataByKeyword(String keyword) {
    return null;
  }

  @Override
  public Service getDataById(int id) {
    Service ret = null;
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String query = "SELECT * FROM Services WHERE service_id=" + id;
    Cursor cursor = db.rawQuery(query, null);
    if (cursor.moveToFirst()) {
      Service data = new Service();
      data.fromCursor(cursor);
      ret = data;
    }
    db.close();
    return ret;
  }

  @Override
  public void insert(Service data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.insert("Services", null, data.toContentValues());
    db.close();
  }

  @Override
  public void update(Service data) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.update("Services", data.toContentValues(), "service_id=?", new String[]{data.getId() + ""});
    db.close();
  }

  @Override
  public void delete(int id) {

  }

  public ArrayList<Service> getDataByTypeId(int id) {
    ArrayList<Service> list = new ArrayList<>();
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT * FROM Services WHERE service_type_id=" + id;
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        Service data = new Service();
        data.fromCursor(cursor);
        list.add(data);
      } while (cursor.moveToNext());
    }
    db.close();
    return list;
  }

  public ArrayList<Integer> getProductIdInService(int servieId) {
    ArrayList<Integer> list = new ArrayList<>();
    SQLiteDatabase db = DatabaseAccesser.getInstance().openReadDatabase(ctx);
    String sql = "SELECT product_id FROM Service_products WHERE service_id=" + servieId;
    Cursor cursor = db.rawQuery(sql, null);
    if (cursor.moveToFirst()) {
      do {
        Integer i = cursor.getInt(cursor.getColumnIndex("product_id"));
        list.add(i);
      } while (cursor.moveToNext());
    }
    db.close();
    return list;
  }

  public void insertServiceProducts(int serviceId, int productId) {
    ContentValues data = new ContentValues();
    data.put("service_id", serviceId);
    data.put("product_id", productId);
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.insert("Service_products", null, data);
    db.close();

  }

  public void deleteServiceProducts(int serviceId, int productId) {
    SQLiteDatabase db = DatabaseAccesser.getInstance().openWriteDatabase(ctx);
    db.delete("Service_products", "service_id=? AND product_id=?", new String[]{serviceId + "", productId + ""});
    db.close();
  }

}

