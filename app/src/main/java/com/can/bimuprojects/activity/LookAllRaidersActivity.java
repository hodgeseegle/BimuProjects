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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.GetLookAllRaidersRequest;
import com.can.bimuprojects.Module.Response.GetLookAllRaidersResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.LookAllRaidersAdapter;
import com.can.bimuprojects.application.BimuApplication;
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
 * Created by can on 2017/4/24.
 * 查看全部攻略
 */

public class LookAllRaidersActivity extends BaseActivity implements View.OnClickListener, LoadMoreListView.OnRefreshListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private LoadMoreListView lv; //数据集合控件
    private ImageView iv_exit; //退出按钮
    private LinearLayout ll_comment ; //写点评按钮
    private ImageView iv_write ; //写点评
    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_lookallraider);
        lv = (LoadMoreListView) findViewById(R.id.lv_look_all_raiders);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        iv_write = (ImageView) findViewById(R.id.iv_write_comment);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.write_comment).into(iv_write);
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.raiders);
        ll_comment = (LinearLayout) findViewById(R.id.ll_look_all_raiders_write_comment);
    }

    private String bid ; //品牌bid
    private int page = 0 ; //页码
    private List<GetLookAllRaidersResponse.ArticlesBean> list;//数据集合
    private LookAllRaidersAdapter adapter ; //适配器
    private boolean hasMore = true; //是否有更多数据
    private String brand ; //品牌名称
    /**
     * 初始化数据
     */
    private void initData() {
        bid = getIntent().getStringExtra("bid");
        brand = getIntent().getStringExtra("brand");

        list = new ArrayList<>();
        adapter = new LookAllRaidersAdapter(this,list);
        lv.setAdapter(adapter);

        requestITData();
    }

    /**
     * 获取网络数据
     */
    private void requestITData() {
        GetLookAllRaidersRequest request = new GetLookAllRaidersRequest();
        request.setUid(LoginUtils.getUid());
        request.setBid(bid);
        request.setP(""+page);
        HttpUtils.postWithoutUid(MethodConstant.GET_LOOK_ALL_RAIDERS, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetLookAllRaidersResponse response = (GetLookAllRaidersResponse) receive.getResponse();
                if(response!=null&&response.getExe_success()==1){
                    if(response.getArticles().size()>=20){
                        hasMore = true;
                    }else{
                        hasMore = false;
                    }
                    list.addAll(response.getArticles());
                    adapter.notifyDataSetChanged();
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, GetLookAllRaidersResponse.class);
    }


    /**
     * 设置监听
     */
    private void setListener() {
        iv_exit.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exit: //退出
                finish();
                break;
            case R.id.ll_look_all_raiders_write_comment://写点评
                if(showLoginDialog())
                    break;
                Intent intent = new Intent(LookAllRaidersActivity.this,SendArticleActivity.class);
                intent.putExtra("id",bid);
                intent.putExtra("brand",brand);
                startActivity(intent);
                break;
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            lv.completeRefresh();
            if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
            }else{
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i<list.size()){
            Intent intent = new Intent(this,ArticleDetailActivity.class);
            intent.putExtra("id",list.get(i).getArticle_id());
            startActivity(intent);
        }
    }
}
