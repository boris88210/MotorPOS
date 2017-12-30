package com.develop.boris.mobilepos_ver3.myDetail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Sale;
import com.develop.boris.mobilepos_ver3.data.SaleDAO;
import com.develop.boris.mobilepos_ver3.data.SaleProducts;
import com.develop.boris.mobilepos_ver3.data.SaleProductsDAO;
import com.develop.boris.mobilepos_ver3.myadapter.MySaleProductsAdapter;

import java.util.ArrayList;

public class MyDetailSaleActivity extends AppCompatActivity implements View.OnClickListener{
    
    // UI
    private TextView id;
    private TextView date;
    private TextView salesman;
    private TextView customer;
    private TextView consumeType;
    private TextView totalPrice;
    private ListView listView;
    
    // Data
    private SaleDAO saleDAO;
    private ArrayList<Sale> list;
    private Sale sale;
    
    private SaleProductsDAO productsDAO;
    private ArrayList<SaleProducts> productsList;
    private SaleProducts products;
    private MySaleProductsAdapter productsAdapter;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_detail_sale);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        getSupportActionBar().setTitle("銷售單");
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_144px_48dp_ver3);
        
        initData();
        
        initView();
        
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor_list,menu);
        return true;
    }
    
    private void initData() {
        long id = getIntent().getLongExtra("ID", -1);
        
        if (id == -1) {
            Toast.makeText(this, "此銷售單不存在!", Toast.LENGTH_SHORT).show();
            finish();
        }
        // 銷售單DAO
        saleDAO = new SaleDAO(this);
        sale = saleDAO.getDataById(id);
        
        
        productsDAO = new SaleProductsDAO(this);
        productsList = productsDAO.getAllProductsInTheSale(id);
        productsAdapter = new MySaleProductsAdapter(this, productsList);
        
        Log.i("List", "list size: " + productsList.size());
    }
    
    private void initView() {
        id = (TextView) findViewById(R.id.tv_sa_id);
        date = (TextView) findViewById(R.id.tv_sa_date);
        salesman = (TextView) findViewById(R.id.tv_salesman);
        customer = (TextView) findViewById(R.id.tv_sa_c_id);
        consumeType = (TextView) findViewById(R.id.tv_consume_type);
        totalPrice = (TextView) findViewById(R.id.tv_sa_total_price);
        listView = (ListView) findViewById(R.id.lv_sale_products);
        
        id.setText(sale.getId() + "");
        date.setText(sale.getDate().substring(0, 10));
        salesman.setText(sale.getSalesman() + "");
        customer.setText(sale.getCustomerName());
        consumeType.setText(sale.getConsumeTypeName());
        totalPrice.setText(sale.getTotalPrice() + "");
        
        listView.setAdapter(productsAdapter);
    }

    @Override
    public void onClick(View view) {

    }
}
