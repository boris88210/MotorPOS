package com.develop.boris.mobilepos_ver3.myWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.develop.boris.mobilepos_ver3.data.SaleProducts;

/**
 * Created by user on 2017/12/17.
 */

public class MyListView extends ListView {

    private final int MAX_WIDTH = 120;//可滑動的最大距離(dp)
    private LinearLayout itemRoot;
    private int mlastX;//移動後的距離

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 換算dp to px
    private int dipToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    // 處理滑動的行為
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int maxLength = dipToPx(getContext(), MAX_WIDTH);//最大移動距離(px)
        // 點擊位置座標
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //我们想知道当前点击了哪一行
                int position = pointToPosition(x, y);
                if (position != INVALID_POSITION) {
                    SaleProducts data = (SaleProducts) getItemAtPosition(position);
                    itemRoot = data.getLayout();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (itemRoot != null) {

                    int scrollX = itemRoot.getScrollX();
                    int newScrollX = scrollX + mlastX - x;
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    } else if (newScrollX > maxLength) {
                        newScrollX = maxLength;
                    }
                    itemRoot.scrollTo(newScrollX, 0);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (itemRoot != null) {
                    int scrollX = itemRoot.getScrollX();
                    int newScrollX = scrollX + mlastX - x;
                    if (scrollX > maxLength / 2) {
                        newScrollX = maxLength;
                    } else {
                        newScrollX = 0;
                    }
                    itemRoot.scrollTo(newScrollX, 0);
                }
            }
            break;
        }

        mlastX = x;
        return super.onTouchEvent(event);
    }
}
