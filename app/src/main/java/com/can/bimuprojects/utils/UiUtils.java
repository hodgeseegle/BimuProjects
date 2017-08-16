package com.can.bimuprojects.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.can.bimuprojects.R;
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


    /**
     * 设置数据为空时显示内容
     */
    public static void setEmptyView(Context context,ListView lv){
        View view = LayoutInflater.from(context).inflate(R.layout.item_empty_listview,null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setVisibility(View.GONE);
        if(view!=null&&lv!=null){
            if(lv.getParent()!=null)
                ((ViewGroup)lv.getParent()).addView(view);
            lv.setEmptyView(view);
        }
    }

    private static int [] bgs = new int[]{R.drawable.shape_brand_color1, R.drawable.shape_brand_color2,R.drawable.shape_brand_color3,R.drawable.shape_brand_color4};
    /**
     * 添加textview
     */
    public static TextView getTV(Context context, String content, int index){
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,20,0);
        tv.setLayoutParams(params);
        tv.setMaxLines(1);
        tv.setTextSize(10);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setPadding(10,5,10,5);
        tv.setText(content);
        index = index%4;
        tv.setBackgroundResource(bgs[index]);
        tv.setTextColor(context.getResources().getIntArray(R.array.brand_colors)[index]);
        return tv;
    }
}
