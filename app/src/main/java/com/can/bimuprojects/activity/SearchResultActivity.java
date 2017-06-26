package com.can.bimuprojects.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Hook.DefaultErrorHook;
import com.can.bimuprojects.Module.Request.SearchResultRequest;
import com.can.bimuprojects.Module.Response.SearchResultResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.SearchResultAdapter;
import com.can.bimuprojects.adapter.SearchResultVPAdapter;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.utils.UiUtils;
import com.can.bimuprojects.view.LoadMoreListView;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, View.OnTouchListener {

    private LoadMoreListView lvResult;
    public final static int TYPE_DEFAULT = 0;
    private String content;
    private List<SearchResultResponse.DataBean> mCards=new ArrayList<>();
    private int mIndex;
    private int mType = 0;
    private SearchResultAdapter searchResultAdapter;
    private ImageView iv_back;

    private boolean hasMore ; //是否有更多的内容

  //  private TextView tv_head_title ; //头部标题
   // private ViewPager vp ; //头部滚动
  //  private LinearLayout ll_head_bottom; //滚动底部圆点

    private int page = 0;//页数
    private TextView tv_search_result_title ; //标题
  //  private SearchResultVPAdapter head_adapter ; //头部适配器
    //private List<ImageView> head_list;//头部数据

   // private List<SearchResultResponse.CategoryBean> data ; //头部数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtils.cancelAll(this);
    }

    private void initData() {
    //    data = new ArrayList<>();
     //   head_list = new ArrayList<>();
      //  head_adapter = new SearchResultVPAdapter(head_list);
       // lvResult.addHeaderView(headView);
      //  vp.setAdapter(head_adapter);
        searchResultAdapter=new SearchResultAdapter(SearchResultActivity.this,mCards);
        lvResult.setAdapter(searchResultAdapter);
        getCategoryList(TYPE_DEFAULT);
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
            }else{
                mIndex++;
                getMoreCategoryList(mType, mIndex);
                lvResult.completeRefresh();
            }
            return false;
        }
    });

    private void setListener() {
//        vp.addOnPageChangeListener(this);
      //  vp.setOnTouchListener(this);
        lvResult.setOnRefreshListener(new LoadMoreListView.OnRefreshListener() {
            @Override
            public void onLoadingMore() {
                if(hasMore)
                handler.sendEmptyMessageDelayed(1,1200);
                else
                    lvResult.completeRefresh();
            }
        });
        iv_back.setOnClickListener(this);
        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(position<mCards.size()){
                        MobclickAgent.onEvent(SearchResultActivity.this,"lv_search_results");
                        Intent intent = new Intent(SearchResultActivity.this,BrandActivity.class);
                        intent.putExtra("index",mCards.get(position).getBrand_id());
                        startActivity(intent);
                    }
            }
        });
    }

    //private View headView ; //头部view
    private void initView() {
        Intent intent = getIntent();
        content = intent.getStringExtra("content");
        lvResult = (LoadMoreListView) findViewById(R.id.lv_search_results);
        iv_back = (ImageView) findViewById(R.id.iv_exit);
        tv_search_result_title = (TextView) findViewById(R.id.tv_title);
        tv_search_result_title.setText(content);
      //  headView = LayoutInflater.from(this).inflate(R.layout.head_search_result,null);
       // vp = (ViewPager) headView.findViewById(R.id.vp_search_result);
       // ll_head_bottom = (LinearLayout) headView.findViewById(R.id.ll_container);
       // tv_head_title = (TextView) headView.findViewById(R.id.tv_head_search_result);
    }


    @Override
    public void onClick(View v) {
        if (mCards != null) {
            mCards.clear();
            searchResultAdapter.notifyDataSetChanged();
            switch (v.getId()) {
                case R.id.iv_exit:
                    finish();
                    break;
            }

        }
    }

    List<SearchResultResponse.CategoryBean> head_data;//头部数据集合
    /**
     * 第一次进入获取数据
     * @param type
     */
    private void getCategoryList(int type) {
        SearchResultRequest request = new SearchResultRequest();
        request.setKeyword(content);
        request.setType(type+"");
        request.setPage(page+"");
        request.setUid(LoginUtils.getLoginUid());
        if(null!=mCards&&mCards.size()!=0){
            mCards.clear();
            searchResultAdapter.notifyDataSetChanged();
        }
        HttpUtils.postWithoutUid(MethodConstant.SEARCH, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                SearchResultResponse response = (SearchResultResponse) receive.getResponse();
                if (response != null && response.getExe_success() == 1) {
                    head_data = response.getCategory();
                //    data.addAll(response.getCategory());
                    if(head_data.size()>0){
                        DisplayMetrics metrics = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        for(int i = 0 ;i<head_data.size();i++){
                            ImageView imageView = new ImageView(context);
                            if(Util.isOnMainThread())
                            Glide.with(context).load(head_data.get(i).getClassify_icon()).centerCrop().into(imageView);
                       //     head_list.add(imageView);
                            ImageView dot = new ImageView(context);
                            if (i == 0) {
                                dot.setImageResource(R.drawable.bullet_white);//设置当前页的圆点
                            } else {
                                dot.setImageResource(R.drawable.bullet_red);//其余页的圆点
                            }
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiUtils.dip2px(18), UiUtils.dip2px(18));
                            dot.setLayoutParams(params);
                          //  ll_head_bottom.addView(dot);//将圆点添加到容器中
                        }
                      //  tv_head_title.setText(head_data.get(0).getClassify_name());
                     //   head_adapter.notifyDataSetChanged();
                    }
                    if (response.getData().size() > 0) {
                        if(response.getData().size()==20){
                            hasMore = true;
                        }else{
                            hasMore = false;
                        }
                        mCards.addAll(response.getData());
                        searchResultAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showShort(SearchResultActivity.this, "没有更多的内容");
                        lvResult.completeRefresh();
                    }
                } else if (response != null &&response.getTotal()==0&&page==0){
                    Dialog dialog = ToastUtils.showDialog(SearchResultActivity.this,"提示",getString(R.string.no_brand),View.inflate(context, R.layout.dialog_circle,null),R.style.circle_dialog);
                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            SearchResultActivity.this.finish();
                        }
                    });
                }else{
                    lvResult.completeRefresh();
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
            }
        }, SearchResultResponse.class);
    }

    /**
     * 获取更多数据
     * @param type
     * @param index
     */
    private void getMoreCategoryList(int type, int index) {
        page++;
        SearchResultRequest request = new SearchResultRequest();
        request.setKeyword(content);
        request.setPage(page+"");
        request.setType(type+"");
        request.setUid(LoginUtils.getLoginUid());
        HttpUtils.postWithoutUid(MethodConstant.SEARCH, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                SearchResultResponse response = (SearchResultResponse) receive.getResponse();
                if (response != null && response.getExe_success() == 1) {
                    List<SearchResultResponse.DataBean> list = response.getData();
                    if (list.size() > 0) {
                        if(response.getData().size()==20){
                            hasMore = true;
                        }else{
                            hasMore = false;
                        }
                        lvResult.completeRefresh();
                        mCards.addAll(list);
                        searchResultAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showShort(SearchResultActivity.this, "没有更多的内容");
                        lvResult.completeRefresh();
                    }
                } else {
                    ToastUtils.showShort(SearchResultActivity.this, "没有更多的内容");
                    lvResult.completeRefresh();
                }
            }
        }, new DefaultErrorHook(), SearchResultResponse.class);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
     //   tv_head_title.setText(head_data.get(position).getClassify_name());
