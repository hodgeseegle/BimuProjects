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
import com.can.bimuprojects.Module.Response.ChoiceRecommendResponse;
import com.can.bimuprojects.Module.Response.HomePagerResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.UiUtils;

import java.util.List;

/**
 * Created by can on 2017/7/6.
 * 热门品牌适配器
 */

public class BrandListAdapter extends BaseAdapter {

    private List<ChoiceRecommendResponse.BrandLabelBean.DataBean> list;
    private Context context;

    public BrandListAdapter(Context context, List<ChoiceRecommendResponse.BrandLabelBean.DataBean> list){
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_hot_brand, null);
            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.iv_item_search_result_biglogo);
            viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_home_tv_title);
            viewHolder.tv_area = (TextView) convertView.findViewById(R.id.tv_home_tv_area);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_home_tv_money);
            viewHolder.tv_number = (TextView) convertView.findViewById(R.id.tv_home_tv_number);
            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.ll_item_home);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChoiceRecommendResponse.BrandLabelBean.DataBean bean = list.get(position);
        String logo = bean.getBrand_background();
        String str_title = bean.getBrand_name();
        String str_area = "总部"+bean.getBrand_location()+" · 面积 "+bean.getShop_area();
        String str_money = bean.getInvest_amount();
        String str_number =bean.getScribe();

        if(Util.isOnMainThread()&&logo!=null)
            Glide.with(context).load(logo).placeholder(R.drawable.loading).dontAnimate().into(viewHolder.iv);

        if(str_title!=null)
            viewHolder.tv_title.setText(str_title);
        if(str_area!=null)
            viewHolder.tv_area.setText(str_area);
        if(str_money!=null)
            viewHolder.tv_money.setText("投资 "+str_money+" 万");
        if(str_number!=null)
            viewHolder.tv_number.setText(str_number+"咨询");
        String auto1 = bean.getAutof();
        String auto2 = bean.getAutos();
        viewHolder.ll.removeAllViews();
        if(auto1!=null&&!auto1.equals(""))
            viewHolder.ll.addView(UiUtils.getTV(context,auto1,0));
        if(auto2!=null&&!auto2.equals(""))
            viewHolder.ll.addView(UiUtils.getTV(context,auto2,1));
        return convertView;
    }

    static class ViewHolder {
        ImageView iv;
        TextView tv_title,tv_area,tv_money,tv_number;
        LinearLayout ll;
    }

}
