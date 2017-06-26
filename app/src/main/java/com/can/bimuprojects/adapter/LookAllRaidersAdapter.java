package com.can.bimuprojects.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Module.Response.GetLookAllRaidersResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.DateUtils;
import com.can.bimuprojects.utils.GlideRoundTransform;
import com.can.bimuprojects.view.gallery.ImagePagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/24.
 * 查看全部攻略适配器
 */

public class LookAllRaidersAdapter extends BaseAdapter {

    private Context context;
    private List<GetLookAllRaidersResponse.ArticlesBean> list;

    public LookAllRaidersAdapter(Context context,List<GetLookAllRaidersResponse.ArticlesBean> list){
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
    public int getItemViewType(int position) {
        String type  = list.get(position).getType();
        if(type.equals("2"))
            return 1;
        else
            return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH vh =null;
        VH2 vh2 =null;
        int type = getItemViewType(i);
        if(view==null){
            switch (type){
                case 0:
                    view = LayoutInflater.from(context).inflate(R.layout.item_brand_comment2,viewGroup,false);
                    vh = new VH();
                    vh.tv_title = (TextView) view.findViewById(R.id.tv_item_inspection_title);
                    vh.tv_content = (TextView) view.findViewById(R.id.tv_item_inspection_content);
                    vh.iv_img = (ImageView) view.findViewById(R.id.iv_item_inspecial_img);
                    vh.ll = (LinearLayout) view.findViewById(R.id.ll_item_inspection_content);
                    vh.tv_reducible_brands = (TextView) view.findViewById(R.id.tv_item_inspection_reducible_brands);
                    vh.tv_look = (TextView) view.findViewById(R.id.tv_item_inspection_look);
                    vh.tv_comment = (TextView) view.findViewById(R.id.tv_item_inspection_comment);
                    vh.tv_content2 = (TextView) view.findViewById(R.id.tv_item_inspection_content2);
                    vh.iv_content = (ImageView) view.findViewById(R.id.iv_item_brand_comment2);
                    view.setTag(vh);
                    break;
                case 1:
                    view = LayoutInflater.from(context).inflate(R.layout.item_brand_comment,viewGroup,false);
                    vh2 = new VH2();
                    vh2.tv_name = (TextView) view.findViewById(R.id.tv_item_brand_assess_name);
                    vh2.tv_date = (TextView) view.findViewById(R.id.tv_item_brand_assess_date);
                    vh2.tv_content = (TextView) view.findViewById(R.id.tv_item_brand_assess_content);
                    vh2.iv_logo = (ImageView) view.findViewById(R.id.iv_item_brand_assess_logo);
                    vh2.iv1 = (ImageView) view.findViewById(R.id.iv_find_iv1);
                    vh2.iv2 = (ImageView) view.findViewById(R.id.iv_find_iv2);
                    vh2.iv3 = (ImageView) view.findViewById(R.id.iv_find_iv3);
                    view.setTag(vh2);
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
            }
        }

        switch (type){
            case 0:
                if(list.get(i).getArticle_summary()!=null)
                    vh.tv_title.setText(list.get(i).getArticle_summary());
                if(list.get(i).getArticle_html()!=null)
                    vh.tv_content.setText(list.get(i).getArticle_html());
                if(list.get(i).getArticle_html()!=null)
                    vh.tv_content2.setText(list.get(i).getArticle_html());
                if(list.get(i).getArticle_logo()==null||list.get(i).getArticle_logo().size()==0){
                    vh.tv_content2.setVisibility(View.VISIBLE);
                    vh.ll.setVisibility(View.GONE);
                }else{
                    vh.tv_content2.setVisibility(View.GONE);
                    vh.ll.setVisibility(View.VISIBLE);
                    if(Util.isOnMainThread())
                        Glide.with(context).load(list.get(i).getArticle_logo().get(0)).centerCrop().into(vh.iv_img);
                }
                vh.tv_look.setText(list.get(i).getLook_time()+"");
                vh.tv_comment.setText(list.get(i).getArticle_comment_number()+"");
                vh.tv_reducible_brands.setText(list.get(i).getUserName()+"");
                if(Util.isOnMainThread()&&list.get(i).getUserImage()!=null)
                    Glide.with(context).load(list.get(i).getUserImage()).transform(new GlideRoundTransform(context)).placeholder(R.drawable.default_logo).dontAnimate().into(vh.iv_content);

                break;

            case 1:
                final List<String> logo_list = list.get(i).getArticle_logo();
                if(list.get(i).getUserName()!=null)
                    vh2.tv_name.setText(list.get(i).getUserName());
                if(list.get(i).getArticle_html()!=null)
                    vh2.tv_content.setText(list.get(i).getArticle_html());
                if(list.get(i).getShowtime()!=null){
                    String date =  list.get(i).getShowtime();
                    vh2.tv_date.setText(DateUtils.timestampToString(date));
                }
                if(Util.isOnMainThread()&&list.get(i).getUserImage()!=null)
                    Glide.with(context).load(list.get(i).getUserImage()).transform(new GlideRoundTransform(context)).placeholder(R.drawable.default_logo).dontAnimate().into(vh2.iv_logo);
                if(logo_list!=null)
                    if(logo_list.size()>=3&&(Util.isOnMainThread())){
                        Glide.with(context).load(logo_list.get(0)).placeholder(R.drawable.loading).centerCrop().into(vh2.iv1);
                        Glide.with(context).load(logo_list.get(1)).placeholder(R.drawable.loading).centerCrop().into(vh2.iv2);
                        Glide.with(context).load(logo_list.get(2)).placeholder(R.drawable.loading).centerCrop().into(vh2.iv3);
                        vh2.iv1.setVisibility(View.VISIBLE);
                        vh2.iv2.setVisibility(View.VISIBLE);
                        vh2.iv3.setVisibility(View.VISIBLE);
                    }else if(logo_list.size()==2&&(Util.isOnMainThread())){
                        Glide.with(context).load(logo_list.get(0)).placeholder(R.drawable.loading).centerCrop().into(vh2.iv1);
                        Glide.with(context).load(logo_list.get(1)).placeholder(R.drawable.loading).centerCrop().into(vh2.iv2);
                        vh2.iv1.setVisibility(View.VISIBLE);
                        vh2.iv2.setVisibility(View.VISIBLE);
                        vh2.iv3.setVisibility(View.INVISIBLE);
                    }else if(logo_list.size()==1&&(Util.isOnMainThread())){
                        Glide.with(context).load(logo_list.get(0)).placeholder(R.drawable.loading).centerCrop().into(vh2.iv1);
                        vh2.iv1.setVisibility(View.VISIBLE);
                        vh2.iv2.setVisibility(View.INVISIBLE);
                        vh2.iv3.setVisibility(View.INVISIBLE);
                    }else{
                        vh2.iv1.setVisibility(View.GONE);
                        vh2.iv2.setVisibility(View.GONE);
                        vh2.iv3.setVisibility(View.GONE);
                    }
                vh2.iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                        intent.putStringArrayListExtra("list", (ArrayList<String>) logo_list);
                        context.startActivity(intent);
                        ((Activity)context).overridePendingTransition(0, android.R.anim.fade_out);
                    }
                });
                vh2.iv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 1);
                        intent.putStringArrayListExtra("list", (ArrayList<String>) logo_list);
                        context.startActivity(intent);
                        ((Activity)context).overridePendingTransition(0, android.R.anim.fade_out);
                    }
                });
                vh2.iv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 2);
                        intent.putStringArrayListExtra("list", (ArrayList<String>) logo_list);
                        context.startActivity(intent);
                        ((Activity)context).overridePendingTransition(0, android.R.anim.fade_out);
                    }
                });
                break;
        }
        return view;
    }

    class VH{
        TextView tv_title;
        TextView tv_content;
        ImageView iv_img;
        TextView tv_reducible_brands;
        LinearLayout ll ;
        ImageView iv_content;
        TextView tv_look;
        TextView tv_comment;
        TextView tv_content2;
    }

    class VH2{
        TextView tv_name,tv_date,tv_content;
        ImageView iv_logo;
        ImageView iv1,iv2,iv3;
    }


}
