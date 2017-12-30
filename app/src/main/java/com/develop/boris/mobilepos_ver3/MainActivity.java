package com.develop.boris.mobilepos_ver3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.develop.boris.mobilepos_ver3.data.CustomerDAO2;
import com.develop.boris.mobilepos_ver3.data.DataDAO;
import com.develop.boris.mobilepos_ver3.data.ProductDAO;
import com.develop.boris.mobilepos_ver3.data.SaleDAO;
import com.develop.boris.mobilepos_ver3.data.SupplierDAO;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailCustomerActivity;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailProductActivity;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailSaleActivity;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailSupplierActivity;
import com.develop.boris.mobilepos_ver3.myadapter.MyBaseAdapter;
import com.develop.boris.mobilepos_ver3.myadapter.MyCustomerAdapter;
import com.develop.boris.mobilepos_ver3.myadapter.MyProductAdapter;
import com.develop.boris.mobilepos_ver3.myadapter.MySalesAdapter;
import com.develop.boris.mobilepos_ver3.myadapter.MySupplierAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
  // UI
  private Button query;
  private EditText condition;
  private ImageView deleteButton;
  private FloatingActionButton fab;
  private ListView listView;

  private DrawerLayout drawer; // 拖曳頁面
  private NavigationView navigationView; // 滑出頁面的標題
  private Toolbar toolbar; // 畫面上方標題欄

  // Data
  private String function;
  private DataDAO dao;
  private ArrayList dataList;
  private MyBaseAdapter adapter;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    // Setup spinner
    Spinner spinner = (Spinner) findViewById(R.id.spinner);
    spinner.setAdapter(new MyAdapter(
            toolbar.getContext(), getResources().getStringArray(R.array.list_spinner)));

    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        String[] functions = {"customer", "product", "sale", "supplier",};
        function = functions[position];
        selectData(function);
        listView.setAdapter(adapter);
        showSearchResult(condition.getText().toString());
