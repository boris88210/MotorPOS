package com.develop.boris.mobilepos_ver3.myDetail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Category;
import com.develop.boris.mobilepos_ver3.data.CategoryDAO;
import com.develop.boris.mobilepos_ver3.data.ConsumeTypeDAO;
import com.develop.boris.mobilepos_ver3.data.Product;
import com.develop.boris.mobilepos_ver3.data.ProductDAO;
import com.develop.boris.mobilepos_ver3.myadapter.MySpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//TODO:資料未修改不需要update

public class MyDetailProductActivity extends AppCompatActivity {
  // UI
  private LinearLayout dataView;
  private LinearLayout dataInputView;
  private TextView name;
  private TextView model;
  private TextView barcode;
  private TextView warehouse;
  private TextView unitName;
  private TextView unitPrice;
  private TextView createDate;
  private TextView category;
  private TextView productQty;
  private TextView safeQty;

  private EditText editName;
  private EditText editModel;
  private EditText editBarcode;
  private EditText editWarehouse;
  private EditText editUnitName;
  private EditText editUnitPrice;
  private EditText editProductQty;
  private EditText editSafeQty;
  private Spinner spinnerProductCate;

  // Data
  private ProductDAO dao;
  private Product currentData;
  private ArrayList<Category> categoriesList;
  private Category selectedCategory;
  private int id;
  // flag
  private static final int NORMAL_MODE = 1;
  private static final int CREATE_MODE = 2;
  private static final int Edit_MODE = 3;
  private int mode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_detail_product);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_144px_48dp_ver3);

    initData();
    initView();
    initHandler();
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

  //
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
          invalidateOptionsMenu();
          initData();
          mode = NORMAL_MODE;
          refreshData();
          Toast.makeText(this, "資料已更新", Toast.LENGTH_SHORT).show();
        }
        break;
      case R.id.action_edit://進入編輯模式
        mode = Edit_MODE;
        refreshData();
        invalidateOptionsMenu();
        break;
      // 取消刪除功能
