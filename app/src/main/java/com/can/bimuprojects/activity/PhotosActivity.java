package com.can.bimuprojects.activity;

import android.content.Context;
import android.os.Bundle;
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
import com.can.bimuprojects.Fragment.PhotosFragment;
import com.can.bimuprojects.Module.Request.BrandRequest;
import com.can.bimuprojects.Module.Response.BrandImgResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/6/13.
 * 相册不同类型浏览界面
 */

public class PhotosActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private TabLayout tl; //滑动标题
    private ViewPager vp ; //图片切换viewpager
    private ImageView iv_exit;//退出按钮
    private TextView tv_title; //打开企业版

    //初始化view
    private void initView() {
        setContentView(R.layout.activity_photos);
        tl = (TabLayout) findViewById(R.id.tl_photos);
        vp = (ViewPager) findViewById(R.id.vp_photos);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }

    //设置监听
    private void setListener() {
        iv_exit.setOnClickListener(this);
        tv_title.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exit: //退出
                this.finish();
                break;
            case R.id.tv_title:
//                if(AppUtils.isPkgInstalled(this,"com.can.bimubusiness"))
//                    AppUtils.openOtherApp(this,"com.can.bimubusiness","");
                break;
        }
    }

    private List<PhotosFragment> fragmentLists; //fragment集合
    private List<String> titleLists; //标题集合
    private String bid; //品牌id
    private boolean isConsult;//是否咨询过
    private ArrayList<ArrayList<String>> lists ;//数据集合
    private String brand ; //品牌名称
    private String str_logo ; //品牌logo
    //初始化数据
    private void initData() {
        lists = new ArrayList<>();
        fragmentLists = new ArrayList<>();
        titleLists = new ArrayList<>();
        bid = getIntent().getStringExtra("bid");
        isConsult = getIntent().getBooleanExtra("consult",false);
        brand = getIntent().getStringExtra("brand");
        str_logo = getIntent().getStringExtra("logo");

        if(brand!=null)
            tv_title.setText(brand);

        setTabLayout();


        getImgData();
    }

    /**
     * 获取图库数据
     */
    private void getImgData() {
        BrandRequest request = new BrandRequest();
        request.setBid(bid);
        request.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.GET_BRAND_IMGS, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                BrandImgResponse response = (BrandImgResponse) receive.getResponse();
                if(response!=null){
                    setData(response);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, BrandImgResponse.class);

    }

    //设置网络数据
    private void setData(BrandImgResponse response) {
        List<BrandImgResponse.DataBean> bean = response.getData();
        if(bean!=null){
            for(int i =0;i<bean.size();i++){
                BrandImgResponse.DataBean data = bean.get(i);
                String tag = data.getTag();
                ArrayList<String> datalist = data.getList();
                if(tag!=null)
                    titleLists.add(tag);
                if(datalist!=null)
                    lists.add(datalist);
            }
            PhotosAdapter adapter = new PhotosAdapter(getSupportFragmentManager(),titleLists);
            vp.setAdapter(adapter);
            tl.setupWithViewPager(vp);
        }

    }

    //设置滚动标题样式
    private void setTabLayout() {
        tl.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.color_app_text_yes));
        tl.setSelectedTabIndicatorHeight(UiUtils.dip2px(1));
        tl.setTabMode(TabLayout.MODE_SCROLLABLE); //滚动模式
    }

    /**
     * 图片不同类型选择适配器
     */
    class PhotosAdapter extends FragmentStatePagerAdapter {

        private List<String> list;

        public PhotosAdapter(FragmentManager fm, List<String> list){
            super(fm);
            this.list = list;
            for (int i = 0; i < list.size(); i++) {
                PhotosFragment fragment = new PhotosFragment();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("list",lists.get(i));
                bundle.putString("bid",bid);
                bundle.putBoolean("consult",isConsult);
                bundle.putString("logo",str_logo);
                bundle.putString("name",brand);
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