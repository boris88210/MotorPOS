package com.develop.boris.mobilepos_ver3.myDetail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Supplier;
import com.develop.boris.mobilepos_ver3.data.SupplierDAO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDetailSupplierActivity extends AppCompatActivity {

  // UI
  private TextView name;
  private TextView tel;
  private TextView phone;
  private TextView fax;
  private TextView email;
  private TextView address;
  private TextView createDate;

  private LinearLayout infomation;
  private LinearLayout inputForm;


  private EditText editName;
  private EditText editTel;
  private EditText editPhone;
  private EditText editFax;
  private EditText editEmail;
  private EditText editAddress;


  // Data
  private int supplierId;
  private SupplierDAO dao;
  private Supplier currentData;

  private int mode = 1;
  // Flag
  private static final int NORMAL_MODE = 1;
  private static final int CREATE_MODE = 2;
  private static final int Edit_MODE = 3;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_detail_supplier);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
      }
    });
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_144px_48dp_ver3);


    initData();
    initView();
  }

  private void initView() {
    name = (TextView) findViewById(R.id.tv_supplier_name);
    tel = (TextView) findViewById(R.id.tv_supplier_tel);
    phone = (TextView) findViewById(R.id.tv_supplier_phone);
    fax = (TextView) findViewById(R.id.tv_supplier_fax);
    email = (TextView) findViewById(R.id.tv_supplier_mail);
    address = (TextView) findViewById(R.id.tv_supplier_add);
    createDate = (TextView) findViewById(R.id.tv_supplier_create_time);


    infomation = (LinearLayout) findViewById(R.id.layout_supplier_info);
    inputForm = (LinearLayout) findViewById(R.id.layout_supplier_input);

    editName = (EditText) findViewById(R.id.et_supplier_name);
    editTel = (EditText) findViewById(R.id.et_supplier_tel);
    editPhone = (EditText) findViewById(R.id.et_supplier_phone);
    editFax = (EditText) findViewById(R.id.et_supplier_fax);
    editEmail = (EditText) findViewById(R.id.et_supplier_mail);
    editAddress = (EditText) findViewById(R.id.et_supplier_add);

    refreshData();
  }

  private void refreshData() {
    switch (mode) {
      case NORMAL_MODE:
        name.setText(currentData.getName());
        if (currentData.getTel() == 0) {
          tel.setText("");
        } else {
          tel.setText("0" + currentData.getTel());
        }
        if (currentData.getPhone() == 0) {
          phone.setText("");
        } else {
          phone.setText("0" + currentData.getPhone());
        }
        if (currentData.getFax() == 0) {
          fax.setText("");
        } else {
          fax.setText("0" + currentData.getFax());
        }
        email.setText(currentData.getMail());
        address.setText(currentData.getAddress());
        createDate.setText(currentData.getCreateDate().substring(0, 10));

        infomation.setVisibility(View.VISIBLE);
        inputForm.setVisibility(View.GONE);

        editName.setText(currentData.getName());
        if (currentData.getTel() == 0) {
          editTel.setText("");
        } else {
          editTel.setText("0" + currentData.getTel());
        }
        if (currentData.getPhone() == 0) {
          editPhone.setText("");
        } else {
          editPhone.setText("0" + currentData.getPhone());
        }
        if (currentData.getFax() == 0) {
          editFax.setText("");
        } else {
          editFax.setText("0" + currentData.getFax());
        }
        editEmail.setText(currentData.getMail());
        editAddress.setText(currentData.getAddress());
        break;
      case CREATE_MODE:
        editName.setText("");
        editTel.setText("");
        editPhone.setText("");
        editFax.setText("");
        editEmail.setText("");
        editAddress.setText("");

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        createDate.setText(format.format(new Date()));

      case Edit_MODE:
        infomation.setVisibility(View.GONE);
        inputForm.setVisibility(View.VISIBLE);
        break;

    }

  }

  private void initData() {
    mode = getIntent().getIntExtra("MODE", 1);
    supplierId = getIntent().getIntExtra("ID", -1);
    dao = new SupplierDAO(this);
    if (supplierId == -1) {
      currentData = new Supplier();
    } else {
      currentData = dao.getDataById(supplierId);
    }
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
          initData();
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

  private boolean checkUIData() {

    return (!editName.getText().toString().equals(""));
  }

  public void getDataFromUI() {
    currentData.setName(editName.getText().toString());
    if (editTel.getText().toString().equals("")) {
      currentData.setTel(0);
    } else {
      currentData.setTel(Integer.parseInt(editTel.getText().toString()));
    }
    if (editPhone.getText().toString().equals("")) {
      currentData.setPhone(0);
    } else {
      currentData.setPhone(Integer.parseInt(editPhone.getText().toString()));
    }
    if (editFax.getText().toString().equals("")) {
      currentData.setFax(0);
    } else {
      currentData.setFax(Integer.parseInt(editFax.getText().toString()));
    }
    currentData.setMail(editEmail.getText().toString());
    currentData.setAddress(editAddress.getText().toString());
  }

}
