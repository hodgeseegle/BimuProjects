package com.can.bimuprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.InterestRequest;
import com.can.bimuprojects.Module.Response.InterestReponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.InterestGVAdapter;
import com.can.bimuprojects.adapter.InterestLVAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by can on 2017/4/10.
 * 兴趣行业选择
 */

public class ActivityInterest extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static final String TRADE = "trade";
    public static final String CHANGE = "change";
    public static final int REQUEST_CODE = 0x111;
    public static final int RESPONSE_CODE = 1;

    public static int lv_position ; //点击的listview下标
    public static List<List<Boolean>> list_boolean  ; //多选的选择集合
    private boolean isMore = false ; //是否选择多项，还是单项
    private boolean isTrade = false ; //点击右侧图片是否进入行业
    private boolean isChangeEnter = false ; //是否从更换行业进入
    private int select_total ; //选择总数
    private List<String> select_list; //选择的集合
    private List<List<String>> id_list ; //id的集合

    private List<InterestReponse.DataBean> data ; //网络数据集合

    private ListView lv ; //左侧显示
    private ImageView iv_exit;//退出
    private TextView tv_sure ; //确定按钮

    private InterestLVAdapter adapter_lv;//左侧 适配器
    private List<String> list_lv; //左侧数据集合

    private GridView gv ; //右侧显示
    private InterestGVAdapter adapter_gv ; //右侧适配器
    private List<InterestReponse.DataBean.SmallCategoriesBean> list_gv;//右侧 数据集合

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initListener();
        initData();
    }

    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_interest);
        lv = (ListView) findViewById(R.id.lv_interest);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        gv = (GridView) findViewById(R.id.gv_interest);
        tv_sure = (TextView) findViewById(R.id.tv_right);
        
    }
    /**
     * 设置监听
     */
    private void initListener() {
        iv_exit.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        gv.setOnItemClickListener(this);
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_exit://退出
                finish();
                break;
            case R.id.tv_right: //确定
                if(list2String()==null){//||list2String().equals("")
                    ToastUtils.showShort(this,"请至少选择一项");
                    return;
                }
                if(list2String()!=null) {
                    Map<String, String> map_value = new HashMap<String, String>();
                    map_value.put("interest", list2String());
                    MobclickAgent.onEventValue(this, "tv_sure_interest", map_value, 100);
                }
                getIntent().putExtra("string",list2String());
                getIntent().putExtra("class",idList2String());
                setResult(1,getIntent());
                finish();
                break;
        }
    }

    /**
     * 将集合转为字符串
     */
    private String list2String(){
        String string = "";
        for(int i =0;i<select_list.size();i++){
            if(i!=select_list.size()-1){
                string += select_list.get(i)+",";
            }else{
                string+=select_list.get(i);

            }
        }
        return string;
    }

    /**
     * 将id的集合转为字符串
     */
    private String idList2String(){
        String string = "";
        for(int i =0;i<list_boolean.size();i++){
            List<Boolean> list1 = list_boolean.get(i);
            for(int j=0;j< list1.size();j++){
                boolean flag = list1.get(j);
                if(flag)
                    string+=id_list.get(i).get(j)+",";
            }
        }
        string = removeDou(string);
        return string;
    }

    /**
     * 去除字符串的逗号
     */
    private String removeDou(String string){
        if(string.endsWith(",")){
            string = string.substring(0,string.length()-1);
        }
        return string;
    }


    /**
     * 数据初始化
     */
    private void initData() {
        data = new ArrayList<>();
        list_lv = new ArrayList<>();
        list_gv = new ArrayList<>();
        list_boolean = new ArrayList<>();
        select_list = new ArrayList<>();
        id_list = new ArrayList<>();

        Intent intent = getIntent();
        isMore = intent.getBooleanExtra("more",false);
        isTrade = intent.getBooleanExtra(TRADE,false);
        isChangeEnter = intent.getBooleanExtra(CHANGE,false);
        tv_sure.setText(getString(R.string.sure));

        if(isMore)
            tv_sure.setVisibility(View.VISIBLE);

        adapter_lv = new InterestLVAdapter(this,list_lv);
        lv.setAdapter(adapter_lv);

        adapter_gv = new InterestGVAdapter(this,list_gv);
        gv.setAdapter(adapter_gv);

        requestInterent();
    }

    /**
     * 请求网络数据
     */
    private void requestInterent() {
        InterestRequest request = new InterestRequest();
        request.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.GET_INTEREST, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                InterestReponse response = (InterestReponse) receive.getResponse();
                if(response!=null&&((InterestReponse) receive.getResponse()).getExe_success()==1){
                    if(response.getData().size()>0){
                        data = response.getData();
                        setNewData(data); //设置新数据
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, InterestReponse.class);
    }


    /**
     * 设置listview数据
     */
    private void setNewData(List<InterestReponse.DataBean> data){
        for(int i = 0;i<data.size();i++){
            list_lv.add(data.get(i).getLarge_category_name());
            List<Boolean> list1 = new ArrayList<>();
            List<String> list2 = new ArrayList<>();
            for(int k = 0;k<data.get(i).getSmall_categories().size();k++){
                list1.add(false);
                list2.add(data.get(i).getSmall_categories().get(k).getId());
            }
            id_list.add(list2);
            list_boolean.add(list1);
        }
        adapter_lv.notifyDataSetChanged();
        for(int j =0 ;j<data.get(0).getSmall_categories().size();j++){
            list_gv.add(data.get(0).getSmall_categories().get(j));
        }
        adapter_gv.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_interest: //listview点击事件
                lv_position = i;
                list_gv.clear();
                for(int j =0 ;j<data.get(i).getSmall_categories().size();j++){
                    list_gv.add(data.get(i).getSmall_categories().get(j));
                }
                adapter_gv.notifyDataSetChanged();
                adapter_lv.notifyDataSetChanged();
                break;
            case R.id.gv_interest://gridview点击事件
                if (isMore){
                    boolean flag = list_boolean.get(lv_position).get(i);
                    if(flag)
                        select_total--;
                    else
                        select_total++;
                    if(select_total==6){
                        if(!this.isFinishing())
                        showDialog("最多选择5个");
                        select_total=5;
                        break;
                    }
                    list_boolean.get(lv_position).set(i,!flag);
                    if(flag){
                        if(select_list.contains(data.get(lv_position).getSmall_categories().get(i).getName())){
                            select_list.remove(data.get(lv_position).getSmall_categories().get(i).getName());
                        }
                    }else{
                        select_list.add(data.get(lv_position).getSmall_categories().get(i).getName());
                    }
                    adapter_gv.notifyDataSetChanged();
                }else{
                    if(isTrade){
                        Intent intent = new Intent(this,ChooseTypeActivity.class);
                        intent.putExtra("notFind",true);
                        intent.putExtra("cid",list_gv.get(i).getId());
                        intent.putExtra("project",list_gv.get(i).getName());
                        startActivity(intent);
                    }else if(isChangeEnter){
                        Intent intent = getIntent();
                        intent.putExtra("cid",list_gv.get(i).getId());
                        intent.putExtra("project",list_gv.get(i).getName());
                        setResult(RESPONSE_CODE,intent);
                        finish();
                    }else{
                        PrefUtils.put("class_index",list_gv.get(i).getId());
                        PrefUtils.put("interest",list_gv.get(i).getName());
                        Intent intent = new Intent(ActivityInterest.this,MainActivity.class);
                        intent.putExtra("find",true);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lv_position = 0; //listview下标返回0
    }
}
