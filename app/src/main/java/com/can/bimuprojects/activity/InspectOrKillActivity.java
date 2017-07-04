package com.can.bimuprojects.activity;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.AddLoveListRequest;
import com.can.bimuprojects.Module.Request.BookServiceRequest;
import com.can.bimuprojects.Module.Request.DataServiceRequest;
import com.can.bimuprojects.Module.Request.Focus2Request;
import com.can.bimuprojects.Module.Request.FocusRequest;
import com.can.bimuprojects.Module.Request.TypeServiceRequest;
import com.can.bimuprojects.Module.Response.AddLoveListResponse;
import com.can.bimuprojects.Module.Response.BookServiceResponse;
import com.can.bimuprojects.Module.Response.DataServiceResponse;
import com.can.bimuprojects.Module.Response.Focus2Response;
import com.can.bimuprojects.Module.Response.FocusResponse;
import com.can.bimuprojects.Module.Response.TypeServiceResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.InspectAdapter;
import com.can.bimuprojects.adapter.TypeServiceAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/19.
 * 考察或淘汰
 */

public class InspectOrKillActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, InspectAdapter.onChangeStateListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setlistener();
        initData();
    }

    private TextView tv_title,tv_sure;//品牌标题 确认
    private ImageView ws2; //开关
    private ImageView iv_exit ; //退出
    private NoScrollListView lv_inspect; //选择集合控件
    private List<BookServiceResponse.ServiceBean> list_inspect ; //选择集合
    private InspectAdapter adapter_inspect ;  //选择适配器
    private Dialog dialog ;//弹出框
    private NoScrollListView lv;//集合控件
    private TextView tv_reason ; //淘汰原因
    private TextView tv_tit ; //标题



    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activtiy_inspect);
        tv_title = (TextView) findViewById(R.id.tv_inspect_brand);
        ws2 = (ImageView) findViewById(R.id.ws_inspect2);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        tv_tit = (TextView) findViewById(R.id.tv_title);
        tv_tit.setText(R.string.reservation_service);
        tv_sure = (TextView) findViewById(R.id.tv_inspect_sure);
        lv_inspect = (NoScrollListView) findViewById(R.id.lv_inspect);
        tv_reason = (TextView) findViewById(R.id.tv_inspect_reason);
        initDialog();
    }

    /**
     * 初始化弹出框
     */
    private void initDialog() {
        dialog = new Dialog(this,R.style.style_dialog);
        View view_dialog = LayoutInflater.from(this).inflate(R.layout.dialog_type_service, null);
        lv = (NoScrollListView) view_dialog.findViewById(R.id.lv_type_service);
        dialog.setContentView(view_dialog);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialogStyle);
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        params.width = metric.widthPixels;
        params.height = metric.heightPixels * 2 / 3;
        window.setAttributes(params);
    }


    private String bid; //品牌id
    private List<String> list ; //淘汰原因集合
    private TypeServiceAdapter adapter ; //适配器
    public  List<Boolean> list_boolean ; //选择集合
    private String sid ; //原因id
    private String str_reason=""; //淘汰原因
    List<BookServiceResponse.ServiceBean> data; //预约服务数据集合
    private boolean go_review ; //是否前往考察
    private boolean go_kill ; //是否淘汰
    //初始化数据
    private void initData() {
        list = new ArrayList<>();
        adapter = new TypeServiceAdapter(this,list);
        lv.setAdapter(adapter);

        list_inspect = new ArrayList<>();
        adapter_inspect = new InspectAdapter(list_inspect,this);
        lv_inspect.setAdapter(adapter_inspect);
        adapter_inspect.setOnChangeStateListener(this);
        list_boolean = new ArrayList<>();

        bid = getIntent().getStringExtra("brand");
        BookServiceRequest request = new BookServiceRequest();
        request.setUid(LoginUtils.getUid());
        request.setBid(bid);
        HttpUtils.postWithoutUid(MethodConstant.BOOK_SERVICE, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                BookServiceResponse response = (BookServiceResponse) receive.getResponse();
                if(response!=null){
                    setData(response);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, BookServiceResponse.class);
        TypeServiceRequest re = new TypeServiceRequest();
        re.setUid(LoginUtils.getUid());
        re.setType("0");
        HttpUtils.postWithoutUid(MethodConstant.TYPE_SERVICE, re, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                TypeServiceResponse response = (TypeServiceResponse) receive.getResponse();
                if(response!=null){
                    list.addAll(response.getData());
                    adapter.notifyDataSetChanged();
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, TypeServiceResponse.class);

    }

    //设置数据
    private void setData(BookServiceResponse response) {
        tv_title.setText(response.getBrand().getBrand_name()+" · 投资 "+response.getBrand().getInvest_amount()+"万 · 总部"+response.getBrand().getShop_area()+" · 适合面积"+response.getBrand().getShop_area());
        data = response.getService();
        if(data.size()>0){
            list_inspect.addAll(data);
            adapter_inspect.notifyDataSetChanged();
            for(int i =0;i<data.size();i++){
                list_boolean.add(i,data.get(i).getChoose().equals("true"));
            }
        }
    }

    //监听
    private void setlistener() {
        iv_exit.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        ws2.setOnClickListener(this);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exit://退出
                finish();
                break;
            case R.id.tv_inspect_sure: //确认
                sure();
                break;
            case R.id.ws_inspect2: //点击淘汰品牌选择原因
                if(str_reason!=null&&str_reason.equals("")){
                    openBrand();
                }else{
                    setKillFalse();
                }
                break;
        }
    }

    //取消淘汰品牌
    private void setKillFalse() {
        go_kill = false;
        str_reason = "";
        tv_reason.setText(getString(R.string.kill_brand_say));
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.radio_button_off).into(ws2);
    }

    //确认
    private void sure() {
        for(int i =0;i<list_boolean.size();i++){
            if(list_boolean.get(i)) {
                go_review = true;
                break;
            }
        }
        if(!go_kill&&!go_review){
            if(!this.isFinishing())
                showDialog("请选择条件");
        }else{
            if(go_review){
                for(int i=0;i<list_boolean.size();i++){
                    if(list_boolean.get(i)){
                        DataServiceRequest request = new DataServiceRequest();
                        request.setBid(bid);
                        request.setSid(data.get(i).getId());
                        request.setUid(LoginUtils.getUid());
                        HttpUtils.postWithoutUid(MethodConstant.DATA_SERVICE, request, new ResponseHook() {
                            @Override
                            public void deal(Context context, JsonReceive receive) {
                                DataServiceResponse res = (DataServiceResponse) receive.getResponse();
                            }
                        }, new ErrorHook() {
                            @Override
                            public void deal(Context context, VolleyError error) {

                            }
                        },DataServiceResponse.class);
                    }
                }
                if(!go_kill){
                    AddLoveListRequest re = new AddLoveListRequest();
                    re.setId(bid);
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
                PrefUtils.putBoolean("love_update",true);
                finish();
            }
            if(go_kill){
                FocusRequest request = new FocusRequest();
                request.setUid(LoginUtils.getUid());
                request.setId(bid);
                request.setType(2);
                HttpUtils.postWithoutUid(MethodConstant.FOCUS, request, new ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        FocusResponse res = (FocusResponse) receive.getResponse();
                    }
                }, new ErrorHook() {
                    @Override
                    public void deal(Context context, VolleyError error) {

                    }
                }, FocusResponse.class);
            }
            if(!str_reason.equals("")){
                Focus2Request request = new Focus2Request();
                request.setUid(LoginUtils.getUid());
                request.setBid(bid);
                request.setSid(sid);
                request.setRefuse(str_reason);
                HttpUtils.postWithoutUid(MethodConstant.DATA_SERVICE, request, new ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        Focus2Response response = (Focus2Response) receive.getResponse();
                        if(response!=null&&response.getExe_success()==1){
                            ToastUtils.show(context,"淘汰成功", Toast.LENGTH_SHORT);
                            PrefUtils.putBoolean("love_update",true);
                            finish();
                        }
                    }
                }, new ErrorHook() {
                    @Override
                    public void deal(Context context, VolleyError error) {

                    }
                }, Focus2Response.class);
            }
        }
    }

    //淘汰原因
    private void openBrand() {
        dialog.show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        str_reason = list.get(i)+"";
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.radio_button_on).into(ws2);
        dialog.dismiss();
        sid = i+"";
        if(str_reason!=null){
            go_kill = true;
            tv_reason.setText(str_reason);

            go_review = false;
            for(int j =0;j<data.size();j++){
                BookServiceResponse.ServiceBean bean = data.get(j);
                bean.setChoose("false");
                list_boolean.set(j,false);
                list_inspect.set(j, bean);
            }

            adapter_inspect.notifyDataSetChanged();
        }
    }

    @Override
    public void onChangeState(int i, boolean flag) {
        list_boolean.set(i,flag);
        if(flag)
            setKillFalse();
    }
}