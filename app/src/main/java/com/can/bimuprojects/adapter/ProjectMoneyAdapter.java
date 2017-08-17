package com.can.bimuprojects.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.can.bimuprojects.R;

/**
 * Created by can on 2017/08/17.
 * 项目库金额适配器
 */

public class ProjectMoneyAdapter extends BaseAdapter {

    private int clickTemp = -1;
    //标识选择的Item
    public void setSeclection(int position) {
        clickTemp = position;
    }

    private Context context;
    private String[] strings;
    public ProjectMoneyAdapter(Context context,String[] strings){
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int i) {
        return strings[i];
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
            view = LayoutInflater.from(context).inflate(R.layout.item_dialog_money,null);
            vh.ll = (LinearLayout) view.findViewById(R.id.ll_item);
            vh.tv = (TextView) view.findViewById(R.id.tv_item);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        String string = strings[i];
        if(string!=null)
            vh.tv.setText(string);
        if(clickTemp==i){
            vh.ll.setBackgroundResource(R.drawable.shape_money_choose);
            vh.tv.setTextColor(ContextCompat.getColor(context,R.color.color_blue));
        }else{
            vh.ll.setBackgroundResource(R.drawable.shape_money_default);
            vh.tv.setTextColor(ContextCompat.getColor(context,R.color.color_small_text));
        }
        return view;
    }

    static class VH{
        LinearLayout ll;
        TextView tv;
    }

}
