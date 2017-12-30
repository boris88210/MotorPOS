package com.develop.boris.mobilepos_ver3.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.develop.boris.mobilepos_ver3.MainActivity;
import com.develop.boris.mobilepos_ver3.QuantityAlertPageActivity;
import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.SaleBuilderActivity;
import com.develop.boris.mobilepos_ver3.data.ConsumeType;
import com.develop.boris.mobilepos_ver3.data.ConsumeTypeDAO;
import com.develop.boris.mobilepos_ver3.data.Customer;
import com.develop.boris.mobilepos_ver3.data.CustomerDAO2;
import com.develop.boris.mobilepos_ver3.data.Product;
import com.develop.boris.mobilepos_ver3.data.ProductDAO;
import com.develop.boris.mobilepos_ver3.data.Sale;
import com.develop.boris.mobilepos_ver3.data.SaleDAO;
import com.develop.boris.mobilepos_ver3.data.SaleProducts;
import com.develop.boris.mobilepos_ver3.data.SaleProductsDAO;
import com.develop.boris.mobilepos_ver3.myadapter.MySaleProductsAdapter;
import com.develop.boris.mobilepos_ver3.myadapter.MySpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by user on 2017/12/20.
 */

public class ItemListFragment extends Fragment {
  // Singleton
  private static ItemListFragment instance;

  public static ItemListFragment newInstance() {
    if (instance == null) {
      instance = new ItemListFragment();
    }
    return instance;
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    activity = (SaleBuilderActivity) context;
  }


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  //
  private SaleBuilderActivity activity;
  // UI
  public TextView id;
  public TextView date;
  public EditText customer;
  private TextView customerNameDisplay;
  public EditText mileage;
  public TextView totalPrice;
  public ListView itemListView;
  private Spinner consumeTypeSpinner;
  // Data
  private ArrayList<SaleProducts> itemList;
  public MySaleProductsAdapter adapter;
  private int customerId;
  private int consumeTypeId;
  private ArrayList<ConsumeType> consumeTypesList;
  private ConsumeType consumeType;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_scaned_items, container, false);

    initData();
    initView(rootView);
