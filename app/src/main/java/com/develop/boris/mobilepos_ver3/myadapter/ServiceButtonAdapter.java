package com.develop.boris.mobilepos_ver3.myadapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Service;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/14.
 */

public class ServiceButtonAdapter extends MyBaseAdapter {

  private View.OnClickListener onClickListener;

  private SparseBooleanArray buttonStatus;
//    private ServiceDAO serviceDAO;// 存取服務所包含商品id
//    private ProductDAO productDAO;// 透過商品id找商品

  public ServiceButtonAdapter(Context context, ArrayList<Service> dataList) {
    super(context, dataList);
  }



  public ServiceButtonAdapter(Context context, ArrayList<Service> dataList, View.OnClickListener clickListener) {
    this(context, dataList);
    onClickListener = clickListener;
  }

  public void setButtonStatus(SparseBooleanArray buttonStatus) {
    this.buttonStatus = buttonStatus;
  }

  @Override
  protected void rowSelected(Object song, int index) {
  }

  @Override
  public View getView(int position, View view, ViewGroup viewGroup) {
    //建立ViewHolder保存畫面元件，不需要重新findViewById
    ViewHolder holder = null;
    if (view == null) {
      view = inflater.inflate(R.layout.row_service_button, viewGroup, false);
      holder = new ViewHolder();
      holder.serviceButton = view.findViewById(R.id.btn_service_button);
//            serviceDAO = new ServiceDAO(context);
//            productDAO = new ProductDAO(context);
      view.setTag(holder);//將viewHolder存在畫面中
    } else {
      holder = (ViewHolder) view.getTag();
    }
    Service data = (Service) dataList.get(position);//取出此位置的資料
    String str = data.getName();
    Log.i("buttonAdapter","service status:"+ data.isPressed());

    if (buttonStatus.get(data.getId())) {
      holder.serviceButton.setBackground(context.getResources().getDrawable(R.drawable.button_background_pressed));
    } else {

    }


    holder.serviceButton.setText(str);
    holder.serviceButton.setTag(data);//將服務存到按鈕中
    holder.serviceButton.setOnClickListener(onClickListener);

    return view;
  }

  private class ViewHolder {
    private Button serviceButton;
  }
}
