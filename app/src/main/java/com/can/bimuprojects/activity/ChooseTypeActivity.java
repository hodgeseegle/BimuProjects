package com.can.bimuprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private ImageView iv_exit; //退出按钮
    private TextView tv_change ; //更换行业
    //初始化view
    private void initView() {
        setContentView(R.layout.activity_choose_type);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        tv_change = (TextView) findViewById(R.id.tv_right);
    }

    FindFragment fragment ;
    //设置数据
    private void initData() {
        tv_change.setText(getString(R.string.change_professing));
        tv_change.setVisibility(View.VISIBLE);

         FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment = new FindFragment();
        transaction.add(R.id.ll_choose_type, fragment);
        transaction.show(fragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==ActivityInterest.REQUEST_CODE&&resultCode==ActivityInterest.RESPONSE_CODE){
            if(fragment!=null){
                String cid = data.getStringExtra("cid");
                String str_project = data.getStringExtra("project");
                fragment.updateData(cid,str_project);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //设置监听
    private void setListener() {
        iv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseTypeActivity.this,ActivityInterest.class);
                intent.putExtra(ActivityInterest.CHANGE,true);
                startActivityForResult(intent,ActivityInterest.REQUEST_CODE);
            }
        });
    }



}
