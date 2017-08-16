package com.can.bimuprojects.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Module.Response.SpecialResponse;
import com.can.bimuprojects.R;

import java.util.List;

/**
 * Created by can on 2017/4/13.
 * 专题适配器
 */

public class SpecialAdapter extends BaseAdapter{

    private Context context;
    private List<SpecialResponse.ListBean> list;

    public SpecialAdapter(Context context,List<SpecialResponse.ListBean> list){
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
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_find,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv_find);
            viewHolder.tv= (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(Util.isOnMainThread())
        Glide.with(context)
                .load(list.get(i).getImg_url())
        .centerCrop()
        .dontAnimate()
                .placeholder(R.drawable.loading)
                .into(viewHolder.imageView);
        String title = list.get(i).getTitle();
        if(title!=null){
            SpannableStringBuilder style=new SpannableStringBuilder(title);
            style.setSpan(new BackgroundColorSpan(ContextCompat.getColor(context,R.color.low_transl)),0,title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.tv.setText(style);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView tv;
    }

}
