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
import com.develop.boris.mobilepos_ver3.data.Sale;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailSaleActivity;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/6.
 */

public class MySalesAdapter extends MyBaseAdapter<Sale> {

  private TextView saleId;
  private TextView date;
  private LinearLayout root;

  public MySalesAdapter(Context context, ArrayList<Sale> dataList) {
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
  protected void rowSelected(Sale sale, int index) {
    Intent it = new Intent(context, MyDetailSaleActivity.class);
    it.putExtra("ID", sale.getId());
    context.startActivity(it);
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    ViewHolder viewHolder = null;
    if (view == null) {
      view = inflater.inflate(R.layout.row_adapter, viewGroup, false);
      viewHolder = new ViewHolder();
      viewHolder.saleId = (TextView) view.findViewById(R.id.tv_adapter_title);
      viewHolder.date = (TextView) view.findViewById(R.id.tv_adapter_subtitle);
      viewHolder.icon = (ImageView) view.findViewById(R.id.img_adapter_img);
      viewHolder.root = (LinearLayout) view.findViewById(R.id.layout_adapter);
      view.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) view.getTag();
    }
    if (dataList.size() == 0) {
      viewHolder.saleId.setText("目前無資料可顯示");
      viewHolder.date.setText("");
    } else {
      // 取得這個位置的資料
      final Sale data = dataList.get(position);
      final int index = position;

      // 如果這個sale未完成，則變粉紅色
      int isComplete = data.getIsComplete();
      if (isComplete == 0) {
//                viewHolder.root.setBackgroundColor(0xffff8888);
//                viewHolder.saleId.setText(data.getId() + "(未完成)");
      } else {
      }
      viewHolder.saleId.setText(data.getId() + "");
      viewHolder.date.setText(data.getDate());
      if (data.getIcon() != null) {
        byte[] blob = data.getIcon();
        Bitmap bmp = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        viewHolder.icon.setImageBitmap(bmp);
      } else {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.document_144px_48dp);
        viewHolder.icon.setImageBitmap(bmp);
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
    TextView saleId;
    TextView date;
    ImageView icon;
    LinearLayout root;
  }


}
