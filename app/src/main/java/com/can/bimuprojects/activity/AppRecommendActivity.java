package com.can.bimuprojects.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Entity.AppRecommendBean;
import com.can.bimuprojects.Module.Request.GetAppRecommendRequest;
import com.can.bimuprojects.Module.Response.GetAppRecommendResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.AppRecommendAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/5/16.
 * 应用推荐
 */

public class AppRecommendActivity extends BaseActivity implements View.OnClickListener, LoadMoreListView.OnRefreshListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private ImageView iv_exit ; //退出按钮
    private LoadMoreListView lv ; //集合控件
    private AppRecommendAdapter adapter ; //适配器
    private List<AppRecommendBean> list ; //数据集合
    private int page = 0 ;//页码
    private boolean hasMore = false ; //是否有更多数据

    //初始化view
    private void initView() {
        setContentView(R.layout.activity_app_recommend);
        iv_exit  = (ImageView) findViewById(R.id.iv_exit);
        lv = (LoadMoreListView) findViewById(R.id.lv_app_recommend);
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.app_recommend);
    }

    //初始化数据
    private void initData() {
        list = new ArrayList<>();
        adapter = new AppRecommendAdapter(this,list);
        lv.setAdapter(adapter);
        requestITData();
    }

    //请求网络数据
    private void requestITData() {
        GetAppRecommendRequest request = new GetAppRecommendRequest();
        request.setUid(LoginUtils.getUid());
        request.setType("1"); //1表示android
        request.setP(page+"");
        HttpUtils.postWithoutUid(MethodConstant.GET_APP_RECOMMEND, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetAppRecommendResponse response = (GetAppRecommendResponse) receive.getResponse();
                hasMore = false;
                if(response!=null&&response.getExe_success()==1){
                    if(response.getList().size()>=10)
                        hasMore = true;
                    list.addAll(response.getList());
                    adapter.notifyDataSetChanged();
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        },GetAppRecommendResponse.class);
    }

    //设置监听
    private void setListener() {
        iv_exit.setOnClickListener(this);
        lv.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exit://退出
                finish();
                break;
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            lv.completeRefresh();
            if(!AppUtils.isNetworkOK(AppRecommendActivity.this))
                ToastUtils.showShort(AppRecommendActivity.this,"网络连接错误");
            else {
                page++;
                requestITData();
            }
            return false;
        }
    });

    @Override
    public void onLoadingMore() {
        if(hasMore)
            handler.sendEmptyMessageDelayed(1,500);
        else
            lv.completeRefresh();
    }
}
