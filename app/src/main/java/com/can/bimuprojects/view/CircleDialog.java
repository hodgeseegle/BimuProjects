package com.can.bimuprojects.view;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.can.bimuprojects.R;

/**
 * Created by can on 2017/4/11.
 * 圆角dialog
 */

public class CircleDialog extends Dialog {

    public CircleDialog(Context context, String str,View layout, int style) {
        super(context, style);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        layout.findViewById(R.id.btn_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircleDialog.super.cancel();
            }
        });
        TextView tv = (TextView) layout.findViewById(R.id.tv_dialog_content);
        tv.setText(str);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = width*3/4;
        params.height = height*1/4;
        window.setAttributes(params);
    }

}