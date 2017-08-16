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
import com.can.bimuprojects.Module.Response.ChoiceRecommendResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.GlideRoundTransform;

import java.util.List;

/**
 * Created by can on 2017/08/15.
 * 精选推荐适配器
 */
public class ChoiceRecommendAdapter extends BaseAdapter {

    private List<ChoiceRecommendResponse.BrandLabelBean> list ;
    private Context context;

    public ChoiceRecommendAdapter(Context context,List<ChoiceRecommendResponse.BrandLabelBean> list){
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
        VH vh ;
        if(view==null){
            view  = LayoutInflater.from(context).inflate(R.layout.item_choice_recommend,null);
            vh = new VH();
            vh.tv_name = (TextView) view.findViewById(R.id.tv_item);
            vh.iv_logo = (ImageView) view.findViewById(R.id.iv_item);
            view.setTag(vh);
        }else {
            vh = (VH) view.getTag();
        }
        ChoiceRecommendResponse.BrandLabelBean bean = list.get(i);
        String name = bean.getName();
        String logo = bean.getLabel_img();
        if(name!=null)
            vh.tv_name.setText(name);
        if(logo!=null&& Util.isOnMainThread()){
            Glide.with(context).load(logo).placeholder(R.drawable.loading).dontAnimate().transform(new GlideRoundTransform(context)).into(vh.iv_logo);
        }
        return view;
    }

    static class VH{
        ImageView iv_logo;
        TextView tv_name;
    }

}
