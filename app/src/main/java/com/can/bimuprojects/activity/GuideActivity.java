package com.can.bimuprojects.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.LoginUtils;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity {

    private ViewPager guide_viewpager;
    private final static int[] imagesId = {R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3, R.drawable.guide_4};
    private List<ImageView> list;
    private Button btn_end_guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    private void setListener() {
        guide_viewpager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == list.size() - 1) {
                    btn_end_guide.setVisibility(View.VISIBLE);
                } else {
                    btn_end_guide.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btn_end_guide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setHaveUsed();
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
            }
        });
    }


    private void setHaveUsed() {
        SharedPreferences sp = this.getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("firstUse1", false);
        edit.apply();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < imagesId.length; i++) {
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(imagesId[i] ).centerCrop().into(imageView);
            list.add(imageView);
        }
        guide_viewpager.setAdapter(new GuideViewPager());

    }

    private void initView() {
        setContentView(R.layout.activity_guide);
        guide_viewpager = (ViewPager) findViewById(R.id.guide_viewpager);
        btn_end_guide = (Button) findViewById(R.id.btn_end_guide);
    }

    class GuideViewPager extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = list.get(position);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

}
