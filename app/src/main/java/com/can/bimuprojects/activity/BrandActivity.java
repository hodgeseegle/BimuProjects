package com.can.bimuprojects.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Constant.AppConstant;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.AddLoveListRequest;
import com.can.bimuprojects.Module.Request.BrandDetailDetailRequest;
import com.can.bimuprojects.Module.Request.BrandDetailDetailResponse;
import com.can.bimuprojects.Module.Request.BrandRequest;
import com.can.bimuprojects.Module.Request.SetUserNameRequest;
import com.can.bimuprojects.Module.Response.AddLoveListResponse;
import com.can.bimuprojects.Module.Response.BrandImgResponse;
import com.can.bimuprojects.Module.Response.BrandResponse;
import com.can.bimuprojects.Module.Response.OpenShopResponse;
import com.can.bimuprojects.Module.Response.SetUserNameResponse;
import com.can.bimuprojects.Module.Response.SpecialResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.BrandAssessAdapter;
import com.can.bimuprojects.adapter.BrandOpenShopAdapter;
import com.can.bimuprojects.adapter.BrandRecommendAdapter;
import com.can.bimuprojects.adapter.SpecialAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.GlideRoundTransform;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.NumberUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.BrandChildListView;
import com.can.bimuprojects.view.BrandGridView;
import com.can.bimuprojects.view.BrandParentListView;
import com.can.bimuprojects.view.LoadDialog;
import com.can.bimuprojects.view.gallery.ImagePagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/13.
 * 品牌详情页
 */

