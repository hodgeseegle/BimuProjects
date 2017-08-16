package com.can.bimuprojects.utils;

/**
 * Created by can on 2017/08/15.
 */

import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.ListView;

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

}
