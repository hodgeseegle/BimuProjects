package com.can.bimuprojects.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Fragment.RankingFragment;
import com.can.bimuprojects.Module.Request.RankingRequest;
import com.can.bimuprojects.Module.Response.RankingResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.UiUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/08/15.
 * 实时热度排行界面
 */

public class RankingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private ImageView iv_exit; //退出按钮
    private TextView tv_title ; //标题
    private TabLayout tl; //滑动标题
    private ViewPager vp ; //内容切换viewpager
    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_ranking);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tl = (TabLayout) findViewById(R.id.tl_activity);
        vp = (ViewPager) findViewById(R.id.vp_activity);
    }

    private List<RankingFragment> fragmentLists; //fragment集合
    private List<String> titleLists; //标题集合
    private List<List<RankingResponse.BrandIndustryBean.DataBean>> lists ;//数据集合
    /**
     * 初始化数据
     */
    private void initData() {
        tv_title.setText(getString(R.string.time_hot_ranking));

        fragmentLists = new ArrayList<>();
        lists = new ArrayList<>();
        titleLists = new ArrayList<>();
        setTabLayout();

        requestITData();
    }

    //设置滚动标题样式
    private void setTabLayout() {
        tl.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.color_app_text_yes));
        tl.setSelectedTabIndicatorHeight(UiUtils.dip2px(1));
        tl.setTabMode(TabLayout.MODE_SCROLLABLE); //滚动模式
    }

    //请求网络数据
    private void requestITData() {
        RankingRequest request = new RankingRequest();
        request.setUid(LoginUtils.getLoginUid());
        request.setPage("0");
        request.setArea("");
        request.setCid("");
        request.setType("");
        HttpUtils.postWithoutUid(MethodConstant.RANKING, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                RankingResponse response = (RankingResponse) receive.getResponse();
                if(response!=null){
                    setData(response);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, RankingResponse.class);
    }

    //设置网络数据
    private void setData(RankingResponse response) {
        List<RankingResponse.BrandIndustryBean> bean = response.getBrand_industry();
        if(bean!=null){
            List<RankingResponse.BrandIndustryBean.DataBean> datalist;
            for(int i =0;i<bean.size();i++){
                RankingResponse.BrandIndustryBean data = bean.get(i);
                String tag = data.getName();
                datalist = data.getData();
                if(tag!=null)
                    titleLists.add(tag);
                if(datalist!=null){
                    lists.add(datalist);
                }
            }
            RankingAdapter adapter = new RankingAdapter(getSupportFragmentManager(),titleLists);
            vp.setAdapter(adapter);
            tl.setupWithViewPager(vp);
        }

    }

    /**
     设置监听
     */
    private void setListener() {
        iv_exit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exit:
                finish();
                break;
        }
    }


    /**
     * 图片不同类型选择适配器
     */
    class RankingAdapter extends FragmentStatePagerAdapter {

        private List<String> list;

        public RankingAdapter(FragmentManager fm, List<String> list){
            super(fm);
            this.list = list;
            for (int i = 0; i < lists.size(); i++) {
                RankingFragment fragment = new RankingFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("list", (Serializable) lists.get(i));
                bundle.putString("tag",titleLists.get(i));
                fragment.setArguments(bundle);
                fragmentLists.add(fragment);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentLists.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position);
        }
    }


}
