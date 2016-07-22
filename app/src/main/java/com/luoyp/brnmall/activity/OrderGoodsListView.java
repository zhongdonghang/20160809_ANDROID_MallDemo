package com.luoyp.brnmall.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lyp3314@gmail.com on 16/7/22.
 */

public class OrderGoodsListView extends ListView {
    public OrderGoodsListView(Context context) {
        super(context);
    }

    public OrderGoodsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OrderGoodsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置不滚动
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
