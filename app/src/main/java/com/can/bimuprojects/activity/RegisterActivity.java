package com.can.bimuprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.GetVerifyCodeRequest;
import com.can.bimuprojects.Module.Request.RegisterRequest;
import com.can.bimuprojects.Module.Response.GetVerifyCodeResponse;
import com.can.bimuprojects.Module.Response.RegisterResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.network.utils.Constants;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.EncryptionUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.ClearEditText;

public class RegisterActivity extends BaseActivity implements View.OnFocusChangeListener, TextView.OnEditorActionListener {


    private ClearEditText et_phone_num;
    private EditText et_msg_check;
    private EditText et_pwd;
    private Button btn_ac_msg_check;
    private TextView btn_register;
    private String phone;
    private String verify;
    private String password;
    private TextView tv_protocal;
    private ImageView iv_exit;

    private TextView tv_phone ; //电话号码
    private TextView tv_phone2 ;

    private TextView tv_num1,tv_num2 ,tv_code1,tv_code2;

    private boolean flag = false; //是否为弹窗进来


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
    }

    private void setListener() {
        btn_ac_msg_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = et_phone_num.getText().toString();
                if (AppUtils.isMobileNO(phone)) {
                    getVerifyCode();
                } else {
                    if(!RegisterActivity.this.isFinishing())
                    showDialog("请输入正确的手机号");
                }


            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify = et_msg_check.getText().toString().trim();
                password = et_pwd.getText().toString().trim();
                register(verify, password);
            }
        });


    }

    private void register(String verify, String password) {
        phone = et_phone_num.getText().toString().trim();
        if (!AppUtils.isMobileNO(phone) ){
            if(!this.isFinishing())
            showDialog("请输入正确的手机号码");
            return ;
        } else if (password.equals("")) {
            if(!this.isFinishing())
            showDialog("密码不能为空");
            return ;
        } else if (!password.equals("")&&password.length()<6) {
            if(!this.isFinishing())
            showDialog("密码不能小于6位");
            return ;
        }
        else if (verify.equals("")) {
            if(!this.isFinishing())
            showDialog("验证码不能为空");
            return ;
        }
                RegisterRequest request = new RegisterRequest();
                request.setPassword(EncryptionUtils.encrypt(password, phone));
                request.setPhone(phone);
                request.setVerify(verify);
                request.setTmpuid(PrefUtils.get("uid",""));
                request.setTmpsn(Constants.getSN(this));
                HttpUtils.postWithoutUid(MethodConstant.REGISTER, request, new ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        RegisterResponse response = (RegisterResponse) receive.getResponse();
                        if (response.getCode().equals("1") && response.getExe_success().equals("1")) {
                            if(flag) {
                                setResult(AppConstant.LOGIN_REQUEST);
                                LoginUtils.setLoginUid(response.getUid());
                                LoginUtils.setLoginStatus(false);
                                PrefUtils.putBoolean("update_home",true);
                                RegisterActivity.this.finish();
                            }else{
                                LoginUtils.setLoginUid(response.getUid());
                                LoginUtils.setLoginStatus(false);
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.putExtra("uid", response.getUid());
                                PrefUtils.putBoolean("love_update", true);
                                startActivity(intent);
                                finish();
                            }
                        } else if (response.getCode().equals("2") ) {
                            if(!RegisterActivity.this.isFinishing())
                            showDialog("该号码已被注册");
                        } else if (receive.getStatus() == 402) {
                            if(!RegisterActivity.this.isFinishing())
                            showDialog("验证码已经过期");
                        } else if (receive.getStatus() == 403) {
                            if(!RegisterActivity.this.isFinishing())
                            showDialog("验证码错误");
                        } else if (receive.getStatus() == 502) {
                            if(!RegisterActivity.this.isFinishing())
                            showDialog("未查询到验证码");
                        }
                    }
                }, new ErrorHook() {
                    @Override
                    public void deal(Context context, VolleyError error) {
                        if(!RegisterActivity.this.isFinishing())
                        showDialog("网络似乎在开小差~");
                    }
                }, RegisterResponse.class);
    }

    private void getVerifyCode() {
        btn_ac_msg_check.setEnabled(false);
        timer.start();
        btn_ac_msg_check.setTextColor(0xff565656);
        HttpUtils.postWithoutUid(MethodConstant.GET_VERIFY_CODE, new GetVerifyCodeRequest(phone),
                new ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        GetVerifyCodeResponse response = (GetVerifyCodeResponse) receive.getResponse();
                        if (response.getExe_success() == 1 && response.getHas_send() == 1) {

                        }
                    }
                }, new ErrorHook() {
                    @Override
                    public void deal(Context context, VolleyError error) {
                        btn_ac_msg_check.setEnabled(true);
                    }
                }, GetVerifyCodeResponse.class);
    }


    CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {

            btn_ac_msg_check.setText("| "+millisUntilFinished / 1000 + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            btn_ac_msg_check.setEnabled(true);
            btn_ac_msg_check.setText("| 重新获取");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        HttpUtils.cancelAll(this);
    }

    private void initView() {
        setContentView(R.layout.activity_register);
        flag = getIntent().getBooleanExtra("flag",false);
        tv_phone = (TextView) findViewById(R.id.tv_login_phone);
        tv_phone2 = (TextView) findViewById(R.id.tv_login_phone2);
        tv_code1 = (TextView) findViewById(R.id.tv_register_code1);
        tv_code2 = (TextView) findViewById(R.id.tv_register_code2);
        tv_num1 = (TextView) findViewById(R.id.tv_login_number1);
        tv_num2 = (TextView) findViewById(R.id.tv_login_number2);
        et_phone_num = (ClearEditText) findViewById(R.id.et_tel);
        et_phone_num.setOnFocusChangeListener(this);
        et_msg_check = (EditText) findViewById(R.id.et_msg_check);
        et_msg_check.setOnFocusChangeListener(this);
        et_msg_check.setOnEditorActionListener(this);
        btn_ac_msg_check = (Button) findViewById(R.id.btn_ac_msg_check);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwd.setOnFocusChangeListener(this);
        btn_register = (TextView) findViewById(R.id.btn_register);
        tv_protocal = (TextView) findViewById(R.id.tv_protocal);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final String protocal = new String(getResources().getString(R.string.user_protocal));
        tv_protocal.setText("注册即代表您同意 ");
        tv_protocal.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString ss = new SpannableString(protocal);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(RegisterActivity.this, ProtocalActivity.class);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ContextCompat.getColor(RegisterActivity.this,R.color.color_small_text));
                ds.setUnderlineText(true);
            }
        }, 0, protocal.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_protocal.append(ss);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        switch (view.getId()){
            case R.id.et_tel:
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
                        tv_phone.setTextColor( ContextCompat.getColor(this,R.color.color_app_text_yes));
                    }
                    handler.removeMessages(3);
                }else{
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line2);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    et_phone_num.setCompoundDrawables(null, null, null, rightDrawable);
                    if(et_phone_num.getText().toString().equals("")){
                        tv_phone.setTextColor( ContextCompat.getColor(this,R.color.color_red));
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
            case R.id.et_pwd:
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
                        tv_num1.setTextColor( ContextCompat.getColor(this,R.color.color_red));
                    }
                    handler.removeMessages(4);
                }else{
                    Drawable rightDrawable =ContextCompat.getDrawable(this,R.drawable.shape_et_line2);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    et_pwd.setCompoundDrawables(null, null, null, rightDrawable);
                    if(et_pwd.getText().toString().equals("")){
                        tv_num1.setTextColor( ContextCompat.getColor(this,R.color.color_red));
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
            case R.id.et_msg_check:
                if(b){
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    et_msg_check.setCompoundDrawables(null, null, null, rightDrawable);
                    if(et_msg_check.getText().toString().equals("")){
                        Animation animation = AnimationUtils.loadAnimation(this,R.anim.et_focus);
                        tv_code1.startAnimation(animation);
                        tv_code1.setVisibility(View.VISIBLE);
                        et_msg_check.setHint("");
                    }else{
                        tv_code1.setTextColor( ContextCompat.getColor(this,R.color.color_red));
                    }
                    handler.removeMessages(6);
                }else{
                    Drawable rightDrawable = ContextCompat.getDrawable(this,R.drawable.shape_et_line2);
                    rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                    et_msg_check.setCompoundDrawables(null, null, null, rightDrawable);
                    if(et_msg_check.getText().toString().equals("")){
                        tv_code1.setTextColor( ContextCompat.getColor(this,R.color.color_red));
                        tv_code1.setVisibility(View.GONE);
                        tv_code2.setVisibility(View.VISIBLE);
                        Animation animation = AnimationUtils.loadAnimation(this,R.anim.et_cancle_focus);
                        tv_code2.startAnimation(animation);
                        handler.sendEmptyMessageDelayed(5,animation.getDuration());
                        handler.sendEmptyMessageDelayed(6,animation.getDuration());
                    }else{
                        tv_code1.setTextColor(ContextCompat.getColor(this,R.color.color_small_text));
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
                    et_phone_num.setHint(getString(R.string.please_input_phone_num));
                    break;
                case 4:
                    et_pwd.setHint(getString(R.string.pwd));
                    break;
                case 5:
                    tv_code2.setVisibility(View.GONE);
                    break;
                case 6:
                    et_msg_check.setHint(getString(R.string.please_input_erify_code));
                    break;
            }
            return false;
        }
    });

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch (i){
            case EditorInfo.IME_ACTION_DONE:
                verify = et_msg_check.getText().toString().trim();
                password = et_pwd.getText().toString().trim();
                register(verify, password);
                break;
        }
        return false;
    }
}
