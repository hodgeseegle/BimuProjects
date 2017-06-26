package com.can.bimuprojects.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Hook.DefaultErrorHook;
import com.can.bimuprojects.Module.Request.GetUserInfoRequest;
import com.can.bimuprojects.Module.Response.GetUserInfoResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.AppRecommendActivity;
import com.can.bimuprojects.activity.CollectionArticleActivity;
import com.can.bimuprojects.activity.DiscountActivity;
import com.can.bimuprojects.activity.ListActivity;
import com.can.bimuprojects.activity.LoginActivity;
import com.can.bimuprojects.activity.PersonalPageActivity;
import com.can.bimuprojects.activity.SettingActivity;
import com.can.bimuprojects.activity.UserFeedbackActivity;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.view.GlideCircleTransform;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by can on 17/4/18.
 * 我的
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private Context context;

    @Bind(R.id.fragment_me_rl_feedback)
    RelativeLayout rlFeedback;
    @Bind(R.id.fragment_me_rl_notification)
    RelativeLayout rlNotification;
    @Bind(R.id.fragment_me_rl_my_bargin)
    RelativeLayout rlMyBargin;
    @Bind(R.id.fragment_me_iv_head)
    ImageView ivHeadIcon;
    @Bind(R.id.fragment_me_tv_name)
    TextView tvName;
    @Bind(R.id.fragment_me_ll_main)
    LinearLayout llMain;
    @Bind(R.id.fragment_me_red_pot)
    View redPot;
    @Bind(R.id.rl_mine_setting)
    RelativeLayout rl_setting; //设置
    @Bind(R.id.rl_mine_love_article)
    RelativeLayout rl_love ; //收藏的文章
    @Bind(R.id.fragment_me_rl_app_recommend)
    RelativeLayout rl_app_recommend ;//应用推荐

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        init(view);
        return view;
    }

    @Bind(R.id.iv_fragment_me_notice)
    ImageView iv_notice;
    @Bind(R.id.iv_fragment_me_yuehui)
    ImageView iv_yuehui;
    @Bind(R.id.iv_fragment_me_love)
    ImageView iv_love;
    @Bind(R.id.iv_fragment_me_gonglue)
    ImageView iv_gonglue;
    @Bind(R.id.iv_fragment_me_setting)
    ImageView iv_setting;
    @Bind(R.id.iv_fragment_me_dabaogao)
    ImageView iv_dabaogao;
    @Bind(R.id.iv_fragment_me_app_recommend)
    ImageView iv_app_recommend;

    private void init(View view) {
        ButterKnife.bind(this, view);
        if(Util.isOnMainThread())
            Glide.with(getContext().getApplicationContext()).load(R.drawable.img_notice).into(iv_notice) ;
        if(Util.isOnMainThread())
            Glide.with(getContext().getApplicationContext()).load(R.drawable.img_yuehui).into(iv_yuehui);
        if(Util.isOnMainThread())
            Glide.with(getContext().getApplicationContext()).load(R.drawable.img_love).into(iv_love);
        if(Util.isOnMainThread())
            Glide.with(getContext().getApplicationContext()).load(R.drawable.img_gonglue).into(iv_gonglue);
        if(Util.isOnMainThread())
            Glide.with(getContext().getApplicationContext()).load(R.drawable.img_setting).into(iv_setting);
        if(Util.isOnMainThread())
            Glide.with(getContext().getApplicationContext()).load(R.drawable.img_dabaogao).into(iv_dabaogao);
        if(Util.isOnMainThread())
            Glide.with(getContext().getApplicationContext()).load(R.drawable.img_app_recommend).into(iv_app_recommend);

        rlFeedback.setOnClickListener(this);
        rlMyBargin.setOnClickListener(this);
        rlNotification.setOnClickListener(this);
        ivHeadIcon.setOnClickListener(this);
        tvName.setOnClickListener(this);
        llMain.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_love.setOnClickListener(this);
        rl_app_recommend.setOnClickListener(this);
        view.findViewById(R.id.fragment_me_rl_my_page).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_me_rl_feedback: //打小报告
                startActivity(new Intent(context,UserFeedbackActivity.class));
                break;
            case R.id.fragment_me_rl_my_bargin://约惠
                startActivity(new Intent(context, DiscountActivity.class));
                break;
            case R.id.fragment_me_rl_my_page: //个人主页（我的攻略）
                Intent intent2 = new Intent(context, PersonalPageActivity.class);
                intent2.putExtra(AppConstant.UID,LoginUtils.getLoginUid());
                intent2.putExtra("PERSON_ME",true);
                startActivity(intent2);
                break;
            case R.id.fragment_me_rl_notification: //通知
                Intent messageIntent = new Intent(context, ListActivity.class);
                messageIntent.putExtra(AppConstant.LIST_TYPE, AppConstant.LIST_TYPE_MESSAGE);
                startActivity(messageIntent);
                break;
            case R.id.fragment_me_tv_name:
            case R.id.fragment_me_iv_head:
                Intent intent3 = new Intent(context, SettingActivity.class);
                startActivity(intent3);
                break;
            case R.id.rl_mine_setting://设置
                Intent intent = new Intent(context, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_mine_love_article: //收藏的文章
                Intent intent_love = new Intent(getContext(), CollectionArticleActivity.class);
                startActivity(intent_love);
                break;
            case R.id.fragment_me_rl_app_recommend: //应用推荐
                startActivity(new Intent(getContext(), AppRecommendActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        loadDataFromNet();
        super.onResume();
    }

    private void loadDataFromNet() {
        GetUserInfoRequest request = new GetUserInfoRequest();
        request.setUid(LoginUtils.getLoginUid());
        HttpUtils.post(MethodConstant.USER_INFO, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetUserInfoResponse response = (GetUserInfoResponse) receive.getResponse();
                if (response != null && response.getExe_success() == 1) {
                    tvName.setText(response.getUser_name());
                    if(Util.isOnMainThread())
                    Glide.with(context).load(response.getUser_image_url())
                            .transform(new GlideCircleTransform(context))
                            .into(ivHeadIcon);
                    if (response.getNot_read_message() >0) {
                        redPot.setVisibility(View.VISIBLE);
                    }else {
                        redPot.setVisibility(View.GONE);
                    }
                }
            }
        }, new DefaultErrorHook(), GetUserInfoResponse.class);
    }
}
