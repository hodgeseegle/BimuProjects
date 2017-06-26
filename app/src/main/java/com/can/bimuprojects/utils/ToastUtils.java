package com.can.bimuprojects.utils;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.can.bimuprojects.view.NoDataDialog;

/**
 * Created by can on 2017/4/14.
 */

/**
 * Toast统一管理类
 *
 */
public class ToastUtils
{
    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message)
    {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param resId
     */
    public static void showShort(Context context, int resId)
    {
        if (isShow)
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();

    }

    /**
     * 没有数据提示显示
     * @param context
     * @param content
     * @param view
     * @param style
     * @return
     */
    public static Dialog showDialog(Context context,String title,String content,View view ,int style) {
        NoDataDialog builder = new NoDataDialog(context,title,content,view ,style);
        builder.setCancelable(false);
        builder.show();
        return builder;
    }

    public static Dialog getDialog(Context context,String title,String content,View view ,int style,boolean canCanle ,boolean flag) {
        NoDataDialog builder = new NoDataDialog(context,title,content,view ,style);
        builder.setCancelable(canCanle);
        if(flag)
        builder.show();
        return builder;
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message)
    {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration)
    {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param resId
     * @param duration
     */
    public static void show(Context context, int resId, int duration)
    {
        if (isShow)
            Toast.makeText(context, resId, duration).show();
    }
}
