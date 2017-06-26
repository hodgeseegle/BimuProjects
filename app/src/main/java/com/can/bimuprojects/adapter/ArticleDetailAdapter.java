package com.can.bimuprojects.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Module.Response.ArticleDetailResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.PersonalPageActivity;
import com.can.bimuprojects.utils.DateUtils;
import com.can.bimuprojects.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by can on 2017/4/18.
 * 文章详情页适配器(评论)
 */

public class ArticleDetailAdapter extends BaseAdapter {

    private Context context;
    private List<ArticleDetailResponse.CommentsBean> list;

    public ArticleDetailAdapter(Context context, List<ArticleDetailResponse.CommentsBean> list){
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
         VH vh;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_article_detail_comment,null);
            vh = new VH();
            vh.iv = (ImageView) view.findViewById(R.id.iv_item_article_detail_person);
            vh.tv_name1 = (TextView) view.findViewById(R.id.tv_item_article_detail_comment_name1);
            vh.tv_name2 = (TextView) view.findViewById(R.id.tv_item_article_detail_comment_name2);
            vh.tv_rep = (TextView) view.findViewById(R.id.tv_item_article_detail_comment_rep);
            vh.tv_content = (TextView) view.findViewById(R.id.tv_item_article_detail_comment_content);
            vh.tv_date = (TextView) view.findViewById(R.id.tv_item_article_detail_comment_date);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        ArticleDetailResponse.CommentsBean data = list.get(i);
        if(Util.isOnMainThread())
        Glide.with(context).load(data.getAuthor_image()).transform(new GlideRoundTransform(context)).placeholder(R.drawable.default_logo).dontAnimate().into(vh.iv);
        if(data.getIs_reply()==1){ //回复
            vh.tv_rep.setVisibility(View.VISIBLE);
            vh.tv_name2.setVisibility(View.VISIBLE);
        }else{ //非回复
            vh.tv_rep.setVisibility(View.GONE);
            vh.tv_name2.setVisibility(View.GONE);
        }
        vh.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_person = new Intent(context,PersonalPageActivity.class);
                intent_person.putExtra(AppConstant.UID,list.get(i).getAuthor_uid());
                context.startActivity(intent_person);
            }
        });
        vh.tv_name1.setText(""+data.getAuthor_nickname());
        vh.tv_name2.setText(""+data.getRe_user_name());
        vh.tv_content.setText(data.getComment_text().trim());
        if(data.getTimestamp().equals("刚刚"))
            vh.tv_date.setText("刚刚");
            else
        vh.tv_date.setText(DateUtils.timestampToString(data.getTimestamp()));

        return view;
    }


    class VH{
        TextView tv_name1,tv_name2,tv_rep,tv_content,tv_date;
        ImageView iv;
    }

}
