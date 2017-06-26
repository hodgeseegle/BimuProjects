package com.can.bimuprojects.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Module.Entity.AppRecommendBean;
import com.can.bimuprojects.R;

import java.util.List;

/**
 * Created by can on 2017/5/16.
 * 应用推荐适配器
 */

public class AppRecommendAdapter extends BaseAdapter {

    private Context context;
    private List<AppRecommendBean> list;

    public AppRecommendAdapter(Context context,List<AppRecommendBean> list){
        this.context  = context;
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
        final VH vh ;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_app_recommend,null);
            vh = new VH();
            vh.iv_logo = (ImageView) view.findViewById(R.id.iv_item_app_recommend_logo);
            vh.tv_title = (TextView) view.findViewById(R.id.tv_item_app_recommend_title);
            vh.tv_content = (TextView) view.findViewById(R.id.tv_item_app_recommend_content);
            vh.btn = (Button) view.findViewById(R.id.btn_item_app_recommend_down);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        AppRecommendBean data = list.get(i);
        final String title = data.getApp_name();
        String logo = data.getApp_logo();
        String content = data.getApp_about();
        final String link = data.getApp_link();
        if(logo!=null&& Util.isOnMainThread())
            Glide.with(context).load(logo).into(vh.iv_logo);
        if(title!=null)
            vh.tv_title.setText(title.trim());
        if(content!=null)
            vh.tv_content.setText(content.trim());
        if(link!=null)
        vh.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri uri = Uri.parse(link);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });
        return view;
    }

    class VH{
        ImageView iv_logo;
        TextView tv_title,tv_content;
        Button btn;
    }
}
