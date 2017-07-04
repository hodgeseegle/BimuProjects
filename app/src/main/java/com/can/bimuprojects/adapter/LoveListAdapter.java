package com.can.bimuprojects.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.activity.InspectOrKillActivity;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.AddLoveListRequest;
import com.can.bimuprojects.Module.Request.BrandRequest;
import com.can.bimuprojects.Module.Request.SetUserNameRequest;
import com.can.bimuprojects.Module.Response.AddLoveListResponse;
import com.can.bimuprojects.Module.Response.LoveListResponse;
import com.can.bimuprojects.Module.Response.OpenShopResponse;
import com.can.bimuprojects.Module.Response.SetUserNameResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.BrandActivity;
import com.can.bimuprojects.activity.SendArticleActivity;
import com.can.bimuprojects.activity.OpenShopResultActivity;
import com.can.bimuprojects.activity.PersonalInformationProtectActivity;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.GlideRoundTransform;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/19.
 * 心愿单适配器
 */

public class LoveListAdapter extends BaseAdapter {

    private Context context;
    private List<LoveListResponse.WishListBean> list;
    private WindowManager wm;

    public LoveListAdapter(WindowManager wm,Context context,List<LoveListResponse.WishListBean> list){
        this.list = list;
        this.context = context;
        this.wm = wm;
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
        if (view == null) {
            vh = new VH();
            view = LayoutInflater.from(context).inflate(R.layout.item_love_list_review,null);
            vh.iv = (ImageView) view.findViewById(R.id.iv_item_love_list);
            vh.tv_go = (TextView) view.findViewById(R.id.tv_item_love_list_go);
            vh.tv_review = (TextView) view.findViewById(R.id.tv_item_love_list_review);
            vh.tv_title = (TextView) view.findViewById(R.id.tv_item_love_list_title);
            vh.tv_write_or_get = (TextView) view.findViewById(R.id.tv_item_love_list_get);
            vh.iv_logo = (ImageView) view.findViewById(R.id.iv_item_love_list_logo);
            view.setTag(vh);
        }else{
            vh = (VH) view.getTag();
        }
        final LoveListResponse.WishListBean data = list.get(i);
        if(Util.isOnMainThread())
            Glide.with(context).load(data.getBrand_background()).into(vh.iv);
        if(Util.isOnMainThread())
            Glide.with(context).load(data.getBrand_logo()).transform(new GlideRoundTransform(context)).into(vh.iv_logo);
        vh.tv_title.setText(data.getBrand_name()+" · 投资 "+data.getInvest_amount()+" 万 · 总部"+data.getBrand_location()+" · 适合面积 "+data.getShop_area());
        if(data.getIs_consult().equals("1")) { //待考察:写点评
            vh.tv_review.setText(R.string.no_review);
            vh.tv_review.setBackgroundColor(ContextCompat.getColor(context,R.color.color_app_text_yes));
            vh.tv_write_or_get.setText(R.string.write_comment);
        }else{ //待沟通:获取开店方案
            vh.tv_review.setText(R.string.no_comm);
            vh.tv_review.setBackgroundColor(ContextCompat.getColor(context,R.color.color_app_text_yes));
            vh.tv_write_or_get.setText(R.string.get_openshop_plan);
        }


        vh.tv_go.setOnClickListener(new View.OnClickListener() { //前往考察/淘汰
            @Override
            public void onClick(View view) {
                MobclickAgent.onEvent(context,"tv_item_love_list_go");
                Intent intent = new Intent(context, InspectOrKillActivity.class);
                intent.putExtra("brand",data.getBrand_id());
                context.startActivity(intent);
            }
        });

        vh.tv_write_or_get.setOnClickListener(new View.OnClickListener() { //获取开店方案或点评
            @Override
            public void onClick(View view) {
                if(data.getIs_consult().equals("1")){//写点评
                    Intent intent = new Intent(context, SendArticleActivity.class);
                    intent.putExtra("id",data.getBrand_id());
                    intent.putExtra("brand",data.getBrand_name());
                    context.startActivity(intent);
                }else{//获取开店方案
                    MobclickAgent.onEvent(context,"tv_item_love_list_get");
                    showDialogFromBottom(context, wm,data.getBrand_id());
                    requestOpenShopData(data.getBrand_id());
                }
            }
        });

        return view;
    }

    class VH{
        TextView tv_review,tv_title,tv_go,tv_write_or_get;
        ImageView iv,iv_logo;
    }

    private Dialog dialog; //获取开店方案的dialog
    private ListView lv_dialog; //listview
    private TextView tv_dialog_cancle; //取消
    private TextView tv_dialog_sure; //确定
    private List<OpenShopResponse.DataBean> list_openshop = new ArrayList<>();//打开开店方案数据集合
    private BrandOpenShopAdapter adapter_dialog; //开店方案适配器
    private List<Boolean> list_boolean = new ArrayList<>(); //开店方案选择集合
    private LinearLayout ll_dialog; //选择先生女士
    private EditText et_dialog; //请输入您的姓氏
    private RadioGroup rg_dialog;
    private RadioButton rb_xiansheng;
    private RadioButton rb_nvshi;
    private ImageView iv_dialog;
    private int sex = 0; //0：先生 1：女士
    private TextView tv_dialog_open_shop_agree ; //个人信息保护声明

