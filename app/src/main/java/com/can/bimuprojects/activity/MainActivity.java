package com.can.bimuprojects.activity;


import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Fragment.ProjectFragment;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.inapp.IUmengInAppMsgCloseCallback;
import com.umeng.message.inapp.InAppMessageManager;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Fragment.FindFragment;
import com.can.bimuprojects.Fragment.HomePageFragment;
import com.can.bimuprojects.Fragment.LoveListFragment;
import com.can.bimuprojects.Fragment.MeFragment;
import com.can.bimuprojects.Module.Hook.DefaultErrorHook;
import com.can.bimuprojects.Module.Request.GetUserFollowRequest;
import com.can.bimuprojects.Module.Request.QueryUpdateRequest;
import com.can.bimuprojects.Module.Response.NotReadMsg;
import com.can.bimuprojects.Module.Response.QueryUpdateResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 主页
 */
public class MainActivity extends BaseActivity implements IUmengInAppMsgCloseCallback, View.OnClickListener {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_FIND = 1;
    private static final int FRAGMENT_LOVE = 2;
    private static final int FRAGMENT_MINE = 3;
    private static final int FRAGMENT_COUNT = 4; //Fragment总数目

    private int currentFragment = FRAGMENT_COUNT;

    private Fragment[] fragments = new Fragment[FRAGMENT_COUNT];
    private FragmentManager fragmentManager;

