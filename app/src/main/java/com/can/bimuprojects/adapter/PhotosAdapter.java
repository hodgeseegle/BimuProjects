package com.can.bimuprojects.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.R;
import com.can.bimuprojects.view.gallery.ImagePagerActivity;

import java.util.ArrayList;

/**
 * Created by can on 2017/6/13.
 * 图库分类选择适配器
 */

public class PhotosAdapter extends BaseAdapter {

    private ArrayList<String> list;
    private Context context;
    private String bid;
    private boolean consult;
    private String str_logo;
    private String name;

    public PhotosAdapter(Context context,String bid,boolean consult ,ArrayList<String> list,String str_logo,String name){
        this.list = list;
        this.context = context;
        this.bid = bid;
        this.consult = consult;
        this.str_logo = str_logo;
        this.name = name;
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
        VH vh ;
        if(view==null){
            vh = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_photos,null);
            vh.iv = (ImageView) view.findViewById(R.id.iv_item_photos);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        String string = list.get(i);
        if(string!=null&& Util.isOnMainThread()){
            Glide.with(context).load(string).placeholder(R.drawable.loading).error(R.drawable.loading).dontAnimate().into(vh.iv);
            vh.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ImagePagerActivity.class);
                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, i);
                    intent.putStringArrayListExtra("list",  list);
                    intent.putExtra("brand",true);
                    intent.putExtra("bid",bid);
                    intent.putExtra("consult",consult);
                    intent.putExtra("logo",str_logo);
                    intent.putExtra("name",name);
                    context.startActivity(intent);
                    ((Activity)context).overridePendingTransition(0, android.R.anim.fade_out);
                }
            });
        }

        return view;
    }

    class VH{
        ImageView iv;
    }

    public void setData(boolean consult){
        this.consult = consult;
    }

}