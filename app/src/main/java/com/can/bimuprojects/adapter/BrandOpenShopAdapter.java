package com.can.bimuprojects.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Module.Response.OpenShopResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.GlideRoundTransform;
import com.can.bimuprojects.utils.UiUtils;

import java.util.List;

/**
 * Created by can on 2017/4/16.
 * 品牌开店方案适配器
 */

public class BrandOpenShopAdapter extends BaseAdapter{

    private Context context;
    private List<OpenShopResponse.DataBean> list;
    private List<Boolean> list_boolean ;

    public BrandOpenShopAdapter(Context context, List<OpenShopResponse.DataBean> list,List<Boolean> list_boolean){
        this.context = context;
        this.list = list;
        this.list_boolean = list_boolean;
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
        final VH vh ;
        if(view==null){
            vh = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_brand_open_shop,null);
            vh.iv = (ImageView) view.findViewById(R.id.iv_brand_open_shop);
            vh.tv_title = (TextView) view.findViewById(R.id.tv_brand_open_shop_title);
            vh.tv_content = (TextView) view.findViewById(R.id.tv_brand_open_shop_content);
            vh.cb = (ImageView) view.findViewById(R.id.cb_brand_open_shop);
            vh.ll = (LinearLayout) view.findViewById(R.id.ll_item_open_shop);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        OpenShopResponse.DataBean bean = list.get(i);
        String background = bean.getBrand_background();
        String str_autof = bean.getAutof();
        String str_autos = bean.getAutos();
        String name = bean.getBrand_name();
        String money = bean.getInvest_amount();

        if(Util.isOnMainThread()&&background!=null)
        Glide.with(context).load(background).placeholder(R.drawable.loading).dontAnimate().into(vh.iv);
        if(name!=null)
        vh.tv_title.setText(name);
        vh.ll.removeAllViews();
        if(str_autof!=null&&!str_autof.equals("")){
            vh.ll.addView(UiUtils.getTV(context,str_autof,0));
        }
        if(str_autos!=null&&!str_autos.equals("")){
            vh.ll.addView(UiUtils.getTV(context,str_autos,1));
        }
        if(money!=null)
            vh.tv_content.setText("投资 "+money+" 万");
        vh.cb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        vh.cb.setTag(true);
        vh.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bb = (Boolean) view.getTag();
                list_boolean.set(i, !bb);
                vh.cb.setTag(!bb);
                if(!bb)
                    vh.cb.setImageResource(R.drawable.radio_button_on);
                else
                    vh.cb.setImageResource(R.drawable.radio_button_off);
            }
        });
        return view;
    }

    public List<Boolean> getCheckState(){
        return list_boolean;
    }

    class VH{
        ImageView iv ;
        TextView tv_title;
        TextView tv_content;
        ImageView cb ;
        LinearLayout ll;

    }

}
