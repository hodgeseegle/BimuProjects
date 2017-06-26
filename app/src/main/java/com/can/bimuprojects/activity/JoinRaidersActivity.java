package com.can.bimuprojects.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.GetJoinRaidersRequest;
import com.can.bimuprojects.Module.Response.GetJoinRaidersResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;

/**
 * Created by can on 2017/4/14.
 * 加盟攻略
 */

public class JoinRaidersActivity extends BaseActivity {

    private WebView wv ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_raiders);
        wv = (WebView) findViewById(R.id.wv_join_raiders);
        findViewById(R.id.iv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.join_raiders);
        GetJoinRaidersRequest request = new GetJoinRaidersRequest();
        request.setUid(LoginUtils.getUid());
        if(getIntent().getStringExtra("cid").equals("")){
            return;
        }
        request.setCid(getIntent().getStringExtra("cid"));
        HttpUtils.postWithoutUid(MethodConstant.GET_JOIN_RAIDERS, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetJoinRaidersResponse response = (GetJoinRaidersResponse) receive.getResponse();
                if (response != null) {
                    wv.loadData(response.getData(), "text/html;charset=UTF-8", null);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, GetJoinRaidersResponse.class);
    }
}
