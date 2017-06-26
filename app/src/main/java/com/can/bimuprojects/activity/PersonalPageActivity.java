package com.can.bimuprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Entity.PersonalCardBean;
import com.can.bimuprojects.Module.Hook.DefaultErrorHook;
import com.can.bimuprojects.Module.Request.PersonalPageRequest;
import com.can.bimuprojects.Module.Response.PersonalPageResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.PersonalAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.GlideCircleTransform;
import com.can.bimuprojects.view.LoadDialog;
import com.can.bimuprojects.view.LoadMoreListView;

import java.util.List;

/**
 * 个人主页
 */

public class PersonalPageActivity extends BaseActivity {
    private ImageView ivHeadIcon;
    private TextView tvNickName;
    private List<PersonalCardBean> mCards;

    /**
     * 当前这个主页的用户的id
     */
    private String uid;

    //文章列表相关
    private LoadMoreListView lvArticles;
    private TextView tvSignalture;
    private View top_view;
    private ImageView iv_exit;
    private String personal_sign;
    private String user_image;
    private String user_name;

    private PersonalAdapter personalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        LoadDialog.show(this,"数据加载中...");
        if ((uid = getIntent().getStringExtra(AppConstant.UID)) != null) {
            initData(uid, LoginUtils.getLoginUid());//登录状态 访问别人的主页、或自己的主页
        }
        setListener();
    }

    private void setListener() {
        iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int clickPos = position - lvArticles.getHeaderViewsCount();
                if(clickPos<mCards.size()){
                    if(clickPos>-1){
                        Intent intent = new Intent(PersonalPageActivity.this, ArticleDetailActivity.class);
                        intent.putExtra("id", mCards.get(clickPos).getArticle_id());
                        startActivity(intent);
                    }
                }
            }
        });
        lvArticles.setOnRefreshListener(new LoadMoreListView.OnRefreshListener() {
            @Override
            public void onLoadingMore() {
                if(hasMore){
                    if(AppUtils.isNetworkOK(PersonalPageActivity.this))
                    initMoreData();
                    else
                        lvArticles.completeRefresh();
                }
                else
                    lvArticles.completeRefresh();
            }
        });


    }

    private boolean hasMore =false; //是否有更多数据
    private void initMoreData() {
        PersonalPageRequest request = new PersonalPageRequest();
        request.setUid(LoginUtils.getLoginUid());
        if(mCards.size()>0){
            request.setTimestamp(mCards.get(mCards.size()-1).getTimestamp());
        }else{
            request.setTimestamp("0");
        }
        request.setAccess_uid(uid);
        HttpUtils.postWithoutUid(MethodConstant.GET_MORE_ARTICLE, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                PersonalPageResponse response = (PersonalPageResponse) receive.getResponse();
                if (response.getExe_success().equals("1")) {
                    List<PersonalCardBean> more_below_ads = response.getCards();
                    if(more_below_ads!=null){
                        if(more_below_ads.size()>0){
                            setBelowCardsView(more_below_ads, true);
                        }
                        if(more_below_ads.size()>=10)
                            hasMore = true;
                        else
                            hasMore = false;
                    }
                    lvArticles.completeRefresh();
                } else {
                    ToastUtils.showShort(PersonalPageActivity.this, "请求数据失败");
                }
            }
        }, new DefaultErrorHook(), PersonalPageResponse.class);
    }

    private void initData(String access_uid,String uid) {
        PersonalPageRequest request = new PersonalPageRequest();
        request.setAccess_uid(access_uid);
        request.setUid(uid);
        HttpUtils.postWithoutUid(MethodConstant.ACCESS_USER_INFO, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                LoadDialog.dismiss(context);
                PersonalPageResponse response = (PersonalPageResponse) receive.getResponse();
                if (response != null && response.getExe_success().equals("1")) {
                    personal_sign = response.getPersonal_sign();
                    user_image = response.getUser_image();
                    user_name = response.getUser_name();
                    setPersonalTop(user_image, user_name,  personal_sign);
                    mCards = response.getCards();
                    if(mCards!=null){
                        setBelowCardsView(mCards, false);
                        if(mCards.size()>=10)
                            hasMore = true;
                        else
                            hasMore = false;
                    }
                } else {
                    ToastUtils.showShort(PersonalPageActivity.this, "请求数据失败");
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
                LoadDialog.dismiss(context);
            }
        }, PersonalPageResponse.class);
    }

    private void setBelowCardsView(List<PersonalCardBean> belows_ads, boolean hasMore) {
        if (!hasMore) {
            lvArticles.setAdapter(null);
            personalAdapter = new PersonalAdapter(belows_ads,this);
            lvArticles.addHeaderView(top_view);
            lvArticles.setAdapter(personalAdapter);
        } else {
            mCards.addAll(belows_ads);
            personalAdapter.notifyDataSetChanged();
        }
    }

    private void setPersonalTop(String userImage, String userName, String personalSign) {
                if(Util.isOnMainThread()&&!this.isFinishing())
                Glide.with(PersonalPageActivity.this).load(userImage)
                        .placeholder(R.drawable.default_logo)
                        .transform(new GlideCircleTransform(PersonalPageActivity.this)).into(ivHeadIcon);
        tvNickName.setText(userName+"");
        tvSignalture.setText(personalSign+"");
    }


    private void initView() {
        setContentView(R.layout.activity_personal_page);
        top_view = View.inflate(this, R.layout.item_personal_top, null);
        ivHeadIcon = (ImageView) top_view.findViewById(R.id.personal_iv_head_icon);
        tvNickName = (TextView) top_view.findViewById(R.id.personal_tv_nick_name);
        iv_exit= (ImageView) findViewById(R.id.iv_exit);
        lvArticles = (LoadMoreListView) findViewById(R.id.personal_lv_articles);
        tvSignalture = (TextView) top_view.findViewById(R.id.personal_tv_signature);
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.personal_page);
    }

    @Override
    protected void onResume() {
        PersonalPageRequest request = new PersonalPageRequest();
        request.setAccess_uid(uid);
        request.setUid(LoginUtils.getLoginUid());
        HttpUtils.postWithoutUid(MethodConstant.ACCESS_USER_INFO, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                PersonalPageResponse response = (PersonalPageResponse) receive.getResponse();
                if (response != null && response.getExe_success().equals("1")) {
                    personal_sign = response.getPersonal_sign();
                    user_image = response.getUser_image();
                    user_name = response.getUser_name();
                    setPersonalTop(user_image, user_name, personal_sign);
                    if (null != mCards && personalAdapter != null) {
                        mCards.clear();
                        mCards.addAll(response.getCards());
                        personalAdapter.notifyDataSetChanged();
                    }
                }
            }
        }, new DefaultErrorHook(), PersonalPageResponse.class);
        super.onResume();
    }
}
