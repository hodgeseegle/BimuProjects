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
import com.can.bimuprojects.Module.Entity.RecommandCardBean;
import com.can.bimuprojects.R;

import java.util.List;

/**
 * Created by can on 2017/4/20.
 * 发现页
 */

public class SpecialArticleListAdapter extends BaseAdapter {

    private Context context;
    private List<RecommandCardBean> list;

    public SpecialArticleListAdapter(Context context, List<RecommandCardBean> list){
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
        VH vh;
        if(view==null){
            vh = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_special_article_list,null);
            vh.tv_title = (TextView) view.findViewById(R.id.tv_special_article_list_title);
            vh.tv_content = (TextView) view.findViewById(R.id.tv_special_article_list_content);
            vh.tv_look = (TextView) view.findViewById(R.id.tv_special_article_list_look);
            vh.tv_love = (TextView) view.findViewById(R.id.tv_special_article_list_love);
            vh.tv_comment = (TextView) view.findViewById(R.id.tv_special_article_list_comment);
            vh.iv1 = (ImageView) view.findViewById(R.id.iv_special_article_list_iv1);
            vh.iv2 = (ImageView) view.findViewById(R.id.iv_special_article_list_iv2);
            vh.iv3 = (ImageView) view.findViewById(R.id.iv_special_article_list_iv3);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        RecommandCardBean data = list.get(i);
        vh.tv_title.setText(""+data.getArticle_summary());
        vh.tv_content.setText(""+data.getRticle_html());
        vh.tv_look.setText(""+data.getRead_num());
        vh.tv_love.setText(""+data.getLike_count());
        vh.tv_comment.setText(""+data.getComment_count());
        if(data.getArticle_image_urls().size()>=3&&(Util.isOnMainThread())){
            Glide.with(context).load(data.getArticle_image_urls().get(0)).placeholder(R.drawable.loading).into(vh.iv1);
            Glide.with(context).load(data.getArticle_image_urls().get(1)).placeholder(R.drawable.loading).into(vh.iv2);
            Glide.with(context).load(data.getArticle_image_urls().get(2)).placeholder(R.drawable.loading).into(vh.iv3);
            vh.iv1.setVisibility(View.VISIBLE);
            vh.iv2.setVisibility(View.VISIBLE);
            vh.iv3.setVisibility(View.VISIBLE);
        }else if(data.getArticle_image_urls().size()==2&&(Util.isOnMainThread())){
            Glide.with(context).load(data.getArticle_image_urls().get(0)).placeholder(R.drawable.loading).into(vh.iv1);
            Glide.with(context).load(data.getArticle_image_urls().get(1)).placeholder(R.drawable.loading).into(vh.iv2);
            vh.iv1.setVisibility(View.VISIBLE);
            vh.iv2.setVisibility(View.VISIBLE);
            vh.iv3.setVisibility(View.INVISIBLE);
        }else if(data.getArticle_image_urls().size()==1&&(Util.isOnMainThread())){
            Glide.with(context).load(data.getArticle_image_urls().get(0)).placeholder(R.drawable.loading).into(vh.iv1);
            vh.iv1.setVisibility(View.VISIBLE);
            vh.iv2.setVisibility(View.INVISIBLE);
            vh.iv3.setVisibility(View.INVISIBLE);
        }else{
            vh.iv1.setVisibility(View.GONE);
            vh.iv2.setVisibility(View.GONE);
            vh.iv3.setVisibility(View.GONE);
        }
        return view;
    }

    class VH{
        TextView tv_title;
        TextView tv_content;
        TextView tv_look;
        TextView tv_love;
        TextView tv_comment;
        ImageView iv1,iv2,iv3;

    }
}
