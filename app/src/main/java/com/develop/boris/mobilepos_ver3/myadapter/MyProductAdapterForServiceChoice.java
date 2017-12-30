package com.develop.boris.mobilepos_ver3.myadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Product;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailProductActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 2017/11/15.
 */

public class MyProductAdapterForServiceChoice extends MyBaseAdapter<Product> implements CompoundButton.OnCheckedChangeListener {

//  private HashMap<Integer, Boolean> selectedProducts;
  private SparseBooleanArray selectedProducts;

  // Constructor
  public MyProductAdapterForServiceChoice(Context context, ArrayList dataList) {
    super(context, dataList);
  }

//  public void setSelectedProducts(HashMap<Integer, Boolean> selectedProducts) {
//    this.selectedProducts = selectedProducts;
//  }

  public void setSelectedProducts(SparseBooleanArray selectedProducts) {
    this.selectedProducts = selectedProducts;
  }

  // 定義欄位被選擇的行為
  @Override
  protected void rowSelected(Product product, int index) {
    Intent it = new Intent(context, MyDetailProductActivity.class);
    it.putExtra("ID", product.getId());
    context.startActivity(it);
  }

  @Override
  public int getCount() {
    if (dataList.size() == 0) {
      return 1;
    }
    return super.getCount();
  }

  // 回傳特定位置資料的adapter畫面
  @Override
  public View getView(final int position, View view, ViewGroup viewGroup) {
    //使用ViewHolder寫法
    ViewHolder holder = null;
    if (view == null) {
      view = inflater.inflate(R.layout.row_service_product_multichoice, viewGroup, false);
      holder = new ViewHolder();
      holder.root = (LinearLayout) view.findViewById(R.id.layout_adapter);
      holder.productName = (TextView) view.findViewById(R.id.tv_adapter_product_title);
      holder.productModel = (TextView) view.findViewById(R.id.tv_adapter_product_subtitle);
      holder.isChecked = view.findViewById(R.id.checkBox_product_choice);


      view.setTag(holder);

    } else {
      holder = (ViewHolder) view.getTag();
    }
    if (dataList.size() == 0) {
      holder.productName.setText("目前無資料可顯示");
      holder.productModel.setText("");
    } else {
      // 取得這個位置的資料
      final Product data = dataList.get(position);
      final int index = position;
      holder.productName.setText(data.getName());
      holder.productModel.setText(data.getModel());

      holder.isChecked.setTag(data.getId());
      holder.isChecked.setOnCheckedChangeListener(this);
      holder.isChecked.setChecked(selectedProducts.get(data.getId()));

      holder.root.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//          rowSelected(data, index);
          Log.i("ServiceItems",selectedProducts.toString());
        }
      });
    }
    return view;
  }

  @Override
  public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
    int productId = (int) compoundButton.getTag();
    selectedProducts.put(productId,b);
  }

  private class ViewHolder {
    LinearLayout root;
    TextView productName;
    TextView productModel;
    ImageView productImage;
    CheckBox isChecked;
  }

  private class DataHolder {
    boolean[] isProcuctChecked;


  }


}


