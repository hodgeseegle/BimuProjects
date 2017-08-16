package com.can.bimuprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.ChoiceRecommendRequest;
import com.can.bimuprojects.Module.Request.HomePagerRequest;
import com.can.bimuprojects.Module.Response.ChoiceRecommendResponse;
import com.can.bimuprojects.Module.Response.HomePagerResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.ChoiceRecommendAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.view.BrandGridView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/08/15.
 * 精选推荐界面
 */

public class ChoiceRecommendActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private ImageView iv_exit; //退出按钮
    private TextView tv_title ; //标题
    private BrandGridView lv ; //集合控件
    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_choice_recommend);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        tv_title = (TextView) findViewById(R.id.tv_title);
        lv = (BrandGridView) findViewById(R.id.lv_choice_recommend);
    }

    private List<ChoiceRecommendResponse.BrandLabelBean> list ; //集合数据
    private ChoiceRecommendAdapter adapter ; //适配器
    /**
     * 初始化数据
     */
    private void initData() {
        tv_title.setText(getString(R.string.choice_recommend));
        list = new ArrayList<>();
        adapter = new ChoiceRecommendAdapter(this,list);
        lv.setAdapter(adapter);

        requestITData();
    }

    //请求网络数据
    private void requestITData() {
        ChoiceRecommendRequest request = new ChoiceRecommendRequest();
        request.setUid(LoginUtils.getLoginUid());
        request.setPage(""+0);
        HttpUtils.postWithoutUid(MethodConstant.CHOICE_RECOMMEND, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                ChoiceRecommendResponse response = (ChoiceRecommendResponse) receive.getResponse();
                if(response!=null){
                    List<ChoiceRecommendResponse.BrandLabelBean> data = response.getBrand_label();
                    if(data!=null){
                        list.addAll(data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, ChoiceRecommendResponse.class);

    }


    /**
     设置监听
     */
    private void setListener() {
        iv_exit.setOnClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_choice_recommend:
                Intent intent = new Intent(ChoiceRecommendActivity.this,BrandListActivity.class);
                intent.putExtra(BrandListActivity.DATA, (Serializable) list.get(i).getData());
                intent.putExtra(BrandListActivity.NAME,list.get(i).getName());
                startActivity(intent);
                break;
        }
    }
}
