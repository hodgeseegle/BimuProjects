package com.can.bimuprojects.utils;

import android.content.Context;

import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.network.NetworkManager;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.view.LoadDialog;

/**
 * Created by can on 2017/4/12.
 */
public class HttpUtils {


    /**
     * 在原来的post基础上添加了网络状态的判断
     *
     * @param method
     * @param request
     * @param responseHook
     * @param errorHook
     * @param responses
     */
    public static void post(String method, Object request, ResponseHook responseHook, ErrorHook
            errorHook, Class<?>... responses) {
        if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
            ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
            LoadDialog.dismiss(BimuApplication.getContext());
            return;
        } else {
            NetworkManager.getInstance().post(method, request, responseHook, errorHook, responses);
        }
    }

    /**
     * 发送请求前不做是否登录的判断
     *
     * @param method
     * @param request
     * @param responseHook
     * @param errorHook
     * @param responses
     */

    public static void postWithoutUid(String method, Object request, ResponseHook responseHook,
                                      ErrorHook
                                              errorHook, Class<?>... responses) {
        if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
            ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
            LoadDialog.dismiss(BimuApplication.getContext());
            return;
        } else {
            NetworkManager.getInstance().post(method, request, responseHook, errorHook, responses);
        }
    }

    public static void cancelAll(Context context) {
        NetworkManager.getInstance().getRequestQueue().cancelAll(context);
    }





}
