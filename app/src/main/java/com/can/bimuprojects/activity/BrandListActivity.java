package com.can.bimuprojects.activity;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.ChoiceRecommendRequest;
import com.can.bimuprojects.Module.Response.ChoiceRecommendResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.BrandListAdapter;
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
 * Created by can on 2017/08/15.
 * 品牌列表界面
 */
public class BrandListActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, LoadMoreListView.OnRefreshListener {

    public static final String DATA = "data";
    public static final String NAME = "name";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private ImageView iv_exit; //退出按钮
    private TextView tv_title ; //标题
    private LoadMoreListView lv; //集合控件
    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_brand_list);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        tv_title = (TextView) findViewById(R.id.tv_title);
        lv = (LoadMoreListView) findViewById(R.id.lv_activity);
    }

    private List<ChoiceRecommendResponse.BrandLabelBean.DataBean> list ; //数据集合
    private BrandListAdapter adapter ; //适配器
    private String name ; //标签
    private boolean hasMore = false ; //是否有更多数据
    private int page = 0; //页码
    /**
     * 初始化数据
     */
    private void initData() {
        tv_title.setText(getString(R.string.brand_list));
        list = new ArrayList<>();
        adapter = new BrandListAdapter(this,list);
        lv.setAdapter(adapter);

        name = getIntent().getStringExtra(NAME);
        if(name!=null)
            tv_title.setText(name);
        List<ChoiceRecommendResponse.BrandLabelBean.DataBean> data = (List<ChoiceRecommendResponse.BrandLabelBean.DataBean>) getIntent().getSerializableExtra(DATA);
        if(data!=null){
            if(data.size()>=5)
                hasMore= true;
            list.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    //请求网络数据
    private void requestITData() {
        ChoiceRecommendRequest request = new ChoiceRecommendRequest();
        request.setUid(LoginUtils.getLoginUid());
        request.setPage(""+page);
        HttpUtils.postWithoutUid(MethodConstant.CHOICE_RECOMMEND, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                ChoiceRecommendResponse response = (ChoiceRecommendResponse) receive.getResponse();
                hasMore = false;
                if(response!=null){
                    List<ChoiceRecommendResponse.BrandLabelBean> data = response.getBrand_label();
                    if(data!=null){
                        for(int i =0;i<data.size();i++){
                            String string = data.get(i).getName();
                            List<ChoiceRecommendResponse.BrandLabelBean.DataBean> beanList = data.get(i).getData();
                            if(string.equals(name)){
                                if(beanList!=null&&beanList.size()>=5)
                                    hasMore = true;
                                list.addAll(beanList);
                                adapter.notifyDataSetChanged();
                                return;
                            }
                        }
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
        lv.setOnRefreshListener(this);
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
            case R.id.lv_activity:
                if(i<list.size()) {
                    Intent intent_hot = new Intent(BrandListActivity.this, BrandActivity.class);
                    intent_hot.putExtra("index", list.get(i).getBrand_id());
                    startActivity(intent_hot);
                }
                break;
        }
    }

    @Override
    public void onLoadingMore() {
        if(hasMore){
            handler.sendEmptyMessageDelayed(1,500);
        }else{
            lv.completeRefresh();
        }
    }

    /**
     * 主线程处理
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    lv.completeRefresh();
                    if(!AppUtils.isNetworkOK(BrandListActivity.this)){
                        ToastUtils.showShort(BrandListActivity.this,"网络连接错误!");
                    }else{
                        page++;
                        requestITData();
                    }
                    break;
            }
            return false;
        }
    });

}
