package com.can.bimuprojects.adapter;


import android.content.Context;
import android.text.SpannableStringBuilder;
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
import com.can.bimuprojects.utils.NumberUtils;

import java.util.List;

/**
 * Created by can on 2017/6/28.
 * 搜索结果文章适配器
 */

public class SearchResultArticleAdapter extends BaseAdapter {

    private Context context ;
    private List<SearchResultResponse.ArticleBean> list;
    private String tag ;

    public SearchResultArticleAdapter(Context context,List<SearchResultResponse.ArticleBean> list,String tag){
        this.context = context;
        this.list = list;
        this.tag = tag;
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
            view = LayoutInflater.from(context).inflate(R.layout.item_search_result_article,null);
            vh.iv_logo = (ImageView) view.findViewById(R.id.iv_item_search_logo);
            vh.tv_title = (TextView) view.findViewById(R.id.tv_item_search_title);
            vh.tv_content = (TextView) view.findViewById(R.id.tv_item_search_content);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        SearchResultResponse.ArticleBean bean = list.get(i);
        String logo = bean.getArticle_logo();
        String title = bean.getArticle_title();
        String content = bean.getArticle_html();
        if(logo!=null&& Util.isOnMainThread())
            Glide.with(context).load(logo).placeholder(R.drawable.loading).dontAnimate().into(vh.iv_logo);
        if(title!=null){
            SpannableStringBuilder style= NumberUtils.getSpannableString(context,title,tag);
            vh.tv_title.setText(style);
            style.clear();
        }
        if(content!=null){
            SpannableStringBuilder style=NumberUtils.getSpannableString(context,content,tag);
            vh.tv_content.setText(style);
            style.clear();
        }
        return view;
    }



    static class VH{
        TextView tv_title;
        TextView tv_content;
        ImageView iv_logo;
    }


}
