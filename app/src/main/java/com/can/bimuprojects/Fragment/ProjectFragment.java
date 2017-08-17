package com.can.bimuprojects.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.ProjectRequest;
import com.can.bimuprojects.Module.Response.ProjectResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.ActivityInterest;
import com.can.bimuprojects.activity.BrandActivity;
import com.can.bimuprojects.adapter.ProjectAdapter;
import com.can.bimuprojects.adapter.ProjectMoneyAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.GlideUtil;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.NumberUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.utils.ViewUtil;
import com.can.bimuprojects.view.RefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/08/15.
 * 项目库
 */

public class ProjectFragment extends Fragment implements View.OnClickListener, RefreshListView.OnRefreshListener, AdapterView.OnItemClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project,null);
        initView(view);
        setListener();
        initData();
        return view;
    }

    private TextView tv_title ;//标题
    private RefreshListView lv ; //集合控件
    private LinearLayout ll_trade ; //行业
    private LinearLayout ll_money  ; //金额
    private LinearLayout ll_sort ; //排序
    private ImageView iv_item1,iv_item2,iv_item3 ;
    /**
     * 初始化view
     */
    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        lv = (RefreshListView) view.findViewById(R.id.lv_fragment);
        ll_trade = (LinearLayout) view.findViewById(R.id.ll_item1);
        ll_money = (LinearLayout) view.findViewById(R.id.ll_item2);
        ll_sort = (LinearLayout) view.findViewById(R.id.ll_item3);
        iv_item1 = (ImageView) view.findViewById(R.id.iv_item1);
        iv_item2 = (ImageView) view.findViewById(R.id.iv_item2);
        iv_item3 = (ImageView) view.findViewById(R.id.iv_item3);
        GlideUtil.loadDrawableImg(getContext(),R.drawable.img_arrow_down,iv_item1);
        GlideUtil.loadDrawableImg(getContext(),R.drawable.img_arrow_down,iv_item2);
        GlideUtil.loadDrawableImg(getContext(),R.drawable.img_arrow_down,iv_item3);
        initPopupWindow();
    }

    private PopupWindow dialog_money,dialog_sort ; //弹窗（金额和排序）
    private GridView gv_money ; //金额集合控件
    private GridView gv_sort ; //排序集合控件
    //初始化弹窗
    private void initPopupWindow() {
        View view_money = LayoutInflater.from(getContext()).inflate(R.layout.dialog_money,null);
        gv_money = (GridView) view_money.findViewById(R.id.gv_dialog);
        view_money.findViewById(R.id.view_money).setOnClickListener(this);
        view_money.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        dialog_money = new PopupWindow(view_money,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_money.setOutsideTouchable(false);
        dialog_money.setFocusable(true);
        dialog_money.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        View view_sort = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sort,null);
        gv_sort = (GridView) view_sort.findViewById(R.id.gv_dialog_sort);
        view_sort.findViewById(R.id.view_sort).setOnClickListener(this);
        view_sort.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        dialog_sort = new PopupWindow(view_sort,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog_sort.setOutsideTouchable(false);
        dialog_sort.setFocusable(true);
        dialog_sort.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
    }

    private List<ProjectResponse.DataBean> list ; //数据集合
    private boolean hasMore = false; //是否有更多数据
    private ProjectAdapter adapter ; //适配器

    private String [] strs_money ; //金额数组
    private ProjectMoneyAdapter adapter_money ; //金额适配器

    private String [] strs_sort ; //排序数组
    private ProjectMoneyAdapter adapter_sort ; //排序适配器

    /**
     * 初始化数据
     */
    private void initData() {
        tv_title.setText(getString(R.string.project));
        list = new ArrayList<>();
        adapter = new ProjectAdapter(getContext(),list);
        lv.setAdapter(adapter);

        strs_money = getResources().getStringArray(R.array.money_lists2);
        adapter_money = new ProjectMoneyAdapter(getContext(),strs_money);
        gv_money.setAdapter(adapter_money);

        strs_sort = getResources().getStringArray(R.array.sort_lists);
        adapter_sort = new ProjectMoneyAdapter(getContext(),strs_sort);
        gv_sort.setAdapter(adapter_sort);

        requestITData();
    }

    private int page = 0 ; //页码
    private String area = "" ; //地区
    private String amount = "0-1000" ; //金额
    private String classX = "" ; //行业
    private String sort = "0" ;//排序
    //请求网络数据
    private void requestITData() {
        if(page==0)
            ViewUtil.stopListView(lv,0);
        ProjectRequest request = new ProjectRequest();
        request.setUid(LoginUtils.getUid());
        request.setPage(page+"");
        request.setArea(area);
        request.setAmount(amount);
        request.setClassX(classX);
        request.setSort(sort);
        HttpUtils.postWithoutUid(MethodConstant.PROJECT, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                ProjectResponse response = (ProjectResponse) receive.getResponse();
                hasMore =false;
                if(response!=null){
                    if(page==0)
                        list.clear();
                    List<ProjectResponse.DataBean> beanList = response.getData();
                    int total = response.getTotal();
                    if(total>=5)
                        hasMore = true;
                    if(beanList!=null){
                        list.addAll(beanList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, ProjectResponse.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ActivityInterest.REQUEST_CODE&&resultCode==ActivityInterest.RESPONSE_CODE){
            classX = data.getStringExtra("class");
            page = 0;
            requestITData();
        }
    }


    /**
     * 设置监听
     */
    private void setListener() {
        ll_sort.setOnClickListener(this);
        ll_trade.setOnClickListener(this);
        ll_money.setOnClickListener(this);
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
        gv_money.setOnItemClickListener(this);
        gv_sort.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_item1: //行业
                Intent intent = new Intent(getContext(), ActivityInterest.class);
                intent.putExtra("more",true);
                startActivityForResult(intent,ActivityInterest.REQUEST_CODE);

                break;
            case R.id.ll_item2: //金额
                if(!dialog_money.isShowing())
                    dialog_money.showAsDropDown(view);
                break;
            case R.id.ll_item3: //排序
                if(!dialog_sort.isShowing())
                    dialog_sort.showAsDropDown(view);
                break;
            case R.id.view_sort: //点击排序弹窗其它地方
                dialog_sort.dismiss();
                break;
            case R.id.view_money://点击金额弹窗其它地方
                dialog_money.dismiss();
                break;
        }
    }

    @Override
    public void onPullRefresh() {
        handler.sendEmptyMessageDelayed(1,500);
    }

    @Override
    public void onLoadingMore() {
        if(hasMore){
            handler.sendEmptyMessageDelayed(2,500);
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
                case 2: //加载更多
                    lv.completeRefresh();
                    if(!AppUtils.isNetworkOK(getContext())){
                        ToastUtils.showShort(getContext(),"网络连接错误!");
                    }else{
                        page++;
                        requestITData();
                    }
                    break;
                case 1 : //刷新
                    lv.completeRefresh();
                    if(!AppUtils.isNetworkOK(getContext())){
                        ToastUtils.showShort(getContext(),"网络连接错误!");
                    }else{
                        page=0;
                        requestITData();
                    }
                    break;
            }
            return false;
        }
    });

    /**
     * 双击返回顶部
     */
    public  void setListView2Top(){
        ViewUtil.stopListView(lv,0);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_fragment: //点击项目库item
                if(i-1<list.size()){
                    Intent intent = new Intent(getContext(), BrandActivity.class);
                    intent.putExtra("index",list.get(i-1).getBrand_id());
                    startActivity(intent);
                }
                break;
            case R.id.gv_dialog: //选择金额item
                adapter_money.setSeclection(i);
                adapter_money.notifyDataSetChanged();
                amount = NumberUtils.getMoneyString(strs_money[i]);
                page = 0;
                requestITData();
                dialog_money.dismiss();
                break;
            case R.id.gv_dialog_sort: //选择排序item
                adapter_sort.setSeclection(i);
                adapter_sort.notifyDataSetChanged();
                sort = i+"";
                page = 0;
                requestITData();
                dialog_sort.dismiss();
                break;
        }
    }
}
