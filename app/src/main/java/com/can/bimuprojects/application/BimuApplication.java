package com.can.bimuprojects.application;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.utils.Log;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by can on 2017/4/8.
 */
public class BimuApplication extends MultiDexApplication {

    private static Context context;
    private static Handler handler;
    private static List<Activity> oList;

    @Override
    public void onCreate() {
        super.onCreate();
        UMShareAPI.get(this);
        context=this;
        NetworkManager.getInstance().init(this);
        handler=new Handler();
        oList = new ArrayList<>();
        //集合自定义事件测试
        MobclickAgent.setDebugMode( true );


        //配置微信
        PlatformConfig.setWeixin("wx83d33f0622eed5e7", "a653b801d4ffc5338d1e291ca36318b9");
        Config.IsToastTip = false;
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType. E_UM_NORMAL);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setResourcePackageName(R.class.getPackage().getName());
        //mPushAgent.setDebugMode(true);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.i("dog","device : "+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("dog","注册失败 : "+s+" , "+s1);
            }
        });

        //友盟自定义消息
        UmengMessageHandler messageHandler = new UmengMessageHandler(){
            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {
                new Handler(getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {
                        // 对于自定义消息，PushSDK默认只统计送达。若开发者需要统计点击和忽略，则需手动调用统计方法。
                        boolean isClickOrDismissed = true;
                        if(isClickOrDismissed) {
                            //自定义消息的点击统计
                            UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
                        } else {
                            //自定义消息的忽略统计
                            UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
                        }
                        Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                    }
                });
            }
        };
        mPushAgent.setMessageHandler(messageHandler);

}
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static Context getContext(){
        return context;
    }

    public static SharedPreferences getPreference(){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Handler getHandler(){
        return handler;
    }

    /**
     * 添加Activity
     */
    public void addActivity(Activity activity) {
// 判断当前集合中不存在该Activity
        if (!oList.contains(activity)) {
            oList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public static void removeActivity(Activity activity) {
//判断当前集合中存在该Activity
        if (oList.contains(activity)) {
            oList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }

    /**
     * 销毁所有的Activity
     */
    public static void removeALLActivity() {
        //通过循环，把集合中的所有Activity销毁
        for (Activity activity : oList) {
           activity.finish();
        }
    }
}
