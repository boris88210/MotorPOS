package com.develop.boris.mobilepos_ver3.myadapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.ServiceType;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/20.
 */

public class MyServiceTypeAdapter extends MyBaseAdapter<ServiceType>  {

    private OnClickListener clickListener;
    private OnLongClickListener longClickListener;

    public MyServiceTypeAdapter(Context context, ArrayList<ServiceType> dataList,
                                OnClickListener clickListener, OnLongClickListener longClickListener) {
        super(context, dataList);
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @Override
    protected void rowSelected(ServiceType song, int index) {

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.row_single_line, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.tv_single_line_item_name);
            viewHolder.title.setHeight(72);
            viewHolder.root = (LinearLayout) view.findViewById(R.id.adaptorLayout);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (dataList.size() == 0) {
            viewHolder.title.setText("目前無資料可顯示");
        } else {
            // 取得這個位置的資料
            final ServiceType data = dataList.get(position);
            final int index = position;

            viewHolder.title.setText(data.getName());
            viewHolder.root.setTag(R.id.adaptorLayout,data);
            viewHolder.root.setBackgroundColor(0xffffffff);
            viewHolder.root.setOnClickListener(clickListener);
            viewHolder.root.setOnLongClickListener(longClickListener);
        }

        return view;
    }

    private class ViewHolder{
        TextView title;
        LinearLayout root;
    }
}
