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
import com.can.bimuprojects.Module.Response.BrandResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by can on 2017/4/14.
 * 品牌详情 推荐 适配器
 */

public class BrandRecommendAdapter extends BaseAdapter {

    private Context context;
    private List<BrandResponse.BrandlistBean> list;

    public BrandRecommendAdapter(Context context, List<BrandResponse.BrandlistBean> list){
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
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH vh ;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_brand_guess_interest,viewGroup,false);
            vh = new VH();
            vh.iv_big = (ImageView) view.findViewById(R.id.iv_item_brand_interest_biglogo);
            vh.iv_small = (ImageView) view.findViewById(R.id.iv_item_brand_interest_smalllogo);
            vh.tv1 = (TextView) view.findViewById(R.id.tv_item_brand_interest_content1);
            vh.tv2 = (TextView) view.findViewById(R.id.tv_item_brand_interest_content2);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        if(Util.isOnMainThread())
        Glide.with(context).load(list.get(i).getBrand_background()).placeholder(R.drawable.loading).into(vh.iv_big);
        if(Util.isOnMainThread())
        Glide.with(context).load(list.get(i).getBrand_logo()).transform(new GlideRoundTransform(context)).into(vh.iv_small);
        vh.tv1.setText(list.get(i).getBrand_name()+" · 投资"+list.get(i).getInvest_amount()+" 万");
        vh.tv2.setText("总部"+list.get(i).getBrand_location()+" · 面积 "+list.get(i).getShop_area());
        return view;
    }

    class VH {
        ImageView iv_big,iv_small;
        TextView tv1,tv2;
    }

}
