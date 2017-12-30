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
import com.develop.boris.mobilepos_ver3.data.Supplier;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailActivity;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailSupplierActivity;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/25.
 */

public class MySupplierAdapter extends MyBaseAdapter<Supplier> {
  // UI
  private LinearLayout root;
  private TextView supplierName;
  private TextView supplierPhone;
  private ImageView supplierImage;

  public MySupplierAdapter(Context context, ArrayList dataList) {
    super(context, dataList);
  }


  @Override
  public int getCount() {
    if (dataList.size() == 0) {
      return 1;
    }
    return super.getCount();
  }

  @Override
  protected void rowSelected(Supplier supplier, int index) {
    Intent it = new Intent(context, MyDetailSupplierActivity.class);
    it.putExtra("ID", supplier.getId());
    it.putExtra("MODE", MyDetailActivity.NORMAL_MODE);
    context.startActivity(it);
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {


    //使用ViewHolder寫法

    ViewHolder holder = null;
    if (view == null) {
      view = inflater.inflate(R.layout.row_adapter, viewGroup, false);
      holder = new ViewHolder();
      holder.root = (LinearLayout) view.findViewById(R.id.layout_adapter);
      holder.supplierName = (TextView) view.findViewById(R.id.tv_adapter_title);
      holder.supplierPhone = (TextView) view.findViewById(R.id.tv_adapter_subtitle);
      holder.supplierImage = (ImageView) view.findViewById(R.id.img_adapter_img);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }

    if (dataList.size() == 0) {
      holder.supplierName.setText("目前無資料可顯示");
      holder.supplierPhone.setText("");
    } else {
      // 取得這個位置的資料
      final Supplier data = dataList.get(position);
      final int index = position;
      holder.supplierName.setText(data.getName());
      if (data.getPhone() == 0) {
        holder.supplierPhone.setText("");
      } else {
        holder.supplierPhone.setText("0" + data.getPhone());
      }
      if (data.getIcon() != null) {
        byte[] blob = data.getIcon();
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        holder.supplierImage.setImageBitmap(bmp);
      } else {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.supplier_144px_48dp);
        holder.supplierImage.setImageBitmap(bmp);
      }
      holder.root.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          rowSelected(data, index);
        }
      });
    }


    return view;
  }

  public class ViewHolder {
    private LinearLayout root;
    private TextView supplierName;
    private TextView supplierPhone;
    private ImageView supplierImage;
  }
}