//      case R.id.action_detail_delete://刪除
//        //跳出刪除提示
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("確定要刪除嗎?")
//                .setNegativeButton("取消", null)
//                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
//                  @Override
//                  public void onClick(DialogInterface dialogInterface, int i) {
//                    dao.delete(id);
//                    Toast.makeText(MyDetailProductActivity.this, "已新增資料", Toast.LENGTH_SHORT).show();
//                    finish();
//                  }
//                }).show();
//        break;
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
    spinnerProductCate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory = categoriesList.get(i);
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
  }

  // 控制畫面顯示的資料
  private void refreshData() {

    switch (mode) {
      default:
      case NORMAL_MODE:
        //隱藏編輯資料
//        editName.setVisibility(View.GONE);
//        editModel.setVisibility(View.GONE);
//        editBarcode.setVisibility(View.GONE);
//        editWarehouse.setVisibility(View.GONE);
//        editUnitName.setVisibility(View.GONE);
//        editUnitPrice.setVisibility(View.GONE);
//        editCreateDate.setVisibility(View.GONE);
        dataInputView.setVisibility(View.GONE);

        editName.setText(currentData.getName());
        editModel.setText(currentData.getModel());
        if (currentData.getBarcode() == 0) {
          editBarcode.setText("");
        } else {
          editBarcode.setText(currentData.getBarcode() + "");
        }
        editWarehouse.setText(currentData.getWarehouse());
        editUnitName.setText(currentData.getUnit());
        editUnitPrice.setText(currentData.getUnitPrice() + "");
        editProductQty.setText(currentData.getQty() + "");
        editSafeQty.setText(currentData.getSafeQty() + "");

//        name.setVisibility(View.VISIBLE);
//        model.setVisibility(View.VISIBLE);
//        barcode.setVisibility(View.VISIBLE);
//        warehouse.setVisibility(View.VISIBLE);
//        unitName.setVisibility(View.VISIBLE);
//        unitPrice.setVisibility(View.VISIBLE);
//        createDate.setVisibility(View.VISIBLE);
        dataView.setVisibility(View.VISIBLE);

        name.setText(currentData.getName());
        model.setText(currentData.getModel());
        if (currentData.getBarcode() == 0) {
          barcode.setText("");
        } else {
          barcode.setText(currentData.getBarcode() + "");
        }

        warehouse.setText(currentData.getWarehouse());
        category.setText(currentData.getCategory());
        unitName.setText(currentData.getUnit());
        unitPrice.setText(currentData.getUnitPrice() + "");
        productQty.setText(currentData.getQty() + "");
        safeQty.setText(currentData.getSafeQty() + "");
        createDate.setText(currentData.getCreateDate().substring(0, 10));
        break;
      case CREATE_MODE:
        // 清空編輯資料
        editName.setText("");
        editModel.setText("");
        editBarcode.setText("");
        editWarehouse.setText("");
        editUnitName.setText("");
        editUnitPrice.setText("");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        createDate.setText(format.format(new Date()));

      case Edit_MODE:

        // 資料隱藏
//        name.setVisibility(View.GONE);
//        model.setVisibility(View.GONE);
//        barcode.setVisibility(View.GONE);
//        warehouse.setVisibility(View.GONE);
//        unitName.setVisibility(View.GONE);
//        unitPrice.setVisibility(View.GONE);
//        createDate.setVisibility(View.GONE);
        dataView.setVisibility(View.GONE);

        //顯示編輯資料
//        editName.setVisibility(View.VISIBLE);
//        editModel.setVisibility(View.VISIBLE);
//        editBarcode.setVisibility(View.VISIBLE);
//        editWarehouse.setVisibility(View.VISIBLE);
//        editUnitName.setVisibility(View.VISIBLE);
//        editUnitPrice.setVisibility(View.VISIBLE);
//        editCreateDate.setVisibility(View.VISIBLE);
        dataInputView.setVisibility(View.VISIBLE);
        break;
    }

  }

  private void initView() {
    // UI initialize
    dataView = (LinearLayout) findViewById(R.id.ly_data);
    dataInputView = (LinearLayout) findViewById(R.id.ly_input);

    name = (TextView) findViewById(R.id.tv_p_name);
    model = (TextView) findViewById(R.id.tv_p_model);
    barcode = (TextView) findViewById(R.id.tv_p_barcode);
    warehouse = (TextView) findViewById(R.id.tv_p_warehouse);
    category = (TextView) findViewById(R.id.tv_p_category);
    unitName = (TextView) findViewById(R.id.tv_p_unitName);
    unitPrice = (TextView) findViewById(R.id.tv_p_unitPrice);
    createDate = (TextView) findViewById(R.id.tv_p_createDate);
    productQty = (TextView) findViewById(R.id.tv_p_qty);
    safeQty = (TextView) findViewById(R.id.tv_p_safe_qty);

    editName = (EditText) findViewById(R.id.et_p_name);
    editModel = (EditText) findViewById(R.id.et_p_model);
    editBarcode = (EditText) findViewById(R.id.et_p_barcode);
    editWarehouse = (EditText) findViewById(R.id.et_p_warehouse);
    editUnitName = (EditText) findViewById(R.id.et_p_unitName);
    editUnitPrice = (EditText) findViewById(R.id.et_p_unitPrice);
    editProductQty = (EditText) findViewById(R.id.et_p_qty);
    editSafeQty = (EditText) findViewById(R.id.et_p_safe_qty);
    spinnerProductCate = (Spinner) findViewById(R.id.spinner_product_category);
    // Data display
    MySpinnerAdapter adapter = new MySpinnerAdapter(this, new CategoryDAO(this).getAllData());
    spinnerProductCate.setAdapter(adapter);
    refreshData();
  }

  private void initData() {
    mode = getIntent().getIntExtra("MODE", 1);
    id = getIntent().getIntExtra("ID", -1);
    dao = new ProductDAO(this);
    if (id == -1) {
      currentData = new Product();
    } else {
      currentData = dao.getDataById(id);
    }
    categoriesList = new CategoryDAO(this).getAllData();
  }


  private void getDataFromUI() {
    currentData.setName(editName.getText().toString());
    currentData.setModel(editModel.getText().toString());
    if (editBarcode.getText().toString().equals("")) {
      currentData.setBarcode(0);
    } else {
      currentData.setBarcode(Long.parseLong(editBarcode.getText().toString()));
    }
    currentData.setWarehouse(editWarehouse.getText().toString());
    currentData.setUnit(editUnitName.getText().toString());
    if (editBarcode.getText().toString().equals("")) {
      currentData.setUnitPrice(0);
    } else {
      currentData.setUnitPrice(Integer.parseInt(editUnitPrice.getText().toString()));
    }
    if (editProductQty.getText().toString().equals("")) {
      currentData.setQty(0);
    } else {
      currentData.setQty(Integer.parseInt(editProductQty.getText().toString()));
    }
    currentData.setCategoryId(selectedCategory.getId());
    if (editSafeQty.getText().toString().equals("")) {
      currentData.setSafeQty(0);
    } else {
      currentData.setSafeQty(Integer.parseInt(editSafeQty.getText().toString()));
    }


  }

  private boolean checkUIData() {
    return !editName.getText().toString().equals("");
  }

}
