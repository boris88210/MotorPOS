package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/8.
 */

public abstract class DataDAO<T> {
  protected Context ctx;
  private static final String DB_NAME = "posDB.db";

  public DataDAO(Context ctx) {
    this.ctx = ctx;
  }

  public abstract ArrayList<T> getAllData();

  public abstract ArrayList<T> getDataByKeyword(String keyword);

  public abstract T getDataById(int id);

  public abstract void insert(T data);

  public abstract void update(T data);

  public abstract void delete(int id);

}
