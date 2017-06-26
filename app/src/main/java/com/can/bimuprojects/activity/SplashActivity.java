package com.can.bimuprojects.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Module.Request.SNRequest;
import com.can.bimuprojects.Module.Response.SNResponse;
import com.can.bimuprojects.network.utils.Constants;
import com.can.bimuprojects.utils.PrefUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Response.LaunchPictureResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;


/*
加载启动页图片的逻辑

一.
先判断软件是否第一次使用
1.如果是，则取加载默认的图片
2.如果不是，那么去文件夹拿图片，
文件夹有图片就加载文件夹图片，没有就还是显示默认图片

二.
将第一次使用的标志记为false，
然后开始调用获取图片的接口，
服务器返回最新的ImageUrl，
再与本地存储的ImageUrl对比，
1.如果不同，则使用此ImageUrl下载图片，
下载完成后，更新本地存储的ImageUrl。
2.如果相同，则不下载图片
 */


public class SplashActivity extends Activity {
    private Intent intent;
    private ImageView ivStart;
    private String imgUrl;

    private SwitchHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivStart = (ImageView) findViewById(R.id.activity_skip_iv_start);
        if(Util.isOnMainThread()&&!this.isFinishing()){
                Glide.with(SplashActivity.this).load(R.drawable.img_open_app).centerCrop().into(ivStart);
        }
        if(LoginUtils.getLoginUid()==null||LoginUtils.getLoginUid().equals("")){
            LoginUtils.setLoginStatus(true);
            AppUtils.setSNWithoutLogin(SplashActivity.this);
        }
        if (AppUtils.isNetworkOK(this)) {
            getLatestImageUrl();
        }
        handler = new SwitchHandler();
        handler.sendEmptyMessageDelayed(1,1500);
    }

    private void getLatestImageUrl() {
        HttpUtils.postWithoutUid(MethodConstant.LAUNCH_PICTURE, "", new
                ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        final LaunchPictureResponse response = (LaunchPictureResponse) receive
                                .getResponse();
                        if (response!=null&&response.getExe_success().equals("1")) {
                            imgUrl = response.getImg_url();
                            if (!TextUtils.isEmpty(imgUrl)) {
                                if(Util.isOnMainThread()&&!SplashActivity.this.isFinishing())
                                        Glide.with(SplashActivity.this).load(imgUrl).centerCrop().into(ivStart);
                            } else if (getImageUrl() != response.getImg_url()) {
                                saveImageUrl(response.getImg_url());
                            }
                        }
                    }
                }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
            }
        }, LaunchPictureResponse.class);
    }


    class SwitchHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //已登录，跳转主页面
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                    break;
                case 3:
                    //第一次使用，跳转引导页
                    intent = new Intent(SplashActivity.this, GuideActivity.class);
                    break;
            }

            SplashActivity.this.startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            SplashActivity.this.finish();
        }
    }

    /**
     * 获取本地存储的图片的url
     */
    private String getImageUrl() {
        SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
        String url = sp.getString("imageUrl", null);
        return url;
    }

    /**
     * 将服务器最新的图片url存放起来
     */
    private void saveImageUrl(String imgUrl) {
        SharedPreferences sp = this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("imageUrl", imgUrl);
        edit.apply();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtils.cancelAll(this);
    }
}
