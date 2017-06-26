package com.can.bimuprojects.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Entity.MessageBean;
import com.can.bimuprojects.Module.Request.GetMessageListRequest;
import com.can.bimuprojects.Module.Response.GetMessageListResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.ListAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.view.DividerLine;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 消息列表展示页
 */
public class ListActivity extends BaseActivity {
    private String listType;
    private RecyclerView.Adapter adapter;
    private List<MessageBean> dataList = new ArrayList<>();
    private String title = ""; //如果Intent里传入了title，则显示这个title

    @Bind(R.id.list_rv_main)
    RecyclerView recyclerView;
    private ImageView iv_back;
    private TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        if ((listType = getIntent().getStringExtra(AppConstant.LIST_TYPE)) == null) {
        }
        init();
        loadDataFromNet(true);
    }

    private void init() {
        adapter = new ListAdapter(this, listType, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DividerLine dividerLine;
        dividerLine = new DividerLine(DividerLine.VERTICAL);
        if (getIntent().getStringExtra(AppConstant.LIST_TYPE) .equals(AppConstant.LIST_TYPE_MESSAGE)){
            dividerLine.setSize(2);
        } else {
            dividerLine.setSize(22);
        }
        dividerLine.setColor(Color.parseColor("#ede7d9"));
        recyclerView.addItemDecoration(dividerLine);
        iv_back = (ImageView) findViewById(R.id.iv_exit);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void loadDataFromNet(boolean isFirstLoad) {
        //根据列表类型载入相应的数据
        if(isFirstLoad){
            selectLoad();
        }else{
            selectLoad();
        }

    }

    private void selectLoad() {
         if (AppConstant.LIST_TYPE_MESSAGE.equals(listType)) {
            //消息列表
            tv_title.setText("消息列表");
            loadMessageList();
        }

        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
    }

    /**
     * 获取消息列表
     */
    private void loadMessageList() {
        GetMessageListRequest request = new GetMessageListRequest();
        request.setUid(LoginUtils.getLoginUid());
        request.setTimestamp("0");
        HttpUtils.post(MethodConstant.GET_MESSAGE_LIST, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetMessageListResponse response = (GetMessageListResponse) receive.getResponse();
                if (response != null && response.getExe_success() == 1) {
                     List<MessageBean> data  = response.getCards();
                    dataList.clear();
                    for(int i = 0 ;i<data.size();i++){
                        if(data.get(i).getId()!=null&&data.get(i).getType().equals("1")||data.get(i).getType().equals("3")) //1为文章 3为个人主页
                        dataList.add(data.get(i));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
            }
        }, GetMessageListResponse.class);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtils.cancelAll(this);
    }

    @Override
    protected void onResume(){
        loadDataFromNet(false);
        super.onResume();
    }

}
