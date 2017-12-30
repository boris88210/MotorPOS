package com.develop.boris.mobilepos_ver3.myDetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.SaleBuilderActivity;
import com.develop.boris.mobilepos_ver3.data.Customer;
import com.develop.boris.mobilepos_ver3.data.CustomerDAO2;
import com.develop.boris.mobilepos_ver3.data.SaleDAO;
import com.develop.boris.mobilepos_ver3.myadapter.MyConsumeHistoryAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyDetailCustomerActivity extends AppCompatActivity {

  // UI
  private TextView name;
  private TextView plate;
  private TextView phone;
  private TextView tel;
  private TextView createDate;
  private Button toMaintainPage;

  private EditText editName;
  private EditText editPlate;
  private EditText editPhone;
  private EditText editTel;

  private ListView historyListView;
  private LinearLayout maintainHistory;
  // Data
  private CustomerDAO2 dao;
  private Customer currentData;
  private int id;

  private SaleDAO consumeHistoryDAO;
  private ArrayList consumeHistoryList;
  private MyConsumeHistoryAdapter consumeHistoryAdapter;
  // flag
  private static final int NORMAL_MODE = 1;
  private static final int CREATE_MODE = 2;
  private static final int Edit_MODE = 3;
  private int mode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_detail_customer);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_144px_48dp_ver3);

    initData();
    initView();
    initHandler();
  }

  @Override
  protected void onResume() {
    super.onResume();
    consumeHistoryList = consumeHistoryDAO.getDataByCustomerId(id);
    consumeHistoryAdapter.setDataList(consumeHistoryList);
    consumeHistoryAdapter.notifyDataSetChanged();
  }

  //建立menu
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    switch (mode) {
      case NORMAL_MODE:
        getMenuInflater().inflate(R.menu.menu_info_display, menu);
        break;
      default:
        getMenuInflater().inflate(R.menu.menu_editor_list, menu);
        break;
    }
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    switch (itemId) {
      case R.id.action_save://完成編輯
        if (mode == CREATE_MODE) {
          if (checkUIData()) {
            getDataFromUI();
            dao.insert(currentData);
            Toast.makeText(this, "已新增資料", Toast.LENGTH_SHORT).show();
            finish();
          } else {
            editName.setHint("商品名稱為必填欄位");
            Toast.makeText(this, "商品名稱為必填欄位", Toast.LENGTH_SHORT).show();
          }
        } else {
          getDataFromUI();
          dao.update(currentData);
          Toast.makeText(this, "資料已更新", Toast.LENGTH_SHORT).show();
          mode = NORMAL_MODE;
          invalidateOptionsMenu();
          refreshData();
        }
        break;
      case R.id.action_edit://進入編輯模式
        mode = Edit_MODE;
        refreshData();
        invalidateOptionsMenu();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    if (mode == Edit_MODE) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage("確定要放棄編輯嗎?")
              .setNegativeButton("取消", null)
              .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  mode = NORMAL_MODE;
                  refreshData();
                  invalidateOptionsMenu();
                }
              }).show();
    } else if (mode == CREATE_MODE) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage("確定要放棄編輯嗎?")
              .setNegativeButton("取消", null)
              .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  finish();
                }
              }).show();
    } else {
      super.onBackPressed();
    }
  }

  private void initHandler() {
    // 按鈕跳轉至保養選單
    toMaintainPage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent it = new Intent(MyDetailCustomerActivity.this, SaleBuilderActivity.class);
        it.putExtra("Tab", "Maintain");
        it.putExtra("CustomerId", id);
        it.putExtra("ConsumeType", 1);
        startActivity(it);
      }
    });
  }

  // 控制畫面顯示的資料
  private void refreshData() {
    switch (mode) {
      default:
      case NORMAL_MODE:
        //隱藏編輯資料
        editName.setVisibility(View.GONE);
        editPlate.setVisibility(View.GONE);
        editPhone.setVisibility(View.GONE);
        editTel.setVisibility(View.GONE);

        editName.setText(currentData.getName());
        editPlate.setText(currentData.getPlate());
        if (currentData.getPhone() == 0) {
          editPhone.setText("");
        } else {
          editPhone.setText("0" + currentData.getPhone());
        }
        if (currentData.getTel() == 0) {
          editTel.setText("");
        } else {
          editTel.setText("" + currentData.getTel());
        }

        name.setVisibility(View.VISIBLE);
        plate.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        tel.setVisibility(View.VISIBLE);
        createDate.setVisibility(View.VISIBLE);
        toMaintainPage.setVisibility(View.VISIBLE);
        maintainHistory.setVisibility(View.VISIBLE);

        name.setText(currentData.getName());
        plate.setText(currentData.getPlate());
        if (currentData.getPhone() == 0) {
          phone.setText("");
        } else {
          phone.setText("0" + currentData.getPhone());
        }
        if (currentData.getTel() == 0) {
          tel.setText("");
        } else {
          tel.setText("" + currentData.getTel());
        }
        createDate.setText(currentData.getCreateDate().substring(0, 10));
        break;
      case CREATE_MODE:
        // 清空編輯資料
        editName.setText("");
        editPlate.setText("");
        editPhone.setText("");
        editTel.setText("");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        createDate.setText(format.format(new Date()));
      case Edit_MODE:

        // 資料隱藏
        name.setVisibility(View.GONE);
        plate.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        tel.setVisibility(View.GONE);
//        createDate.setVisibility(View.GONE);
        toMaintainPage.setVisibility(View.GONE);
        maintainHistory.setVisibility(View.GONE);

        //顯示編輯資料
        editName.setVisibility(View.VISIBLE);
        editPlate.setVisibility(View.VISIBLE);
        editPhone.setVisibility(View.VISIBLE);
        editTel.setVisibility(View.VISIBLE);
        break;
    }

  }

  private void initView() {
    // UI initialize
    name = (TextView) findViewById(R.id.tv_c_name);
    plate = (TextView) findViewById(R.id.tv_plate);
    phone = (TextView) findViewById(R.id.tv_c_phone);
    tel = (TextView) findViewById(R.id.tv_c_tel);
    createDate = (TextView) findViewById(R.id.tv_c_create_date);
    toMaintainPage = (Button) findViewById(R.id.btn_to_maintain_page);

    editName = (EditText) findViewById(R.id.et_c_name);
    editPlate = (EditText) findViewById(R.id.et_plate);
    editPhone = (EditText) findViewById(R.id.et_c_phone);
    editTel = (EditText) findViewById(R.id.et_c_tel);

    maintainHistory = (LinearLayout) findViewById(R.id.maintain_history);
    historyListView = (ListView) findViewById(R.id.lv_consume_history);
    // Data display
    historyListView.setAdapter(consumeHistoryAdapter);

    refreshData();
  }

  private void initData() {
    dao = new CustomerDAO2(this);
    id = getIntent().getIntExtra("ID", -1);
    mode = getIntent().getIntExtra("MODE", 1);
    if (id == -1) {
      currentData = new Customer();
    } else {
      currentData = dao.getDataById(id);
    }
    // 搜尋該客戶的消費歷史紀錄
    consumeHistoryDAO = new SaleDAO(this);
    consumeHistoryList = consumeHistoryDAO.getDataByCustomerId(id);
    consumeHistoryAdapter = new MyConsumeHistoryAdapter(this, consumeHistoryList);

  }

  private void getDataFromUI() {
    currentData.setName(editName.getText().toString());
    currentData.setPlate(editPlate.getText().toString());
    if (editPhone.getText().toString().equals("")) {
      currentData.setPhone(0);
    } else {
      currentData.setPhone(Integer.parseInt(editPhone.getText().toString()));
    }

    if (editTel.getText().toString().equals("")) {
      currentData.setTel(0);
    } else {
      currentData.setTel(Integer.parseInt(editTel.getText().toString()));
    }
  }

  private boolean checkUIData() {

    // 名稱不能為空
    // 資料有更新才存檔，否則結束編輯模式，並提醒資料未更新
    return !editPlate.getText().toString().equals("");
  }

}
