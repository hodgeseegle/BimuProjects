package com.can.bimuprojects.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Module.Entity.PersonalCardBean;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.DateUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class PersonalAdapter extends BaseAdapter {
    private List<PersonalCardBean> dataList;
    private Context context;

    public PersonalAdapter(List<PersonalCardBean> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int i, View convertView, ViewGroup parent) {
        VH vh;
        if(convertView==null){
            vh = new VH();
            convertView  = LayoutInflater.from(context).inflate(R.layout.item_person,null);
            vh.iv1 = (ImageView) convertView.findViewById(R.id.iv_person_iv1);
            vh.iv2 = (ImageView) convertView.findViewById(R.id.iv_person_iv2);
            vh.iv3 = (ImageView) convertView.findViewById(R.id.iv_person_iv3);
            vh.tv_date = (TextView) convertView.findViewById(R.id.tv_person_date);
            vh.tv_title = (TextView) convertView.findViewById(R.id.tv_person_title);
            vh.tv_look = (TextView) convertView.findViewById(R.id.tv_item_inspection_look);
            vh.tv_comment = (TextView) convertView.findViewById(R.id.tv_item_inspection_comment);
            vh.ll = (LinearLayout) convertView.findViewById(R.id.ll_person_imgs);
            vh.iv_circle = (ImageView) convertView.findViewById(R.id.iv_item_person_circle);
            convertView.setTag(vh);
        }else{
            vh = (VH) convertView.getTag();
        }
        PersonalCardBean data = dataList.get(i);
        if(data.getTimestamp()!=null)
            vh.tv_date.setText(DateUtils.timestampToString(data.getTimestamp()));
        vh.tv_title.setText(data.getText()+"");
        vh.tv_look.setText(data.getRead_num()+"");
        vh.tv_comment.setText(data.getComment_count()+"");
        if(Util.isOnMainThread())
            Glide.with(context).load(R.drawable.img_person_circle).into(vh.iv_circle);
        List<String> list = data.getArticle_image_urls();
        if(list!=null) {
            if (list.size() > 0) {
                if (list.size() >= 3 && (Util.isOnMainThread())) {
                    Glide.with(context).load(list.get(0)).placeholder(R.drawable.loading).dontAnimate().into(vh.iv1);
                    Glide.with(context).load(list.get(1)).placeholder(R.drawable.loading).dontAnimate().into(vh.iv2);
                    Glide.with(context).load(list.get(2)).placeholder(R.drawable.loading).dontAnimate().into(vh.iv3);
                    vh.iv1.setVisibility(View.VISIBLE);
                    vh.iv2.setVisibility(View.VISIBLE);
                    vh.iv3.setVisibility(View.VISIBLE);
                } else if (list.size() == 2 && (Util.isOnMainThread())) {
                    Glide.with(context).load(list.get(0)).placeholder(R.drawable.loading).dontAnimate().into(vh.iv1);
                    Glide.with(context).load(list.get(1)).placeholder(R.drawable.loading).dontAnimate().into(vh.iv2);
                    vh.iv1.setVisibility(View.VISIBLE);
                    vh.iv2.setVisibility(View.VISIBLE);
                    vh.iv3.setVisibility(View.INVISIBLE);
                } else if (list.size() == 1 && (Util.isOnMainThread())) {
                    Glide.with(context).load(list.get(0)).placeholder(R.drawable.loading).dontAnimate().into(vh.iv1);
                    vh.iv1.setVisibility(View.VISIBLE);
                    vh.iv2.setVisibility(View.INVISIBLE);
                    vh.iv3.setVisibility(View.INVISIBLE);
                }
            }else{
                vh.iv1.setVisibility(View.GONE);
                vh.iv2.setVisibility(View.GONE);
                vh.iv3.setVisibility(View.GONE);
            }
        } else {
            vh.iv1.setVisibility(View.GONE);
            vh.iv2.setVisibility(View.GONE);
            vh.iv3.setVisibility(View.GONE);
        }
        return convertView;
    }

    public String getString(String s, String s1)//s是需要删除某个子串的字符串s1是需要删除的子串
    {
        if(!s.contains(s1)){
            return s;
        }
        int postion = s.indexOf(s1);
        int length = s1.length();
        int Length = s.length();
        String newString = s.substring(0,postion) + s.substring(postion + length, Length);
        return newString;//返回已经删除好的字符串
    }

    class VH{
        TextView tv_date;
        TextView tv_title;
        LinearLayout ll;
        TextView tv_look;
        TextView tv_comment;
        ImageView iv1,iv2,iv3;
        ImageView iv_circle;
    }

}