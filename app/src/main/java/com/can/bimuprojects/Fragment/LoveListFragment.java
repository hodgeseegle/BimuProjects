package com.can.bimuprojects.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.LoveListRequest;
import com.can.bimuprojects.Module.Response.LoveListResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.BrandActivity;
import com.can.bimuprojects.adapter.LoveListAdapter;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.LoadDialog;
import com.can.bimuprojects.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/19.
 * 心愿单
 */

public class LoveListFragment extends Fragment implements LoadMoreListView.OnRefreshListener, AdapterView.OnItemClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_love_list,null);
        initView(view);
        setListener();
        initData();
        return view;
    }

    private LoadMoreListView lv;//集合控件
    private LoveListAdapter adapter ; //集合适配器
    private TextView tv_title ; //标题
    /**
     * 初始化view
     * @param view
     */
    private void initView(View view) {
        lv = (LoadMoreListView) view.findViewById(R.id.rlv_love_list);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
    }

    private int page =0;//页数
    private List<LoveListResponse.WishListBean> data ; //数据集合
    private int total =0;//总数
    private boolean hasMore =true; //是否有更多数据
    private boolean isFirst = true; //是否第一次进入
    /**
     * 初始化数据
     */
    private void initData() {
        tv_title.setText(getString(R.string.your_love));
        data = new ArrayList<>();
        adapter = new LoveListAdapter(getActivity().getWindowManager(),getContext(),data);
        lv.setAdapter(adapter);
        setEmptyData();
    }
    /**
     * 数据为空时候
     */
    private void setEmptyData() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_empty_love_list,null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_item_empty_love);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        view.setVisibility(View.GONE);
        if(Util.isOnMainThread())
            Glide.with(getContext()).load(R.drawable.love_empty).into(iv);
        ((ViewGroup)lv.getParent()).addView(view);
        lv.setEmptyView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isLoadingNew = PrefUtils.getBoolean("love_update",false);
        if(isLoadingNew||isFirst){
            if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
            }else{
                LoadDialog.show(getContext());
                page = 0;
                data.clear();
                requestITData();
                lv.setSelection(0);
            }
            PrefUtils.putBoolean("love_update",false);
            isFirst = false;
        }
    }

    /**
     * 请求网络数据
     */
    private void requestITData() {
        LoveListRequest request = new LoveListRequest();
        request.setUid(LoginUtils.getUid());
        request.setP(page+"");
        HttpUtils.postWithoutUid(MethodConstant.GET_LOVE_LIST, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                LoadDialog.dismiss(getContext());
                LoveListResponse response = (LoveListResponse) receive.getResponse();
                if(response!=null&&response.getExe_success()==1){
                    String str = response.getTrue_name();
                    if(str!=null&&!str.equals("")){
                        LoginUtils.setUserName(str);
                    }
                    if(response.getTotal()>0){
                        hasMore = true;
                        data.addAll(response.getWish_list());
                        adapter.notifyDataSetChanged();
                    }else{
                        hasMore = false;
                        data.addAll(response.getWish_list());
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
                LoadDialog.dismiss(context);
            }
        }, LoveListResponse.class);
    }


    private void requestMoreITData() {
        LoveListRequest request = new LoveListRequest();
        request.setUid(LoginUtils.getUid());
        request.setP(page+"");
        HttpUtils.postWithoutUid(MethodConstant.GET_LOVE_LIST, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                LoadDialog.dismiss(getContext());
                LoveListResponse response = (LoveListResponse) receive.getResponse();
                if(response!=null&&response.getExe_success()==1){
                    if(response.getTotal()>0){
                        hasMore = true;
                        data.addAll(response.getWish_list());
                        adapter.notifyDataSetChanged();
                    }else{
                        hasMore = false;
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, LoveListResponse.class);
    }


    /**
     * 设置监听
     */
    private void setListener() {
        lv.setOnRefreshListener(this);
        lv.setOnItemClickListener(this);
    }


    @Override
    public void onLoadingMore() {
        if(total!=0&&hasMore){
            if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
            }else{

                requestMoreITData();
            }
        }else{
            lv.completeRefresh();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==R.id.rlv_love_list&&i<data.size()){
            MobclickAgent.onEvent(getContext(),"rlv_love_list");
            Intent intent = new Intent(getContext(), BrandActivity.class);
            intent.putExtra("index",data.get(i).getBrand_id());
            startActivity(intent);
        }
    }
}
