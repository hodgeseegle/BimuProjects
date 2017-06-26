package com.can.bimuprojects.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by can on 2017/4/14.
 * 品牌详情 子 listview
 */

public class BrandChildListView extends ListView {


    public BrandChildListView(Context context) {
        super(context);
    }

    public BrandChildListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BrandChildListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