//        for (int i = 0; i < ll_head_bottom.getChildCount(); i++) {
//            ImageView imageView = (ImageView) ll_head_bottom.getChildAt(i);
//            if (i == position) {
//                imageView.setImageResource(R.drawable.bullet_white);
//            } else {
//                imageView.setImageResource(R.drawable.bullet_red);
//            }
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private int flage = 0;
    private float x1,x2,y1,y2;
    /**
     * viewpager点击事件
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = motionEvent.getX();
                y1= motionEvent.getY();
                flage = 0 ;
                break ;
            case MotionEvent.ACTION_MOVE:
                flage = 1 ;
                break ;
            case  MotionEvent.ACTION_UP :
                x2 = motionEvent.getX();
                y2 = motionEvent.getY();
                if(Math.abs(x1-x2)<=10&&Math.abs(y1-y2)<=10){
                    MobclickAgent.onEvent(SearchResultActivity.this,"vp_search_result");
                   // int item = vp.getCurrentItem();
                 //   Intent intent = new Intent(this,ChooseTypeActivity.class);
                 //   intent.putExtra("notFind",true);
                 //   intent.putExtra("cid",data.get(item).getClassify_id());
                 //   intent.putExtra("project",data.get(item).getClassify_name());
                 //   startActivity(intent);
                }
                break ;
        }
        return false;
    }

}
