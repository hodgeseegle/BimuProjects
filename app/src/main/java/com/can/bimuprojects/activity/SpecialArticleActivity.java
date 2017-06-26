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
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Entity.RecommandCardBean;
import com.can.bimuprojects.Module.Request.GetListAdRequest;
import com.can.bimuprojects.Module.Response.HomeMoreResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.SpecialArticleListAdapter;
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
 * Created by can on 2017/4/24.
 * 专题文章列表
 */

public class SpecialArticleActivity extends BaseActivity implements View.OnClickListener, RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }


    private RefreshListView lv; //集合控件
    private ImageView iv_exit;//退出按钮
    private List<RecommandCardBean> list; //集合
    private SpecialArticleListAdapter adapter; //适配器
    private TextView tv_title; //标题
    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_special_article);
        lv = (RefreshListView) findViewById(R.id.lv_special_article);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    private String id ; //id
    private int page =0;//页码
    private boolean hasMore = true;

    /**
     * 初始化数据
     */
    private void initData() {
        tv_title.setText(R.string.aritcle_list);
        id = getIntent().getStringExtra("id");
        list = new ArrayList<>();
        adapter = new SpecialArticleListAdapter(this,list);
        lv.setAdapter(adapter);

        requestITData();
    }

    /**
     * 请求网络数据
     */
    private void requestITData() {
        GetListAdRequest request = new GetListAdRequest();
        request.setUid(LoginUtils.getUid());
        request.setAd_id(id);
        request.setType(0);
        request.setId_type(2);
        request.setTimestamp(page+"");
        HttpUtils.postWithoutUid(MethodConstant.GET_ARTICLE_LIST_AD, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                HomeMoreResponse response = (HomeMoreResponse) receive.getResponse();
                if(response!=null){
                    if(page==0){
                        list.clear();
                    }
                    list.addAll(response.getCards());
                    adapter.notifyDataSetChanged();
                    if(response.cards.size()>=5) {
                        hasMore = true;
                    }else{
                        hasMore = false;
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, HomeMoreResponse.class);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        iv_exit.setOnClickListener(this);
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.iv_exit){
            finish();
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    lv.completeRefresh();
                    if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                        ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
                        break;
                    }else{
                        page=0;
                        requestITData();
                    }
                    break;
                case 2:
                    lv.completeRefresh();
                    if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                        ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
                        break;
                    }else{
                        page++;
                        requestITData();
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public void onPullRefresh() {
        handler.sendEmptyMessageDelayed(1,500);
    }

    @Override
    public void onLoadingMore() {
        if(hasMore)
            handler.sendEmptyMessageDelayed(2,500);
        else
            lv.completeRefresh();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i>0&&i-1<list.size()){
            Intent intent = new Intent(this,ArticleDetailActivity.class);
            intent.putExtra("id",list.get(i-1).getArticle_id());
            startActivity(intent);
        }
    }
}
