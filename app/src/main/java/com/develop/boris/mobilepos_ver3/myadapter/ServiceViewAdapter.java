package com.develop.boris.mobilepos_ver3.myadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;


import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Service;
import com.develop.boris.mobilepos_ver3.data.ServiceDAO;
import com.develop.boris.mobilepos_ver3.data.ServiceType;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/14.
 */

public class ServiceViewAdapter extends MyBaseAdapter {
    
    private View.OnClickListener onClickListener;
    private ServiceButtonAdapter buttonAdapter;
    private ServiceType serviceType;
    private ServiceDAO serviceDAO;
    private ArrayList<Service> buttonList;
    
    public ServiceViewAdapter(Context context, ArrayList<ServiceType> dataList) {
        super(context, dataList);
    }
    
    public ServiceViewAdapter(Context context, ArrayList<ServiceType> dataList, View.OnClickListener clickListener) {
        this(context, dataList);
        onClickListener = clickListener;
    }

    public void setButtonAdapter(ServiceButtonAdapter buttonAdapter) {
        this.buttonAdapter = buttonAdapter;
    }

    @Override
    protected void rowSelected(Object song, int index) {
    
    }
    
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.row_service_view, viewGroup, false);
            holder = new ViewHolder();
            holder.serviceTitle = (TextView) view.findViewById(R.id.tv_service_type_title);
            holder.serviceButtonsView = (GridView) view.findViewById(R.id.gv_service_buttons_view);
            serviceDAO = new ServiceDAO(context);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        
        serviceType = (ServiceType) dataList.get(i);
        holder.serviceTitle.setText(serviceType.getName());
        
        
        buttonList = serviceDAO.getDataByTypeId(serviceType.getId());
        
////        Test
//        Service s = new Service();
//        buttonList.add(s);
//        buttonList.add(s);
//        buttonList.add(s);
//        buttonList.add(s);
//        buttonList.add(s);
//        buttonList.add(s);
//        buttonList.add(s);
//        buttonList.add(s);
//        buttonList.add(s);
//        buttonList.add(s);
//        buttonList.add(s);
        
        buttonAdapter.setDataList(buttonList);
        holder.serviceButtonsView.setAdapter(buttonAdapter);
        return view;
    }
    
    private class ViewHolder {
        private TextView serviceTitle;
        private GridView serviceButtonsView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        buttonAdapter.notifyDataSetChanged();
    }
}
