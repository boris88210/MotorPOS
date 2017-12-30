package com.develop.boris.mobilepos_ver3.myadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Customer;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailActivity;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailCustomerActivity;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/22.
 */

public class MyCustomerAdapter extends MyBaseAdapter<Customer> {
  //public static int numberOfSelected = 0; 取消選取刪除功能
  // UI
  private LinearLayout root;
  private TextView customerName;
  private TextView plate;
  private ImageView customerImage;
  // View data
  private int position;
  private View view;
  private ViewGroup viewGroup;

  // Constructor
  public MyCustomerAdapter(Context context, ArrayList dataList) {
    super(context, dataList);
  }

  // 定義欄位被選擇的行為
  @Override
  protected void rowSelected(Customer customer, int index) {
    Intent it = new Intent(context, MyDetailCustomerActivity.class);
    it.putExtra("ID", customer.getId());
    it.putExtra("MODE", MyDetailActivity.NORMAL_MODE);
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
    ViewHolder viewHolder = null;
    if (view == null) {
      view = inflater.inflate(R.layout.row_adapter, viewGroup, false);
      viewHolder = new ViewHolder();
      viewHolder.root = (LinearLayout) view.findViewById(R.id.layout_adapter);
      viewHolder.customerName = (TextView) view.findViewById(R.id.tv_adapter_subtitle);
      viewHolder.plate = (TextView) view.findViewById(R.id.tv_adapter_title);
      viewHolder.customerImage = (ImageView) view.findViewById(R.id.img_adapter_img);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }
    if (dataList.size() == 0) {
      viewHolder.customerName.setText("目前無資料可顯示");
      viewHolder.plate.setText("");
    } else {
      this.position = position;
      final int index = position;
      // 取得這個位置的資料
      final Customer data = dataList.get(position);
      viewHolder.customerName.setText(data.getName());
      viewHolder.plate.setText(data.getPlate());

      if (data.getIcon() != null) {
        byte[] blob = data.getIcon();
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        viewHolder.customerImage.setImageBitmap(bmp);
      } else {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.customer_144px_48dp);
        viewHolder.customerImage.setImageBitmap(bmp);
      }
      viewHolder.root.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          rowSelected(data, position);
        }
      });
    }
    return view;
  }

  private class ViewHolder {
    LinearLayout root;
    TextView customerName;
    TextView plate;
    ImageView customerImage;
  }
}
