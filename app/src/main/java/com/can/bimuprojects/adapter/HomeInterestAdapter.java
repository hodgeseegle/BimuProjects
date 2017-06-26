package com.can.bimuprojects.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Module.Response.HomePagerResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by can on 2017/4/12.
 * 首页猜你感兴趣适配器
 */

public class HomeInterestAdapter extends RecyclerView.Adapter<HomeInterestAdapter.ViewHolder> implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        if (listener!=null)
        listener.onItemClick(view,Integer. parseInt(view.getTag().toString()));
    }

    public void setOnItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    private ItemClickListener listener;

    public interface ItemClickListener{
         void onItemClick(View view , int position);
    }

    private Context context;
    private List<HomePagerResponse.BrandlistBean> list;

    public HomeInterestAdapter(Context context, List<HomePagerResponse.BrandlistBean> list){
        this.context = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_guess_interest,parent,false);
        view.setOnClickListener(this);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        if(Util.isOnMainThread())
        Glide.with(context).load(list.get(i).getBrand_background()).placeholder(R.drawable.loading).dontAnimate().into(holder.iv_big);
        if(Util.isOnMainThread())
        Glide.with(context).load(list.get(i).getBrand_logo()).transform(new GlideRoundTransform(context)).dontAnimate().into(holder.iv_small);
        holder.tv1.setText(list.get(i).getBrand_name()+" · 投资"+list.get(i).getInvest_amount()+" 万");
        holder.tv2.setText("总部"+list.get(i).getBrand_location()+" · 面积 "+list.get(i).getShop_area());
        holder.tv3.setText(list.get(i).getScribe()+"个比友正在关注");
        holder.itemView.setTag(i+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_big,iv_small;
         TextView tv1,tv2,tv3;
        public ViewHolder(View view) {
            super(view);
            iv_big = (ImageView) view.findViewById(R.id.iv_item_home_interest_biglogo);
            iv_small = (ImageView) view.findViewById(R.id.iv_item_home_interest_smalllogo);
            tv1 = (TextView) view.findViewById(R.id.tv_item_home_interest_content1);
            tv2 = (TextView) view.findViewById(R.id.tv_item_home_interest_content2);
            tv3 = (TextView) view.findViewById(R.id.tv_item_home_interest_content3);
        }

    }


}
