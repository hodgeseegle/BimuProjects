package com.can.bimuprojects.utils;

/**
 * Created by can on 2017/08/15.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by can on 2017/08/03.
 * 一些view的统一操作
 */

public class ViewUtil {

    /**
     * listview强制停止滚动
     * @param lv
     */
    public static void stopListView(ListView lv, int index){
        if(lv!=null){
            lv.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
            lv.setSelection(index);
        }
    }

    /***
     * 设置popupWindow背景变暗
     */
    public static void setPopWinDark(PopupWindow pp , final Window window){
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.7f;
        window.setAttributes(lp);
        pp.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 1f;
                window.setAttributes(lp);
            }
        });
    }

    public static TextView getEmptyView(Context context, String string){
        TextView emptyView = new TextView(context);
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        emptyView.setText(string);
        emptyView.setTextSize(20);
        emptyView.setTextColor(Color.parseColor("#222222"));
        emptyView.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        emptyView.setVisibility(View.GONE);
        return emptyView;
    }

    /**
     * 设置没有数据时显示的内容
     * @param lv
     * @param context
     * @param string
     */
    public static void addEmptyView(ListView lv,Context context,String string){
        TextView emptyView = getEmptyView(context,string);
        ((ViewGroup)lv.getParent()).addView(emptyView);
        lv.setEmptyView(emptyView);
    }

}