    private LinearLayout llTabs;  //底部标签的LinearLayout
    private ImageView ivHome, ivBargin, ivDiscovery, ivMine;
    private TextView tvHome, tvDiscovery, tvBargin, tvMine;
    private static View red_pot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setListener();
        showFragment(FRAGMENT_HOME);
       // queryUpdate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        findViewById(R.id.main_ll_home).setOnClickListener(this);
        findViewById(R.id.main_ll_discovery).setOnClickListener(this);
        findViewById(R.id.main_ll_bargin).setOnClickListener(this);
        findViewById(R.id.main_ll_mine).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //底部tab点击切换
            case R.id.main_ll_home:
                showFragment(FRAGMENT_HOME);
                break;
            case R.id.main_ll_discovery:
                MobclickAgent.onEvent(this,"main_ll_discovery");
//                if(PrefUtils.get("class_index","").equals("")){
//                    Intent intent = new Intent(this,ActivityInterest.class);
//                    startActivity(intent);
//                }else
                    showFragment(FRAGMENT_FIND);
                break;
            case R.id.main_ll_bargin:
                if(showLoginDialog())
                    break;
                MobclickAgent.onEvent(this,"main_ll_bargin");
                showFragment(FRAGMENT_LOVE);
                break;
            case R.id.main_ll_mine:
                if(showLoginDialog())
                    break;
                showFragment(FRAGMENT_MINE);
                break;
        }
    }

    public void init() {
        fragmentManager = getSupportFragmentManager();
        llTabs = (LinearLayout) findViewById(R.id.main_ll_tabs);
        ivBargin = (ImageView) findViewById(R.id.main_iv_bargin);
        ivHome = (ImageView) findViewById(R.id.main_iv_home);
        ivDiscovery = (ImageView) findViewById(R.id.main_iv_discovery);
        ivMine = (ImageView) findViewById(R.id.main_iv_mine);
        tvHome = (TextView) findViewById(R.id.main_tv_home);
        tvDiscovery = (TextView) findViewById(R.id.main_tv_discovery);
        tvBargin = (TextView) findViewById(R.id.main_tv_bargin);
        tvMine = (TextView) findViewById(R.id.main_tv_mine);
        red_pot = findViewById(R.id.red_pot);

        //友盟应用内消息
        InAppMessageManager.getInstance(this).showCardMessage(this, "main",  this);
    }

    //友盟推送 应用消息内 关闭插屏返回
    @Override
    public void onColse() {

    }

    private int mNotReadMsg=0;
    public void hasNotReadMsg(){
        String uid = LoginUtils.getLoginUid();
        GetUserFollowRequest request = new GetUserFollowRequest(uid);
        HttpUtils.postWithoutUid(MethodConstant.HAS_NOT_READ_MSG, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                NotReadMsg response = (NotReadMsg) receive.getResponse();
                if (response!=null&&response.getExe_success().equals("1")) {
                    mNotReadMsg = response.getNot_read_count();
                } else {
                    mNotReadMsg = 0;
                }
                if(mNotReadMsg!=0){
                    red_pot.setVisibility(View.VISIBLE);
                }else{
                    red_pot.setVisibility(View.INVISIBLE);
                }
            }
        }, new DefaultErrorHook(), NotReadMsg.class);
    }

    /**
     * 查询应用更新信息，分为强制更新和非强制更新
     */
    private void queryUpdate() {
        HttpUtils.postWithoutUid(MethodConstant.GET_UPDATE_INFO, new QueryUpdateRequest(1), new
                ResponseHook
                        () {

                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        final QueryUpdateResponse response = (QueryUpdateResponse) receive.getResponse();
                        if (response!=null&&response.getExe_success() == 1) {
                            String force_update = response.getForce_update()+"";
                            float apk_version = 0;
                            if(response.getApk_version()!=null&&!response.getApk_version().equals(""))
                                apk_version= (int)Math.floor(Double.parseDouble(response.getApk_version()));
                            String apk_url = "";
                            if(response.getApk_url()!=null)
                                apk_url = response.getApk_url().trim()+"";
                            String  update_text = response.getUpdate_text()+"";
                            float current_version = AppUtils.getAppVersionCode(MainActivity.this);
                            if(apk_version>current_version) {
                                if (force_update.equals("1")) {
                                    final String finalApk_url = apk_url;
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this)
                                            .setTitle("新版本更新提示")
                                            .setMessage(update_text)
                                            .setCancelable(true)
                                            .setPositiveButton(getString(R.string.update_noti), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    updateAPP(finalApk_url);
                                                }
                                            });
                                    AlertDialog alertDialog = dialog.create();
                                    alertDialog.show();
                                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff0000"));
                                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#999999"));
                                } else {
                                    final String finalApk_url1 = apk_url;
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this)
                                            .setTitle("更新提示")
                                            .setMessage(update_text)
                                            .setPositiveButton(getString(R.string.update_noti), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    updateAPP(finalApk_url1);
                                                }
                                            })
                                            .setNegativeButton("稍后再说", null);
                                    AlertDialog alertDialog = dialog.create();
                                    alertDialog.show();
                                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff0000"));
                                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#999999"));
                                }
                            }
                        }
                    }
                }, new DefaultErrorHook(), QueryUpdateResponse.class);
    }

    private void updateAPP(String apkUrl) {
        new DownloadTask().execute(apkUrl);
    }

    private long long_home =0; //双击home返回顶部的time
    private long long_find =0; //双击find返回顶部的time
    /**
     * 显示某个Fragment
     *
     * @param fragmentIndex
     */
    public void showFragment(int fragmentIndex) {
        if (currentFragment == fragmentIndex){
            if(currentFragment==0){
                if (long_home <= 0) {
                    long_home = System.currentTimeMillis();
                } else {
                    long currentClickTime = System.currentTimeMillis();
                    if (currentClickTime - long_home < 1000) {
                        ((HomePageFragment)(fragments[fragmentIndex])).setListView2Top();
                    } else {
                        long_home = currentClickTime;
                    }
                }
            }else if(currentFragment==1){
                if (long_find <= 0) {
                    long_find = System.currentTimeMillis();
                } else {
                    long currentClickTime = System.currentTimeMillis();
                    if (currentClickTime - long_find < 1000) {
                        ((ProjectFragment)(fragments[fragmentIndex])).setListView2Top();
                    } else {
                        long_find = currentClickTime;
                    }
                }
            }
            return;
        }
        currentFragment = fragmentIndex;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragments(transaction);
        changeTab(fragmentIndex);
        switch (fragmentIndex) {
            case FRAGMENT_HOME:
                if (fragments[fragmentIndex] == null) {
                    fragments[fragmentIndex] = new HomePageFragment();
                    transaction.add(R.id.main_ll_content, fragments[fragmentIndex]);
                }
                transaction.show(fragments[fragmentIndex]);
                transaction.commit();
                break;

            case FRAGMENT_FIND:
                    if (fragments[fragmentIndex] == null) {
                        fragments[fragmentIndex] = new ProjectFragment();
                        transaction.add(R.id.main_ll_content, fragments[fragmentIndex]);
                    }
                transaction.show(fragments[fragmentIndex]);
                transaction.commit();
                break;

            case FRAGMENT_LOVE:
                if (fragments[fragmentIndex] == null) {
                    fragments[fragmentIndex] = new LoveListFragment();
                    transaction.add(R.id.main_ll_content, fragments[fragmentIndex]);
                }
                transaction.show(fragments[fragmentIndex]);
                transaction.commit();
                break;

            case FRAGMENT_MINE:
                if (fragments[fragmentIndex] == null) {
                    fragments[fragmentIndex] = new MeFragment();
                    transaction.add(R.id.main_ll_content, fragments[fragmentIndex]);
                }
                transaction.show(fragments[fragmentIndex]);
                transaction.commit();
                break;
        }
    }

    private void changeTab(int fragmentIndex) {
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.img_search).placeholder(R.drawable.img_search).into(ivHome);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.discovery).placeholder(R.drawable.discovery).into(ivDiscovery);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.img_love_grey).placeholder(R.drawable.img_love_grey).into(ivBargin);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.img_mine).placeholder(R.drawable.img_mine).into(ivMine);
        tvMine.setTextColor(ContextCompat.getColor(this,R.color.color_app_text_no));
        tvHome.setTextColor(ContextCompat.getColor(this,R.color.color_app_text_no));
        tvDiscovery.setTextColor(ContextCompat.getColor(this,R.color.color_app_text_no));
        tvBargin.setTextColor(ContextCompat.getColor(this,R.color.color_app_text_no));

        switch (fragmentIndex) {
            case FRAGMENT_HOME:
                tvHome.setTextColor(ContextCompat.getColor(this,R.color.color_app_bg));
                if(Util.isOnMainThread())
                    Glide.with(this).load(R.drawable.img_search_blue).placeholder(R.drawable.img_search_blue).into(ivHome);
                break;
            case FRAGMENT_FIND:
                tvDiscovery.setTextColor(ContextCompat.getColor(this,R.color.color_app_bg));
                if(Util.isOnMainThread())
                    Glide.with(this).load(R.drawable.discovery_red).placeholder(R.drawable.discovery_red).into(ivDiscovery);
                break;
            case FRAGMENT_LOVE:
                tvBargin.setTextColor(ContextCompat.getColor(this,R.color.color_app_bg));
                if(Util.isOnMainThread())
                    Glide.with(this).load(R.drawable.img_love_blue).placeholder(R.drawable.img_love_blue).into(ivBargin);
                break;
            case FRAGMENT_MINE:
                tvMine.setTextColor(ContextCompat.getColor(this,R.color.color_app_bg));
                if(Util.isOnMainThread())
                    Glide.with(this).load(R.drawable.img_mine_blue).placeholder(R.drawable.img_mine_blue).into(ivMine);
                break;
        }
    }


    private void hideAllFragments(FragmentTransaction ft) {
        for (Fragment fg : fragments) {
            if (fg != null) {
                ft.hide(fg);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        float height = llTabs.getHeight();
        ObjectAnimator animator = ObjectAnimator.ofFloat(llTabs, "translationY", height, 0);
        animator.setDuration(700);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        //animator.start();
        if(!LoginUtils.getLoginStatus())
        hasNotReadMsg();
    }

    private long lastClickTime = 0;

    @Override
    public void onBackPressed() {

        if (lastClickTime <= 0) {
            ToastUtils.showShort(this, "再按一次退出");
            lastClickTime = System.currentTimeMillis();
        } else {
            long currentClickTime = System.currentTimeMillis();
            if (currentClickTime - lastClickTime < 1000) {
                removeALLActivity();
            } else {
                ToastUtils.showShort(this, "再按一次退出");
                lastClickTime = currentClickTime;
            }
        }
    }



    class DownloadTask extends AsyncTask<String, Integer, File> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("下载中");
            progressDialog.setProgress(0);
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.show();
            super.onPreExecute();
        }


        @Override
        protected File doInBackground(String... params) {
            File file = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                int length = conn.getContentLength();
                int downLength = 0;
                InputStream is = conn.getInputStream();
                File dir = new File(Environment.getExternalStorageDirectory(),
                        "bimu");
                if (!dir.exists())
                    dir.mkdir();
                file = new File(dir, "new.apk");
                FileOutputStream fos = new FileOutputStream(file);
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buff = new byte[1024];
                int l;
                while ((l = bis.read(buff)) != -1) {
                    fos.write(buff, 0, l);
                    downLength += l;
                    publishProgress(100*downLength / length);
                }
                fos.close();
                bis.close();
                is.close();
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                progressDialog.dismiss();
                this.cancel(true);
            }
            return file;
        }

        @Override
        protected void onPostExecute(File file) {
            progressDialog.dismiss();
            AppUtils.installApk(MainActivity.this, file);
            super.onPostExecute(file);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtils.cancelAll(this);
    }

    //为了从登陆界面点击返回键直接结束应用
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(AppConstant.QUIT_LOGIN,false)){
            finish();
        }else if(intent.getBooleanExtra("love",false)){
                showFragment(FRAGMENT_LOVE);
        }else if(intent.getBooleanExtra("find",false)){
            showFragment(FRAGMENT_FIND);
        }
    }
}



