package com.can.bimuprojects.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Response.ProtocalResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;

public class ProtocalActivity extends BaseActivity {

    private WebView webView;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocal);
        tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.user_protocal);
        findViewById(R.id.iv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView = (WebView) findViewById(R.id.wv_protocal);
        loadDataFromNet();
    }
    private void loadDataFromNet(){
        HttpUtils.postWithoutUid(MethodConstant.USER_PROTOCAL, null, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                ProtocalResponse response= (ProtocalResponse) receive.getResponse();
                if(null!=response&&response.getExe_success().equals("1")){
                    webView.loadData(response.getProtocal_content(), "text/html;charset=UTF-8", null);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        },ProtocalResponse.class);
    }

}
