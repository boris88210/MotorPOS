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
import com.develop.boris.mobilepos_ver3.data.Product;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailProductActivity;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/15.
 */

public class MyProductAdapter extends MyBaseAdapter<Product> {

  // Constructor
  public MyProductAdapter(Context context, ArrayList dataList) {
    super(context, dataList);
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
  public View getView(int position, View view, ViewGroup viewGroup) {
    //使用ViewHolder寫法
    ViewHolder viewHolder = null;
    if (view == null) {
      view = inflater.inflate(R.layout.row_adapter, viewGroup, false);
      viewHolder = new ViewHolder();
      viewHolder.root = (LinearLayout) view.findViewById(R.id.layout_adapter);
      viewHolder.productName = (TextView) view.findViewById(R.id.tv_adapter_title);
      viewHolder.productModel = (TextView) view.findViewById(R.id.tv_adapter_subtitle);
      viewHolder.productImage = (ImageView) view.findViewById(R.id.img_adapter_img);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }
    if (dataList.size() == 0) {
      viewHolder.productName.setText("目前無資料可顯示");
      viewHolder.productModel.setText("");
      Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),R.mipmap.box_144px_48dp);
      viewHolder.productImage.setImageBitmap(bmp);
    } else {
      // 取得這個位置的資料
      final Product data = dataList.get(position);
      final int index = position;
      viewHolder.productName.setText(data.getName());
      viewHolder.productModel.setText(data.getModel());
      if (data.getIcon() != null) {
        byte[] blob = data.getIcon();
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        viewHolder.productImage.setImageBitmap(bmp);
      } else {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),R.mipmap.box_144px_48dp);
        viewHolder.productImage.setImageBitmap(bmp);
      }
      viewHolder.root.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          rowSelected(data, index);
        }
      });
    }
    return view;
  }

  private class ViewHolder {
    LinearLayout root;
    TextView productName;
    TextView productModel;
    ImageView productImage;
  }
}


