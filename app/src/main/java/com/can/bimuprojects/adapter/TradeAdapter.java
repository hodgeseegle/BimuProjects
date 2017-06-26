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

import java.util.List;

/**
 * Created by can on 2017/6/15.
 * 行业适配器
 */

public class TradeAdapter extends BaseAdapter {

    private List<SearchResultResponse.CategoryBean> list;//数据集合
    private Context context;

    public TradeAdapter(Context context, List<SearchResultResponse.CategoryBean> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH vh ;
        if(view==null){
            vh = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_trade,null);
            vh.tv = (TextView) view.findViewById(R.id.tv_item_trade);
            vh.iv = (ImageView) view.findViewById(R.id.iv_item_trade);
            view.setTag(vh);
        }else {
            vh = (VH) view.getTag();
        }
        SearchResultResponse.CategoryBean bean = list.get(i);
        String name = bean.getClassify_name();
        String icon = bean.getClassify_icon();
        if(name!=null)
            vh.tv.setText(name);
        if(Util.isOnMainThread()&&icon!=null)
            Glide.with(context).load(icon).into(vh.iv);
        return view;
    }

    static class VH{
        TextView tv;
        ImageView iv;
    }

}
