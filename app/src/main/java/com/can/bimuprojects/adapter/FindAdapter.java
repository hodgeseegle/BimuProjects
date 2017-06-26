package com.can.bimuprojects.adapter;

import android.app.Activity;
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
import com.can.bimuprojects.Module.Response.GetFindResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.view.gallery.ImagePagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/20.
 * 发现页
 */

public class FindAdapter extends BaseAdapter {

    private Context context;
    private List<GetFindResponse.ArticledataBean> list;

    public FindAdapter(Context context,List<GetFindResponse.ArticledataBean> list){
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
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        //1表示后台文章，2表示客户端发帖
        String type = list.get(position).getType();
        if(type!=null&&type.equals("3"))
            return 1;
        else if(type!=null&&type.equals("1"))
         return 2;
        return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH vh=null;
        VH2 vh2=null;
        int type = getItemViewType(i);
        if(view==null){
            switch (type){
                case 1:
                    vh = new VH();
                    view = LayoutInflater.from(context).inflate(R.layout.item_fragment_find,null);
                    vh.tv_title = (TextView) view.findViewById(R.id.tv_find_title);
                    vh.tv_content = (TextView) view.findViewById(R.id.tv_find_content);
                    vh.tv_look = (TextView) view.findViewById(R.id.tv_find_look);
                    vh.tv_love = (TextView) view.findViewById(R.id.tv_find_love);
                    vh.tv_comment = (TextView) view.findViewById(R.id.tv_find_comment);
                    vh.iv1 = (ImageView) view.findViewById(R.id.iv_find_iv1);
                    vh.iv2 = (ImageView) view.findViewById(R.id.iv_find_iv2);
                    vh.iv3 = (ImageView) view.findViewById(R.id.iv_find_iv3);
                    view.setTag(vh);
                    break;
                case 2:
                    vh2 = new VH2();
                    view = LayoutInflater.from(context).inflate(R.layout.item_fragment_find2,null);
                    vh2.tv_name = (TextView) view.findViewById(R.id.tv_item_brand_assess_name);
                    vh2.tv_date = (TextView) view.findViewById(R.id.tv_item_brand_assess_date);
                    vh2.tv_content = (TextView) view.findViewById(R.id.tv_item_brand_assess_content);
                    vh2.iv_logo = (ImageView) view.findViewById(R.id.iv_item_brand_assess_logo);
                    vh2.tv_look = (TextView) view.findViewById(R.id.tv_find2_look);
                    vh2.tv_love = (TextView) view.findViewById(R.id.tv_find2_love);
                    vh2.tv_comment = (TextView) view.findViewById(R.id.tv_find2_comment);
                    vh2.iv1 = (ImageView) view.findViewById(R.id.iv_find2_iv1);
                    vh2.iv2 = (ImageView) view.findViewById(R.id.iv_find2_iv2);
                    vh2.iv3 = (ImageView) view.findViewById(R.id.iv_find2_iv3);
                    view.setTag(vh2);
                    break;
            }
        }else{
            switch (type){
                case 1:
                    vh = (VH) view.getTag();
                    break;
                case 2:
                    vh2 = (VH2) view.getTag();
                    break;
            }
        }
        switch (type){
            case 1:
                GetFindResponse.ArticledataBean data = list.get(i);
                if(data==null)
                    break;
                final List<String> logo_list1 = data.getArticle();
                if(data.getArticle_summary()!=null)
                vh.tv_title.setText(data.getArticle_summary());
                if(data.getArticle_html()!=null)
                vh.tv_content.setText(data.getArticle_html());
                if(data.getLook_time()!=null)
                vh.tv_look.setText(data.getLook_time());
                if(data.getArticle_thumbs()!=null)
                vh.tv_love.setText(data.getArticle_thumbs());
                if(data.getArticle_comment_number()!=null)
                vh.tv_comment.setText(data.getArticle_comment_number());
                if(logo_list1!=null){
                    if(data.getArticle().size()>=3&&(Util.isOnMainThread())){
                        Glide.with(context).load(data.getArticle().get(0)).dontAnimate().into(vh.iv1);
                        Glide.with(context).load(data.getArticle().get(1)).dontAnimate().into(vh.iv2);
                        Glide.with(context).load(data.getArticle().get(2)).dontAnimate().into(vh.iv3);
                        vh.iv1.setVisibility(View.VISIBLE);
                        vh.iv2.setVisibility(View.VISIBLE);
                        vh.iv3.setVisibility(View.VISIBLE);
                    }else if(data.getArticle().size()==2&&(Util.isOnMainThread())){
                        Glide.with(context).load(data.getArticle().get(0)).dontAnimate().into(vh.iv1);
                        Glide.with(context).load(data.getArticle().get(1)).dontAnimate().into(vh.iv2);
                        vh.iv1.setVisibility(View.VISIBLE);
                        vh.iv2.setVisibility(View.VISIBLE);
                        vh.iv3.setVisibility(View.INVISIBLE);
                    }else if(data.getArticle().size()==1&&(Util.isOnMainThread())){
                        Glide.with(context).load(data.getArticle().get(0)).dontAnimate().into(vh.iv1);
                        vh.iv1.setVisibility(View.VISIBLE);
                        vh.iv2.setVisibility(View.INVISIBLE);
                        vh.iv3.setVisibility(View.INVISIBLE);
                    }else{
                        vh.iv1.setVisibility(View.GONE);
                        vh.iv2.setVisibility(View.GONE);
                        vh.iv3.setVisibility(View.GONE);
                    }
                    vh.iv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, ImagePagerActivity.class);
                            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                            intent.putStringArrayListExtra("list", (ArrayList<String>) logo_list1);
                            context.startActivity(intent);
                            ((Activity)context).overridePendingTransition(0, android.R.anim.fade_out);
                        }
                    });
                    vh.iv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, ImagePagerActivity.class);
                            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 1);
                            intent.putStringArrayListExtra("list", (ArrayList<String>) logo_list1);
                            context.startActivity(intent);
                            ((Activity)context).overridePendingTransition(0, android.R.anim.fade_out);
                        }
                    });
                    vh.iv3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, ImagePagerActivity.class);
                            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 2);
                            intent.putStringArrayListExtra("list", (ArrayList<String>) logo_list1);
                            context.startActivity(intent);
                            ((Activity)context).overridePendingTransition(0, android.R.anim.fade_out);
                        }
                    });
                }
                break;
            case 2:
                GetFindResponse.ArticledataBean data2 = list.get(i);
                if(data2==null)
                    break;
                final List<String> logo_list = data2.getArticle();
                if(data2.getArticle_html()!=null)
                    vh2.tv_content.setText(data2.getArticle_html());
                if(data2.getLook_time()!=null)
                    vh2.tv_look.setText(data2.getLook_time());
                if(data2.getArticle_thumbs()!=null)
                    vh2.tv_love.setText(data2.getArticle_thumbs());
                if(data2.getArticle_comment_number()!=null)
                    vh2.tv_comment.setText(data2.getArticle_comment_number());
                if(logo_list!=null){
                    if(data2.getArticle().size()>=3&&(Util.isOnMainThread())){
                        Glide.with(context).load(data2.getArticle().get(0)).dontAnimate().into(vh2.iv1);
                        Glide.with(context).load(data2.getArticle().get(1)).dontAnimate().into(vh2.iv2);
                        Glide.with(context).load(data2.getArticle().get(2)).dontAnimate().into(vh2.iv3);
                        vh2.iv1.setVisibility(View.VISIBLE);
                        vh2.iv2.setVisibility(View.VISIBLE);
                        vh2.iv3.setVisibility(View.VISIBLE);
                    }else if(data2.getArticle().size()==2&&(Util.isOnMainThread())){
                        Glide.with(context).load(data2.getArticle().get(0)).dontAnimate().into(vh2.iv1);
                        Glide.with(context).load(data2.getArticle().get(1)).dontAnimate().into(vh2.iv2);
                        vh2.iv1.setVisibility(View.VISIBLE);
                        vh2.iv2.setVisibility(View.VISIBLE);
                        vh2.iv3.setVisibility(View.INVISIBLE);
                    }else if(data2.getArticle().size()==1&&(Util.isOnMainThread())){
                        Glide.with(context).load(data2.getArticle().get(0)).dontAnimate().into(vh2.iv1);
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
                }
                break;
        }


        return view;
    }

    static class VH{
        TextView tv_title;
        TextView tv_content;
        TextView tv_look;
        TextView tv_love;
        TextView tv_comment;
        ImageView iv1,iv2,iv3;
    }

    static class VH2{
        TextView tv_name,tv_date,tv_content;
        ImageView iv_logo;
        TextView tv_look;
        TextView tv_love;
        TextView tv_comment;
        ImageView iv1,iv2,iv3;
    }
}
