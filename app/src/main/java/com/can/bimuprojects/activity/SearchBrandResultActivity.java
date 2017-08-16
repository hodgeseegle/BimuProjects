package com.can.bimuprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.adapter.SearchResultArticleAdapter;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.SearchResultRequest;
import com.can.bimuprojects.Module.Response.SearchResultResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.SearchResultAdapter;
import com.can.bimuprojects.adapter.TradeAdapter;
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


/**
 * Created by can on 2017/6/15.
 * 搜索品牌结果界面
 * 根据传过来的string进行搜索
 */

public class SearchBrandResultActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private ImageView iv_exit;//退出按钮
    private TextView tv_title ;//标题
    private TabLayout tl; //滑动标题
    private ViewPager vp ; //内容切换viewpager

    private LoadMoreListView lv_article,lv_brand,lv_trade ; //文章，品牌，行业集合控件

    //初始化view
    private void initView() {
        setContentView(R.layout.activity_search_brand_result);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tl = (TabLayout) findViewById(R.id.tl_photos);
        vp = (ViewPager) findViewById(R.id.vp_photos);
        lv_article = new LoadMoreListView(this);
        lv_brand = new LoadMoreListView(this);
        lv_brand.setDividerHeight(0);
        lv_trade = new LoadMoreListView(this);
    }

    //初始化监听
    private void setListener() {
        iv_exit.setOnClickListener(this);
        lv_article.setOnItemClickListener(this);
        lv_brand.setOnItemClickListener(this);
        lv_trade.setOnItemClickListener(this);

        lv_article.setOnRefreshListener(new LoadMoreListView.OnRefreshListener() {
            @Override
            public void onLoadingMore() {
                if(hasMore_article)
                    handler.sendEmptyMessageDelayed(1,500);
                else
                    lv_article.completeRefresh();
            }
        });

        lv_brand.setOnRefreshListener(new LoadMoreListView.OnRefreshListener() {
            @Override
            public void onLoadingMore() {
                if(hasMore_brand)
                    handler.sendEmptyMessageDelayed(2,500);
                else
                    lv_brand.completeRefresh();
            }
        });

        lv_trade.setOnRefreshListener(new LoadMoreListView.OnRefreshListener() {
            @Override
            public void onLoadingMore() {
                if(hasMore_trade)
                    handler.sendEmptyMessageDelayed(3,500);
                else
                    lv_trade.completeRefresh();
            }
        });
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(!AppUtils.isNetworkOK(SearchBrandResultActivity.this)){
                ToastUtils.showShort(SearchBrandResultActivity.this,"网络连接错误");
                lv_brand.completeRefresh();
                lv_trade.completeRefresh();
                return  false;
            }
            switch (message.what){
                case 1: //品牌
                    tag = 1;
                    lv_article.completeRefresh();
                    page_article++;
                    requestITData(page_article);
                    break;

                case 2://文章
                    tag = 2;
                    lv_brand.completeRefresh();
                    page_brand++;
                    requestITData(page_brand);
                    break;

                case 3: //行业
                    tag =3;
                    lv_trade.completeRefresh();
                    page_trade++;
                    requestITData(page_trade);
                    break;
            }

            return false;
        }
    });

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exit:
                this.finish();
                break;
        }
    }

    private String content;//搜索内容
    private List<View> viewsList ; //控件集合
    private String []strings ; //标题集合

    private int tag = 0; //请求数据的标志

    private SearchResultAdapter adapter_brand;//品牌适配器
    private List<SearchResultResponse.DataBean> list_brand=new ArrayList<>(); //品牌数据集合
    private int page_brand = 0; //品牌页码
    private boolean hasMore_brand = false ; //是否有更多品牌

    private TradeAdapter adapter_trade ; //行业适配器
    private List<SearchResultResponse.CategoryBean> list_trade=new ArrayList<>(); //行业数据集合
    private int page_trade = 0; //行业页码
    private boolean hasMore_trade = false ; //是否有更多行业

    private SearchResultArticleAdapter adapter_article ; //文章适配器
    private List<SearchResultResponse.ArticleBean> list_article =new ArrayList<>(); //文章数据集合
    private int page_article = 0 ; //文章页码
    private boolean hasMore_article = false ; //是否有更多文章

    //初始化数据
    private void initData() {
        content = getIntent().getStringExtra("content");

        adapter_brand=new SearchResultAdapter(this,list_brand);
        lv_brand.setAdapter(adapter_brand);

        adapter_trade = new TradeAdapter(this,list_trade);
        lv_trade.setAdapter(adapter_trade);

        adapter_article = new SearchResultArticleAdapter(this,list_article,content);
        lv_article.setAdapter(adapter_article);

        lv_article.setTag(1);
        lv_brand.setTag(2);
        lv_trade.setTag(3);
        viewsList = new ArrayList<>();

        tv_title.setText(content);
        strings = getResources().getStringArray(R.array.search_brand_result);
        setTabLayout();

        viewsList.add(lv_brand);
        viewsList.add(lv_article);
        viewsList.add(lv_trade);

        ViewAdapter adapter = new ViewAdapter(viewsList);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);

        requestITData(page_brand);

    }

    //请求网络数据
    private void requestITData(int page) {
        SearchResultRequest request = new SearchResultRequest();
        request.setPage(page+"");
        request.setKeyword(content);
        request.setType(0+"");
        request.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.SEARCH, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                SearchResultResponse response = (SearchResultResponse) receive.getResponse();
                if(response!=null){
                    if(tag==0||tag==1){ //文章数据
                        List<SearchResultResponse.ArticleBean> data_article = response.getArticle();
                        if(data_article!=null){
                            if(data_article.size()>=20)
                                hasMore_article = true;
                            else
                                hasMore_article = false;
                            list_article.addAll(data_article);
                            adapter_article.notifyDataSetChanged();
                        }else{
                            hasMore_article = false;
                        }
                    }


                    if(tag==0||tag==2){ //品牌数据
                        List<SearchResultResponse.DataBean> data_brand = response.getData();
                        if(data_brand!=null){ //品牌数据
                            if(data_brand.size()>=20)
                                hasMore_brand = true;
                            else
                                hasMore_brand = false;
                            list_brand.addAll(data_brand);
                            adapter_brand.notifyDataSetChanged();
                        }else{
                            hasMore_brand = false;
                        }
                    }

                    if(tag==0||tag==3){//行业数据
                        List<SearchResultResponse.CategoryBean> data_trade = response.getCategory();
                        if(data_trade!=null){ //行业数据
                            if(data_trade.size()>=20)
                                hasMore_trade = true;
                            else
                                hasMore_trade = false;
                            list_trade.addAll(data_trade);
                            adapter_trade.notifyDataSetChanged();
                        }else{
                            hasMore_trade = false;
                        }
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, SearchResultResponse.class);

    }

    //设置滚动标题样式
    private void setTabLayout() {
        tl.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.color_app_text_yes));
        tl.setSelectedTabIndicatorHeight(UiUtils.dip2px(1));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int tag = (int) adapterView.getTag();
        switch (tag){
            case 1://文章
                if(i<list_article.size()&&list_article.get(i)!=null){
                    Intent intent = new Intent(this, ArticleDetailActivity.class);
                    intent.putExtra("id", list_article.get(i).getArticle_id());
                    startActivity(intent);
                }
                break;

            case 2://品牌
                if(i<list_brand.size()&&list_brand.get(i)!=null) {
                    MobclickAgent.onEvent(this, "lv_search_results");
                    Intent intent = new Intent(this, BrandActivity.class);
                    intent.putExtra("index", list_brand.get(i).getBrand_id());
                    startActivity(intent);
                }
                break;

            case 3://行业
                if(i<list_trade.size()&&list_trade.get(i)!=null){
                    MobclickAgent.onEvent(this,"vp_search_result");
                    Intent intent = new Intent(this,ChooseTypeActivity.class);
                    intent.putExtra("notFind",true);
                    intent.putExtra("cid",list_trade.get(i).getClassify_id());
                    intent.putExtra("project",list_trade.get(i).getClassify_name());
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 图片不同类型选择适配器
     */
    class ViewAdapter extends PagerAdapter {

        private List<View> views;

        public ViewAdapter(List<View> views){
            this.views = views;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
}
