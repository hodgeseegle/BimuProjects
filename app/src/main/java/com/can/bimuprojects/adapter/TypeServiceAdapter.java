package com.can.bimuprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.can.bimuprojects.R;

import java.util.List;

/**
 * Created by can on 2017/4/19.
 * 淘汰原因适配器
 */

public class TypeServiceAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public TypeServiceAdapter(Context context,List<String> list){
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        VH vh;
        if(view==null){
            vh = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_type_service,null);
            vh.tv = (TextView) view.findViewById(R.id.tv_item_type_service);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        vh.tv.setText(list.get(i));
        return view;
    }

    class VH{
        TextView tv;
    }

}
