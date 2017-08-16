package com.can.bimuprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.BrandAfterConsultRequest;
import com.can.bimuprojects.Module.Response.BrandAfterConsultResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.OpenShopResultAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.GlideUtil;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ShareUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.LoadMoreListView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/16.
 * 获取开店方案结果
 */

public class OpenShopResultActivity extends BaseActivity implements View.OnClickListener, LoadMoreListView.OnRefreshListener, AdapterView.OnItemClickListener {

    public static String STRING_LIST = "string";
    private String stringList = ""; //咨询的品牌的列表id
    private String bid = "" ; //咨询的主品牌id
    private int p = 0 ; //页码
    private OpenShopResultAdapter adapter ; //适配器
    private List<BrandAfterConsultResponse.DataBean> list ; //数据集合
    private boolean hasMore = false ; //是否有更多数据
    private ImageView iv_exit; //返回按钮

    private LoadMoreListView lv; //listview

    private String str_logo ; //分享的品牌logo
    private String str_name ; //分享的品牌名

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_openshop_result);
        lv = (LoadMoreListView) findViewById(R.id.lv_openshop_result);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        initTopView();
    }

    private LinearLayout ll_wx,ll_wx_circle ; //微信好友 微信朋友圈
    private ImageView iv_wx , iv_wx_circle; //微信好友 微信朋友圈
    //初始化顶部view
    private void initTopView() {
        View view_top = LayoutInflater.from(this).inflate(R.layout.item_openshop_result_top,null);
        ll_wx = (LinearLayout) view_top.findViewById(R.id.ll_wx);
        ll_wx_circle = (LinearLayout) view_top.findViewById(R.id.ll_wx_circle);
        iv_wx = (ImageView) view_top.findViewById(R.id.iv_wx);
        iv_wx_circle = (ImageView) view_top.findViewById(R.id.iv_wx_circle);
        lv.addHeaderView(view_top);
    }

    /**
     * 初始化监听
     */
    private void setListener() {
        iv_exit.setOnClickListener(this);
        ll_wx.setOnClickListener(this);
        ll_wx_circle.setOnClickListener(this);
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_wx: //分享到微信好友
                ShareUtils.shareBrand(this, SHARE_MEDIA.WEIXIN,bid,str_name,str_logo);
                break;
            case R.id.ll_wx_circle: //分享到朋友圈
                ShareUtils.shareBrand(this,SHARE_MEDIA.WEIXIN_CIRCLE,bid,str_name,str_logo);
                break;
            case R.id.iv_exit: //退出
                finish();
                break;
        }
    }

    /**
     * 初始化数据
     */
    private void initData() {
        stringList = getIntent().getStringExtra(STRING_LIST);
        bid = getIntent().getStringExtra("brand");
        str_logo = getIntent().getStringExtra("logo");
        str_name =getIntent().getStringExtra("name");
        GlideUtil.loadDrawableImg(this,R.drawable.img_wx,iv_wx);
        GlideUtil.loadDrawableImg(this,R.drawable.img_wx_circle,iv_wx_circle);

        list = new ArrayList<>();
        adapter = new OpenShopResultAdapter(this,list);
        lv.setAdapter(adapter);
        requestITData();
    }

    /**
     * 请求网络数据
     */
    private void requestITData() {
        BrandAfterConsultRequest request = new BrandAfterConsultRequest();
        request.setUid(LoginUtils.getUid());
        request.setBid(stringList);
        request.setP(p+"");
        HttpUtils.postWithoutUid(MethodConstant.BRAND_AFTERCONSULT, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                BrandAfterConsultResponse response = (BrandAfterConsultResponse) receive.getResponse();
                hasMore = false;
                if(response!=null){
                    List<BrandAfterConsultResponse.DataBean> bean = response.getData();
                    if(bean!=null){
                        if(bean.size()>=5)
                            hasMore = true;
                        list.addAll(bean);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, BrandAfterConsultResponse.class);
    }

    @Override
    public void onLoadingMore() {
        if(hasMore)
            handler.sendEmptyMessageDelayed(1,500);
        else
            lv.completeRefresh();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    lv.completeRefresh();
                    if(AppUtils.isNetworkOK(OpenShopResultActivity.this)){
                        p++;
                        requestITData();
                    }else{
                        ToastUtils.showShort(OpenShopResultActivity.this,"网络连接错误！");
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i>=1&&i-1<list.size()){
            String id = list.get(i-1).getBrand_id();
            Intent intent = new Intent(this,BrandActivity.class);
            intent.putExtra("index",id);
            startActivity(intent);
        }
    }
}