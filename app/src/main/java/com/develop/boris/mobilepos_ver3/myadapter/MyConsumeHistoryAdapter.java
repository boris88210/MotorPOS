package com.develop.boris.mobilepos_ver3.myadapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Sale;
import com.develop.boris.mobilepos_ver3.myDetail.MyDetailSaleActivity;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/6.
 */

public class MyConsumeHistoryAdapter extends MyBaseAdapter<Sale> {

  private long id;


  public MyConsumeHistoryAdapter(Context context, ArrayList dataList) {
    super(context, dataList);
  }


  @Override
  protected void rowSelected(Sale sale, int index) {
    Intent it = new Intent(context, MyDetailSaleActivity.class);
    it.putExtra("ID", sale.getId());
    context.startActivity(it);
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    ViewHolder holder = null;
    if (view == null) {
      view = inflater.inflate(R.layout.row_consume_history, viewGroup, false);
      holder = new ViewHolder();
      holder.mileage = (TextView) view.findViewById(R.id.tv_history_mileage);
      holder.consumeDate = (TextView) view.findViewById(R.id.tv_history_date);
      holder.root = (LinearLayout) view.findViewById(R.id.layout_consume_history);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    // 取得這個位置的資料
    final Sale data = this.dataList.get(position);
    this.id = data.getId();
    final int index = position;
    if (data.getMileage() == 0) {
      holder.mileage.setText(data.getConsumeTypeName());
    } else {
      holder.mileage.setText(String.valueOf(data.getMileage()) + " KM");
    }
    holder.consumeDate.setText(data.getDate().substring(0, 10));
    holder.root.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        rowSelected(data, index);
      }
    });
    return view;
  }

  class ViewHolder {
    TextView mileage;
    TextView consumeDate;
    LinearLayout root;
  }
}
