package com.can.bimuprojects.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Module.Request.GetFindProjectRecode;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.FindProject2Request;
import com.can.bimuprojects.Module.Response.FindProject2Reponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.FindProjectAdapter;
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
 * Created by can on 2017/4/11.
 * 找项目返回结果界面
 */

public class FindProjectResultActivity extends BaseActivity implements View.OnClickListener, RefreshListView.OnRefreshListener,  AdapterView.OnItemClickListener, ViewTreeObserver.OnScrollChangedListener {

    public static final int TYPE_REFRESH = 1; //刷新
    public static final int TYPE_LOADMORE = 2; //加载更多

    private boolean hasMore = false; //是否有更多数据
    private List<FindProject2Reponse.DataBean> list; //数据集合

    private ImageView iv_exit; //推出按钮
    private RefreshListView lv ; //集合控件
    private FindProjectAdapter adapter ; //适配器
    private TextView tv1,tv2,tv3 ; //隐藏控件子view
    private TextView tv1_item,tv2_item,tv3_item;//listview头部子view
    private LinearLayout ll_hide ; //隐藏控件父view

    private boolean isFind  =false; //是否从查看全部进来的

    private Handler handler = new Handler(new Handler.Callback() {//主线程处理
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case TYPE_REFRESH:
                    lv.completeRefresh();
                    if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                        ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
                        break;
                    }else{
                        page = 0;
                        requestInternet();
                    }
                    break;
                case TYPE_LOADMORE:
                    lv.completeRefresh();
                    if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                        ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
                        break;
                    }else {
                        page++;
                        requestInternet();
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

    private String amount ; //金额
    private String cid ; //兴趣行业cid
    private String area ; //面积大小
    private String interest ; //兴趣行业内容
    private int page = 0;
    /**
     * 数据初始化
     */
    private void initData() {
        tv_title.setText(R.string.bimu_recommend);

        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");
        cid = intent.getStringExtra("cid");
        area = intent.getStringExtra("area");
        interest = intent.getStringExtra("interest");
        isFind = intent.getBooleanExtra("isFind",false);

        if(isFind){
            //lv.addHeaderView(new TextView(this));
        }else{
            lv.addHeaderView(headview);
        }

        if(amount.equals("")){
            if(area.equals("")){
                tv1.setText(interest);
                tv1_item.setText(interest);
            }else{
                tv1.setText(area);
                tv2.setText(interest);
                tv1_item.setText(area);
                tv2_item.setText(interest);
            }
        }else {
            tv1.setText(amount);
            tv1_item.setText(amount);
            if(area.equals("")){
                tv2.setText(interest);
                tv2_item.setText(interest);
            }else{
                tv2.setText(area);
                tv3.setText(interest);
                tv2_item.setText(area);
                tv3_item.setText(interest);
            }
        }

        list = new ArrayList<>();
        adapter = new FindProjectAdapter(this,list);
        lv.setAdapter(adapter);

        if(area.equals("暂无")) {
            area = "" +
                    "";
        }
        if(amount!=null){
            amount = amount.trim().replace("万","").replaceAll("以上","").replaceAll("以下","");
            if(amount.equals("10")) {
                amount = "0.1-10";
            }
            if(amount.equals("不限")) {
                amount = "0-1000";
            }
            if(amount.equals("100")){
                amount = "100-10000";
            }
        }
        GetFindProjectRecode recode = new GetFindProjectRecode();
        recode.setAmount(amount);
        recode.setUid(LoginUtils.getUid());
        recode.setArea(area);
        recode.setLocate("");
        recode.setCname(interest);
        recode.setClassX(cid);

        HttpUtils.postWithoutUid(MethodConstant.GET_FIND_PROJECT_RECODE, recode, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {

            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        });

        requestInternet();
    }

    /**
     * 请求网络数据
     */
    private void requestInternet() {
        FindProject2Request request = new FindProject2Request();
        request.setUid(LoginUtils.getUid());
        request.setArea(area);
        request.setAmount(amount);
        request.setCid(cid);
        request.setType("1");
        request.setPage(page+"");
        HttpUtils.postWithoutUid(MethodConstant.GET_FIND_PROJECT, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                hasMore = false;
                lv.completeRefresh();
                FindProject2Reponse response = (FindProject2Reponse) receive.getResponse();
                if(response!=null&&response.getExe_success()==1){
                    if(page==0){
                        list.clear();
                    }
                    if(response.getData().size()>0){
                        hasMore = true;
                        if(response.getData().size()>=5){
                            hasMore = true;
                        }else{
                            hasMore = false;
                        }
                        list.addAll(response.getData());
                        adapter.notifyDataSetChanged();
                    }
                }else if(response!=null&&response.getData().size()==0&&page==0){
                    Dialog dialog = ToastUtils.showDialog(FindProjectResultActivity.this,"提示",getString(R.string.no_brand),View.inflate(context, R.layout.dialog_circle,null),R.style.circle_dialog);
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            FindProjectResultActivity.this.finish();
                        }
                    });
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
                lv.completeRefresh();
            }
        }, FindProject2Reponse.class);
    }

    private TextView tv_title ; //标题
    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_findproject_result);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        tv_title = (TextView) findViewById(R.id.tv_title);
        lv = (RefreshListView) findViewById(R.id.lv_findproject_result);
        tv1 = (TextView) findViewById(R.id.tv_find_project_result1);
        tv2 = (TextView) findViewById(R.id.tv_find_project_result2);
        tv3 = (TextView) findViewById(R.id.tv_find_project_result3);
        ll_hide  = (LinearLayout) findViewById(R.id.ll_find_project_result);
        initHeadView();
    }

    private View headview ;
    /**
     * 初始化头部view
     */
    private void initHeadView() {
        headview = LayoutInflater.from(this).inflate(R.layout.headview_find_project_result,null);
        tv1_item = (TextView) headview.findViewById(R.id.tv_item_find_project_result1);
        tv2_item = (TextView) headview.findViewById(R.id.tv_item_find_project_result2);
        tv3_item = (TextView) headview.findViewById(R.id.tv_item_find_project_result3);
    }

    /**
     * 设置点击监听
     */
    private void setListener() {
        iv_exit.setOnClickListener(this);
        lv.setOnRefreshListener(this);
        lv.getViewTreeObserver().addOnScrollChangedListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exit://点击 退出
                finish();
                break;
        }
    }

    @Override
    public void onPullRefresh() {
        handler.sendEmptyMessageDelayed(TYPE_REFRESH,500);
    }

    @Override
    public void onLoadingMore() {
        if (hasMore)
            handler.sendEmptyMessageDelayed(TYPE_LOADMORE,500);
        else
            lv.completeRefresh();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(isFind){
            if (i > 0&&i - 1<list.size()) {
                MobclickAgent.onEvent(this,"lv_findproject_result");
                Intent intent = new Intent(this, BrandActivity.class);
                intent.putExtra("index", list.get(i - 1).getBrand_id());
                startActivity(intent);
            }
        }else{
            if (i > 1&&i - 2<list.size()) {
                MobclickAgent.onEvent(this,"lv_findproject_result");
                Intent intent = new Intent(this, BrandActivity.class);
                intent.putExtra("index", list.get(i - 2).getBrand_id());
                startActivity(intent);
            }
        }

    }

    @Override
    public void onScrollChanged() {
        if(!isFind) {
            if (lv.getFirstVisiblePosition() == 1 && lv.getChildAt(1).getTop() >= lv.getListPaddingTop()) {//显示控件
                ll_hide.setVisibility(View.VISIBLE);
            } else if (lv.getFirstVisiblePosition() == 0) {//隐藏控件
                ll_hide.setVisibility(View.GONE);
            }
        }
    }
}