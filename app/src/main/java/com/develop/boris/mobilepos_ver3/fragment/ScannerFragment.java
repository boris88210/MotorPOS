package com.develop.boris.mobilepos_ver3.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.boris.mobilepos_ver3.R;
import com.develop.boris.mobilepos_ver3.data.Product;
import com.develop.boris.mobilepos_ver3.data.ProductDAO;
import com.develop.boris.mobilepos_ver3.data.SaleProducts;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 2017/12/20.
 */

public class ScannerFragment extends Fragment {


  // 此Fragment 只有一個物件存在
  private static ScannerFragment instance;

  private ScannerFragment() {
  }

  // Singleton
  public static ScannerFragment newInstance() {
    if (instance == null) {
      instance = new ScannerFragment();
    }
    return instance;
  }

  // UI
  private SurfaceView cameraPreview;
  private TextView scanResult;
  private EditText scannedBarcode;
  private TextView productName;
  private EditText productQty;
  private TextView productUnit;
  private Button searchBarcode;

  // Data
  private Product product;
  private ProductDAO dao;
  private ArrayList<Product> searchResultList;
  private ArrayList<Product> tempList;

  public SaleProducts scannedItem;

  // BarcodeDetector
  private BarcodeDetector barcodeDetector;
  private CameraSource cameraSource;

  // PermissionID
  private final int RequestCameraPermissionID = 1001;
  private final int ScanningInterval = 3000;// 毫秒

  // 紀錄所屬的Activity，為了可以通知Activity資料更新
  private ItemStorer itemStorer;

  // 註冊Activity
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    Log.i("ScannerFragment", "OnAttach");
    itemStorer = (ItemStorer) context;
  }

  //
  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initData();
    Log.i("ScannerFragment", "OnCreate");
  }

  //
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_scanner, container, false);
    initView(rootView);
    initHandler();
    Log.i("ScannerFragment", "OnCreateView");
    return rootView;
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.i("ScannerFragment", "OnResume");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.i("ScannerFragment", "OnPause");

  }

  @Override
  public void onStop() {
    super.onStop();
    Log.i("ScannerFragment", "OnStop");

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    Log.i("ScannerFragment", "OnDestroyView");
    cleanInfo();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.i("ScannerFragment", "OnDestroy");

  }

  @Override
  public void onDetach() {
    super.onDetach();
    Log.i("ScannerFragment", "OnDetach");
    cleanInfo();

  }


  private void initHandler() {
    searchBarcode.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //取得barcode
        String barcode = scannedBarcode.getText().toString();
        // 比對商品
        Product p = findProductByBarcode(barcode);
        if (p != null) {
          Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
          vibrator.vibrate(300);
          productQty.setText("1");
        }

        // 儲存到清單
        storeScannedItem(p);
      }
    });

    // Barcode Detector設定
    // 建立物件
    barcodeDetector = new BarcodeDetector.Builder(getActivity())
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build();
    cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector)
            .setRequestedPreviewSize(1024, 768)
            .setAutoFocusEnabled(true)
            .build();

    // 設定行為
    cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
      @Override
      public void surfaceCreated(SurfaceHolder holder) {
        //要用到預覽畫面要先取得相機權限
        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
        } else {
          //有取得權限的情況
          try {
            //開始camera source
            cameraSource.start(cameraPreview.getHolder());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

      @Override
      public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

      }

      @Override
      public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
      }
    });

    barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
      @Override
      public void release() {

      }

      @Override
      public void receiveDetections(Detector.Detections<Barcode> detections) {

        final SparseArray<Barcode> barcodes = detections.getDetectedItems();
        if (barcodes.size() != 0) {
          // // 建立一個執行緒，此執行緒在UI執行緒執行

          scannedBarcode.post(new Runnable() {
            @Override
            public void run() {
              // 取得掃描到的barcode
              String scannedResult = barcodes.valueAt(0).displayValue;
              // 震動回饋
              Vibrator vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
              vibrator.vibrate(300);

              scannedBarcode.setText(scannedResult);
              productQty.setText("1");
              // 比對商品
              Product p = findProductByBarcode(scannedResult);
              // 儲存到清單
              storeScannedItem(p);

              searchResultList = dao.getDataByBarcode(Long.parseLong(scannedResult));

            }
          });
          // 掃描間隔時間
          try {
            Thread.sleep(ScanningInterval);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    });
  }

  // 將接收的Product轉成SaleProduct，通知Activity要儲存此物件
  private void storeScannedItem(Product p) {
//            Log.i("Scanner", "storeScannerItem");
    if (p == null) {
      return;
    }
    SaleProducts sp = new SaleProducts();
    sp.setProductId(p.getId());
    sp.setName(p.getName());
    sp.setPrice(p.getUnitPrice());
    sp.setQty(Integer.parseInt(productQty.getText().toString()));
    sp.setUnit(p.getUnit());
    scannedItem = sp;
    itemStorer.storeItem(sp);
  }

  // 使用barcode找到商品資料
  private Product findProductByBarcode(String barcode) {
    if (barcode.equals("")) {
      Toast.makeText(getActivity(), "尚未輸入Barcode資訊", Toast.LENGTH_SHORT).show();
      return null;
    }
    searchResultList = dao.getDataByBarcode(Long.parseLong(barcode));
    Log.i("ScannerFragment", "search Result Size:" + searchResultList.size());
    if (searchResultList.size() == 1) {
      product = searchResultList.get(0);
      productName.setText(product.getName());
      productUnit.setText(product.getUnit());
      tempList.add(product);
      return product;
    } else if (searchResultList.size() == 0) {
      productName.setText("");
      productQty.setText("");
      Toast.makeText(getActivity(), "沒有此商品資料", Toast.LENGTH_SHORT).show();
    } else {
      Toast.makeText(getActivity(), "找到超過1項商品", Toast.LENGTH_SHORT).show();
    }
    return null;
  }


  private void initView(View view) {
    cameraPreview = (SurfaceView) view.findViewById(R.id.cameraPreview);
    scanResult = (TextView) view.findViewById(R.id.scanResult);
    scannedBarcode = (EditText) view.findViewById(R.id.et_scanResult);
    productName = (TextView) view.findViewById(R.id.et_scan_product_name);
    productQty = (EditText) view.findViewById(R.id.et_scan_product_qty);
    productUnit = (TextView) view.findViewById(R.id.tv_product_unit);
//            addItem = (Button) view.findViewById(R.id.btn_add_item);
    searchBarcode = (Button) view.findViewById(R.id.btn_search_barcode);


  }

  private void initData() {
    dao = new ProductDAO(getActivity());
    tempList = new ArrayList<>();
  }

  public String getProductQty() {
    return productQty.getText().toString();
  }

  public void setProductQty(String productQty) {
    this.productQty.setText(productQty);
  }


  public void cleanInfo() {
    if (scannedBarcode != null) {

      scannedBarcode.setText("");
    }
    if (productUnit != null) {

      productUnit.setText("");
    }
    if (productName != null) {
      productName.setText("");
    }
  }

}
