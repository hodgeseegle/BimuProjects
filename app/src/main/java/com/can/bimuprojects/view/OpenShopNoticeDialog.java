package com.can.bimuprojects.view;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.BrandActivity;

/**
 * Created by can on 2017/07/29.
 */

public class OpenShopNoticeDialog extends Dialog{


    public OpenShopNoticeDialog(Context context, View layout, int style) {
        super(context, style);
        layout.findViewById(R.id.tv_dialog_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenShopNoticeDialog.super.cancel();
            }
        });
        setContentView(layout);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }


}
