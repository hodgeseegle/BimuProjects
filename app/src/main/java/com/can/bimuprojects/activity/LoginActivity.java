package com.can.bimuprojects.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Module.Request.LoginRequest;
import com.can.bimuprojects.Module.Response.LoginResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.utils.Constants;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.EncryptionUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.ClearEditText;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity implements View.OnFocusChangeListener, TextView.OnEditorActionListener {
    private EditText et_pwd;
    private ClearEditText et_phone_num;

    private ImageView iv_back;

    private TextView tv_phone ; //电话号码
    private TextView tv_phone2 ;

    private TextView tv_num1,tv_num2 ;

    private boolean flag = false; //是否为弹窗进来


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        flag = getIntent().getBooleanExtra("flag",false);

        tv_phone = (TextView) findViewById(R.id.tv_login_phone);
        tv_phone2 = (TextView) findViewById(R.id.tv_login_phone2);
        tv_num1 = (TextView) findViewById(R.id.tv_login_number1);
        tv_num2 = (TextView) findViewById(R.id.tv_login_number2);
        et_phone_num = (ClearEditText) findViewById(R.id.login_et_phone_num);
        et_phone_num.setOnFocusChangeListener(this);
        et_pwd = (EditText) findViewById(R.id.login_et_pwd);
        et_pwd.setOnFocusChangeListener(this);
        et_pwd.setOnEditorActionListener(this);
        iv_back = (ImageView) findViewById(R.id.iv_exit);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        WindowManager.LayoutParams params = getWindow().getAttributes();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.login_btn_find_pwd:
                startActivity(new Intent(LoginActivity.this, FindPwdActivity.class));
                break;
            case R.id.login_btn_login:
                login();
                break;
        }
    }

    private void login() {
        String phone_num = et_phone_num.getText().toString().trim();
        String pwd = et_pwd.getText().toString().trim();
        if (!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(phone_num)) {
            pwd = EncryptionUtils.encrypt(pwd, phone_num);
        }

        if (testValid()) {
            LoginUtils.startLogin(new LoginRequest(phone_num, pwd,PrefUtils.get("uid",""), Constants.getSN(this)), new LoginUtils.LoginListener() {
                @Override
                public void onResponse(JsonReceive receive) {
                    LoginResponse response = (LoginResponse) receive.getResponse();
                    if (response.getExe_success().equals("1")) {
                        //code 1 代表登录成功 ， 2代表为未注册
                        if (response.getCode().equals("1")) {
                            if(flag){
                                LoginUtils.setLoginUid(response.getUid());
                                LoginUtils.setLoginStatus(false);
                                PrefUtils.putBoolean("update_home",true);
                                LoginActivity.this.finish();
                            }else{
                                BimuApplication.removeALLActivity();
                                LoginUtils.setLoginStatus(false);
                                LoginUtils.setLoginUid(response.getUid());
                                setResult(RESULT_OK);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                finish();
                            }
                        }
                    } else {
                        if (response.getCode().equals("2")) {
                            if(!LoginActivity.this.isFinishing())
                            showDialog("此号码尚未注册!").show();
                        }
                        if (response.getCode().equals("3")) {
                            if(!LoginActivity.this.isFinishing())
                            showDialog("密码错误").show();
                        }
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    if(!LoginActivity.this.isFinishing())
                    showDialog("网络似乎在开小差~").show();
                }
            });

        }
    }


    private boolean testValid() {
        if (!AppUtils.isMobileNO(et_phone_num.getText().toString().trim())) {
            if(!this.isFinishing())
            showDialog("请输入正确的手机号码").show();
            return false;
        } else if (TextUtils.isEmpty(et_pwd.getText().toString().trim())) {
            if(!this.isFinishing())
            showDialog("密码不能为空").show();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        HttpUtils.cancelAll(this);
        super.onDestroy();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.login_et_phone_num:
                if(b){
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    et_phone_num.setCompoundDrawables(null, null, null, rightDrawable);
                    if(et_phone_num.getText().toString().equals("")){
                        Animation animation = AnimationUtils.loadAnimation(this,R.anim.et_focus);
                        tv_phone.startAnimation(animation);
                        tv_phone.setVisibility(View.VISIBLE);
                        et_phone_num.setHint("");
                    }else{
                        tv_phone.setTextColor(ContextCompat.getColor(this,R.color.color_red));
                    }
                    handler.removeMessages(3);
                }else{
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line2);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    et_phone_num.setCompoundDrawables(null, null, null, rightDrawable);
                    if(et_phone_num.getText().toString().equals("")){
                        tv_phone.setTextColor(ContextCompat.getColor(this,R.color.color_red));
                        tv_phone.setVisibility(View.GONE);
                        tv_phone2.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(this,R.anim.et_cancle_focus);
                        tv_phone2.startAnimation(animation);
                        handler.sendEmptyMessageDelayed(1,animation.getDuration());
                        handler.sendEmptyMessageDelayed(3,animation.getDuration());
                    }else{
                        tv_phone.setTextColor(ContextCompat.getColor(this,R.color.color_small_text));
                    }

                }
                break;
            case R.id.login_et_pwd:
                if(b){
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    et_pwd.setCompoundDrawables(null, null, null, rightDrawable);
                    if(et_pwd.getText().toString().equals("")){
                        Animation animation = AnimationUtils.loadAnimation(this,R.anim.et_focus);
                        tv_num1.startAnimation(animation);
                        tv_num1.setVisibility(View.VISIBLE);
                        et_pwd.setHint("");
                    }else{
                        tv_num1.setTextColor(ContextCompat.getColor(this,R.color.color_red));
                    }
                    handler.removeMessages(4);
                }else{
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line2);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    et_pwd.setCompoundDrawables(null, null, null, rightDrawable);
                    if(et_pwd.getText().toString().equals("")){
                        tv_num1.setTextColor(ContextCompat.getColor(this,R.color.color_red));
                        tv_num1.setVisibility(View.GONE);
                        tv_num2.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(this,R.anim.et_cancle_focus);
                        tv_num2.startAnimation(animation);
                        handler.sendEmptyMessageDelayed(2,animation.getDuration());
                        handler.sendEmptyMessageDelayed(4,animation.getDuration());
                    }else{
                        tv_num1.setTextColor(ContextCompat.getColor(this,R.color.color_small_text));
                    }
                }
                break;
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    tv_phone2.setVisibility(View.GONE);
                    break;
                case 2:
                    tv_num2.setVisibility(View.GONE);
                    break;
                case 3:
                    et_phone_num.setHint("请输入手机号");
                    break;
                case 4:
                    et_pwd.setHint("请输入密码");
                    break;
            }
            return false;
        }
    });

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch (i){
            case EditorInfo.IME_ACTION_DONE:
                login();
                break;
        }
        return false;
    }
}
