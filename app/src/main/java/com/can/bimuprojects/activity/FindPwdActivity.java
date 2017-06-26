package com.can.bimuprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Hook.DefaultErrorHook;
import com.can.bimuprojects.Module.Request.FindPasswordRequest;
import com.can.bimuprojects.Module.Request.GetVerifyCodeRequest;
import com.can.bimuprojects.Module.Response.FindPasswordResponse;
import com.can.bimuprojects.Module.Response.GetVerifyCodeResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.network.utils.Constants;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.EncryptionUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 找回密码，与修改密码不同， 不需要输入原密码
 */
public class FindPwdActivity extends BaseActivity implements  View.OnFocusChangeListener, TextView.OnEditorActionListener {

    @Bind(R.id.find_pwd_btn_get_verify)
    Button btnGetVerify;
    @Bind(R.id.find_pwd_btn_confirm)
    TextView btnConfirm;
    @Bind(R.id.find_pwd_et_new_pwd)
    EditText etNewPwd;
    @Bind(R.id.find_pwd_et_phone_num)
    EditText etPhoneNum;
    @Bind(R.id.find_pwd_et_verify)
    TextView etVerify;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.iv_exit)
    ImageView iv_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        String title = getIntent().getStringExtra("title");
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
            btnConfirm.setText("修改密码");
        } else {
            tv_title.setText("密码找回");
            btnConfirm.setText("密码找回");
        }

        iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPassword();
            }
        });
        btnGetVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVerifyCode();
            }
        });
        etVerify.setOnFocusChangeListener(this);
        etNewPwd.setOnFocusChangeListener(this);
        etPhoneNum.setOnFocusChangeListener(this);
        etNewPwd.setOnEditorActionListener(this);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;
    }

    private void getVerifyCode() {
        String phone = etPhoneNum.getText().toString().trim();
        if (!AppUtils.isMobileNO(phone)) {
            if(!this.isFinishing())
            showDialog("请输入正确的手机号");
            return;
        }
        timer.start();
        HttpUtils.postWithoutUid(MethodConstant.GET_VERIFY_CODE,
                new GetVerifyCodeRequest(etPhoneNum.getText().toString().trim()),
                new ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        GetVerifyCodeResponse response = (GetVerifyCodeResponse) receive.getResponse();
                        if (response.getExe_success() == 1 && response.getHas_send() == 1) {
                            //已发送验证码
                            btnGetVerify.setEnabled(false);

                        }
                    }
                },
                new DefaultErrorHook(),
                GetVerifyCodeResponse.class
        );
    }

    //找回密码
    private void findPassword() {
        if (!AppUtils.isMobileNO(etPhoneNum.getText().toString().trim()) ){
            if(!this.isFinishing())
            showDialog("请输入正确的手机号码");
            return ;
        }  else if (etVerify.getText().toString().trim().equals("")) {
            if(!this.isFinishing())
            showDialog("验证码不能为空");
            return ;
        }else if (etNewPwd.getText().toString().trim().equals("")) {
            if(!this.isFinishing())
            showDialog("密码不能为空");
            return ;
        }else if (!etNewPwd.getText().toString().equals("")&&etNewPwd.getText().toString().trim().length()<6) {
            if(!this.isFinishing())
            showDialog("密码不能小于6位");
            return ;
        }

        FindPasswordRequest request = new FindPasswordRequest();
        String pwd = etNewPwd.getText().toString().trim();
        String phone = etPhoneNum.getText().toString().trim();
        if(!TextUtils.isEmpty(pwd)){
            request.setNew_password(EncryptionUtils.encrypt(pwd, phone));
        }
        request.setPhone(phone);
        request.setVerify(etVerify.getText().toString().trim());
        request.setTmpuid(PrefUtils.get("uid",""));
        request.setTmpsn(Constants.getSN(this));
        HttpUtils.postWithoutUid(MethodConstant.FIND_PASSWORD, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                FindPasswordResponse response = (FindPasswordResponse) receive.getResponse();
                if (response.getExe_success() == 1 && response.getCode() == 1) {
                    //找回密码成功
                    ToastUtils.showShort(FindPwdActivity.this, "找回密码成功！");
//                    LoginUtils.setLoginStatus(true);
//                    LoginUtils.setLoginUid(response.getUid());
//                    startActivity(new Intent(FindPwdActivity.this, MainActivity.class));
//                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                } else if (response.getCode() == 2) {
                    //号码未注册
                    if(!FindPwdActivity.this.isFinishing())
                    showDialog("此号码尚未注册");
                }else if(receive.getStatus()==402||receive.getStatus()==404){
                    if(!FindPwdActivity.this.isFinishing())
                    showDialog("修改密码未变");
                }else if(receive.getStatus()==401){
                    if(!FindPwdActivity.this.isFinishing())
                    showDialog("数据不完整");
                }else if(receive.getStatus()==501){
                    if(!FindPwdActivity.this.isFinishing())
                    showDialog("数据库连接错误");
                }
            }
        }, new DefaultErrorHook(), FindPasswordResponse.class);

    }

    CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            btnGetVerify.setText("| "+millisUntilFinished / 1000 + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            btnGetVerify.setText("| 重新获取");
            btnGetVerify.setEnabled(true);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.find_pwd_et_new_pwd:
                if(b){
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    etNewPwd.setCompoundDrawables(null, null, null, rightDrawable);
                }else{
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line2);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    etNewPwd.setCompoundDrawables(null, null, null, rightDrawable);
                }
                break;
            case R.id.find_pwd_et_phone_num:
                if(b){
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    etPhoneNum.setCompoundDrawables(null, null, null, rightDrawable);
                }else{
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line2);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    etPhoneNum.setCompoundDrawables(null, null, null, rightDrawable);
                }
                break;
            case R.id.find_pwd_et_verify:
                if(b){
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    etVerify.setCompoundDrawables(null, null, null, rightDrawable);
                }else{
                    Drawable rightDrawable =ContextCompat.getDrawable(this,R.drawable.shape_et_line2);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    etVerify.setCompoundDrawables(null, null, null, rightDrawable);
                }
                break;
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch (i){
            case EditorInfo.IME_ACTION_DONE:
                findPassword();
                break;
        }

        return false;
    }
}
