package com.can.bimuprojects.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Module.Request.FocusRequest;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.GetExerciseRequest;
import com.can.bimuprojects.Module.Request.SetUserNameRequest;
import com.can.bimuprojects.Module.Response.AddLoveListResponse;
import com.can.bimuprojects.Module.Response.GetExerciseResponse;
import com.can.bimuprojects.Module.Response.SetUserNameResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.LoadDialog;

/**
 * Created by can on 2017/4/27.
 * 活动页
 */

public class ExerciseActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private ImageView iv_exit; //退出
    private ImageView iv_share; //分享
    private LinearLayout ll_say ; //活动说明
    private TextView tv_look; //查看品牌介绍
    private TextView tv_get;// 获取优惠
    private WebView wv ; //活动内容
    private ImageView iv_dagou ;//打钩

    private Dialog dialog ; //弹窗
    private LinearLayout ll_dialog; //选择先生女士
    private EditText et_dialog; //请输入您的姓氏
    private RadioGroup rg_dialog;
    private RadioButton rb_xiansheng;
    private RadioButton rb_nvshi;
    private int sex = 0; //0：先生 1：女士
    private TextView tv_dialog_cancle,tv_dialog_sure;//取消 提交
    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_exercise);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        iv_share = (ImageView) findViewById(R.id.iv_right);
        iv_dagou = (ImageView) findViewById(R.id.iv_exercise_dagou);
        if(Util.isOnMainThread()) {
            Glide.with(this).load(R.drawable.dian).into(iv_share);
            Glide.with(this).load(R.drawable.da_gou).into(iv_dagou);
        }
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.discount_exercie);
        tv_get = (TextView) findViewById(R.id.tv_exercise_get);
        tv_look  = (TextView) findViewById(R.id.tv_exercise_look);
        ll_say = (LinearLayout) findViewById(R.id.ll_exercise);
        wv = (WebView) findViewById(R.id.wv_exercise);

        dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_get_discount, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_gift);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.img_gift).into(iv);
        ll_dialog = (LinearLayout) view.findViewById(R.id.ll_dialog_exercise);
        et_dialog = (EditText) view.findViewById(R.id.et_dialog_exercise);
        rg_dialog = (RadioGroup) view.findViewById(R.id.rg_dialog_exercise);
        rb_xiansheng = (RadioButton) view.findViewById(R.id.rb_dialog_exercise_xiansheng);
        rb_nvshi = (RadioButton) view.findViewById(R.id.rb_dialog_exercise_nvshi);
        tv_dialog_cancle = (TextView) view.findViewById(R.id.tv_exercise_dialog_cancle);
        tv_dialog_sure = (TextView) view.findViewById(R.id.tv_exercise_dialog_sure);
        rg_dialog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_dialog_exercise_xiansheng) {//先生
                    sex = 0;
                } else { //女士
                    sex = 1;
                }
            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        params.width = metric.widthPixels - 50;
        window.setAttributes(params);
    }

    private String id ; //活动id
    private String bid ; //品牌id
    private boolean hasFollow = false ;//是否参与过活动
    private String content; //分享的内容
    /**
     * 初始化数据
     */
    private void initData() {
        WebSettings webSettings =   wv .getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);
        wv.requestFocusFromTouch();

        id = getIntent().getStringExtra("id");
        requestITData();
    }

    /**
     * 请求网络数据
     */
    private void requestITData() {
        LoadDialog.show(this,"内容加载中...");
        GetExerciseRequest request = new GetExerciseRequest();
        request.setUid(LoginUtils.getUid());
        request.setAct_id(id);
        HttpUtils.postWithoutUid(MethodConstant.GET_EXERCISE_DETAIL, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetExerciseResponse response = (GetExerciseResponse) receive.getResponse();
                if(response!=null){
                    LoadDialog.dismiss(context);
                    bid = response.getBrand_id();
                    content = response.getBrand_name();
                    wv.loadData(response.getContent_url(), "text/html;charset=UTF-8", null);
                    if(response.getHas_follow()==1){
                        hasFollow = true;
                    }else if(response.getHas_follow()==0){
                        hasFollow = false;
                    }
                    String str = response.getTrue_name();
                    if(str!=null)
                        LoginUtils.setUserName(str);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
                LoadDialog.dismiss(context);
            }
        }, GetExerciseResponse.class);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        iv_exit.setOnClickListener(this);
        iv_share.setOnClickListener(this);
        tv_get.setOnClickListener(this);
        ll_say.setOnClickListener(this);
        tv_look.setOnClickListener(this);
        tv_dialog_cancle.setOnClickListener(this);
        tv_dialog_sure.setOnClickListener(this);
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_exercise_get: //获取优惠
                if(showLoginDialog())
                    break;

                if(!LoginUtils.getUserName().equals("")){
                    ll_dialog.setVisibility(View.GONE);
                }else{
                    ll_dialog.setVisibility(View.VISIBLE);
                }
                if(bid!=null){
                    if(hasFollow){ //报名活动过
                        if(!this.isFinishing())
                            showDialog("您已经预约过该活动了！");
                    }else if(!dialog.isShowing()){
                        dialog.show();
                    }
                }
                break;

            case R.id.tv_exercise_look: //查看品牌介绍
                if(bid!=null){
                    Intent intent = new Intent(this,BrandActivity.class);
                    intent.putExtra("index",bid);
                    startActivity(intent);
                }
                break;

            case R.id.iv_exit: //退出
                finish();
                break;
            case R.id.ll_exercise : //活动说明
                if(bid!=null){
                    Intent intent = new Intent(this,ExerciseExplainActivity.class);
                    intent.putExtra("id",bid);
                    startActivity(intent);
                }
                break;

            case R.id.iv_right: //分享
                if(bid!=null)
                    share();
                break;

            case R.id.tv_exercise_dialog_sure: //提交
                sure();
                break;
            case R.id.tv_exercise_dialog_cancle://
                dialog.cancel();
                break;
        }

    }

    //分享
    private void share() {
        UMImage umImage = new UMImage(this, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(umImage)
                .withTitle(getString(R.string.share_exercise_title))
                .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ToastUtils.showShort(ExerciseActivity.this,"分享成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtils.showShort(ExerciseActivity.this,"分享失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtils.showShort(ExerciseActivity.this,"分享已取消");
                    }
                })
                .withText(content)
                .withTargetUrl("http://30.bimuwang.com/version3/interface/share_active.php?article_id=" + id
                )
                .open();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== AppConstant.LOGIN_REQUEST)
            requestITData();
    }

    /**
     * 点击提交
     */
    private void sure() {
        if (ll_dialog.getVisibility() == View.VISIBLE) {
            if (et_dialog.getText().toString()==null||et_dialog.getText().toString().equals("")||et_dialog.getText().toString().trim().equals("")) {
                if(!this.isFinishing())
                    showDialog("请输入您的姓氏");
                return;
            }else {
                SetUserNameRequest request = new SetUserNameRequest();
                request.setUid(LoginUtils.getUid());
                request.setUser_name(et_dialog.getText().toString().trim());
                request.setUser_sex(sex + "");
                HttpUtils.postWithoutUid(MethodConstant.SET_USER_NAME, request, new ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        SetUserNameResponse response = (SetUserNameResponse) receive.getResponse();
                        if(response!=null){
                            LoginUtils.setUserName(et_dialog.getText().toString().trim());
                        }
                    }
                }, new ErrorHook() {
                    @Override
                    public void deal(Context context, VolleyError error) {

                    }
                }, SetUserNameResponse.class);
            }
        }
        dialog.dismiss();
        FocusRequest re = new FocusRequest();
        re.setId(id);
        re.setType(5);
        re.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.FOCUS, re, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                AddLoveListResponse response = (AddLoveListResponse) receive.getResponse();
                if(response!=null){

                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        },AddLoveListResponse.class);
        Dialog dl = ToastUtils.showDialog(ExerciseActivity.this,"提示","您已经报名成功！",View.inflate(this, R.layout.dialog_circle,null),R.style.circle_dialog);
        dl.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                ExerciseActivity.this.finish();
            }
        });
        dl.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
