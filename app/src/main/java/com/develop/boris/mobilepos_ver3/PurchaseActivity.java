package com.develop.boris.mobilepos_ver3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.boris.mobilepos_ver3.data.Customer;
import com.develop.boris.mobilepos_ver3.data.SaleProducts;
import com.develop.boris.mobilepos_ver3.fragment.ItemListFragment;
import com.develop.boris.mobilepos_ver3.fragment.ItemStorer;
import com.develop.boris.mobilepos_ver3.fragment.MaintainService;
import com.develop.boris.mobilepos_ver3.fragment.PurchaseListFragment;
import com.develop.boris.mobilepos_ver3.fragment.ScannerFragment;

import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        ItemStorer {

  private android.support.v4.app.FragmentTransaction fragmentTransaction;
  private PurchaseListFragment itemListFragment = PurchaseListFragment.newInstance();
  private ScannerFragment scanner = ScannerFragment.newInstance();
  public ArrayList<SaleProducts> saleProductsList;
  private Customer customer;
  private int customerId;
  private int consumeType;
  private BottomNavigationView navigation;


  // BottomNavigationView的行為
  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    fragmentTransaction = getSupportFragmentManager().beginTransaction();
    switch (item.getItemId()) {
      case R.id.navigation_scanner:
        fragmentTransaction.replace(R.id.container, ScannerFragment.newInstance())
                .commit();
        return true;

      case R.id.navigation_purchase_list:
        fragmentTransaction.replace(R.id.container, PurchaseListFragment.newInstance())
                .commit();
        return true;
    }
    return false;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_purchase);

    initData();
    // BottomNavigationbar
    navigation = (BottomNavigationView) findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(this);
    // 不同的路徑進入要顯示不同的fragment
    if (customerId != -1) {
      onNavigationItemSelected(navigation.getMenu().getItem(1));
    } else {
      onNavigationItemSelected(navigation.getMenu().getItem(0));
    }
    getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_144px_48dp_ver3);
  }

  private void initData() {
    // 取得Intent資料
    customerId = getIntent().getIntExtra("CustomerId", -1);
    consumeType = getIntent().getIntExtra("ConsumeType", 1);
  }

  // override 音量鍵按下的功能，按下可變化ScannerFragment的商品數量
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    int scannerFragmentId = navigation.getMenu().getItem(0).getItemId();
    int currentFragmentId = navigation.getSelectedItemId();
    if (currentFragmentId == scannerFragmentId) {
      Log.i("salebuilder", " Key down in scanner");

      String s = scanner.getProductQty();
      int i;
      if (s.equals("")) {
        i = 0;
      } else {
        i = Integer.parseInt(scanner.getProductQty());
      }
      switch (keyCode) {
        // 音量鍵UP，商品數量++
        case KeyEvent.KEYCODE_VOLUME_UP:
          i++;
          //與庫存量做比對
          //若大於庫存，則做提示
          scanner.setProductQty(String.valueOf(i));
          return true;
        // 音量鍵UP，商品數量--
        case KeyEvent.KEYCODE_VOLUME_DOWN:
          if (i <= 0) {
            i = 0;
          } else {
            i--;
          }
          scanner.setProductQty(String.valueOf(i));
          return true;
        default:
          return super.onKeyDown(keyCode, event);
      }
    }
    return super.onKeyDown(keyCode, event);
  }

  // override 音量鍵放開的功能，放開後將ScannerFragment的商品數量存到ItemListFragment內
  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    int scannerFragmentId = navigation.getMenu().getItem(0).getItemId();
    int currentFragmentId = navigation.getSelectedItemId();
    if (currentFragmentId == scannerFragmentId) {
      switch (keyCode) {
        // 音量鍵UP，商品數量++
        case KeyEvent.KEYCODE_VOLUME_UP:
        case KeyEvent.KEYCODE_VOLUME_DOWN:
          String s = scanner.getProductQty();
          int qty = Integer.parseInt(s);
          if (scanner.scannedItem != null) {
            updateProductQty(scanner.scannedItem, qty);
          }
          return true;
        default:
          return super.onKeyUp(keyCode, event);
      }
    } else {
      return super.onKeyUp(keyCode, event);
    }
  }

  public void updateProductQty(SaleProducts product, int productQty) {
    int index = saleProductsList.indexOf(product);
    if (index != -1) {
      if (productQty <= 0) {
        saleProductsList.remove(index);
      } else {
        saleProductsList.get(index).setQty(productQty);
      }
    }
  }


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_delet:
        int position = itemListFragment.itemListView.getPositionForView(v);
        itemListFragment.adapter.removeItem(position);
        itemListFragment.refreshData();
        break;
    }
  }

  @Override
  public void storeItem(SaleProducts item) {
    if (saleProductsList == null) {
      saleProductsList = new ArrayList<>();
    }

    addItemToList(item);
//        Log.i("SaleBuilder", "storeItem" + saleProductsList.size());
  }

  // 若清單沒有相同商品則新增，若有相同則增加數量
  public void addItemToList(SaleProducts item) {
//            Log.i("ItemListFragment", "addItemToList:" );
    if (saleProductsList.contains(item)) {
      Log.i("ItemListFragment", "addItemToList, already contains object");
      saleProductsList.get(saleProductsList.indexOf(item)).setQty(saleProductsList.get(saleProductsList.indexOf(item)).getQty() + 1);
    } else {
      Log.i("ItemListFragment", "addItemToList, New Item!");
      saleProductsList.add(item);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_editor_list, menu);
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
      if (saleProductsList == null || saleProductsList.size() == 0) {
        Toast.makeText(this, "沒有商品，採購不成立", Toast.LENGTH_SHORT).show();
      } else {
        try {
          itemListFragment.saveSale(saleProductsList);
        } catch (Exception e) {
          e.printStackTrace();
          Toast.makeText(this, "請到採購清單頁面確認商品再儲存", Toast.LENGTH_SHORT).show();
          return super.onOptionsItemSelected(item);
        }
        Toast.makeText(this, "銷售單已儲存", Toast.LENGTH_SHORT).show();
        finish();
      }
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public int getCustomerId() {
    return customerId;
  }

  public int getConsumeType() {
    return consumeType;
  }

  public Customer getCustomer() {

    return customer;
  }

  @Override
  protected void onStop(){
    super.onStop();
//    scanner.cleanInfo();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
//    scanner.cleanInfo();
  }
}
