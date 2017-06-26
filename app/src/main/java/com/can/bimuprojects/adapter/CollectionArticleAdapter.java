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
import com.can.bimuprojects.Module.Response.GetUserCollectionResponse;
import com.can.bimuprojects.R;

import java.util.List;

/**
 * Created by can on 2017/4/26.
 * 收藏的文章适配器
 */

public class CollectionArticleAdapter extends BaseAdapter {


    private Context context;
    private List<GetUserCollectionResponse.ListBean> list;

    public CollectionArticleAdapter(Context context ,List<GetUserCollectionResponse.ListBean> list){
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
            view = LayoutInflater.from(context).inflate(R.layout.item_collection_article,null);
            vh.tv = (TextView) view.findViewById(R.id.tv_item_collection_article);
            vh.iv = (ImageView) view.findViewById(R.id.iv_item_collection_article);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        GetUserCollectionResponse.ListBean data = list.get(i);
        vh.tv.setText(""+data.getArticle_summary());
        if(Util.isOnMainThread())
        Glide.with(context).load(data.getImg()).placeholder(R.drawable.loading).into(vh.iv);
        return view;
    }

    class VH{
        TextView tv;
        ImageView iv;
    }


}
