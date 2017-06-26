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
import com.can.bimuprojects.Module.Response.HomePagerResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by can on 2017/6/19.
 * 首页品牌适配器
 */

public class HomeBrandAdapter extends BaseAdapter {

    private Context context;
    private List<HomePagerResponse.BrandlistBean> list;

    public HomeBrandAdapter(Context context,List<HomePagerResponse.BrandlistBean> list){
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_brand, null);
            viewHolder = new ViewHolder();
            viewHolder.iv2 = (ImageView) convertView.findViewById(R.id.iv_item_search_result_smalllogo);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_item_search_result_biglogo);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_item_search_result);
            viewHolder.tv_people = (TextView) convertView.findViewById(R.id.tv_item_search_result_people);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final HomePagerResponse.BrandlistBean bean = list.get(i);
        if(Util.isOnMainThread())
            Glide.with(context).load(bean.getBrand_background()).asBitmap().fitCenter().placeholder(R.drawable.loading).into(viewHolder.iv);
        if(Util.isOnMainThread())
            Glide.with(context).load(bean.getBrand_logo()).transform(new GlideRoundTransform(context)).into(viewHolder.iv2);
        String string = bean.getBrand_name()+" · 投资 "+bean.getInvest_amount()+" 万 · 总部"+bean.getBrand_location()+" · 适合面积 "+bean.getShop_area();
        viewHolder.tv.setText(string);
        int total = bean.getScribe();
        viewHolder.tv_people.setText(total+"人已免费获得官方开店方案");
        return convertView;
    }

    class ViewHolder {
        ImageView iv,iv2;
        TextView tv;
        TextView tv_people;
    }


}
