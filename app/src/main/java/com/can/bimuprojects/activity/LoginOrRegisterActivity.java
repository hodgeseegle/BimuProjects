package com.can.bimuprojects.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.R;
import com.can.bimuprojects.application.BimuApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by can on 2017/5/17.
 * 登录or注册页面
 */

public class LoginOrRegisterActivity extends Activity {

    private BimuApplication application;
    private ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        iv = (ImageView) findViewById(R.id.iv_login_or_register);

        if ( Util.isOnMainThread()) {
            Glide.with(this).load(R.drawable.img_login_bg).into(iv);
        }

        findViewById(R.id.btn_guide_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOrRegisterActivity.this,LoginActivity.class));
            }
        });
        findViewById(R.id.btn_guide_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginOrRegisterActivity.this,RegisterActivity.class));
            }
        });

        if (application == null) {
            // 得到Application对象
            application = (BimuApplication) getApplication();
        }
        application.addActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent home = new Intent(Intent.ACTION_MAIN);
//            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            home.addCategory(Intent.CATEGORY_HOME);
//            startActivity(home);
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
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

}
