package com.develop.boris.mobilepos_ver3;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.develop.boris.mobilepos_ver3.data.Product;
import com.develop.boris.mobilepos_ver3.data.ProductDAO;
import com.develop.boris.mobilepos_ver3.data.Service;
import com.develop.boris.mobilepos_ver3.data.ServiceDAO;
import com.develop.boris.mobilepos_ver3.data.ServiceType;
import com.develop.boris.mobilepos_ver3.data.ServiceTypeDAO;
import com.develop.boris.mobilepos_ver3.myadapter.MyProductAdapterForServiceChoice;
import com.develop.boris.mobilepos_ver3.myadapter.MyServiceAdapter;
import com.develop.boris.mobilepos_ver3.myadapter.MyServiceTypeAdapter;

import java.util.ArrayList;

public class ServiceSettingActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

  private ListView serviceCateListView;

  private ListView serviceListview;
  ServiceTypeDAO serviceTypeDAO;
  private ServiceDAO serviceDAO;
  private ArrayList<Service> servicesList;
  private MyServiceAdapter serviceAdapter;
  MyServiceTypeAdapter serviceTypeAdapter;
  ArrayList<ServiceType> serviceTypesList;

  private FloatingActionButton addType;
  private FloatingActionButton addService;

  private int serviceTypeId;
  // 點選變色，紀錄變色的item
  private LinearLayout lastClickServiceTypeItem;
  private LinearLayout lastClickServiceItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service_setting);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_144px_48dp_ver3);

    initData();

    // card 1 Service Type
    serviceCateListView = (ListView) findViewById(R.id.lv_service_setting);
    serviceTypeDAO = new ServiceTypeDAO(this);
    serviceTypesList = serviceTypeDAO.getAllData();
    serviceTypeAdapter = new MyServiceTypeAdapter(this, serviceTypesList, this, this);
    serviceCateListView.setAdapter(serviceTypeAdapter);

    // card 2 Service
    serviceListview = (ListView) findViewById(R.id.lv_service_name);
    addType = (FloatingActionButton) findViewById(R.id.fab_add_service_type);
    addType.setOnClickListener(this);
    addService = (FloatingActionButton) findViewById(R.id.fab_add_service);
    addService.setOnClickListener(this);


  }

  private void initData() {
    serviceDAO = new ServiceDAO(this);
  }

  private void refreshData() {
        serviceTypesList = serviceTypeDAO.getAllData();
        serviceTypeAdapter.setDataList(serviceTypesList);
        serviceTypeAdapter.notifyDataSetChanged();
  }

  private void refreshData(int serviceId) {
        servicesList = serviceDAO.getDataByTypeId(serviceTypeId);
        serviceAdapter.setDataList(servicesList);
        serviceAdapter.notifyDataSetChanged();
  }


  @Override
  public void onClick(View v) {
    Object obj = v.getTag(R.id.adaptorLayout);
    if (obj instanceof ServiceType) {
      // 選取item變色
      changeColor(v);
      serviceTypeId = ((ServiceType) obj).getId();
      servicesList = serviceDAO.getDataByTypeId(serviceTypeId);
      if (serviceAdapter == null) {
        serviceAdapter = new MyServiceAdapter(this, servicesList, this, this);
        serviceListview.setAdapter(serviceAdapter);
      } else {
        serviceAdapter.setDataList(servicesList);
        serviceAdapter.notifyDataSetChanged();
      }
    } else if (obj instanceof Service) {
      changeColor(v);
      // 取得Service所包含的商品id
      Service service = (Service) obj;
      final int serviceId = service.getId();
      final ServiceDAO serviceDAO = new ServiceDAO(this);
      ArrayList productIdList = serviceDAO.getProductIdInService(serviceId);
//      Log.i("Product Id", "Id List Size: " + productIdList.size());
      // 從資料庫取得所有商品
      ProductDAO productDAO = new ProductDAO(this);
      final ArrayList<Product> products = productDAO.getAllData();
      // 建立一個SparseBooleanArray紀錄Product被選取的狀態
      final SparseBooleanArray selectedProducts = new SparseBooleanArray();
      for (Product p : products) {
        if (productIdList.contains(p.getId())) {
          selectedProducts.put(p.getId(), true);
        } else {
          selectedProducts.put(p.getId(), false);
        }
      }

      // 建立一個暫存的SparseBooleanArray紀錄修改後的商品被選取的狀態
      final SparseBooleanArray tempArray = selectedProducts.clone();
      // 建立adapter，在ListView列出所有商品
      MyProductAdapterForServiceChoice productAdapter = new MyProductAdapterForServiceChoice(this, products);
      productAdapter.setSelectedProducts(tempArray);
      ListView productListView = new ListView(this);
      productListView.setAdapter(productAdapter);
      productListView.setDivider(null);
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("修改服務內容")
              .setView(productListView)
              .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  // 比對所有商品的差異，有變化則更新資料庫
                  for (Product p : products) {
                    int productId = p.getId();
                    if (selectedProducts.get(productId) ^ tempArray.get(productId)) {
                      if (tempArray.get(productId)) {
                        Log.i("Service Products", productId + " 被加入服務");
                        serviceDAO.insertServiceProducts(serviceId, productId);
                      } else {
                        Log.i("Service Products", productId + " 被移除服務");
                        serviceDAO.deleteServiceProducts(serviceId, productId);
                      }
                    }
                  }
                }
              })
              .setNegativeButton("取消", null)
              .show();
    }

    int id = v.getId();
    if (id == R.id.fab_add_service) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      final EditText editText = new EditText(this);
      if (servicesList == null) {
        builder.setTitle("新增服務")
                .setMessage("請先選擇類別")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    Log.i("setting", "建立服務確定");
                  }
                })
                .setNegativeButton("取消", null)
                .show();
      } else {
        builder.setTitle("新增服務")
                .setView(editText)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
//                    Log.i("setting", "建立服務確定");
                    String newTypeName = editText.getText().toString();
                    if (newTypeName.equals("")) {
                      Toast.makeText(ServiceSettingActivity.this, "未輸入內容", Toast.LENGTH_SHORT).show();
                    } else {
                      Service newItem = new Service();
                      newItem.setName(newTypeName);
                      newItem.setServiceType(serviceTypeId);
//                      Log.i("New Service", newItem.getName() + newItem.getServiceType());
                      serviceDAO.insert(newItem);
                      servicesList = serviceDAO.getDataByTypeId(serviceTypeId);
                      serviceAdapter.setDataList(servicesList);
                      serviceAdapter.notifyDataSetChanged();
                    }

                  }
                })
                .setNegativeButton("取消", null)
                .show();
      }
    } else if (id == R.id.fab_add_service_type) {
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      final EditText editText = new EditText(this);
      builder.setTitle("新增服務類別")
              .setView(editText)
              .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  Log.i("setting", "建立服務確定");
                  String newTypeName = editText.getText().toString();
                  if (newTypeName.equals("")) {
                    Toast.makeText(ServiceSettingActivity.this, "未輸入內容", Toast.LENGTH_SHORT).show();
                  } else {
                    ServiceType newItem = new ServiceType();
                    newItem.setName(newTypeName);
                    serviceTypeDAO.insert(newItem);
                    serviceTypesList = serviceTypeDAO.getAllData();
                    serviceTypeAdapter.setDataList(serviceTypesList);
                    serviceTypeAdapter.notifyDataSetChanged();
                  }
                }
              })
              .setNegativeButton("取消", null)
              .show();
    }
  }

  @Override
  public boolean onLongClick(View view) {
    changeColor(view);
    Object obj = view.getTag(R.id.adaptorLayout);
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    final EditText editText = new EditText(this);
    if (obj instanceof ServiceType) {
      final ServiceType st = (ServiceType) obj;
      editText.setText(st.getName());
      dialogBuilder.setTitle("修改服務類別名稱")
              .setNegativeButton("取消", null)
              .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  if (editText.getText().toString().equals("")) {
                    Toast.makeText(ServiceSettingActivity.this, "未輸入新的名稱", Toast.LENGTH_SHORT).show();
                  } else {

                    st.setName(editText.getText().toString());
                    serviceTypeDAO.update(st);
                    refreshData();
                  }
                }
              })
              .setView(editText)
              .show();
    } else if (obj instanceof Service) {
      final Service s = (Service) obj;
      editText.setText(s.getName());
      dialogBuilder.setTitle("修改服務名稱")
              .setNegativeButton("取消", null)
              .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  if (editText.getText().toString().equals("")) {
                    Toast.makeText(ServiceSettingActivity.this, "未輸入新的名稱", Toast.LENGTH_SHORT).show();
                  } else {
                    s.setName(editText.getText().toString());
                    serviceDAO.update(s);
                    refreshData(s.getId());
                  }
                }
              })
              .setView(editText)
              .show();
    }
    return false;
  }

  private void changeColor(View view) {
    Object obj = view.getTag(R.id.adaptorLayout);
    if (obj instanceof ServiceType) {
      if (lastClickServiceTypeItem != null) {
        lastClickServiceTypeItem.setBackgroundColor(getResources().getColor(R.color.colorBackground));
      }
      view.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
      lastClickServiceTypeItem = (LinearLayout) view;

    } else if (obj instanceof Service) {
      if (lastClickServiceItem != null) {
        lastClickServiceItem.setBackgroundColor(getResources().getColor(R.color.colorBackground));
      }
      view.setBackgroundColor(getResources().getColor(R.color.secondaryLightColor));
      lastClickServiceItem = (LinearLayout) view;
    }

  }
}
