package com.develop.boris.mobilepos_ver3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.develop.boris.mobilepos_ver3.data.Category;
import com.develop.boris.mobilepos_ver3.data.CategoryDAO;
import com.develop.boris.mobilepos_ver3.data.ConsumeType;
import com.develop.boris.mobilepos_ver3.data.ConsumeTypeDAO;
import com.develop.boris.mobilepos_ver3.myadapter.MyCategoryAdapter;
import com.develop.boris.mobilepos_ver3.myadapter.MyConsumeTypeAdapter;

import java.util.ArrayList;

public class CategorySettingActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

  // UI
  private ListView productCategory;
  private ListView consumeType;
  private FloatingActionButton addProductCategory;
  private FloatingActionButton addconsumeType;
  private LinearLayout lastClickItem;


  // Data
  private CategoryDAO categoryDAO;
  private ConsumeTypeDAO consumeTypeDAO;

  private MyCategoryAdapter categoryAdapter;
  private MyConsumeTypeAdapter consumeTypeAdapter;

  private ArrayList<Category> categoryList;
  private ArrayList<ConsumeType> consumeTypeList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category_setting);

    // Actionbar setting
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_144px_48dp_ver3);

    // initData
    categoryDAO = new CategoryDAO(this);
    categoryList = categoryDAO.getAllData();
    categoryAdapter = new MyCategoryAdapter(this, categoryList, this, this);

    consumeTypeDAO = new ConsumeTypeDAO(this);
    consumeTypeList = consumeTypeDAO.getAllData();
    consumeTypeAdapter = new MyConsumeTypeAdapter(this, consumeTypeList, this, this);

    // initView
    productCategory = (ListView) findViewById(R.id.lv_product_type_setting);
    productCategory.setAdapter(categoryAdapter);

    consumeType = (ListView) findViewById(R.id.lv_consume_type_setting);
    consumeType.setAdapter(consumeTypeAdapter);

    addconsumeType = (FloatingActionButton) findViewById(R.id.fab_add_consume_type);
    addProductCategory = (FloatingActionButton) findViewById(R.id.fab_add_product_cate);

    // initHandler
    addconsumeType.setOnClickListener(this);
    addProductCategory.setOnClickListener(this);

  }

  @Override
  public void onClick(View view) {
    if (view instanceof LinearLayout) {
      changeItemColor(view);
    }
    int id = view.getId();
    final EditText editText = new EditText(this);
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    dialogBuilder.setNegativeButton("取消", null)
            .setView(editText);
    switch (id) {
      case R.id.fab_add_product_cate:
        dialogBuilder.setTitle("新增商品類別")
                .setPositiveButton("新增", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    String str = editText.getText().toString();
                    if (str.equals("")) {
                      Toast.makeText(CategorySettingActivity.this, "未輸入名稱", Toast.LENGTH_SHORT).show();
                    } else {

                    }

                  }
                })
                .show();
        break;
      case R.id.fab_add_consume_type:

        dialogBuilder.setTitle("新增消費類別")
                .setPositiveButton("新增", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                    String str = editText.getText().toString();
                    if (str.equals("")) {
                      Toast.makeText(CategorySettingActivity.this, "未輸入名稱", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                  }
                })
                .show();
        break;
    }
  }

  @Override
  public boolean onLongClick(View view) {
    changeItemColor(view);
    Object obj = view.getTag(R.id.adaptorLayout);
    final EditText editText = new EditText(this);
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    if (obj instanceof Category) {
      final Category category = (Category) obj;
      editText.setText(category.getName());
      dialogBuilder.setView(editText)
              .setTitle("修改類別名稱")
              .setNegativeButton("取消", null)
              .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  if (editText.getText().toString().equals("")) {
                    Toast.makeText(CategorySettingActivity.this, "未輸入新的名稱", Toast.LENGTH_SHORT).show();
                  } else {
                    category.setName(editText.getText().toString());
                    categoryDAO.update(category);
                    refreshData("Category");
                  }
                }
              })
              .show();

    } else if (obj instanceof ConsumeType) {
      final ConsumeType consumeType = (ConsumeType) obj;
      editText.setText(consumeType.getName());
      dialogBuilder.setView(editText)
              .setTitle("修改消費類別名稱")
              .setNegativeButton("取消", null)
              .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  if (editText.getText().toString().equals("")) {
                    Toast.makeText(CategorySettingActivity.this, "未輸入新的名稱", Toast.LENGTH_SHORT).show();
                  } else {
                    consumeType.setName(editText.getText().toString());
                    consumeTypeDAO.update(consumeType);
                    refreshData("ConsumeType");
                  }
                }
              })
              .show();
    }


    return false;
  }

  private void changeItemColor(View view) {
    if (lastClickItem != null) {
      lastClickItem.setBackgroundColor(getResources().getColor(R.color.colorBackground));
    }
    view.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
    lastClickItem = (LinearLayout) view;
  }

  private void refreshData(String cardName) {
    if (cardName.equals("Category")) {
      categoryList = categoryDAO.getAllData();
      categoryAdapter.setDataList(categoryList);
      categoryAdapter.notifyDataSetChanged();
    } else if (cardName.equals("ConsumeType")) {
      consumeTypeList = consumeTypeDAO.getAllData();
      consumeTypeAdapter.setDataList(consumeTypeList);
      consumeTypeAdapter.notifyDataSetChanged();
    }
  }
}
