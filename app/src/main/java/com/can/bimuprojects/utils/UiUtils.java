package com.can.bimuprojects.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.can.bimuprojects.application.BimuApplication;

/**
 * Created by can on 2017/4/12.
 */
public class UiUtils {

    public static void postDelayed(Runnable runnable,long delayMills){
        BimuApplication.getHandler().postDelayed(runnable,delayMills);
    }
    public static void cancel(Runnable runnable){
        BimuApplication.getHandler().removeCallbacks(runnable);
    }

    public static Resources getResource() {
        return BimuApplication.getContext().getResources();
    }
    public static Context getContext(){
        return BimuApplication.getContext();
    }
    /** dip转换px */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /** px转换dip */

    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     *  图片变灰效果
     */
    public static final Bitmap grey(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap faceIconGreyBitmap = Bitmap
                .createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(faceIconGreyBitmap);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);
        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);
        paint.setColorFilter(colorMatrixFilter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return faceIconGreyBitmap;
    }

}
