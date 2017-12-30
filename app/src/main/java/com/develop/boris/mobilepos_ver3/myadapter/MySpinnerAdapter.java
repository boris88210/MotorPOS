package com.develop.boris.mobilepos_ver3.myadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.MySQLData;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/27.
 */

public class MySpinnerAdapter extends MyBaseAdapter<MySQLData> {



  public MySpinnerAdapter(Context context, ArrayList dataList) {
    super(context, dataList);
  }

  @Override
  protected void rowSelected(MySQLData song, int index) {

  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    ViewHolder holder;
    if (view == null) {
      view = inflater.inflate(R.layout.row_single_line,viewGroup,false);
      holder = new ViewHolder();
      holder.itemName = view.findViewById(R.id.tv_single_line_item_name);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    // 取得此位置資料
    MySQLData data = dataList.get(position);
    holder.itemName.setText(data.getName());
    return view;
  }

  private class ViewHolder {
    TextView itemName;
  }
}
