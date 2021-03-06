package com.can.bimuprojects.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.GetExerciseExplainRequest;
import com.can.bimuprojects.Module.Response.GetExerciseExplainResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;

/**
 * Created by can on 2017/4/27.
 * 活动说明
 */

public class ExerciseExplainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_explain);
        String id = getIntent().getStringExtra("id");
        findViewById(R.id.iv_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.exercise_say);
        final WebView wv = (WebView) findViewById(R.id.wv_exercise_explain);
        GetExerciseExplainRequest request = new GetExerciseExplainRequest();
        request.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.GET_EXERCISE_SAY, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                GetExerciseExplainResponse response = (GetExerciseExplainResponse) receive.getResponse();
                if(response!=null){
                    wv.loadData(response.getData(), "text/html;charset=UTF-8", null);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, GetExerciseExplainResponse.class);

    }
}
