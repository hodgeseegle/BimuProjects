package com.can.bimuprojects.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.can.bimuprojects.Module.Response.HomePagerResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.BrandActivity;
import com.can.bimuprojects.utils.GlideRoundTransform;
import com.can.bimuprojects.utils.UiUtils;
import com.can.bimuprojects.view.gallery.ImagePagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/13.
 * 精选考察攻略适配器
 */

public class InspectionAdapter extends BaseAdapter {

    private Context context;
    private List<HomePagerResponse.InspectionlogBean> list;

    public InspectionAdapter(Context context,List<HomePagerResponse.InspectionlogBean> list){
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
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        String html = list.get(position).getArticle_html();
        String type = list.get(position).getType();
        int index = 0;
        if(type.equals("4")||type.equals("3")||type.equals("1")){ //后台文章或文章列表
            if(html==null||html.equals("")){ //大图
                index = 1;
            }else{ //图文
                index=  0;
            }
        }else if(type.equals("2")){ //客户端发帖
            index = 2;
        }
        return index;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH vh = null;
        VH2 vh2 =null;
        VH3 vh3 = null;
        int type = getItemViewType(i);
        if(view==null){
            switch (type){
                case 0:
                    view = LayoutInflater.from(context).inflate(R.layout.item_inspection,viewGroup,false);
                    vh = new VH();
                    vh.tv_title = (TextView) view.findViewById(R.id.tv_item_inspection_title);
                    vh.tv_content = (TextView) view.findViewById(R.id.tv_item_inspection_content);
                    vh.iv_img = (ImageView) view.findViewById(R.id.iv_item_inspecial_img);
                    vh.ll = (LinearLayout) view.findViewById(R.id.ll_item_inspection);
                    vh.tv_reducible_brands = (TextView) view.findViewById(R.id.tv_item_inspection_reducible_brands);
                    vh.tv_look = (TextView) view.findViewById(R.id.tv_item_inspection_look);
                    vh.tv_comment = (TextView) view.findViewById(R.id.tv_item_inspection_comment);
                    vh.tv_content2 = (TextView) view.findViewById(R.id.tv_item_inspection_content2);
                    vh.ll_content = (LinearLayout) view.findViewById(R.id.ll_item_inspection_content);
                    view.setTag(vh);
                    break;
                case 1:
                    view = LayoutInflater.from(context).inflate(R.layout.item_inspection2,viewGroup,false);
                    vh2 = new VH2();
                    vh2.tv_title = (TextView) view.findViewById(R.id.tv_item_inspection_title);
                    vh2.iv_img = (ImageView) view.findViewById(R.id.iv_item_inspecial_img);
                    vh2.ll = (LinearLayout) view.findViewById(R.id.ll_item_inspection);
                    vh2.tv_reducible_brands = (TextView) view.findViewById(R.id.tv_item_inspection_reducible_brands);
                    vh2.tv_look = (TextView) view.findViewById(R.id.tv_item_inspection_look);
                    vh2.tv_comment = (TextView) view.findViewById(R.id.tv_item_inspection_comment);
                    vh2.tv_content2 = (TextView) view.findViewById(R.id.tv_item_inspection_content2);
                    vh2.ll_content = (LinearLayout) view.findViewById(R.id.ll_item_inspection_content);
                    view.setTag(vh2);
                    break;
                case 2:
                    view = LayoutInflater.from(context).inflate(R.layout.item_inspection3,viewGroup,false);
                    vh3 = new VH3();
                    vh3.tv_content = (TextView) view.findViewById(R.id.tv_item_brand_assess_content);
                    vh3.iv1 = (ImageView) view.findViewById(R.id.iv_find_iv1);
                    vh3.iv2 = (ImageView) view.findViewById(R.id.iv_find_iv2);
                    vh3.iv3 = (ImageView) view.findViewById(R.id.iv_find_iv3);

                    vh3.ll = (LinearLayout) view.findViewById(R.id.ll_item_inspection);
                    vh3.tv_reducible_brands = (TextView) view.findViewById(R.id.tv_item_inspection_reducible_brands);
                    vh3.tv_look = (TextView) view.findViewById(R.id.tv_item_inspection_look);
                    vh3.tv_comment = (TextView) view.findViewById(R.id.tv_item_inspection_comment);
                    view.setTag(vh3);
                    break;
            }
        }else{
            switch (type){
                case 0:
                    vh = (VH) view.getTag();
                    break;
                case 1:
                    vh2 = (VH2) view.getTag();
                    break;
                case 2:
                    vh3 = (VH3) view.getTag();
                    break;
            }
        }

        switch (type){
            case 0:
                vh.tv_title.setText(list.get(i).getArticle_title()+"");
                vh.tv_content.setText(list.get(i).getArticle_html()+"");
                vh.tv_content2.setText(list.get(i).getArticle_html()+"");
                if(list.get(i).getArticle_logo()==null||list.get(i).getArticle_logo().size()==0){
                    vh.tv_content2.setVisibility(View.VISIBLE);
                    vh.ll_content.setVisibility(View.GONE);
                }else{
                    vh.ll_content.setVisibility(View.VISIBLE);
                    vh.tv_content2.setVisibility(View.GONE);
                    if(Util.isOnMainThread()&&list.get(i).getArticle_logo()!=null&&list.get(i).getArticle_logo().size()>0)
                        Glide.with(context).load(list.get(i).getArticle_logo().get(0)).placeholder(R.drawable.loading).dontAnimate().into(vh.iv_img);
                }
                vh.ll.removeAllViews();
                List<HomePagerResponse.InspectionlogBean.OrderBean> order = list.get(i).getOrder();
                if(order!=null)
                    for(int j=0;j<order.size();j++){
                        final HomePagerResponse.InspectionlogBean.OrderBean bean = order.get(j);

                        ImageView imageView = new ImageView(context);
                        DisplayMetrics metrics = new DisplayMetrics();
                        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiUtils.dip2px(28),UiUtils.dip2px(28));
                        params.setMargins(UiUtils.dip2px(4),0,UiUtils.dip2px(4),0);
                        imageView.setLayoutParams(params);
                        if(Util.isOnMainThread()&&bean.getArtimg()!=null)
                            Glide.with(context).load(bean.getArtimg()).transform(new GlideRoundTransform(context)).into(imageView); //图片资源
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(bean.getBid()!=null&&!bean.getBid().equals("")){
                                    Intent intent = new Intent(context, BrandActivity.class);
                                    intent.putExtra("index",bean.getBid());
                                    context.startActivity(intent);
                                }
                            }
                        });
                        vh.ll.addView(imageView);
                    }
                if(order==null||order.size()==0){
                    vh.tv_reducible_brands.setVisibility(View.GONE);
                }else{
                    vh.tv_reducible_brands.setVisibility(View.VISIBLE);
                }
                vh.tv_look.setText(list.get(i).getLook_time()+"");
                vh.tv_comment.setText(list.get(i).getArticle_comment_number()+"");
                break;

            case 1:
                vh2.tv_title.setText(list.get(i).getArticle_title()+"");
                vh2.tv_content2.setText(list.get(i).getArticle_html()+"");
                if(list.get(i).getArticle_logo()==null||list.get(i).getArticle_logo().size()==0){
                    vh2.tv_content2.setVisibility(View.VISIBLE);
                    vh2.ll_content.setVisibility(View.GONE);
                }else{
                    vh2.ll_content.setVisibility(View.VISIBLE);
                    vh2.tv_content2.setVisibility(View.GONE);
                    if(Util.isOnMainThread()&&list.get(i).getArticle_logo()!=null&&list.get(i).getArticle_logo().size()>0)
                        Glide.with(context).load(list.get(i).getArticle_logo().get(0)).placeholder(R.drawable.loading).dontAnimate().into(vh2.iv_img);
                }
                vh2.ll.removeAllViews();
                List<HomePagerResponse.InspectionlogBean.OrderBean> order2 = list.get(i).getOrder();
                if(order2!=null)
                    for(int j=0;j<order2.size();j++){
                        final HomePagerResponse.InspectionlogBean.OrderBean bean = order2.get(j);

                        ImageView imageView = new ImageView(context);
                        DisplayMetrics metrics = new DisplayMetrics();
                        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiUtils.dip2px(28),UiUtils.dip2px(28));
                        params.setMargins(UiUtils.dip2px(4),0,UiUtils.dip2px(4),0);
                        imageView.setLayoutParams(params);
                        if(Util.isOnMainThread()&&bean.getArtimg()!=null)
                            Glide.with(context).load(bean.getArtimg()).transform(new GlideRoundTransform(context)).into(imageView); //图片资源
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(bean.getBid()!=null&&!bean.getBid().equals("")){
                                    Intent intent = new Intent(context, BrandActivity.class);
                                    intent.putExtra("index",bean.getBid());
                                    context.startActivity(intent);
                                }
                            }
                        });
                        vh2.ll.addView(imageView);
                    }
                if(order2==null||order2.size()==0){
                    vh2.tv_reducible_brands.setVisibility(View.GONE);
                }else{
                    vh2.tv_reducible_brands.setVisibility(View.VISIBLE);
                }
                vh2.tv_look.setText(list.get(i).getLook_time()+"");
                vh2.tv_comment.setText(list.get(i).getArticle_comment_number()+"");
                break;

            case 2:
                final List<String> logo_list = list.get(i).getArticle_logo();
                if(list.get(i).getArticle_html()!=null)
                    vh3.tv_content.setText(list.get(i).getArticle_html());
                if(logo_list!=null)
                    if(logo_list.size()>=3&&(Util.isOnMainThread())){
                        Glide.with(context).load(logo_list.get(0)).placeholder(R.drawable.loading).dontAnimate().into(vh3.iv1);
                        Glide.with(context).load(logo_list.get(1)).placeholder(R.drawable.loading).dontAnimate().into(vh3.iv2);
                        Glide.with(context).load(logo_list.get(2)).placeholder(R.drawable.loading).dontAnimate().into(vh3.iv3);
                        vh3.iv1.setVisibility(View.VISIBLE);
                        vh3.iv2.setVisibility(View.VISIBLE);
                        vh3.iv3.setVisibility(View.VISIBLE);
                    }else if(logo_list.size()==2&&(Util.isOnMainThread())){
                        Glide.with(context).load(logo_list.get(0)).placeholder(R.drawable.loading).dontAnimate().into(vh3.iv1);
                        Glide.with(context).load(logo_list.get(1)).placeholder(R.drawable.loading).dontAnimate().into(vh3.iv2);
                        vh3.iv1.setVisibility(View.VISIBLE);
                        vh3.iv2.setVisibility(View.VISIBLE);
                        vh3.iv3.setVisibility(View.INVISIBLE);
                    }else if(logo_list.size()==1&&(Util.isOnMainThread())){
                        Glide.with(context).load(logo_list.get(0)).placeholder(R.drawable.loading).dontAnimate().into(vh3.iv1);
                        vh3.iv1.setVisibility(View.VISIBLE);
                        vh3.iv2.setVisibility(View.INVISIBLE);
                        vh3.iv3.setVisibility(View.INVISIBLE);
                    }else{
                        vh3.iv1.setVisibility(View.GONE);
                        vh3.iv2.setVisibility(View.GONE);
                        vh3.iv3.setVisibility(View.GONE);
                    }
                vh3.iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                        intent.putStringArrayListExtra("list", (ArrayList<String>) logo_list);
                        context.startActivity(intent);
                        ((Activity)context).overridePendingTransition(0, android.R.anim.fade_out);
                    }
                });
                vh3.iv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 1);
                        intent.putStringArrayListExtra("list", (ArrayList<String>) logo_list);
                        context.startActivity(intent);
                        ((Activity)context).overridePendingTransition(0, android.R.anim.fade_out);
                    }
                });
                vh3.iv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 2);
                        intent.putStringArrayListExtra("list", (ArrayList<String>) logo_list);
                        context.startActivity(intent);
                        ((Activity)context).overridePendingTransition(0, android.R.anim.fade_out);
                    }
                });

                vh3.ll.removeAllViews();
                List<HomePagerResponse.InspectionlogBean.OrderBean> order3 = list.get(i).getOrder();
                if(order3!=null)
                    for(int j=0;j<order3.size();j++){
                        final HomePagerResponse.InspectionlogBean.OrderBean bean = order3.get(j);

                        ImageView imageView = new ImageView(context);
                        DisplayMetrics metrics = new DisplayMetrics();
                        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiUtils.dip2px(28),UiUtils.dip2px(28));
                        params.setMargins(UiUtils.dip2px(4),0,UiUtils.dip2px(4),0);
                        imageView.setLayoutParams(params);
                        if(Util.isOnMainThread()&&bean.getArtimg()!=null)
                            Glide.with(context).load(bean.getArtimg()).transform(new GlideRoundTransform(context)).into(imageView); //图片资源
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(bean.getBid()!=null&&!bean.getBid().equals("")){
                                    Intent intent = new Intent(context, BrandActivity.class);
                                    intent.putExtra("index",bean.getBid());
                                    context.startActivity(intent);
                                }
                            }
                        });
                        vh3.ll.addView(imageView);
                    }
                if(order3==null||order3.size()==0){
                    vh3.tv_reducible_brands.setVisibility(View.GONE);
                }else{
                    vh3.tv_reducible_brands.setVisibility(View.VISIBLE);
                }
                vh3.tv_look.setText(list.get(i).getLook_time()+"");
                vh3.tv_comment.setText(list.get(i).getArticle_comment_number()+"");
                break;
        }
        return view;
    }

    //图文
    static class VH{
        TextView tv_title;
        TextView tv_content;
        ImageView iv_img;
        TextView tv_reducible_brands;
        LinearLayout ll,ll_content ;
        TextView tv_look;
        TextView tv_comment;
        TextView tv_content2;
    }

    //大图
    static class VH2{
        TextView tv_title;
        ImageView iv_img;
        TextView tv_reducible_brands;
        LinearLayout ll,ll_content ;
        TextView tv_look;
        TextView tv_comment;
        TextView tv_content2;
    }

    //发帖模式
    static class VH3{
        TextView tv_content;
        ImageView iv1,iv2,iv3;
        TextView tv_look;
        TextView tv_comment;
        LinearLayout ll;
        TextView tv_reducible_brands;
    }

}