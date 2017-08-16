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
import com.can.bimuprojects.Module.Response.HomePagerResponse;
import com.can.bimuprojects.Module.Response.OpenShopResponse;
import com.can.bimuprojects.Module.Response.RankingResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.UiUtils;

import java.util.List;

/**
 * Created by can on 2017/4/16.
 * 品牌开店方案适配器
 */

public class RankingAdapter extends BaseAdapter{

    private Context context;
    private List<RankingResponse.BrandIndustryBean.DataBean> list;

    public RankingAdapter(Context context, List<RankingResponse.BrandIndustryBean.DataBean> list){
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final VH vh ;
        if(view==null){
            vh = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_ranking,null);
            vh.iv = (ImageView) view.findViewById(R.id.iv_brand_open_shop);
            vh.tv_title = (TextView) view.findViewById(R.id.tv_brand_open_shop_title);
            vh.tv_content = (TextView) view.findViewById(R.id.tv_brand_open_shop_content);
            vh.ll = (LinearLayout) view.findViewById(R.id.ll_item_open_shop);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        RankingResponse.BrandIndustryBean.DataBean bean = list.get(i);
        String background = bean.getBrand_logo();
        if(bean.getTag()!=null&&bean.getTag().size()>=0){
            String str_autof = bean.getTag().get(0);
            if(str_autof!=null&&!str_autof.equals("")){
                vh.ll.addView(UiUtils.getTV(context,str_autof,0));
            }
        }
        if(bean.getTag()!=null&&bean.getTag().size()>=1){
            String str_autos = bean.getTag().get(1);
            if(str_autos!=null&&!str_autos.equals("")){
                vh.ll.addView(UiUtils.getTV(context,str_autos,1));
            }
        }
        String name = bean.getBrand_name();
        String money = bean.getInvest_amount();

        if(Util.isOnMainThread()&&background!=null)
        Glide.with(context).load(background).placeholder(R.drawable.loading).dontAnimate().into(vh.iv);
        if(name!=null)
        vh.tv_title.setText(name);
        vh.ll.removeAllViews();


        if(money!=null)
            vh.tv_content.setText("投资 "+money+" 万");
        return view;
    }

    class VH{
        ImageView iv ;
        TextView tv_title;
        TextView tv_content;
        LinearLayout ll;

    }

}
