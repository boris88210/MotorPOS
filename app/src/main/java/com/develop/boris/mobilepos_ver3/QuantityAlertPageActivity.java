package com.develop.boris.mobilepos_ver3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Product;
import com.develop.boris.mobilepos_ver3.data.ProductDAO;
import com.develop.boris.mobilepos_ver3.myadapter.MyProductAdapter;

import java.util.ArrayList;

public class QuantityAlertPageActivity extends AppCompatActivity {

  // Data
  private ArrayList<Product> productsList;
  private ProductDAO productDAO;
  private MyProductAdapter productAdapter;
  // UI
  private ListView productListview;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quantity_alert_page);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_144px_48dp_ver3);

    initData();
    initView();
  }

  private void initView() {
    productListview = (ListView) findViewById(R.id.lv_qty_alert_view);
    productListview.setAdapter(productAdapter);
    productListview.setDivider(null);
  }

  private void initData() {
    productDAO = new ProductDAO(this);
    productsList = productDAO.getAlertData();
    productAdapter = new MyProductAdapter(this,productsList);
  }

  @Override
  protected void onResume() {
    super.onResume();
    productsList = productDAO.getAlertData();
    productAdapter.setDataList(productsList);
    productAdapter.notifyDataSetChanged();
  }
}