//        Log.i("Functions", functions[position]);
        if (function.equals("sale")) {
          fab.setVisibility(View.GONE);
        } else {
          fab.setVisibility(View.VISIBLE);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });


    fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent it = null;
        switch (function) {
          case "customer":
            it = new Intent(MainActivity.this, MyDetailCustomerActivity.class);
            break;
          case "product":
            it = new Intent(MainActivity.this, MyDetailProductActivity.class);
            break;
          case "sale":
            it = new Intent(MainActivity.this, MyDetailSaleActivity.class);
            break;
          case "supplier":
            it = new Intent(MainActivity.this, MyDetailSupplierActivity.class);
            break;
        }
        it.putExtra("MODE", 2);
        startActivity(it);
      }
    });

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);


    initData();
    initView();
    initHandler();
  }

  private void initView() {
    // 畫面上方標題欄
//    toolbar = (Toolbar) findViewById(R.id.toolbar);
    // 回傳可控制ActionBar物件
//    setSupportActionBar(toolbar);
    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    navigationView = (NavigationView) findViewById(R.id.nav_view);

    condition = (EditText) findViewById(R.id.et_condition);
    query = (Button) findViewById(R.id.btn_query);
    deleteButton = findViewById(R.id.imgBtn_delete);

    listView = (ListView) findViewById(R.id.lv_pubs);
    listView.setDivider(null);
  }

  private void initHandler() {
    // 拖曳頁面
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    // 滑出頁面的標題
    navigationView.setNavigationItemSelectedListener(this);

    // 設定搜尋按鈕
    query.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        String keyword = condition.getText().toString().trim();
        showSearchResult(keyword);
//
      }
    });
    // 監控文字輸入的變化，有文字時顯示刪除按鈕
    condition.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        setDeleteButton(editable);
        String keyword = condition.getText().toString().trim();
        showSearchResult(keyword);
      }
    });
    // 輸入框裡的刪除按鈕
    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        condition.setText("");
      }
    });
  }


  private void initData() {
    //顯示資料判定，決定要用甚麼DAO
    function = "customer";
    selectData(function);
  }

  public void setDeleteButton(Editable editable) {
    if (editable.length() < 1) {
      deleteButton.setVisibility(View.INVISIBLE);
    } else {
      deleteButton.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main_list, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_save) {
      Intent it = new Intent(this, SaleBuilderActivity.class);
      it.putExtra("ConsumeType", 1);
      startActivity(it);

      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @SuppressWarnings("StatementWithEmptyBody")
  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    // Handle navigation view item clicks here.
    int id = item.getItemId();//取得選項id

    if (id == R.id.nav_category) {
      Intent it = new Intent(this, CategorySettingActivity.class);
      it.putExtra("Function", "category_setting");
      startActivity(it);
    } else if (id == R.id.nav_service_setting) {
      Intent it = new Intent(this, ServiceSettingActivity.class);
      it.putExtra("Function", "service_setting");
      startActivity(it);
    } else if (id == R.id.nav_product_qty_alert) {
      Intent it = new Intent(this, QuantityAlertPageActivity.class);
      it.putExtra("Function", "product_qty_alert");
      startActivity(it);
    } else if (id == R.id.nav_purchase) {
      Intent it = new Intent(this, PurchaseActivity.class);
      it.putExtra("Function", "purchase");
      startActivity(it);
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  protected void onResume() {
    super.onResume();

    showSearchResult(condition.getText().toString());
  }

  // 顯示搜尋結果
  private void showSearchResult(String keyword) {
    if (keyword.equals("")) {
      dataList = dao.getAllData();
    } else {
      dataList = dao.getDataByKeyword(keyword);
    }
    adapter.setDataList(dataList);
    listView.setAdapter(adapter);
  }

  // 選擇要展示的資料
  private void selectData(String function) {
    switch (function) {
      case "customer":
        dao = new CustomerDAO2(this);
        dataList = dao.getAllData();
        Log.i("TabListActivity", "Customers List size: " + dataList.size());
        adapter = new MyCustomerAdapter(this, dataList);
        break;
      case "product":
        dao = new ProductDAO(this);
        dataList = dao.getAllData();
        Log.i("TabListActivity", "Products List size: " + dataList.size());
        adapter = new MyProductAdapter(this, dataList);
        break;
      case "sale":
        dao = new SaleDAO(this);
        dataList = dao.getAllData();
        Log.i("TabListActivity", "Sales List Size: " + dataList.size());
        adapter = new MySalesAdapter(this, dataList);

//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
//                String date = dateFormat.format(new Date());
//                Sale lastOne = new SaleDAO(this).getLastOneByDate(date);
//                Log.i("TabListActivity", lastOne.getId()+"");
        break;

      case "supplier":
        dao = new SupplierDAO(this);
        dataList = dao.getAllData();
        Log.i("TabListActivity", "Suppliers List Size: " + dataList.size());
        adapter = new MySupplierAdapter(this, dataList);
        break;
//
//      case "order":
//        dao = new OrderDAO(this);
//        dataList = dao.getAllData();
//        adapter = new MyOrderAdpater(this, dataList);
//        break;

//      case "cost":
//        // 這邊cost 成本清單
//        dao = new OrderDAO(this);
//        dataList = dao.getAllData();
//        adapter = new MyOrderAdpater(this, dataList);
//        break;

      default:
        break;
    }
  }


  private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
    private final ThemedSpinnerAdapter.Helper mDropDownHelper;

    public MyAdapter(Context context, String[] objects) {
      super(context, android.R.layout.simple_list_item_1, objects);
      mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
      View view;

      if (convertView == null) {
        // Inflate the drop down using the helper's LayoutInflater
        LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
        view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
      } else {
        view = convertView;
      }

      TextView textView = (TextView) view.findViewById(android.R.id.text1);
      textView.setText(getItem(position));

      return view;
    }

    @Override
    public Resources.Theme getDropDownViewTheme() {
      return mDropDownHelper.getDropDownViewTheme();
    }

    @Override
    public void setDropDownViewTheme(Resources.Theme theme) {
      mDropDownHelper.setDropDownViewTheme(theme);
    }
  }


}
