package com.develop.boris.mobilepos_ver3.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseAccesser {
  private static DatabaseAccesser instance;
  // private Context ctx;
  private static final String DB_NAME = "posDB.db";

  private DatabaseAccesser() {}
//  public void setContext(Context ctx) {
//    this.ctx = this.ctx;
//  }
//
//  public DatabaseAccesser(Context ctx) {
//    this.ctx = ctx;
//  }

  public static DatabaseAccesser getInstance() {
    if (instance == null) {
      instance = new DatabaseAccesser();
    }
    return instance;
  }

  // 開啟讀取資料庫
  public SQLiteDatabase openReadDatabase(Context ctx) {
    // 建立db file物件
    File dbFile = ctx.getDatabasePath(DB_NAME);
    if (!dbFile.exists()) {// 如果不存在
      try {
        File parentDir = new File(dbFile.getParent());
        if (!parentDir.exists()) {// 路徑不存在
          parentDir.mkdir();// 生成路徑
        }
        copyDatabase(ctx, dbFile);
      } catch (IOException ex) {
        throw new RuntimeException("Error creating source databse", ex);
      }
    }
    return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
  }

  // 開啟寫入資料庫
  public SQLiteDatabase openWriteDatabase(Context ctx) {
    File dbFile = ctx.getDatabasePath(DB_NAME);
    //如果檔案不存在，則從assets裡複製
    if (!dbFile.exists()) {
      try {
        File parentDir = new File(dbFile.getParent());
        // 若路徑不存在，則生成路徑
        if (!parentDir.exists()) {
          parentDir.mkdir();
        }
        copyDatabase(ctx, dbFile);
      } catch (IOException e) {
        Log.e("DB", "Error creating writing database.");
      }
    }
    return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
  }

  // 複製資料庫
  private void copyDatabase(Context ctx, File dbFile) throws IOException {
    // 取得assets裡面的db檔案
    InputStream inputStream = ctx.getAssets().open(DB_NAME);
    // 寫入dbFile物件內
    OutputStream outputStream = new FileOutputStream(dbFile);

    byte[] buffer = new byte[1024];// 資料庫的讀取緩衝
    int read = inputStream.read(buffer);// 讀取緩衝區的內容

    while (read != -1) {
      outputStream.write(buffer, 0, read);// 寫入檔案
      read = inputStream.read(buffer);// 繼續讀取
    }
    outputStream.flush();
    outputStream.close();
    inputStream.close();
  }
}
