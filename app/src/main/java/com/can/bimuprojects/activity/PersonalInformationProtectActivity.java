package com.can.bimuprojects.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.GetPersonalInformationProtectRequest;
import com.can.bimuprojects.Module.Response.GetPersonInformationProtectResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;

/**
 * Created by can on 2017/5/10.
 * 个人信息保护声明
 */

public class PersonalInformationProtectActivity extends BaseActivity {

    private WebView wv ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information_protect);
        findViewById(R.id.iv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.persopnal_infor_protect);
        wv = (WebView) findViewById(R.id.wv_personal_infor_protect);
        loadDataFromNet();
    }

    private void loadDataFromNet(){
        GetPersonalInformationProtectRequest request = new GetPersonalInformationProtectRequest();
        request.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.GET_USER_PERSONAL_PROTECT, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetPersonInformationProtectResponse response= (GetPersonInformationProtectResponse) receive.getResponse();
                if(null!=response&&response.getExe_success().equals("1")){
                    wv.loadData(response.getData(), "text/html;charset=UTF-8", null);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
            }
        },GetPersonInformationProtectResponse.class);
    }
}
