package com.can.bimuprojects.utils;

/**
 * Created by can on 2017/08/15.
 */

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;

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

}
