package com.can.bimuprojects.utils;

import android.content.Context;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.LoginRequest;
import com.can.bimuprojects.Module.Response.LoginResponse;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;

/**
 * Created by can on 2017/4/12.
 */

public class LoginUtils {
    private static final String LOGIN_STATUS = "is_need_login";

    public static String getUid(){
            return LoginUtils.getLoginUid();
    }

    /**
     * 判断是否为游客登录
     */
    public static boolean getLoginStatus() {
        return PrefUtils.getDefaultPref().getBoolean(LOGIN_STATUS, false);
    }

    /**
     * true:表示游客登录
     * false：表示手机号登录
     */
    public static void setLoginStatus(boolean status) {
        if (status == false) {
            PrefUtils.putBoolean(LOGIN_STATUS, false);
        } else {
            PrefUtils.putBoolean(LOGIN_STATUS, true);
        }
    }

    //设置登录的UID
    public static void setLoginUid(String uid){
        PrefUtils.put(AppConstant.UID,uid);
    }
    //获取登录的UID
    public static String getLoginUid(){
        return PrefUtils.get(AppConstant.UID,"");
    }


    //设置登录的用户名
    public static void setUserName(String user_name){
        PrefUtils.put(AppConstant.USER_NAME,user_name);
    }

    //获取登录的用户名
    public static String getUserName(){
        return PrefUtils.get(AppConstant.USER_NAME,"");
    }

    /**
     * 退出登录
     */
    public static void exitLogin(){
        LoginUtils.setLoginStatus(true);
        AppUtils.setSNWithoutLogin(BimuApplication.getContext());
        LoginUtils.setUserName("");
    }

    /**
     * 登录功能
     */
    public static void startLogin(LoginRequest request, final LoginListener listener) {
        HttpUtils.postWithoutUid(MethodConstant.LOGIN, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                if (listener != null) {
                    listener.onResponse(receive);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        }, LoginResponse.class);
    }

    public interface LoginListener {
        void onResponse(JsonReceive receive);

        void onError(VolleyError error);
    }


}