public class BrandActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private String bid; //品牌接口的bid
    private boolean isLove = false; //是否已经加入心愿单

    private TextView tv_title; //标题
    private ImageView iv_love; //加入或取消心愿单按钮
    private ImageView iv_exit; //退出按钮
    private BrandParentListView lv; //集合控件
    private ImageView iv_share ; //分享
    private ImageView iv_dagou ; //打钩

    private View view_head; //头部view
    private View view_detail; //详情view
    private View view_fee; //费用view
    private View view_assess; //评价view
    private View view_recommend; //推荐view

    private LinearLayout ll_get_join_process; //加盟流程
    private TextView tv_brand_get_openshop_process; //开店方案

    private TextView tv_detail, tv_fee, tv_assess, tv_recommend; //详情 费用 评价 推荐 按钮

    private boolean isConsult = false; //是否咨询过

    private ImageView iv_add_comment ; //添加评论
    private String brand ; //品牌名称

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isUpdateConsult = PrefUtils.getBoolean("update_consult",false);
        if(isUpdateConsult) {
            isConsult = true;
            tv_brand_get_openshop_process.setText(getString(R.string.entre_love_list));
            tv_brand_get_openshop_process.setBackgroundColor(ContextCompat.getColor(this,R.color.color_zixun_brand));
            if (!isLove) {
                if(!this.isFinishing()&&Util.isOnMainThread())
                    Glide.with(this).load(R.drawable.remove_love_list).into(iv_love);
                isLove = !isLove;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== AppConstant.LOGIN_REQUEST)
            requestITData();
    }

    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_brand);
        tv_title = (TextView) findViewById(R.id.tv_brand_title);
        iv_share = (ImageView) findViewById(R.id.iv_brand_share);
        iv_exit = (ImageView) findViewById(R.id.iv_exit_brand);
        iv_love = (ImageView) findViewById(R.id.iv_brand_love);
        tv_detail = (TextView) findViewById(R.id.tv_brand_detail);
        tv_fee = (TextView) findViewById(R.id.tv_brand_fee);
        iv_add_comment = (ImageView) findViewById(R.id.iv_brand_add_comment);
        iv_dagou = (ImageView) findViewById(R.id.iv_brand_dagou);
        tv_assess = (TextView) findViewById(R.id.tv_brand_assess);
        tv_recommend = (TextView) findViewById(R.id.tv_brand_recommend);
        ll_get_join_process = (LinearLayout) findViewById(R.id.ll_brand_get_join_process);
        lv = (BrandParentListView) findViewById(R.id.lv_brand_detail);
        tv_brand_get_openshop_process = (TextView) findViewById(R.id.tv_brand_get_openshop_process);

        if(Util.isOnMainThread()){
            Glide.with(this).load(R.drawable.img_add_comment).into(iv_add_comment);
            Glide.with(this).load(R.drawable.da_gou).into(iv_dagou);
        }

        lv.setSelected(true);
        showDialogFromBottom();
        initHeadView();
    }

    private ImageView iv_head_bg; //头部背景图
    private ImageView iv_head_logo; //头部logo图
    private TextView tv_head_title; //头部标题
    private LinearLayout ll_head_imgs; //头部全部图片
    private TextView tv_head_imgs; //头部全部图片
    private LinearLayout ll_head_flags ; //头部标签
    private ImageView iv_camera;
    /**
     * 初始化头部view
     */
    private void initHeadView() {
        view_head = LayoutInflater.from(this).inflate(R.layout.brand_detail_head, null);
        iv_camera = (ImageView) view_head.findViewById(R.id.iv_brand_detail_camera);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.camera).into(iv_camera);
        iv_head_bg = (ImageView) view_head.findViewById(R.id.iv_brand_detail_head);
        iv_head_logo = (ImageView) view_head.findViewById(R.id.iv_brand_detail_logo);
        tv_head_title = (TextView) view_head.findViewById(R.id.tv_brand_detail_title);
        ll_head_imgs = (LinearLayout) view_head.findViewById(R.id.ll_brand_detail_head_imgs);
        tv_head_imgs = (TextView) view_head.findViewById(R.id.tv_brand_detail_head_imgs);
        ll_head_flags = (LinearLayout) view_head.findViewById(R.id.ll_brand_detail_head_flags);
        initDetail();
        initFee();
        initAssess();
        initRecommend();
        if(AppUtils.isNetworkOK(this)){
            if(Util.isOnMainThread())
                Glide.with(this).load(R.drawable.share).into(iv_share);
            lv.addHeaderView(view_head);
            lv.addHeaderView(view_detail);
            lv.addHeaderView(view_fee);
            lv.addHeaderView(view_assess);
            lv.addHeaderView(view_recommend);
            iv_add_comment.setVisibility(View.VISIBLE);
        }
    }

    private BrandGridView gv_recommend; //推荐view控件
    private List<BrandResponse.BrandlistBean> list_recommend;//推荐数据集合
    private BrandRecommendAdapter adapter_recommend; //推荐适配器
    private ImageView iv_recommend;
    /**
     * 初始化推荐view
     */
    private void initRecommend() {
        view_recommend = LayoutInflater.from(this).inflate(R.layout.brand_detail_recommend, null);
        iv_recommend = (ImageView) view_recommend.findViewById(R.id.iv_brand_detail_recommend);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.image_recommend).into(iv_recommend);
        gv_recommend = (BrandGridView) view_recommend.findViewById(R.id.gv_brand_recommend);
    }

    private BrandChildListView lv_assess; //评价listview
    private BrandAssessAdapter adapter_assess; //评价适配器
    private List<BrandResponse.ArticledataBean> list_assess; //评价数据集合
    private TextView btn_raiders ; //查看全部攻略
    private ImageView iv_raiders;
    /**
     * 初始化评价view
     */
    private void initAssess() {
        view_assess = LayoutInflater.from(this).inflate(R.layout.brand_detail_raiders, null);
        iv_raiders = (ImageView) view_assess.findViewById(R.id.iv_brand_detail_raiders);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.image_raiders).into(iv_raiders);
        lv_assess = (BrandChildListView) view_assess.findViewById(R.id.lv_brand_assess);

        View emptyView = LayoutInflater.from(this).inflate(R.layout.item_brand_asses_empty,null);
        emptyView.findViewById(R.id.tv_item_brand_assess_rob_sofa).setOnClickListener(this);
        ((ViewGroup)lv_assess.getParent()).addView(emptyView);
        lv_assess.setEmptyView(emptyView);

        btn_raiders = (TextView) view_assess.findViewById(R.id.btn_brand_detail_raiders);
    }

    private TextView tv_detail_total; //预计初期投入
    private RelativeLayout rl_join, rl_baozheng, rl_shebei, rl_wuliao, rl_zhuangxiu, rl_guanli, rl_shangbiao, rl_shiyong, rl_sheji, rl_qita;
    private TextView tv_join, tv_baozheng, tv_shebei, tv_wuliao, tv_zhuangxiu, tv_guanli, tv_shangbiao, tv_shiyong, tv_sheji, tv_qita;
    private ImageView iv_fee;
    /**
     * 初始化费用view
     */
    private void initFee() {
        view_fee = LayoutInflater.from(this).inflate(R.layout.brand_detail_fee, null);
        iv_fee = (ImageView) view_fee.findViewById(R.id.iv_brand_detail_fee);
        if(!this.isFinishing()&&Util.isOnMainThread())
            Glide.with(this).load(R.drawable.image_fee).into(iv_fee);

        tv_detail_total = (TextView) view_fee.findViewById(R.id.tv_brand_detail_fee_total);
        rl_join = (RelativeLayout) view_fee.findViewById(R.id.rl_brand_join);
        rl_baozheng = (RelativeLayout) view_fee.findViewById(R.id.rl_brand_baozheng);
        rl_shebei = (RelativeLayout) view_fee.findViewById(R.id.rl_brand_shebei);
        rl_wuliao = (RelativeLayout) view_fee.findViewById(R.id.rl_brand_wuliao);
        rl_zhuangxiu = (RelativeLayout) view_fee.findViewById(R.id.rl_brand_zhuangxiu);
        rl_guanli = (RelativeLayout) view_fee.findViewById(R.id.rl_brand_guanli);
        rl_shangbiao = (RelativeLayout) view_fee.findViewById(R.id.rl_brand_shangbiao);
        rl_shiyong = (RelativeLayout) view_fee.findViewById(R.id.rl_brand_shiyong);
        rl_sheji = (RelativeLayout) view_fee.findViewById(R.id.rl_brand_sheji);
        rl_qita = (RelativeLayout) view_fee.findViewById(R.id.rl_brand_qita);
        tv_join = (TextView) view_fee.findViewById(R.id.tv_brand_join);
        tv_baozheng = (TextView) view_fee.findViewById(R.id.tv_brand_baozheng);
        tv_shebei = (TextView) view_fee.findViewById(R.id.tv_brand_shebei);
        tv_wuliao = (TextView) view_fee.findViewById(R.id.tv_brand_wuliao);
        tv_zhuangxiu = (TextView) view_fee.findViewById(R.id.tv_brand_zhuangxiu);
        tv_guanli = (TextView) view_fee.findViewById(R.id.tv_brand_guanli);
        tv_shangbiao = (TextView) view_fee.findViewById(R.id.tv_brand_shangbiao);
        tv_shiyong = (TextView) view_fee.findViewById(R.id.tv_brand_shiyong);
        tv_sheji = (TextView) view_fee.findViewById(R.id.tv_brand_sheji);
        tv_qita = (TextView) view_fee.findViewById(R.id.tv_brand_qita);
    }

    private WebView wv; //网页
    private ImageView iv_detail ;
    /**
     * 初始化详情view
     */
    private void initDetail() {
        view_detail = LayoutInflater.from(this).inflate(R.layout.brand_detail_detail, null);
        iv_detail = (ImageView) view_detail.findViewById(R.id.iv_brand_detail_detail);
        if(!this.isFinishing()&&Util.isOnMainThread())
            Glide.with(this).load(R.drawable.image_detail).into(iv_detail);
        wv = (WebView) view_detail.findViewById(R.id.wv_brand);
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        iv_love.setOnClickListener(this);
        iv_exit.setOnClickListener(this);
        tv_recommend.setOnClickListener(this);
        tv_assess.setOnClickListener(this);
        tv_fee.setOnClickListener(this);
        tv_detail.setOnClickListener(this);
        lv_assess.setOnItemClickListener(this);
        gv_recommend.setOnItemClickListener(this);
        ll_get_join_process.setOnClickListener(this);
        tv_brand_get_openshop_process.setOnClickListener(this);
        ll_head_imgs.setOnClickListener(this);
        lv_dialog.setOnItemClickListener(this);
        btn_raiders.setOnClickListener(this);
        lv.setOnScrollListener(this);
        tv_dialog_open_shop_agree.setOnClickListener(this);
        iv_add_comment.setOnClickListener(this);
        iv_share.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_exit_brand: //退出
                this.finish();
                break;
            case R.id.iv_brand_love://加入或取消心愿单
                if(showLoginDialog())
                    break;
                MobclickAgent.onEvent(this,"iv_brand_love");
                addLoveList();
                break;

            case R.id.tv_brand_detail: //详情
                setColorChange(tv_detail);
                setSelect(1);
                break;

            case R.id.tv_brand_fee://费用
                setColorChange(tv_fee);
                setSelect(2);
                break;

            case R.id.tv_brand_assess: //评价
                setColorChange(tv_assess);
                setSelect(3);
                break;

            case R.id.tv_brand_recommend: //推荐
                setColorChange(tv_recommend);
                setSelect(4);
                break;

            case R.id.ll_brand_get_join_process: //加盟流程
                Intent intent_join_process = new Intent(this, JoinProcessActivity.class);
                startActivity(intent_join_process);
                break;

            case R.id.tv_brand_get_openshop_process: //开店方案或前往心愿单
                if(showLoginDialog())
                    break;

                MobclickAgent.onEvent(this,"tv_brand_get_openshop_process");
                if(isConsult){ //前往心愿单
                    Intent intent = new Intent(this,MainActivity.class);
                    intent.putExtra("love",true);
                    startActivity(intent);
                }else { //获取开店方案
                    if (!isHasOpenshopData)
                        requestOpenShopData();
                    else if (!dialog.isShowing())
                        dialog.show();
                }
                break;

            case R.id.ll_brand_detail_head_imgs: //全看全部照片
                if(img_total!=0) {
                    MobclickAgent.onEvent(this,"ll_brand_detail_head_imgs");
                    Intent intent = new Intent(getApplicationContext(), ImagePagerActivity.class);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                    intent.putExtra("bid",bid);
                    intent.putStringArrayListExtra("list",list_img);
                    intent.putExtra("consult",isConsult);
                    intent.putExtra("brand",brand);
                    startActivity(intent);
                    overridePendingTransition(0, android.R.anim.fade_out);
                }
                break;

            case R.id.tv_brand_dialog_cancle: //取消
                dialog.cancel();
                break;
            case R.id.tv_brand_dialog_sure: //确定
                dialog_sure();
                break;
            case R.id.btn_brand_detail_raiders: //查看全部攻略
                MobclickAgent.onEvent(this,"btn_brand_detail_raiders");
                Intent intent_look_all_raider = new Intent(this,LookAllRaidersActivity.class);
                intent_look_all_raider.putExtra("bid",bid);
                intent_look_all_raider.putExtra("brand",brand);
                startActivity(intent_look_all_raider);
                break;
            case R.id.tv_dialog_open_shop_agree: //个人信息保护声明
                startActivity(new Intent(this,PersonalInformationProtectActivity.class));
                break;
            case R.id.iv_brand_add_comment://发帖
            case R.id.tv_item_brand_assess_rob_sofa://抢沙发
                if(showLoginDialog())
                    break;
                Intent intent_comment = new Intent(this,SendArticleActivity.class);
                intent_comment.putExtra("id",bid);
                intent_comment.putExtra("brand",brand);
                startActivity(intent_comment);
                break;

            case R.id.iv_brand_share://分享品牌
                share();
                break;
        }
    }

    /**
     * 分享
     */
    private void share() {
        UMImage umImage = new UMImage(this, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        if(str_share_logo!=null){
            umImage = new UMImage(this,str_share_logo);
        }
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(umImage)
                .withText(getString(R.string.share_article_title))
                .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ToastUtils.showShort(BrandActivity.this,"分享成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtils.showShort(BrandActivity.this,"分享失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtils.showShort(BrandActivity.this,"分享已取消");
                    }
                })
                .withTitle("分享品牌")
                .withTargetUrl("http://app.bimuwang.com/bimu/interface/" +
                        "share_article.php?article_id=" + bid+"&from=singlemessage&isappinstalled=1")
                .open();
    }

    /**
     * listview设置跳到指定位置
     * @param i
     */
    private void setSelect(int i){
        lv.setSelection(i);
    }

    /**
     * 加入移除心愿单
     */
    private void addLoveList() {
        isConsult = false;
        tv_brand_get_openshop_process.setText(getString(R.string.get_openshop_plan));
        tv_brand_get_openshop_process.setBackgroundColor(ContextCompat.getColor(this,R.color.color_app_text_yes));
        if (isLove) {
            if(!this.isFinishing()&&Util.isOnMainThread())
                Glide.with(this).load(R.drawable.love_white).into(iv_love);
        } else {
            if(!this.isFinishing()&&Util.isOnMainThread())
                Glide.with(this).load(R.drawable.remove_love_list).into(iv_love);
        }
        isLove = !isLove;
        PrefUtils.putBoolean("love_update",true);
        handler.sendEmptyMessage(1);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(message.what==1) {
                AddLoveListRequest request = new AddLoveListRequest();
                request.setUid(LoginUtils.getUid());
                request.setType("2");
                request.setId(bid);
                HttpUtils.postWithoutUid(MethodConstant.SET_LOVE_LIST, request, new ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        AddLoveListResponse response = (AddLoveListResponse) receive.getResponse();
                        if (response != null) {

                        }
                    }
                }, new ErrorHook() {
                    @Override
                    public void deal(Context context, VolleyError error) {

                    }
                }, AddLoveListResponse.class);
            }
            return false;
        }
    });

    /**
     * 确定
     */
    private void dialog_sure() {
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

                    }
                }, new ErrorHook() {
                    @Override
                    public void deal(Context context, VolleyError error) {

                    }
                }, SetUserNameResponse.class);
            }
        }
        if (!isLove) {
            if(!this.isFinishing()&&Util.isOnMainThread())
                Glide.with(this).load(R.drawable.remove_love_list).into(iv_love);
            isLove = !isLove;
        }
        list_boolean = adapter_dialog.getCheckState();
        dialog.dismiss();
        isConsult = !isConsult;
        tv_brand_get_openshop_process.setBackgroundColor(ContextCompat.getColor(this,R.color.color_zixun_brand));
        tv_brand_get_openshop_process.setText(getString(R.string.entre_love_list));
        consultShop(bid);
        for(int i =0;i<list_boolean.size();i++){
            if(list_boolean.get(i))
                consultShop(list_openshop.get(i).getBrand_id());
        }
        PrefUtils.putBoolean("love_update",true);
        Intent intent_open_shop = new Intent(BrandActivity.this, OpenShopResultActivity.class);
        startActivity(intent_open_shop);
    }

    /**
     * 咨询店铺
     */
    private void consultShop(String id){
        AddLoveListRequest re = new AddLoveListRequest();
        re.setId(id);
        re.setType("3");
        re.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.SET_LOVE_LIST, re, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {

            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        },AddLoveListResponse.class);
    }


    /**
     * 选中后textview颜色改变
     *
     * @param tv
     */
    private void setColorChange(TextView tv) {
        tv_detail.setTextColor(ContextCompat.getColor(this,R.color.brand_text));
        tv_fee.setTextColor(ContextCompat.getColor(this,R.color.brand_text));
        tv_assess.setTextColor(ContextCompat.getColor(this,R.color.brand_text));
        tv_recommend.setTextColor(ContextCompat.getColor(this,R.color.brand_text));
        tv.setTextColor(ContextCompat.getColor(this,R.color.color_app_text_yes));
    }

    /**
     * 数据初始化
     */
    private void initData() {
        PrefUtils.putBoolean("update_consult",false);

        lv.setOverScrollMode(View.OVER_SCROLL_NEVER); //取消顶部和底部的阴影
        lv.setAdapter(new SpecialAdapter(this, new ArrayList<SpecialResponse.ListBean>()));

        list_assess = new ArrayList<>();
        adapter_assess = new BrandAssessAdapter(this, list_assess);
        lv_assess.setAdapter(adapter_assess);

        list_recommend = new ArrayList<>();
        adapter_recommend = new BrandRecommendAdapter(this, list_recommend);
        gv_recommend.setAdapter(adapter_recommend);

        Intent intent = getIntent();
        bid = intent.getStringExtra("index");
        LoadDialog.show(this,"数据加载中...");
        requestITData();
    }

    /**
     * 获取开店方案网络数据
     */
    private void requestOpenShopData() {
        BrandRequest request = new BrandRequest();
        request.setBid(bid);
        request.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.GET_OPEN_SHOP, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                OpenShopResponse response = (OpenShopResponse) receive.getResponse();
                if (response != null) {
                    isHasOpenshopData = true;
                    if (!dialog.isShowing()) {
                        dialog.show();
                        list_openshop.addAll(response.getData());
                        for (int i = 0; i < list_openshop.size(); i++) {
                            list_boolean.add(i, true);
                        }
                        adapter_dialog.notifyDataSetChanged();
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, OpenShopResponse.class);
    }

    private Dialog dialog; //获取开店方案的dialog
    private ListView lv_dialog; //listview
    private TextView tv_dialog_cancle; //取消
    private TextView tv_dialog_sure; //确定
    private List<OpenShopResponse.DataBean> list_openshop = new ArrayList<>();//打开开店方案数据集合
    private boolean isHasOpenshopData = false; //是否请求过开店方案数据
    private BrandOpenShopAdapter adapter_dialog; //开店方案适配器
    private List<Boolean> list_boolean = new ArrayList<>(); //开店方案选择集合
    private LinearLayout ll_dialog; //选择先生女士
    private EditText et_dialog; //请输入您的姓氏
    private RadioGroup rg_dialog;
    private RadioButton rb_xiansheng;
    private RadioButton rb_nvshi;
    private int sex = 0; //0：先生 1：女士
    private ImageView iv_dialog;
    private TextView tv_dialog_open_shop_agree; //个人信息保护声明

    /**
     * 从底部弹出的dialog
     */
    private void showDialogFromBottom() {
        dialog = new Dialog(this,R.style.style_dialog);
        View view_dialog = LayoutInflater.from(this).inflate(R.layout.dialog_open_shop_plan, null);
        lv_dialog = (ListView) view_dialog.findViewById(R.id.lv_brand_dialog);
        tv_dialog_cancle = (TextView) view_dialog.findViewById(R.id.tv_brand_dialog_cancle);
        tv_dialog_sure = (TextView) view_dialog.findViewById(R.id.tv_brand_dialog_sure);
        tv_dialog_open_shop_agree = (TextView) view_dialog.findViewById(R.id.tv_dialog_open_shop_agree);
        ll_dialog = (LinearLayout) view_dialog.findViewById(R.id.ll_dialog_open_shop_plan);
        et_dialog = (EditText) view_dialog.findViewById(R.id.et_dialog_open_shop_plan);
        rg_dialog = (RadioGroup) view_dialog.findViewById(R.id.rg_dialog_open_shop_plan);
        rb_xiansheng = (RadioButton) view_dialog.findViewById(R.id.rb_dialog_open_shop_plan_xiansheng);
        rb_nvshi = (RadioButton) view_dialog.findViewById(R.id.rb_dialog_open_shop_plan_nvshi);
        iv_dialog = (ImageView) view_dialog.findViewById(R.id.iv_dialog_open_shop_plan);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.get_open_shop_plan).dontAnimate().into(iv_dialog);
        rg_dialog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_dialog_open_shop_plan_xiansheng) {//先生
                    sex = 0;
                } else { //女士
                    sex = 1;
                }
            }
        });
        lv_dialog.setOverScrollMode(View.OVER_SCROLL_NEVER);
        tv_dialog_sure.setOnClickListener(this);
        tv_dialog_cancle.setOnClickListener(this);
        lv_dialog.setOnItemClickListener(this);
        dialog.setContentView(view_dialog);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialogStyle);
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        params.width = metric.widthPixels;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        adapter_dialog = new BrandOpenShopAdapter(this, list_openshop, list_boolean);
        lv_dialog.setAdapter(adapter_dialog);
    }

    /**
     * 请求网络数据
     */
    private void requestITData() {
        BrandRequest request = new BrandRequest();
        request.setUid(LoginUtils.getUid());
        request.setBid(bid);
        HttpUtils.postWithoutUid(MethodConstant.GET_BRAND_DETAIL, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                LoadDialog.dismiss(context);
                BrandResponse response = (BrandResponse) receive.getResponse();
                if (response != null) {//请求数据成功
                    setITData(response);

                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
                LoadDialog.dismiss(context);
            }
        }, BrandResponse.class);
    }

    /**
     * 设置网络数据
     *
     * @param response
     */
    private void setITData(BrandResponse response) {
        getImgData();
        String str = response.getTrue_name();
        if(str==null||str.equals("")){
            ll_dialog.setVisibility(View.VISIBLE);
            iv_dialog.setVisibility(View.GONE);
        }else{
            ll_dialog.setVisibility(View.GONE);
            iv_dialog.setVisibility(View.VISIBLE);
        }
        setHeadData(response.getData());
        setDetailData();
        setAssessData(response.getArticledata());
        setRecommendData(response.getBrandlist());
    }

    /**
     * 设置推荐的数据
     *
     * @param brandlist
     */
    private void setRecommendData(List<BrandResponse.BrandlistBean> brandlist) {
        list_recommend.addAll(brandlist);
        adapter_recommend.notifyDataSetChanged();
    }

    /**
     * 设置评价的数据
     *
     * @param articledata
     */
    private void setAssessData(List<BrandResponse.ArticledataBean> articledata) {
        if (articledata.size()>0){
            btn_raiders.setVisibility(View.VISIBLE);
        }else{
            btn_raiders.setVisibility(View.GONE);
        }
        list_assess.addAll(articledata);
        adapter_assess.notifyDataSetChanged();
    }

    /**
     * 设置详情页的数据
     */
    private void setDetailData() {
        BrandDetailDetailRequest request = new BrandDetailDetailRequest();
        request.setBid(bid);
        HttpUtils.postWithoutUid(MethodConstant.GET_BRAND_DETAIL_DETAIL, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                BrandDetailDetailResponse response = (BrandDetailDetailResponse) receive.getResponse();
                if (response != null) {
                    wv.loadData(response.getHtml(), "text/html;charset=UTF-8", null);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, BrandDetailDetailResponse.class);
    }

    private String str_share_logo; //分享图片
    /**
     * 设置头部的数据
     */
    private void setHeadData(BrandResponse.DataBean data) {
        List<String> list_str = data.getAuth();
        for(int i =0;i<list_str.size();i++){
            ll_head_flags.addView(getTV(list_str.get(i),i));
        }
        if(data.getConsult().equals("0")){
            isConsult = false;
            tv_brand_get_openshop_process.setText(getString(R.string.get_openshop_plan));
            tv_brand_get_openshop_process.setBackgroundColor(ContextCompat.getColor(this,R.color.color_app_text_yes));
        }else{
            isConsult = true;
            tv_brand_get_openshop_process.setText(getString(R.string.entre_love_list));
            tv_brand_get_openshop_process.setBackgroundColor(ContextCompat.getColor(this,R.color.color_zixun_brand));
        }
        if (data.getScribe().equals("0")) {
            isLove = false;
        } else {
            isLove = true;
        }
        if (!isLove) {
            if(!this.isFinishing()&&Util.isOnMainThread())
                Glide.with(this).load(R.drawable.love_white).into(iv_love);
        } else {
            if(!this.isFinishing()&&Util.isOnMainThread())
                Glide.with(this).load(R.drawable.remove_love_list).into(iv_love);
        }
        if(data.getBrand_name()!=null){
            brand = data.getBrand_name();
            tv_title.setText(data.getBrand_name());
        }
        if(Util.isOnMainThread()){
            Glide.with(getApplicationContext()).load(data.getBrand_background()).into(iv_head_bg);
            str_share_logo = data.getBrand_background();
        }
        if(Util.isOnMainThread())
            Glide.with(getApplicationContext()).load(data.getBrand_logo()).transform(new GlideRoundTransform(this)).into(iv_head_logo);
        tv_head_title.setText(data.getBrand_name() + " · 投资 " + data.getInvest_amount() + " 万 · 总部" + data.getBrand_location() + " · 适合面积 " + data.getShop_area());
        setOneFee(data.getBaozheng_fei(), tv_baozheng, rl_baozheng);
        setOneFee(data.getJoin_fei(), tv_join, rl_join);
        setOneFee(data.getShebei_fei(), tv_shebei, rl_shebei);
        setOneFee(data.getWuliao_fei(), tv_wuliao, rl_wuliao);
        setOneFee(data.getZhuangxiu_fei(), tv_zhuangxiu, rl_zhuangxiu);
        setOneFee(data.getGuanli_fei(), tv_guanli, rl_guanli);
        setOneFee(data.getShangbiao_fei(), tv_shangbiao, rl_shangbiao);
        setOneFee(data.getBrand_shiyong_fei(), tv_shiyong, rl_shiyong);
        setOneFee(data.getSheji_fei(), tv_sheji, rl_sheji);
        setOneFee(data.getQita_fei(), tv_qita, rl_qita);
        tv_detail_total.setText("预计初期投入" + data.getInvest_amount() + "万");
    }

    private int [] bgs = new int[]{R.drawable.shape_brand_color1,R.drawable.shape_brand_color2,R.drawable.shape_brand_color3,R.drawable.shape_brand_color4};
    /**
     * 添加textview
     * @param content
     * @return
     */
    private TextView getTV(String content,int index){
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,20,0);
        tv.setLayoutParams(params);
        tv.setMaxLines(1);
        tv.setTextSize(12);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setPadding(10,5,10,5);
        tv.setText(content);
        index = index%4;
        tv.setBackgroundResource(bgs[index]);
        tv.setTextColor(getResources().getIntArray(R.array.brand_colors)[index]);
        return tv;
    }

    private int img_total = 0;
    private ArrayList<String> list_img = new ArrayList<>();
    /**
     * 获取图库数据
     */
    private void getImgData() {
        BrandRequest request = new BrandRequest();
        request.setBid(bid);
        request.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.GET_BRAND_IMGS, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                BrandImgResponse response = (BrandImgResponse) receive.getResponse();
                if(response!=null){
                    img_total = response.getTotal();
                    tv_head_imgs.setText("查看"+response.getTotal()+"张照片");
                    list_img.addAll(response.getData());
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, BrandImgResponse.class);

    }

    /**
     * 设置某一项的值
     */
    private void setOneFee(String fee, TextView tv, RelativeLayout ll) {
        if (!NumberUtils.isRealNumber(fee) || Float.parseFloat(fee) == 0.0) {
            ll.setVisibility(View.GONE);
        } else {
            tv.setText(fee + "万");
            ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()) {
            case R.id.gv_brand_recommend://推荐
                MobclickAgent.onEvent(this,"gv_brand_recommend");
                if(list_recommend.get(i)!=null) {
                    Intent intent_recommend = new Intent(this, BrandActivity.class);
                    intent_recommend.putExtra("index", list_recommend.get(i).getBrand_id());
                    startActivity(intent_recommend);
                }
                break;
            case R.id.lv_brand_dialog://获取开店方案
                if(list_openshop.get(i)!=null) {
                    Intent intent_dialog = new Intent(this, BrandActivity.class);
                    intent_dialog.putExtra("index", list_openshop.get(i).getBrand_id());
                    startActivity(intent_dialog);
                }
                break;
            case R.id.lv_brand_assess: //评价
                MobclickAgent.onEvent(this,"lv_brand_assess");
                if(list_assess.get(i)!=null){
                    Intent intent_assess = new Intent(this,ArticleDetailActivity.class);
                    intent_assess.putExtra("id",list_assess.get(i).getArticle_id());
                    startActivity(intent_assess);
                }
                break;
        }

    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        switch (i) {
            case SCROLL_STATE_TOUCH_SCROLL:
                scrollFlag = true;
                break;
            case SCROLL_STATE_FLING:
                scrollFlag = true;
                break;
            case SCROLL_STATE_IDLE:
                scrollFlag = false;
                break;
            default:
                break;
        }
    }

    private boolean scrollFlag = false;// 标记是否滑动
    private int lastVisibleItemPosition;// 标记上次滑动位置

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int i1, int i2) {
        if(absListView.getId()==R.id.lv_brand_detail){
            if (scrollFlag) {

                if(firstVisibleItem==1){
                    setColorChange(tv_detail);
                }else if(firstVisibleItem==2){
                    setColorChange(tv_fee);
                }else if(firstVisibleItem==3){
                    setColorChange(tv_assess);
                }else if(firstVisibleItem==4){
                    setColorChange(tv_recommend);
                }
                if (firstVisibleItem > lastVisibleItemPosition) {//上滑

                }
                if (firstVisibleItem < lastVisibleItemPosition) {//下滑

                }
                if (firstVisibleItem == lastVisibleItemPosition) {
                    return;
                }
                lastVisibleItemPosition = firstVisibleItem;

            }
        }
    }
}
