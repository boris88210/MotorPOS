package com.develop.boris.mobilepos_ver3.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.SaleBuilderActivity;
import com.develop.boris.mobilepos_ver3.data.Product;
import com.develop.boris.mobilepos_ver3.data.ProductDAO;
import com.develop.boris.mobilepos_ver3.data.SaleProducts;
import com.develop.boris.mobilepos_ver3.data.Service;
import com.develop.boris.mobilepos_ver3.data.ServiceDAO;
import com.develop.boris.mobilepos_ver3.data.ServiceType;
import com.develop.boris.mobilepos_ver3.data.ServiceTypeDAO;
import com.develop.boris.mobilepos_ver3.myadapter.ServiceButtonAdapter;
import com.develop.boris.mobilepos_ver3.myadapter.ServiceViewAdapter;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/20.
 */

public class MaintainService extends Fragment implements View.OnClickListener {

  // Instance
  private static MaintainService instance;
  // UI
  private ListView serviceView;
  // Data
  private ServiceTypeDAO serviceTypeDAO;
  private ArrayList<ServiceType> serviceList;
  private ServiceViewAdapter serviceViewAdapter;
  private ServiceDAO serviceDAO;
  private ProductDAO productDAO;
  private SparseBooleanArray buttonStatus;
  private ServiceButtonAdapter buttonAdapter;
  // Activity
  private SaleBuilderActivity activity;
  private ItemStorer itemStorer;

  // 回傳自身物件
  public static MaintainService newInstance() {
    if (instance == null) {
      instance = new MaintainService();
    }
    Bundle args = new Bundle();
    return instance;
  }

  // 註冊Activity紀錄所屬的Activity，為了可以通知Activity資料更新
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.activity = (SaleBuilderActivity) context;
    itemStorer = (ItemStorer) context;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_maintain_services, container, false);
    initData();
    initView(rootView);
    return rootView;
  }

  private void initData() {
    serviceTypeDAO = new ServiceTypeDAO(getActivity());
    serviceDAO = new ServiceDAO(getActivity());
    productDAO = new ProductDAO(getActivity());
    serviceList = serviceTypeDAO.getAllData();
    serviceViewAdapter = new ServiceViewAdapter(getActivity(), serviceList, this);
    buttonStatus = new SparseBooleanArray();
    ArrayList<Service> servicesList = serviceDAO.getAllData();
    for(Service s : servicesList) {
      buttonStatus.put(s.getId(),false);
    }

    buttonAdapter = new ServiceButtonAdapter(getActivity(), null, this);
    buttonAdapter.setButtonStatus(buttonStatus);
    serviceViewAdapter.setButtonAdapter(buttonAdapter);

  }

  private void initView(View view) {
    serviceView = (ListView) view.findViewById(R.id.lv_service_view);
    serviceView.setAdapter(serviceViewAdapter);
    serviceView.setDivider(null);


  }

  @Override
  public void onClick(View view) {
//            Log.i("MaintainFragment", "Button Click!");
//            ArrayList<SaleProducts> productList = (ArrayList<SaleProducts>) view.getTag();
//            for (SaleProducts sp : productList) {
//                activity.storeItem(sp);
//            }

    //讀取服務內容
    Service service = (Service) view.getTag();// 取出按鈕中的servic
    service.setPressed(!service.isPressed());// 改變按鈕狀態
    Log.i("MaintainService", "button status:" + service.isPressed());
//            serviceViewAdapter.notifyDataSetChanged();
    ArrayList<Integer> productIds = serviceDAO.getProductIdInService(service.getId());
//            Log.i ("MaintainService", "Product size" + productIds.size()) ;
    ArrayList<SaleProducts> productList = new ArrayList<>();
    // 再透過商品ID使用productDAO查詢商品
    for (Integer productId : productIds) {
      Product p = productDAO.getDataById(productId);
      SaleProducts sp = new SaleProducts();
      sp.setProductId(p.getId());
      sp.setName(p.getName());
      sp.setPrice(p.getUnitPrice());
      sp.setQty(1);
      sp.setUnit(p.getUnit());
      productList.add(sp);
      itemStorer.storeItem(sp);
    }
  }
}
