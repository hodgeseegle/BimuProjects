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
import com.can.bimuprojects.Module.Request.GetUserDiscountRequest;
import com.can.bimuprojects.Module.Response.GetUserDiscountResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.UserDiscountAdapter;
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
 * Created by can on 2017/4/27.
 * 我的约惠
 */

public class DiscountActivity extends BaseActivity implements RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private ImageView iv_exit; //退出按钮
    private RefreshListView lv;//集合控件
    private int page = 0;//页码
    private List<GetUserDiscountResponse.BarginListBean> list ; //集合数据
    private boolean hasMore  = false ; //是否有更多数据
    private UserDiscountAdapter adapter ; //适配器

    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_discount);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        lv = (RefreshListView) findViewById(R.id.lv_discount);
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.my_discount);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<>();
        adapter = new UserDiscountAdapter(this,list);
        lv.setAdapter(adapter);

        requestITData();
    }

    /**
     * 请求网络数据
     */
    private void requestITData() {
        GetUserDiscountRequest request = new GetUserDiscountRequest();
        request.setUid(LoginUtils.getUid());
        request.setIndex(""+page);
        HttpUtils.postWithoutUid(MethodConstant.GET_USER_BARGIN, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetUserDiscountResponse response = (GetUserDiscountResponse) receive.getResponse();
                if(response!=null){
                    if(page==0){
                        list.clear();
                    }
                    if(response.getBargin_list().size()>=5){
                        hasMore = true;
                    }else{
                        hasMore = false;
                    }
                    list.addAll(response.getBargin_list());
                    adapter.notifyDataSetChanged();
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        },GetUserDiscountResponse.class);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1://刷新
                    lv.completeRefresh();
                    if(!AppUtils.isNetworkOK(getApplicationContext())){
                        ToastUtils.showShort(getApplicationContext(),"网络连接错误");
                    }else{
                        page=0;
                        requestITData();
                    }
                    break;

                case 2://加载更多
                    lv.completeRefresh();
                    if(!AppUtils.isNetworkOK(getApplicationContext())){
                        ToastUtils.showShort(getApplicationContext(),"网络连接错误");
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
            GetUserDiscountResponse.BarginListBean data = list.get(i-1);
            Intent intent = new Intent(this,ExerciseActivity.class);
            intent.putExtra("id",data.getId());
            startActivity(intent);
        }
    }
}
