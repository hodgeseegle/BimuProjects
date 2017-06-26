package com.can.bimuprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Module.Response.SearchResultResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.GlideRoundTransform;


import java.util.List;

/**
 * Created by can on 2016/4/1.
 * 搜索结果适配器
 */
public class SearchResultAdapter extends BaseAdapter {
    private Context context;
    private List<SearchResultResponse.DataBean> dataList;

    public SearchResultAdapter(Context context, List<SearchResultResponse.DataBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public SearchResultResponse.DataBean getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.iv2 = (ImageView) convertView.findViewById(R.id.iv_item_search_result_smalllogo);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_item_search_result_biglogo);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_item_search_result);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final SearchResultResponse.DataBean bean = getItem(position);
        if(Util.isOnMainThread())
        Glide.with(context).load(bean.getBrand_background()).dontAnimate().into(viewHolder.iv);
        if(Util.isOnMainThread())
        Glide.with(context).load(bean.getBrand_logo()).transform(new GlideRoundTransform(context)).into(viewHolder.iv2);
        String string = bean.getBrand_name()+" · 投资 "+bean.getInvest_amount()+" 万 · 总部"+bean.getBrand_location()+" · 适合面积 "+bean.getShop_area();
        viewHolder.tv.setText(string);

        return convertView;
    }

    static class ViewHolder {
        ImageView iv,iv2;
        TextView tv;
    }

}

