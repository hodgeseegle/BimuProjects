package com.can.bimuprojects.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by can on 2017/4/9.
 * 搜索结果页 头部 viewpager适配器
 */

public class SearchResultVPAdapter extends PagerAdapter {

        private List<ImageView> mViewList;

        public SearchResultVPAdapter(List<ImageView> viewList) {
            mViewList = viewList;
        }

        @Override
        public ImageView instantiateItem(ViewGroup container, int position) {
            ImageView view = mViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }


}
