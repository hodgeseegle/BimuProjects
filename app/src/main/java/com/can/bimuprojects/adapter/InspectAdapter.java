package com.can.bimuprojects.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Module.Response.BookServiceResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.JoinProcessActivity;
import com.can.bimuprojects.utils.GlideRoundTransform;
import com.can.bimuprojects.utils.GlideUtil;

import java.util.List;

/**
 * Created by can on 2017/4/19.
 * 预约适配器
 */

public class InspectAdapter extends BaseAdapter {

    private Context context;
    private List<BookServiceResponse.ServiceBean> list;

    public InspectAdapter(List<BookServiceResponse.ServiceBean> list,Context context){
        this.context =context;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final VH vh;
        if(view==null){
            vh = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_inspect,null);
            vh.iv = (ImageView) view.findViewById(R.id.iv_item_inspect);
            vh.ll = (LinearLayout) view.findViewById(R.id.ll_inspect_contents);
            vh.ws = (ImageView) view.findViewById(R.id.ws_item_inspect);
            vh.tv = (TextView) view.findViewById(R.id.tv_item_inspect_detail_say);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        vh.ll.removeAllViews();
        BookServiceResponse.ServiceBean data =  list.get(i);
        if(Util.isOnMainThread())
            Glide.with(context).load(data.getLogo()).transform(new GlideRoundTransform(context)).into(vh.iv);
        vh.ll.addView(addTextView(16,10,data.getTitle(), Color.RED));
        for(int j =0;j<data.getTip().size();j++){
            vh.ll.addView(addTextView(12,0,data.getTip().get(j),Color.BLACK));
        }
        if(data.getChoose().equals("true")){
            GlideUtil.loadDrawableImg(context,R.drawable.radio_button_on,vh.ws);
            vh.tv.setTag(true);
        }else{
            GlideUtil.loadDrawableImg(context,R.drawable.radio_button_off,vh.ws);
            vh.tv.setTag(false);
        }
        vh.ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bb = (Boolean) vh.tv.getTag();
                listener.onChangeState(i,!bb);
                vh.tv.setTag(!bb);
                if(!bb)
                    GlideUtil.loadDrawableImg(context,R.drawable.radio_button_on,vh.ws);
                else
                    GlideUtil.loadDrawableImg(context,R.drawable.radio_button_off,vh.ws);
            }
        });

        vh.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, JoinProcessActivity.class);
                context.startActivity(intent);
            }
        });
        return view;
    }

    public interface onChangeStateListener{
        void onChangeState(int i,boolean flag);
    }

    private onChangeStateListener listener;

    public void setOnChangeStateListener(onChangeStateListener listener){
        this.listener = listener;
    }


    class VH{
        TextView tv;
        ImageView iv;
        LinearLayout ll;
        ImageView ws;
    }

    //添加textview
    private TextView addTextView(float size,int mar,String text,int color){
        TextView tv = new TextView(context);
        tv.setText(text);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = mar;
        tv.setLayoutParams(params);
        tv.setTextSize(size);
        tv.setTextColor(color);
        return  tv;
    }
}