    /**
     * 从底部弹出的dialog
     */
    private void showDialogFromBottom(final Context context, WindowManager wm, final String bid) {
        dialog = new Dialog(context,R.style.style_dialog);
        View view_dialog = LayoutInflater.from(context).inflate(R.layout.dialog_open_shop_plan, null);
        lv_dialog = (ListView) view_dialog.findViewById(R.id.lv_brand_dialog);
        tv_dialog_cancle = (TextView) view_dialog.findViewById(R.id.tv_brand_dialog_cancle);
        tv_dialog_sure = (TextView) view_dialog.findViewById(R.id.tv_brand_dialog_sure);
        ll_dialog = (LinearLayout) view_dialog.findViewById(R.id.ll_dialog_open_shop_plan);
        et_dialog = (EditText) view_dialog.findViewById(R.id.et_dialog_open_shop_plan);
        rg_dialog = (RadioGroup) view_dialog.findViewById(R.id.rg_dialog_open_shop_plan);
        rb_xiansheng = (RadioButton) view_dialog.findViewById(R.id.rb_dialog_open_shop_plan_xiansheng);
        rb_nvshi = (RadioButton) view_dialog.findViewById(R.id.rb_dialog_open_shop_plan_nvshi);
        iv_dialog = (ImageView) view_dialog.findViewById(R.id.iv_dialog_open_shop_plan);
        tv_dialog_open_shop_agree = (TextView) view_dialog.findViewById(R.id.tv_dialog_open_shop_agree);
        if(Util.isOnMainThread())
            Glide.with(context).load(R.drawable.get_open_shop_plan).dontAnimate().into(iv_dialog);
        if(LoginUtils.getUserName().equals("")){
            ll_dialog.setVisibility(View.VISIBLE);
            iv_dialog.setVisibility(View.GONE);
        }else{
            ll_dialog.setVisibility(View.GONE);
            iv_dialog.setVisibility(View.VISIBLE);
        }
        tv_dialog_open_shop_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //个人信息保护声明
                context.startActivity(new Intent(context,PersonalInformationProtectActivity.class));
            }
        });
        rg_dialog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_dialog_open_shop_plan_xiansheng) {//先生
                    sex = 0;
                } else { //女士
                    sex = 1;
                }
            }
        });
        lv_dialog.setOverScrollMode(View.OVER_SCROLL_NEVER);
        tv_dialog_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_sure(bid);
            }
        });
        tv_dialog_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        lv_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_dialog = new Intent(context, BrandActivity.class);
                intent_dialog.putExtra("index", list_openshop.get(i).getBrand_id());
                context.startActivity(intent_dialog);
            }
        });
        dialog.setContentView(view_dialog);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialogStyle);
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        params.width = metric.widthPixels;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        adapter_dialog = new BrandOpenShopAdapter(context, list_openshop, list_boolean);
        lv_dialog.setAdapter(adapter_dialog);
    }

    /**
     * 获取开店方案网络数据
     */
    private void requestOpenShopData(String bid) {
        list_openshop.clear();
        list_boolean.clear();
        BrandRequest request = new BrandRequest();
        request.setBid(bid);
        request.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.GET_OPEN_SHOP, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                OpenShopResponse response = (OpenShopResponse) receive.getResponse();
                if (response != null) {
                    if (!dialog.isShowing()) {
                        dialog.show();
                        list_openshop.addAll(response.getData());
                        for (int i = 0; i < list_openshop.size(); i++) {
                            list_boolean.add(i, true);
                        }
                        adapter_dialog.notifyDataSetChanged();
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, OpenShopResponse.class);
    }

    /**
     * 确定
     */
    private void dialog_sure(String bid) {
        if (ll_dialog.getVisibility() == View.VISIBLE) {
            if (et_dialog.getText().toString()==null||et_dialog.getText().toString().equals("")||et_dialog.getText().toString().trim().equals("")) {
                ToastUtils.showShort(context,"请输入您的姓氏");
                return;
            }else {
                SetUserNameRequest request = new SetUserNameRequest();
                request.setUid(LoginUtils.getUid());
                request.setUser_name(et_dialog.getText().toString().trim());
                request.setUser_sex(sex + "");
                HttpUtils.postWithoutUid(MethodConstant.SET_USER_NAME, request, new ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        SetUserNameResponse response = (SetUserNameResponse) receive.getResponse();
                        if(response!=null)
                            LoginUtils.setUserName(et_dialog.getText().toString().trim());
                    }
                }, new ErrorHook() {
                    @Override
                    public void deal(Context context, VolleyError error) {

                    }
                }, SetUserNameResponse.class);
            }
        }
        PrefUtils.putBoolean("love_update",true);
        consultShop(bid);
        list_boolean = adapter_dialog.getCheckState();
        for(int i =0;i<list_openshop.size();i++){
            if(list_boolean.get(i)) {
                consultShop(list_openshop.get(i).getBrand_id());
            }
        }
        dialog.dismiss();
        Intent intent_open_shop = new Intent(context, OpenShopResultActivity.class);
        intent_open_shop.putExtra("brand",bid);
        context.startActivity(intent_open_shop);
    }


    /**
     * 咨询店铺
     */
    private void consultShop(String id){
        AddLoveListRequest re = new AddLoveListRequest();
        re.setId(id);
        re.setType("3");
        re.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.SET_LOVE_LIST, re, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {

            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        },AddLoveListResponse.class);
    }

}
