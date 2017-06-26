package com.can.bimuprojects.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by can on 2017/4/14.
 */

public class BrandGridView extends GridView {
    public BrandGridView(Context context) {
        super(context);
    }

    public BrandGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BrandGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
