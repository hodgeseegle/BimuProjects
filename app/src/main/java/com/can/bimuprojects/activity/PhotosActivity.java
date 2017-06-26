package com.can.bimuprojects.activity;

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

import com.can.bimuprojects.Fragment.PhotosFragment;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.AppUtils;
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
    private ArrayList<String> list_img ; //图片集合
    private String bid; //品牌id
    private boolean isConsult;//是否咨询过
    private ArrayList<ArrayList<String>> lists ;//数据集合
    private String brand ; //品牌名称
    //初始化数据
    private void initData() {
        lists = new ArrayList<>();
        fragmentLists = new ArrayList<>();
        titleLists = new ArrayList<>();
        list_img =  new ArrayList<>();
        list_img = getIntent().getStringArrayListExtra("list");
        bid = getIntent().getStringExtra("bid");
        isConsult = getIntent().getBooleanExtra("consult",false);
        brand = getIntent().getStringExtra("brand");

        if(brand!=null)
            tv_title.setText(brand);

        setTabLayout();

        String []strings = getResources().getStringArray(R.array.array_article_type);

        for(int i=0;i<strings.length;i++){
            titleLists.add(strings[i]);
        }
        for(int i =0;i<strings.length;i++){
            ArrayList<String> arrayList = new ArrayList<>();
            for(int j=0;j<list_img.size();j++){
                arrayList.add(list_img.get(j));
                if(i==1||i==2)
                    arrayList.add(list_img.get(j));
                if(i==2)
                    arrayList.add(list_img.get(j));
            }
            lists.add(arrayList);
        }
        PhotosAdapter adapter = new PhotosAdapter(getSupportFragmentManager(),titleLists);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }

    //设置滚动标题样式
    private void setTabLayout() {
        tl.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.color_red));
        tl.setSelectedTabIndicatorHeight(UiUtils.dip2px(1));
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
