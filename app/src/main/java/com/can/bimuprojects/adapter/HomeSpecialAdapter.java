package com.can.bimuprojects.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Module.Response.HomePagerResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.SpecialActivity;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.utils.GlideRoundTransform;
import com.can.bimuprojects.utils.UiUtils;

import java.util.List;

/**
 * Created by can on 2017/4/12.
 * 首页专题适配器
 */
public class HomeSpecialAdapter extends BaseAdapter {

    private List<HomePagerResponse.ListBean> list;
    private Context context;

    public  HomeSpecialAdapter(Context context,List<HomePagerResponse.ListBean> list){
        this.list = list;
        this.context = context;
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
        VH holder ;
        if(view==null){
            holder = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_home_special,null);
            holder.iv = (ImageView) view.findViewById(R.id.iv_item_home_special);
            holder.tv_title = (TextView) view.findViewById(R.id.tv_item_home_special);
            holder.tv_tag = (TextView) view.findViewById(R.id.tv_item_home_special_tag);
            view.setTag(holder);
        }else{
            holder = (VH) view.getTag();
        }
        String tag = list.get(i).getTag();
        if(tag!=null){
            holder.tv_tag.setText(tag);
            if(!tag.equals(""))
                holder.tv_tag.setVisibility(View.VISIBLE);
            else
                holder.tv_tag.setVisibility(View.GONE);
        }
        String img = list.get(i).getImg_url();
        if(Util.isOnMainThread()&&img!=null)
            Glide.with(context).load(img).placeholder(R.drawable.loading).dontAnimate().into(holder.iv);
        String title = list.get(i).getTitle();
        if(title!=null)
            holder.tv_title.setText(title);
        return view;
    }

    class VH{

        TextView tv_tag,tv_title;
        ImageView iv;
    }



}



//public class HomeSpecialAdapter extends RecyclerView.Adapter<HomeSpecialAdapter.ViewHolder> implements View.OnClickListener {
//
//    @Override
//    public void onClick(View view) {
//        listener.onItemClickListener(view,Integer. parseInt(view.getTag().toString()));
//    }
//
//
//    public interface onItemClickListener{
//        void onItemClickListener(View view ,int i);
//    }
//
//    private onItemClickListener listener;
//
//    public void setOnItemClickListener(onItemClickListener listener){
//        this.listener = listener;
//    }
//
//    private Context context;
//    private List<HomePagerResponse.ListBean> list;
//
//    public HomeSpecialAdapter(Context context, List<HomePagerResponse.ListBean> list){
//        this.context = context;
//        this.list = list;
//    }
//
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        ViewHolder vh = null;
//        switch (viewType){
//            case 0:
//                View view = LayoutInflater.from(context).inflate(R.layout.item_home_sepcial,parent,false);
//                vh = new ViewHolder(view);
//                view.setOnClickListener(this);
//                break;
//
//            case 1:
//                View view2 = LayoutInflater.from(context).inflate(R.layout.item_home_special_foot,parent,false);
//                vh = new ViewHolder(view2);
//                view2.setOnClickListener(this);
//                ImageView iv = (ImageView) view2.findViewById(R.id.iv_item_home_special_foot);
//                view2.findViewById(R.id.tv_item_home_special_foot).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        MobclickAgent.onEvent(context, "tv_look_all_special");
//                        Intent intent_special = new Intent(context, SpecialActivity.class);
//                        context.startActivity(intent_special);
//                    }
//                });
//                if(Util.isOnMainThread())
//                Glide.with(BimuApplication.getContext()).load(R.drawable.img_look_all).into(iv);
//                iv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        MobclickAgent.onEvent(context, "tv_look_all_special");
//                        Intent intent_special = new Intent(context, SpecialActivity.class);
//                        context.startActivity(intent_special);
//                    }
//                });
//                break;
//        }
//
//        return vh;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if(position==list.size()-1)
//            return 1;
//        return 0;
//    }
//
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int i) {
//        int type = holder.getItemViewType();
//        switch (type){
//            case 0:
//                holder.itemView.setTag(i+"");
//                if(list.get(i)==null)
//                    return;
//                if(Util.isOnMainThread())
//                    Glide.with(context).load(list.get(i).getImg_url()).placeholder(R.drawable.loading).dontAnimate().into(holder.iv);
//                holder.tv.setText(list.get(i).getTitle()+"");
//                holder.ll.removeAllViews();
//                int size = 0;
//                if(list.get(i).getOrder()!=null){
//                    size = list.get(i).getOrder().size();
//                    if(size>0)
//                        holder.tv_has_brand.setVisibility(View.VISIBLE);
//                    else
//                        holder.tv_has_brand.setVisibility(View.GONE);
//                }
//                String tag = list.get(i).getTag();
//                if(tag!=null){
//                        holder.tv_tag.setText(tag);
//                    if(!tag.equals(""))
//                        holder.tv_has_brand.setVisibility(View.VISIBLE);
//                    else
//                        holder.tv_has_brand.setVisibility(View.GONE);
//                }
//                for(int j=0;j<size;j++){
//                    ImageView imageView = new ImageView(context);
//                    DisplayMetrics metrics = new DisplayMetrics();
//                    ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiUtils.dip2px(28),UiUtils.dip2px(28));
//                    params.setMargins(UiUtils.dip2px(3),0,UiUtils.dip2px(3),0);
//                    imageView.setLayoutParams(params);
//                    if(Util.isOnMainThread())
//                        Glide.with(context).load(list.get(i).getOrder().get(j).toString()).transform(new GlideRoundTransform(context)).dontAnimate().into(imageView); //图片资源
//                    holder.ll.addView(imageView);
//                }
//                break;
//
//            case 1:
//                holder.itemView.setTag(i+"");
//                break;
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//     class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView iv;
//        TextView tv,tv_has_brand;
//         LinearLayout ll;
//         TextView tv_tag;
//        public ViewHolder(View view) {
//            super(view);
//            this.iv = (ImageView) view.findViewById(R.id.iv_item_home_special);
//            this.tv = (TextView) view.findViewById(R.id.tv_item_home_special);
//            this.ll = (LinearLayout) view.findViewById(R.id.ll_home_special);
//            this.tv_has_brand = (TextView) view.findViewById(R.id.tv_item_home_special_has_brand);
//            this.tv_tag = (TextView) view.findViewById(R.id.tv_item_home_special_tag);
//        }
//    }
//
//}
