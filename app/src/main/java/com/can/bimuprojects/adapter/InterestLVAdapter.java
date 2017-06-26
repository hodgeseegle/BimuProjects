package com.can.bimuprojects.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.ActivityInterest;

import java.util.List;

/**
 * Created by can on 2017/4/10.
 * 兴趣行业左侧适配器
 */

public class InterestLVAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;

    public InterestLVAdapter(Context context,List<String> list){
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
            view = LayoutInflater.from(context).inflate(R.layout.item_interest_lv,null);
            vh = new VH();
            vh.tv = (TextView) view.findViewById(R.id.tv_item_interest_lv);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        if(ActivityInterest.lv_position==i){
            view.setBackgroundColor(ContextCompat.getColor(context,R.color.color_gray));
            vh.tv.setTextColor(ContextCompat.getColor(context,R.color.color_app_text_yes));
        }else{
            view.setBackgroundColor(ContextCompat.getColor(context,R.color.color_white));
            vh.tv.setTextColor(ContextCompat.getColor(context,R.color.color_big_text));
        }
        vh.tv.setText(list.get(i));
        return view;
    }

    class VH{
        TextView tv;
    }
}