//        for (int i = 1; i < 10; i++) {
//            SaleProducts p1 = new SaleProducts();
//            p1.setName("商品" + i);
//            p1.setQty(i);
//            p1.setUnit("pc");
//            p1.setPrice(i * 0.85);
//            itemList.add(p1);
//        }
    itemListView = (ListView) rootView.findViewById(R.id.lv_scanned_items);
    itemListView.setFocusable(true);
    adapter = new MySaleProductsAdapter(getActivity(), itemList);
    itemListView.setAdapter(adapter);

    double sum = 0;
    for (SaleProducts sp : itemList) {
      sum += sp.getQty() * sp.getPrice();
    }

    totalPrice.setText(String.valueOf(sum));

    return rootView;
  }

  // Data initialize
  private void initData() {
    customerId = activity.getCustomerId();
    consumeTypeId = activity.getConsumeType();
    if (activity.saleProductsList != null) {
      itemList = activity.saleProductsList;
    } else {
      itemList = new ArrayList<>();
    }
  }

  // view initialize
  private void initView(View view) {
    // UI initialize
    id = (TextView) view.findViewById(R.id.tv_sale_id);
    date = (TextView) view.findViewById(R.id.tv_sale_date);
    customer = (EditText) view.findViewById(R.id.et_sale_customer);
    customerNameDisplay = (TextView) view.findViewById(R.id.tv_sale_customer);
    consumeTypeSpinner = (Spinner) view.findViewById(R.id.spinner_consume_type);
    totalPrice = (TextView) view.findViewById(R.id.tv_sale_total_price);
    mileage = (EditText) view.findViewById(R.id.et_sale_mileage);
    // display data
    date.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    if (customerId == -1) {
      customer.setText("");
      customer.setVisibility(View.VISIBLE);
      customerNameDisplay.setVisibility(View.GONE);
    } else {
      CustomerDAO2 customerDAO = new CustomerDAO2(getActivity());
      Customer thisCustomer = customerDAO.getDataById(customerId);
      customer.setText(thisCustomer.getName() + " " + thisCustomer.getPlate());
      customer.setVisibility(View.GONE);
      customerNameDisplay.setText(thisCustomer.getName() + " " + thisCustomer.getPlate());
      customerNameDisplay.setVisibility(View.VISIBLE);
    }
    // 消費類型spinner
    ConsumeTypeDAO consumeTypeDAO = new ConsumeTypeDAO(getActivity());
    consumeTypesList = consumeTypeDAO.getAllData();
    MySpinnerAdapter adapter = new MySpinnerAdapter(getActivity(), consumeTypeDAO.getAllData());
    consumeTypeSpinner.setAdapter(adapter);
    consumeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        consumeType = consumeTypesList.get(i);
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    SaleDAO saleDAO = new SaleDAO(getActivity());

    //訂單流水號
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMdd");
    String date = dateFormat.format(new Date());
    Sale lastSale = saleDAO.getLastOneByDate(date);
    if (lastSale == null) {
      id.setText(date + "001");
    } else {
      long newSaleId = saleDAO.getLastOneByDate(date).getId() + 1;
      id.setText(String.valueOf(newSaleId)); // 後面編號要遞增，儲存在sharedPerference?
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.i("ItemList", "OnResume, " + itemList.size());
    refreshData();
  }

  public void saveSale(ArrayList<SaleProducts> saleProductsList) {
    //儲存銷售單
    this.itemList = saleProductsList;
    //1. 取得畫面資料for sales
    //銷售單
    Sale sale = new Sale();
    CustomerDAO2 customerDAO = new CustomerDAO2(getActivity());
    SaleDAO saleDAO = new SaleDAO(getActivity());


    sale.setId(Integer.parseInt(id.getText().toString()));// 程式生成的流水號
    // 沒有客戶資料要先建立
    if (customerId == -1) {
      Customer newCustomer = new Customer();
      newCustomer.setPlate(customer.getText().toString());
      newCustomer.setName("");
      customerDAO.insert(newCustomer);
      sale.setCustomer(customerDAO.getLastOne().getId());
    } else {
      sale.setCustomer(customerId);
    }

    // test
//        consumeTypeId = 1;

    sale.setConsumeType(consumeType.getId());
    sale.setTotalPrice(Integer.parseInt(totalPrice.getText().toString()));
    if (mileage.getText().toString().equals("")) {
      sale.setMileage(0);
    } else {
      sale.setMileage(Integer.parseInt(mileage.getText().toString()));
    }

    //2. 取得dataarray資料
    //3. 個別儲存
    //4. 修改庫存量
    saleDAO.insert(sale);
    //商品列表儲存
    long saleId = sale.getId();
    SaleProductsDAO saleProductsDAO = new SaleProductsDAO(getActivity());
    ProductDAO productDAO = new ProductDAO(getActivity());
    for (SaleProducts sp : itemList) {
      sp.setId(saleId);
      Product p = productDAO.getDataById(sp.getProductId());
      p.setQty(p.getQty() - sp.getQty());
      productDAO.update(p);
      saleProductsDAO.insert(sp);
    }

    safeProductQtyAlert();
  }

  private void safeProductQtyAlert() {
    ProductDAO productDAO = new ProductDAO(getActivity());
    ArrayList alertList = productDAO.getAlertData();
    if (alertList.size() > 0) {
      Intent it = new Intent(getContext(), QuantityAlertPageActivity.class);
      TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
      stackBuilder.addParentStack(QuantityAlertPageActivity.class);
      stackBuilder.addNextIntent(it);
      PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

//      PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, it, PendingIntent.FLAG_UPDATE_CURRENT);

      Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon_96px);
      Notification notification = new NotificationCompat.Builder(getActivity())
              .setLargeIcon(largeIcon)
              .setSmallIcon(R.mipmap.app_icon_96px)
              .setContentTitle("商品庫存量不足警報")
              .setContentText("請檢查庫存不足之商品")
              .setWhen(System.currentTimeMillis())
              .setContentIntent(pendingIntent)
              .setAutoCancel(true)
              .setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND)
              .build();
      NotificationManager manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
      manager.notify(1, notification);
    }
  }




  public void refreshData() {
    itemListView.setAdapter(adapter);
    // 計算商品總價
    int sum = 0;
    for (SaleProducts sp : itemList) {
      sum += sp.getQty() * (int) sp.getPrice();
    }

    totalPrice.setText(String.valueOf(sum));
  }


}
