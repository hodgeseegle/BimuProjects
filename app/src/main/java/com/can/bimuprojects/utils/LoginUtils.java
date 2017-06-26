package com.can.bimuprojects.utils;

import android.content.Context;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.LoginRequest;
import com.can.bimuprojects.Module.Response.LoginResponse;
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
     * 判断是否已经登录
     *
     * @return
     */
    public static boolean getLoginStatus() {
        return PrefUtils.getDefaultPref().getBoolean(LOGIN_STATUS, false);
    }

    /**
     * 保存登录的状态
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

    /**
     * 登录功能
     *
     * @return
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
