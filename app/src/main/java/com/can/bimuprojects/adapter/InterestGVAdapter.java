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
import com.can.bimuprojects.Module.Response.InterestReponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.ActivityInterest;

import java.util.List;

/**
 * Created by can on 2017/4/10.
 * 兴趣行业 右侧 适配器
 */

public class
InterestGVAdapter extends BaseAdapter {

    private List<InterestReponse.DataBean.SmallCategoriesBean> data;
    private Context context;

    public InterestGVAdapter(Context context,List<InterestReponse.DataBean.SmallCategoriesBean> data){
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH vh ;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_interest_gv,null);
            vh = new VH();
            vh.iv = (ImageView) view.findViewById(R.id.iv_item_interest_gv);
            vh.tv = (TextView) view.findViewById(R.id.tv_item_interest_gv);
            vh.iv_select = (ImageView) view.findViewById(R.id.iv_item_interest_gv_select);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        String text = data.get(i).getName().toString();
        if(!text.equals("")){
            vh.tv.setText(text);
        }
        if(Util.isOnMainThread())
        Glide.with(context).load(data.get(i).getImg_url()).centerCrop().into(vh.iv);
        boolean flag = ActivityInterest.list_boolean.get(ActivityInterest.lv_position).get(i);
        if(flag)
        vh.iv_select.setVisibility(View.VISIBLE);
        else
        vh.iv_select.setVisibility(View.GONE);
        return view;
    }

    class VH{
        ImageView iv,iv_select;
        TextView tv;
    }


}
