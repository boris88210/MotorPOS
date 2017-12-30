package com.develop.boris.mobilepos_ver3.myadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.SaleProducts;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/6.
 */

public class MySaleProductsAdapter extends MyBaseAdapter<SaleProducts> {

    private View.OnClickListener clickListener;
    private DataHolder dataHolder;

    public MySaleProductsAdapter(Context context, ArrayList dataList) {
        super(context, dataList);
        clickListener = (View.OnClickListener) context;
    }

    @Override
    protected void rowSelected(SaleProducts song, int index) {
    }

    @Override
    public SaleProducts getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        final int index = position;
        ViewHolder holder = null;
        if (view == null || view.getTag() == null) {
            view = inflater.inflate(R.layout.row_sale_products, viewGroup, false);
            holder = new ViewHolder();
            holder.productName = (TextView) view.findViewById(R.id.tv_sale_products_name);
            holder.productQty = (TextView) view.findViewById(R.id.tv_sale_product_qty);
            holder.productPrice = (TextView) view.findViewById(R.id.tv_sale_product_price);
            holder.subtotal = (TextView) view.findViewById(R.id.tv_sale_product_subtotal);
            holder.delete = (TextView) view.findViewById(R.id.tv_delet);
            holder.delete.setOnClickListener(clickListener);
            holder.itemLayout = (LinearLayout) view.findViewById(R.id.adaptorLayout);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // 取得這個位置的資料
        final SaleProducts data = dataList.get(position);

        holder.productName.setText(data.getName());
        holder.productQty.setText(String.format("%3d %s", data.getQty(), data.getUnit()));
        holder.productPrice.setText(String.format("%d", data.getPrice()));
        holder.subtotal.setText(String.format("%d",data.getQty() * data.getPrice()));
        data.setLayout(holder.itemLayout);
        // 離開畫面後會滑動歸位
        data.getLayout().scrollTo(0, 0);
        return view;
    }

    public void removeItem (int position) {
        dataList.remove(position);
        notifyDataSetChanged();
    }


    class ViewHolder {
        TextView productName;
        TextView productQty;
        TextView productPrice;
        TextView subtotal;
        TextView delete;
        LinearLayout itemLayout;

    }

    public class DataHolder {
        public LinearLayout itemLayout;
        public ArrayList<SaleProducts> datalist;

        public DataHolder(LinearLayout itemLayout, ArrayList<SaleProducts> datalist) {
            this.itemLayout = itemLayout;
            this.datalist = datalist;
        }
    }
}
