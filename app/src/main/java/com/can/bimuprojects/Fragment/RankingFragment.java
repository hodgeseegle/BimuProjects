package com.can.bimuprojects.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.RankingRequest;
import com.can.bimuprojects.Module.Response.RankingResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.BrandActivity;
import com.can.bimuprojects.adapter.RankingAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.LoadMoreListView;

import java.util.List;

/**
 * Created by can on 2017/08/16.
 * 实时热度排行fragment
 */

public class RankingFragment extends Fragment implements AdapterView.OnItemClickListener, LoadMoreListView.OnRefreshListener {

    private LoadMoreListView lv ; //集合控件
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking,null);
        lv = (LoadMoreListView) view.findViewById(R.id.lv_fragment);
        setListener();
        initData();
        return view;
    }


    /**
     * 设置监听
     */
    private void setListener() {
        lv.setOnItemClickListener(this);
        lv.setOnRefreshListener(this);
    }

    private List<RankingResponse.BrandIndustryBean.DataBean> dataList ; //数据集合
    private RankingAdapter adapter ; //适配器
    private boolean hasMore = false ; //是否有更多数据
    private String tag ; //标签
    /**
     * 初始化数据
     */
    private void initData() {
        tag = getArguments().getString("tag");
        dataList = (List<RankingResponse.BrandIndustryBean.DataBean>) getArguments().getSerializable("list");
        if(dataList!=null&&dataList.size()>=5)
            hasMore = true;
        adapter = new RankingAdapter(getContext(),dataList);
        lv.setAdapter(adapter);
    }

    private int page = 0;
    //请求网络数据
    private void requestITData() {
        RankingRequest request = new RankingRequest();
        request.setUid(LoginUtils.getLoginUid());
        request.setPage(""+page);
        request.setArea("");
        request.setCid("");
        request.setType("");
        HttpUtils.postWithoutUid(MethodConstant.RANKING, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                RankingResponse response = (RankingResponse) receive.getResponse();
                hasMore = false;
                if(response!=null){
                    List<RankingResponse.BrandIndustryBean> beanList = response.getBrand_industry();
                    if(beanList!=null)
                    for(int i = 0 ; i<beanList.size() ; i++){
                        String tagString = beanList.get(i).getName();
                        List<RankingResponse.BrandIndustryBean.DataBean> data = beanList.get(i).getData();
                        if(tagString!=null&&tagString.equals(tag)){
                            if(data!=null){
                                if(data.size()>=5)
                                    hasMore = true;
                                dataList.addAll(data);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, RankingResponse.class);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(i<dataList.size()) {
            Intent intent_hot = new Intent(getContext(), BrandActivity.class);
            intent_hot.putExtra("index", dataList.get(i).getBrand_id());
            startActivity(intent_hot);
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
                    if(!AppUtils.isNetworkOK(getContext())){
                        ToastUtils.showShort(getContext(),"网络连接错误!");
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
