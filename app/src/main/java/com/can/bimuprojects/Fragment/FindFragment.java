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
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.GetFindRequest;
import com.can.bimuprojects.Module.Response.GetFindResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.ActivityInterest;
import com.can.bimuprojects.activity.ArticleDetailActivity;
import com.can.bimuprojects.activity.FindProjectResultActivity;
import com.can.bimuprojects.activity.JoinRaidersActivity;
import com.can.bimuprojects.adapter.FindAdapter;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.RefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/20.
 * 发现页
 */

public class FindFragment extends Fragment implements View.OnClickListener,  AdapterView.OnItemClickListener, RefreshListView.OnRefreshListener, ViewTreeObserver.OnScrollChangedListener {


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,null);
        initView(view);
        setlistener();
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!PrefUtils.get("class_index","").equals(cid)&&!notFind){
            if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
            }else{
                page=0;
                requestITData();
                lv.setSelection(0);
            }
        }
    }

    private RefreshListView lv ; //刷新控件
    private FindAdapter adapter;//适配器
    private View headView; //头部view
    private ImageView iv_head;//头部图片
    private TextView tv_head; //头部标题
    private TextView tv_total ;//头部总数

    private LinearLayout ll_find_browse ; //浏览比目精选

    private List<GetFindResponse.RecommendBean> list_tag; //标签数据
    private List<GetFindResponse.ArticledataBean> list_recommend ;//推荐数据集合
    private LinearLayout ll_bidu,ll_bimu; //必读和比目
    private LinearLayout ll_tags ; //标记

    private RelativeLayout rl_find_headview_head; //头部可点击的view

    private String str_project = "" ; //行业名字
    private String cid =""; //行业id
    private int page = 0; //页码

    private View view_browse ; //浏览比目精选
    private ImageView iv_down ; //向下的按钮
    private ImageView iv_light ; //必读加盟攻略图标
    private ImageView iv_zuanshi ; //闭目推荐品牌图标
    /**
     * 初始化view
     * @param view
     */
    private void initView(View view) {
        lv = (RefreshListView) view.findViewById(R.id.rlv_find);
        ll_find_browse = (LinearLayout) view.findViewById(R.id.ll_find_browse);

        headView = LayoutInflater.from(getContext()).inflate(R.layout.headview_find,null);
        rl_find_headview_head = (RelativeLayout) headView.findViewById(R.id.rl_find_headview_head);
        iv_head = (ImageView) headView.findViewById(R.id.iv_find_headview_head);
        if(Util.isOnMainThread())
            Glide.with(BimuApplication.getContext()).load(R.drawable.loading).into(iv_head);
        iv_down = (ImageView) headView.findViewById(R.id.iv_find_headview_down);
        if(Util.isOnMainThread())
            Glide.with(BimuApplication.getContext()).load(R.drawable.img_down).into(iv_down);
        iv_light = (ImageView) headView.findViewById(R.id.iv_find_headview_light);
        if(Util.isOnMainThread())
            Glide.with(BimuApplication.getContext()).load(R.drawable.light).into(iv_light);
        iv_zuanshi = (ImageView) headView.findViewById(R.id.iv_find_headview_zuanshi);
        if(Util.isOnMainThread())
            Glide.with(BimuApplication.getContext()).load(R.drawable.img_zunashi).into(iv_zuanshi);
        tv_head = (TextView) headView.findViewById(R.id.tv_find_headview_project);
        tv_total = (TextView) headView.findViewById(R.id.tv_find_headview_total);
        ll_bidu = (LinearLayout) headView.findViewById(R.id.ll_find_headview_bidu);
        ll_bimu = (LinearLayout) headView.findViewById(R.id.ll_find_headview_bimu);
        ll_tags = (LinearLayout) headView.findViewById(R.id.ll_find_tags);

        view_browse = LayoutInflater.from(getContext()).inflate(R.layout.headview_find_browse,null);
        headView.setVisibility(View.GONE);
        view_browse.setVisibility(View.GONE);
        lv.addHeaderView(headView);
        lv.addHeaderView(view_browse);
    }

    //设置监听
    private void setlistener() {
        ll_bimu.setOnClickListener(this);
        ll_bidu.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        lv.setOnRefreshListener(this);
            lv.getViewTreeObserver().addOnScrollChangedListener(this);
        rl_find_headview_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_find_headview_head: //更换行业
                Intent intent = new Intent(getContext(), ActivityInterest.class);
                startActivity(intent);
                break;
            case R.id.ll_find_headview_bidu://必读
                if(cid!=null){
                    MobclickAgent.onEvent(getContext(),"ll_find_headview_bidu");
                    Intent intent_bidu = new Intent(getContext(), JoinRaidersActivity.class);
                    intent_bidu.putExtra("cid",cid);
                    startActivity(intent_bidu);
                }
                break;
            case R.id.ll_find_headview_bimu: //比目
                if(str_project!=null&&cid!=null){
                    MobclickAgent.onEvent(getContext(),"ll_find_headview_bimu");
                    Intent intent_bimu = new Intent(getContext(), FindProjectResultActivity.class);
                    intent_bimu.putExtra("interest",str_project);
                    intent_bimu.putExtra("cid",cid);
                    intent_bimu.putExtra("amount","");
                    intent_bimu.putExtra("area","");
                    intent_bimu.putExtra("isFind",true);
                    startActivity(intent_bimu);
                }
                break;
        }
    }

    private boolean notFind = false; //是否为发现页
    /**
     * 数据初始化
     */
    private void initData() {
        list_recommend = new ArrayList<>();
        adapter = new FindAdapter(getContext(),list_recommend);
        lv.setAdapter(adapter);
        list_tag = new ArrayList<>();

        cid = getActivity().getIntent().getStringExtra("cid");
        str_project = getActivity().getIntent().getStringExtra("project");
        notFind = getActivity().getIntent().getBooleanExtra("notFind",false);
        if(notFind){
            rl_find_headview_head.setClickable(false);
            page = 0;
            requestITData();
        }
    }

    /**
     * 请求网络数据
     */
    private void requestITData() {
        if(!notFind){ //发现页
            cid = PrefUtils.get("class_index","");
            str_project = PrefUtils.get("interest","");
        }else{ //不是发现页

        }
        GetFindRequest request = new GetFindRequest();
        request.setUid(LoginUtils.getUid());
        request.setCid(cid);
        request.setPid(page+"");
        HttpUtils.postWithoutUid(MethodConstant.GET_FIND, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetFindResponse response = (GetFindResponse) receive.getResponse();
                if(response!=null){
                    if(page==0){
                        list_recommend.clear();
                        list_tag.clear();
                        setHeadData(response.getData());
                        setTagsData(response.getRecommend());
                        headView.setVisibility(View.VISIBLE);
                        view_browse.setVisibility(View.VISIBLE);
                    }
                    setArticleData(response.getArticledata());
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
            }
        }, GetFindResponse.class);
    }

    //设置浏览标记数据
    private void setTagsData(List<GetFindResponse.RecommendBean> recommend) {
        if(recommend!=null)
        list_tag.addAll(recommend);
        ll_tags.removeAllViews();
        for(int i =0;i<list_tag.size();i++){
            ll_tags.addView(getTV(list_tag.get(i).getClassify_name(),list_tag.get(i).getPid()));
        }
    }

    /**
     * 添加textview
     * @param content
     * @return
     */
    private View getTV(final String content, final String pid){
        View  view = LayoutInflater.from(getContext()).inflate(R.layout.item_find_tags,null);
        TextView tv = (TextView) view.findViewById(R.id.tv_item_find_tags);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,20,0);
        view.setLayoutParams(params);
        tv.setText(content);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                page = 0;
                if(notFind){
                    cid = pid;
                    str_project = content;
                }else{
                    PrefUtils.put("class_index",pid);
                    PrefUtils.put("interest",content);
                }
                requestITData();
            }
        });
        return view;
    }

    //设置浏览比目精选数据
    private void setArticleData(List<GetFindResponse.ArticledataBean> articledata) {
        if(articledata.size()>=5){
            hasMore = true;
        }else{
            hasMore = false;
        }
        if(articledata!=null)
        list_recommend.addAll(articledata);
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置头部数据
     * @param data
     */
    private void setHeadData(GetFindResponse.DataBean data) {
        if(data==null)
            return;
        str_project = data.getClassify_name()+"";
        cid = data.getClassify_id()+"";
        if(str_project!=null)
        tv_head.setText(str_project);
        tv_total.setText(""+data.getFans());
        if(Util.isOnMainThread()&&getActivity().getApplicationContext()!=null)
        Glide.with(getActivity().getApplicationContext()).load(data.getClassify_icon()).into(iv_head);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId()==R.id.rlv_find&&i>=3){
            MobclickAgent.onEvent(getContext(),"rlv_find");
            Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
            intent.putExtra("id",list_recommend.get(i-3).getArticle_id());
            startActivity(intent);
        }
    }

    private static final int TYPE_REFRESH = 0x111 ; //刷新
    private static final int TYPE_LOADINGMORE = 0x222; //加载更多
    private boolean hasMore = false ; //是否有更多数据
    /**
     * 主线程进行刷新加载数据的操作
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case TYPE_REFRESH: //刷新
                    lv.completeRefresh();
                    if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                        ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
                        break;
                    }else{
                        page = 0;
                        requestITData();
                    }

                    break;
                case TYPE_LOADINGMORE://加载更多
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
        handler.sendEmptyMessageDelayed(TYPE_REFRESH,500);
    }

    @Override
    public void onLoadingMore() {
        if(hasMore)
            handler.sendEmptyMessageDelayed(TYPE_LOADINGMORE, 500);
        else
            lv.completeRefresh();
    }

    @Override
    public void onScrollChanged() {
        if (lv.getFirstVisiblePosition()==2){//显示控件
            ll_find_browse.setVisibility(View.VISIBLE);
        }else if(lv.getFirstVisiblePosition()==1){//隐藏控件
            ll_find_browse.setVisibility(View.GONE);
        }
    }

    /**
     * 双击返回顶部
     */
    public  void setListView2Top(){
        lv.setSelection(0);
        ll_find_browse.setVisibility(View.GONE);
    }
}
