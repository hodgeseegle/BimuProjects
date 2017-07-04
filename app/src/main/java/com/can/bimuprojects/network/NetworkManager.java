package com.can.bimuprojects.network;

import android.content.Context;
import android.content.Intent;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.activity.LoginOrRegisterActivity;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.network.utils.Constants;
import com.can.bimuprojects.network.utils.JsonParseUtil;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;

import org.json.JSONObject;

/**
 * 网络请求操作单例类
 * 使用getInstance方法获得单例类
 * 在程序发送网络请求之前需调用init方法
 * 调用post方法发送网络请求
 * 在程序退出时需调用remove方法
 */
public class NetworkManager {

    private Context mContext;

    /**
     * 网络请求队列
     */
    private RequestQueue mQueue;

    /**
     * 静态内部类的方法实现单例模式
     */
    private static class Holder {
        private static final NetworkManager INSTANCE = new NetworkManager();
    }

    /**
     * 获取发送器单例
     */
    public static NetworkManager getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public void init(Context context) {
        mContext = context;
        mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
    }


    /**
     * 向服务器发送请求
     *
     * @param method       请求的接口名
     * @param request      请求的实体
     * @param responseHook 响应成功的接口class
     * @param errorHook    响应失败的接口class
     * @param responses    响应的实体类class数组
     */
    public void post(final String method, final Object request,
                     final ResponseHook responseHook, final ErrorHook errorHook,
                     final Class<?>... responses) {
        new Thread() {
            @Override
            public void run() {
                if (mQueue != null) {
                    // 将request响应实体加上公共部分，转化成JSONObject
                    JSONObject jsonRequest = JsonParseUtil.beanParseJson(
                            method, request, mContext);
                    JsonObjectRequest req = new JsonObjectRequest(Method.POST,
                            Constants.URL, jsonRequest,
                            new Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject json) {
                                    // 请求成功响应，将JSONObject转换成JsonReceive
                                    if (JsonParseUtil.jsonParseBean(
                                            json, responses).getStatus() == 333) {
                                        BimuApplication.removeALLActivity();
                                        ToastUtils.showShort(BimuApplication.getContext(), "您的帐户已在其他设备登录");
                                       LoginUtils.exitLogin();
                                        Intent intent = new Intent(BimuApplication.getContext(), LoginOrRegisterActivity.class);
                                        intent.putExtra(AppConstant.QUIT_LOGIN,true);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        BimuApplication.getContext().startActivity(intent);
                                        return;
                                    }
                                    if (responseHook != null) {
                                        responseHook.deal(mContext,
                                                JsonParseUtil.jsonParseBean(
                                                        json, responses));
                                    }
                                }
                            }, new ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // 请求失败
                            if (errorHook != null) {
                                errorHook.deal(mContext, error);
                            }
                        }
                    });
                    // 添加到请求队列,设置请求时间过长时的请求次数限制为1次
                    req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    mQueue.add(req);
                } else {
                }
            }
        }.start();
    }

    /**
     * 移除所有的请求
     */
    public void remove() {
        if (mQueue != null) {
            mQueue.cancelAll(mContext.getApplicationContext());
        } else {
        }
    }

    /**
     * 获取请求队列
     *
     * @return 请求队列
     */
    public RequestQueue getRequestQueue() {
        return mQueue;
    }

}
