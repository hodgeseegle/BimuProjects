package com.can.bimuprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.SpecialRequest;
import com.can.bimuprojects.Module.Response.SpecialResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.SpecialAdapter;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.RefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/12.
 * 专题页面
 */

public class SpecialActivity extends BaseActivity implements View.OnClickListener, RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {

    private ImageView iv_exit; //退出按钮
    private RefreshListView rlv;//集合控件
    private SpecialAdapter adapter ; //适配器
    private List<SpecialResponse.ListBean> list ; //数据集合
    private int page = 0 ; //页数
    private static  final int TYPE_REFRESH = 000;//下拉刷新
    private static final int TYPE_LOADMORE = 111 ; //上拉加载
    private boolean hasMore = false; //是否有更多的数据


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case TYPE_LOADMORE : //加载更多
                    rlv.completeRefresh();
                    if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                        ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
                        break;
                    }else{
                        page++;
                        requestITData(page);
                    }
                    break;
                case TYPE_REFRESH: //刷新
                    rlv.completeRefresh();
                    if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                        ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
                        break;
                    }else{
                        page = 0;
                        requestITData(page);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tv_title.setText(R.string.special);
        list = new ArrayList<>();
        adapter = new SpecialAdapter(this,list);
        rlv.setAdapter(adapter);
        requestITData(page);
    }

    /**
     * 请求网络数据
     */
    private void requestITData(int p) {
        SpecialRequest request = new SpecialRequest();
        request.setP(p+"");
        request.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.GET_SPECIAL, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                SpecialResponse response = (SpecialResponse) receive.getResponse();
                if(response!=null&&response.getExe_success()==1&&response.getList().size()>0){
                    if(page==0){
                        list.clear();
                    }
                    if(response.getList().size()==5){
                        hasMore = true;
                    }else{
                        hasMore = false;
                    }
                    list.addAll(response.getList());
                    adapter.notifyDataSetChanged();
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
            }
        },SpecialResponse.class);
    }

    private TextView tv_title ;//标题
    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_special);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        rlv = (RefreshListView) findViewById(R.id.rlv_special);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        iv_exit.setOnClickListener(this);
        rlv.setOnRefreshListener(this);
        rlv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exit://退出
                finish();
                break;
        }
    }

    @Override
    public void onPullRefresh() {
        handler.sendEmptyMessageDelayed(TYPE_REFRESH,1200);
    }

    @Override
    public void onLoadingMore() {
        if (hasMore)
            handler.sendEmptyMessageDelayed(TYPE_LOADMORE,1200);
        else
            rlv.completeRefresh();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i>0&&i-1<list.size()) {
            SpecialResponse.ListBean data = list.get(i - 1);
            int type = data.getType();
            MobclickAgent.onEvent(this, "rlv_special");
            if (type == 3) {//文章详情
                Intent intent = new Intent(this, ArticleDetailActivity.class);
                intent.putExtra("id", data.getId());
                startActivity(intent);
            } else if (type == 1) {//文章列表
                Intent intent = new Intent(this, SpecialArticleActivity.class);
                intent.putExtra("id", data.getId());
                startActivity(intent);
            }
        }
    }
}
