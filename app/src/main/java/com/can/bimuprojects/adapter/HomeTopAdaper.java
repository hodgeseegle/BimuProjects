package com.can.bimuprojects.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Module.Response.HomePagerResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.GlideRoundTransform;
import com.can.bimuprojects.utils.UiUtils;

import java.util.List;

/**
 * Created by can on 2017/6/27.
 * 首页推荐头部适配器
 */
public class HomeTopAdaper extends RecyclerView.Adapter<HomeTopAdaper.ViewHolder> implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        if(listener!=null)
        listener.onItemClickListener(view,Integer. parseInt(view.getTag().toString()));
    }


    public interface onTopItemClickListener{
        void onItemClickListener(View view, int i);
    }

    private onTopItemClickListener listener;

    public void setOnItemClickListener(onTopItemClickListener listener){
        this.listener = listener;
    }

    private Context context;
    private List<HomePagerResponse.ListBean> list;

    public HomeTopAdaper(Context context, List<HomePagerResponse.ListBean> list){
        this.context = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_articles,parent,false);
        view.setOnClickListener(this);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        if(list.get(i)==null)
            return;
        if(Util.isOnMainThread())
            Glide.with(context).load(list.get(i).getImg_url()).placeholder(R.drawable.loading).dontAnimate().into(holder.iv);
        holder.tv.setText(list.get(i).getTitle()+"");
        holder.ll.removeAllViews();
        int size = 0;
        if(list.get(i).getOrder()!=null){
            size = list.get(i).getOrder().size();
            if(size>0)
                holder.tv_has_brand.setVisibility(View.VISIBLE);
            else
                holder.tv_has_brand.setVisibility(View.INVISIBLE);
        }
        String tag = list.get(i).getTag();
        if(tag!=null){
            if(!tag.equals(""))
                holder.tv_has_brand.setVisibility(View.VISIBLE);
            else
                holder.tv_has_brand.setVisibility(View.INVISIBLE);
        }
        for(int j=0;j<size;j++){
            ImageView imageView = new ImageView(context);
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiUtils.dip2px(28),UiUtils.dip2px(28));
            params.setMargins(UiUtils.dip2px(3),0,UiUtils.dip2px(3),0);
            imageView.setLayoutParams(params);
            if(Util.isOnMainThread())
                Glide.with(context).load(list.get(i).getOrder().get(j).toString()).transform(new GlideRoundTransform(context)).dontAnimate().into(imageView); //图片资源
            holder.ll.addView(imageView);
        }
        holder.itemView.setTag(i+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv,tv_has_brand;
        LinearLayout ll;
        public ViewHolder(View view) {
            super(view);
            this.iv = (ImageView) view.findViewById(R.id.iv_item_home_special);
            this.tv = (TextView) view.findViewById(R.id.tv_item_home_special);
            this.ll = (LinearLayout) view.findViewById(R.id.ll_home_special);
            this.tv_has_brand = (TextView) view.findViewById(R.id.tv_item_home_special_has_brand);
        }
    }

}

