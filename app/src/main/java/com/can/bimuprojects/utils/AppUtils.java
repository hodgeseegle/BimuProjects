package com.can.bimuprojects.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;


import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.SNRequest;
import com.can.bimuprojects.Module.Response.SNResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.LoginActivity;
import com.can.bimuprojects.activity.RegisterActivity;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.network.utils.Constants;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by can on 2017/4/12.
 * app管理工具类
 */
public class AppUtils {

    /**
     * 安装apk
     *
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file) {
        if(context==null){
            Log.e("AppUtils:", "传入context为空");
            return;
        }
        if(file.length()==0){
            Log.e("AppUtils:", "传入file为空");
            return;
        }
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setData(Uri.fromFile(file));
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        if(mobiles==null){
            Log.e("AppUtils:", "传入号码为空");
            return false;
        }
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    虚拟运营商：170
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
    /*String telRegex = "^(0|86|17951)?(13[0-9]|15[012356789]|18[0-9]|14[57]|17[07])[0-9]{8}$";
    if (TextUtils.isEmpty(mobiles)) return false;
    else return mobiles.matches(telRegex);*/
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
}
    /**
     * 获得设备的屏幕宽度(px)
     *
     * @param context
     * @return
     */
    @SuppressWarnings("deprecation")
    public static int getDeviceWidth(Context context) {
        if(context==null){
            Log.e("AppUtils:", "传入context为空");
            return 0;
        }
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }

    /**
     * 获取当前应用程序的版本名
     *
     * @return
     */
    public static String getAppVersionName(Context context) {
        if(context==null){
            Log.e("AppUtils:", "传入context为空");
            return null;
        }
        String version = "0";
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("AppUtils","getAppVersion");
        }
        Log.d("AppUtils","该应用的版本号: " + version);
        return version;
    }

    /**
     * 获取当前应用程序的版本名
     *
     * @return
     */
    public static int getAppVersionCode(Context context) {
        if(context==null){
            Log.e("AppUtils:", "传入context为空");
            return 0;
        }
        int version = 0;
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("AppUtils","getAppVersion");
        }
        Log.d("AppUtils","该应用的版本号: " + version);
        return version;
    }

    /**
     * 获取当前应用程序的包名
     * @param context 上下文对象
     * @return 返回包名
     */
    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }

    /**
     * 隐藏软键盘
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void hideSoftInput(Context context) {
        if(context==null){
            Log.e("AppUtils:", "传入context为空");
            return;
        }
        View view = ((Activity) context).getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void showSoftInput(Context context, EditText edit) {
        if(context==null||edit==null){
            Log.e("AppUtils:", "传入参数为空");
            return;
        }
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(edit, 0);
    }

    /**
     * 检查网络连接
     */
    public static boolean isNetworkOK(Context context) {
        if(context==null){
            Log.e("AppUtils:", "传入参数为空");
            return false;
        }
        if (context == null) {
            return false;
        }
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断是否安装某个应用
     */
    public static boolean isPkgInstalled(Context context,String pkgName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 打开某个应用
     */
    public static void openOtherApp(Context context,String packageName,String detail){
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        context.startActivity(intent);
    }

    /**
     * 是否需要弹窗进行登录操作
     */
    public static boolean isNeedShowLoginDialog(){
        if(LoginUtils.getLoginStatus()){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 弹窗进行登录操作
     */
    public static Dialog showLoginDialog(final Context context){
        final Dialog dialog = new Dialog(context, R.style.style_dialog);
        View view_dialog = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        view_dialog.findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() { //登录
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("flag",true);
                ((Activity)context).startActivityForResult(intent,AppConstant.LOGIN_REQUEST);
            }
        });

        view_dialog.findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() { //注册
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Intent intent = new Intent(context, RegisterActivity.class);
                intent.putExtra("flag",true);
                ((Activity)context).startActivityForResult(intent, AppConstant.LOGIN_REQUEST);
            }
        });


        dialog.setContentView(view_dialog);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        params.width = metric.widthPixels*9/10;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        return  dialog;
    }

    //获取并设置游客登录uid
    public static void setSNWithoutLogin(Context context){
        boolean isNeedLogin = LoginUtils.getLoginStatus();
        if(isNeedLogin){
            SNRequest request = new SNRequest();
            request.setSn(Constants.getSN(context));
            HttpUtils.postWithoutUid(MethodConstant.SN, request, new ResponseHook() {
                @Override
                public void deal(Context context, JsonReceive receive) {
                    SNResponse response = (SNResponse) receive.getResponse();
                    if(response!=null){
                        if(response.getExe_success()==1){
                            String uid = response.getUid();
                            if(uid!=null&&!uid.equals("")){
                                Log.i("bimu",uid);
                                LoginUtils.setLoginUid(uid);
                                PrefUtils.put("uid",uid);
                            }
                        }
                    }
                }
            }, new ErrorHook() {
                @Override
                public void deal(Context context, VolleyError error) {

                }
            }, SNResponse.class);
        }
    }


}
