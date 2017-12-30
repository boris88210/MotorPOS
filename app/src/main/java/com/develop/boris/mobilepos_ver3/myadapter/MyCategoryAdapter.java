package com.develop.boris.mobilepos_ver3.myadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Category;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/13.
 */

public class MyCategoryAdapter extends MyBaseAdapter<Category> {

    private View.OnLongClickListener longClickListener;
    private View.OnClickListener clickListener;

    public MyCategoryAdapter(Context context, ArrayList<Category> dataList,View.OnClickListener clickListener, View.OnLongClickListener longClickListener) {
        super(context, dataList);
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }
    
    @Override
    public int getCount() {
        if (dataList.size() == 0) {
            return 1;
        }
        return super.getCount();
    }
    
    @Override
    protected void rowSelected(Category song, int index) {

    }
    
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.row_single_line, viewGroup, false);
            holder = new ViewHolder();
            holder.cateName = view.findViewById(R.id.tv_single_line_item_name);
            holder.root = view.findViewById(R.id.adaptorLayout);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Category data = dataList.get(i);
        if (dataList.size() == 0){
            holder.cateName.setText("沒有資料");
        } else {
            holder.cateName.setText(data.getName());
        }
        holder.root.setTag(R.id.adaptorLayout, data);
        holder.root.setBackgroundColor(context.getResources().getColor(R.color.colorBackground));
        holder.root.setOnLongClickListener(longClickListener);
        holder.root.setOnClickListener(clickListener);
        
        return view;
    }
    
    public class ViewHolder {
        TextView cateName;
        LinearLayout root;
    }
    
}
