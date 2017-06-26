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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.GetUserCollectionRequest;
import com.can.bimuprojects.Module.Response.GetUserCollectionResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.CollectionArticleAdapter;
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
 * Created by can on 2017/4/25.
 * 收藏的文章
 */

public class CollectionArticleActivity extends BaseActivity implements View.OnClickListener, RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private ImageView iv_exit; //退出按钮
    private RefreshListView lv;  //集合控件
    private CollectionArticleAdapter adapter ; //适配器
    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_collection_article);
        lv = (RefreshListView) findViewById(R.id.lv_collection_article);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.collection_article);
        iv_exit.setOnClickListener(this);
    }

    private int page = 0 ;//页码
    private List<GetUserCollectionResponse.ListBean> list; //数据集合
    private boolean hasMore =true; //是否有更多数据
    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<>();
        adapter = new CollectionArticleAdapter(this,list);
        lv.setAdapter(adapter);

        requestITData();
    }

    /**
     * 请求网络数据
     */
    private void requestITData() {
        GetUserCollectionRequest request = new GetUserCollectionRequest();
        request.setUid(LoginUtils.getUid());
        request.setP(page+"");
        HttpUtils.postWithoutUid(MethodConstant.GET_COLLECTION_ARTICLE, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetUserCollectionResponse response = (GetUserCollectionResponse) receive.getResponse();
                if(response!=null){
                    if(page==0){
                        list.clear();
                    }
                    list.addAll(response.getList());
                    adapter.notifyDataSetChanged();
                    if(response.getList().size()>=10)
                        hasMore = true;
                    else
                        hasMore = false;
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, GetUserCollectionResponse.class);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exit:
                finish();
                break;
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1: //刷新
                    lv.completeRefresh();
                    if(!AppUtils.isNetworkOK(getApplicationContext())){
                        ToastUtils.showShort(getApplicationContext(),"网络无法连接");
                    }else{
                        page=0;
                        requestITData();
                    }
                    break;
                case 2://加载更多
                    lv.completeRefresh();
                    if(!AppUtils.isNetworkOK(getApplicationContext())){
                        ToastUtils.showShort(getApplicationContext(),"网络无法连接");
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
        if(i>0){
            Intent intent = new Intent(this,ArticleDetailActivity.class);
            intent.putExtra("id",list.get(i-1).getArticle_id());
            startActivityForResult(intent,1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            if(requestCode==1&&resultCode==1){
                if(!AppUtils.isNetworkOK(this)){
                    Toast.makeText(this, "网络无法连接", Toast.LENGTH_SHORT).show();
                }else{
                    page=0;
                    requestITData();
                }
            }

    }
}




