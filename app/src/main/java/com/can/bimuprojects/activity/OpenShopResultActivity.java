package com.can.bimuprojects.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.can.bimuprojects.R;

/**
 * Created by can on 2017/4/16.
 * 获取开店方案结果
 */

public class OpenShopResultActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_openshop_result);
        findViewById(R.id.btn_open_shop_result_look_love).setOnClickListener(new View.OnClickListener() { //查看心愿单
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OpenShopResultActivity.this,MainActivity.class);
                intent.putExtra("love",true);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_open_shop_result_look_brand).setOnClickListener(new View.OnClickListener() {//查看品牌页
            @Override
            public void onClick(View view) {
                String bid = getIntent().getStringExtra("brand");
                if(bid!=null){
                    Intent intent = new Intent(OpenShopResultActivity.this,BrandActivity.class);
                    intent.putExtra("index",bid);
                    startActivity(intent);
                }else
                finish();
            }
        });
    }
}
