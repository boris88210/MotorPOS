package com.develop.boris.mobilepos_ver3.myadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by user on 2017/11/15.
 */

// 繼承BaseAdapter 製作客製化的adapter
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    // 綁定的Context(activity)
    protected Context context;
    // adapter要呈現的資料
    protected ArrayList<T> dataList;
    // 引入額外layout(XML)的工具
    protected LayoutInflater inflater;
    
    
    // 規範被選被選擇的行為的抽象方法
    protected abstract void rowSelected(T song, int index);
    
    // Constructor
    protected MyBaseAdapter(Context context, ArrayList<T> dataList) {
        super();
        this.context = context;
        this.dataList = dataList;
        // 取得LayoutInflater物件
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public void setDataList(ArrayList<T> dataList) {
        this.dataList = dataList;
    }
    
    // 回傳此adapter需要呈現的資料數量
    @Override
    public int getCount() {
        return dataList.size();
    }
    
    // 回傳特定位置(position)的資料
    @Override
    public T getItem(int position) {
        return dataList.get(position);
    }
    
    // 回傳特定物件(Adapter物件)的id，這邊按照順序排列，位置就是id
    @Override
    public long getItemId(int position) {
        return position;
    }
}
