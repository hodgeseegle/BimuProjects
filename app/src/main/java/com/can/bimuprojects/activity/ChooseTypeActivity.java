package com.can.bimuprojects.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.can.bimuprojects.Fragment.FindFragment;
import com.can.bimuprojects.R;

/**
 * Created by can on 2017/4/25.
 * 选择类型界面（如：街头小吃 西餐...）
 */

public class ChooseTypeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private LinearLayout ll;
    private ImageView iv_exit;
    //初始化view
    private void initView() {
        setContentView(R.layout.activity_choose_type);
        ll = (LinearLayout) findViewById(R.id.ll_choose_type);
        iv_exit = (ImageView) findViewById(R.id.iv_exit_choose_type);
    }

    //设置数据
    private void initData() {
         FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FindFragment fragment = new FindFragment();
        transaction.add(R.id.ll_choose_type, fragment);
        transaction.show(fragment);
        transaction.commit();
    }

    //设置监听
    private void setListener() {
        iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



}
