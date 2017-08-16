package com.can.bimuprojects.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.can.bimuprojects.utils.AppUtils;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.R;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.view.CircleDialog;

/**
 * Created by can on 2017/4/5.
 */
public class BaseActivity extends AppCompatActivity {

    private BimuApplication application;
    private BaseActivity oContext;
    private Dialog dialog_login ;//登录弹窗
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏效果
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
            View statusBarView = new View(window.getContext());
            int statusBarHeight = getStatusBarHeight(window.getContext());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
            params.gravity = Gravity.TOP;
            statusBarView.setLayoutParams(params);
            statusBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.color_app_bg));
            decorViewGroup.addView(statusBarView);
        }
        if (application == null) {
            // 得到Application对象
            application = (BimuApplication) getApplication();
        }
        oContext = this;
        addActivity();
    }

    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
    //销毁当个Activity方法
    public void removeActivity() {
        application.removeActivity(oContext);// 调用myApplication的销毁单个Activity方法
    }

    //销毁所有Activity方法
    public void removeALLActivity() {
        application.removeALLActivity();// 调用myApplication的销毁所有Activity方法
    }

    // 添加Activity方法
    public void addActivity() {
        application.addActivity(oContext);// 调用myApplication的添加Activity方法
    }

    public Dialog showDialog(String content) {
        CircleDialog builder = new CircleDialog(this,content, View.inflate(this, R.layout.dialog_circle,null),R.style.circle_dialog);
        if(!this.isFinishing())
            builder.show();
        return builder;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(null!=this.getCurrentFocus()){
            //AppUtils.hideSoftInput(this);
        }
        return super.onTouchEvent(event);
    }

    //展示登录弹窗
    public boolean showLoginDialog(){
        dialog_login = AppUtils.showSMSLoginDialog(this);
        if(AppUtils.isNeedShowLoginDialog()){
            if(!dialog_login.isShowing()&&!this.isFinishing()){
                dialog_login.show();
                return true;
            }
        }
        return false;
    }

}
