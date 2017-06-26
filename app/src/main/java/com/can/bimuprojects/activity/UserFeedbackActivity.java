package com.can.bimuprojects.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.UserFeedbackRequest;
import com.can.bimuprojects.Module.Response.UserFeedbackResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by can on 17/4/24.
 * 打小报告
 */
public class UserFeedbackActivity extends BaseActivity {

    @Bind(R.id.iv_exit)
    ImageView ivBack;
    @Bind(R.id.user_feedback_et_content)
    EditText etContent;
    @Bind(R.id.user_feedback_btn_commit)
    Button btnCommit;
    @Bind(R.id.tv_title)
    TextView tv_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feedback);
        ButterKnife.bind(this);
        init();
        AppUtils.showSoftInput(this,etContent);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    private void init() {
        tv_title.setText("发表反馈意见");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserFeedbackActivity.this.finish();
            }
        });
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etContent.getText().toString().trim();
                String appVersionName = AppUtils.getAppVersionName(UserFeedbackActivity.this);
                UserFeedbackRequest request = new UserFeedbackRequest(LoginUtils.getLoginUid(),
                        appVersionName + " " + "Android" + "\n" + content);
                if (!TextUtils.isEmpty(content)) {
                    HttpUtils.post(MethodConstant.USER_FEEDBACK, request, new ResponseHook() {
                        @Override
                        public void deal(Context context, JsonReceive receive) {
                            UserFeedbackResponse response = (UserFeedbackResponse) receive
                                    .getResponse();
                            if (response != null && response.getExe_success() == 1) {
                                ToastUtils.showShort(UserFeedbackActivity.this,"反馈提交成功");
                                UserFeedbackActivity.this.finish();
                                return;
                            }
                        }
                    }, new ErrorHook() {
                        @Override
                        public void deal(Context context, VolleyError error) {
                            ToastUtils.showShort(UserFeedbackActivity.this,"提交失败，请重试");
                        }
                    }, UserFeedbackResponse.class);
                }else {
                    ToastUtils.showShort(UserFeedbackActivity.this,"反馈内容不能为空!");
                }

            }
        });


    }
}
