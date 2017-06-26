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
 * Created by can on 2017/4/12.
 * 没有数据的提示
 */

public class NoDataDialog extends Dialog{

    public NoDataDialog(Context context, String title,String str, View layout, int style) {
        super(context, style);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        layout.findViewById(R.id.btn_dialog_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoDataDialog.super.cancel();
            }
        });
        TextView tv_title = (TextView) layout.findViewById(R.id.tv_dialog_title);
        if(title!=null)
        tv_title.setText(title);

        TextView tv = (TextView) layout.findViewById(R.id.tv_dialog_content);
        if(str!=null)
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
