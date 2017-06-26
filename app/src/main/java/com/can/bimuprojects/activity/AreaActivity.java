package com.can.bimuprojects.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.AreaSelect.OnWheelChangedListener;
import com.can.bimuprojects.adapter.AreaSelect.WheelView;
import com.can.bimuprojects.adapter.ArrayWheelAdapter;

/**
 * Created by can on 2017/4/11.
 */

public class AreaActivity extends AreaBaseActivity implements View.OnClickListener, OnWheelChangedListener {

    private WheelView mViewProvince;
    private WheelView mViewCity;
    private WheelView mViewDistrict;

    private TextView tv_sure;//确定
    private ImageView iv_exit_area;//推出

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        params.width = metric.widthPixels;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(params);
        setUpViews();
        setUpListener();
        setUpData();
    }

    private void setUpViews() {
        mViewProvince = (WheelView) findViewById(R.id.id_province);
        mViewCity = (WheelView) findViewById(R.id.id_city);
        mViewDistrict = (WheelView) findViewById(R.id.id_district);
        tv_sure = (TextView) findViewById(R.id.tv_sure_area);
        iv_exit_area = (ImageView) findViewById(R.id.iv_exit_area);
    }

    private void setUpListener() {
        // 添加change事件
        mViewProvince.addChangingListener(this);
        // 添加change事件
        mViewCity.addChangingListener(this);
        // 添加change事件
        mViewDistrict.addChangingListener(this);
        // 添加onclick事件
        tv_sure.setOnClickListener(this);
        iv_exit_area.setOnClickListener(this);
    }

    private void setUpData() {
        initProvinceDatas();
        mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        // 设置可见条目数量
        mViewProvince.setVisibleItems(7);
        mViewCity.setVisibleItems(7);
        mViewDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        // TODO Auto-generated method stub
        if (wheel == mViewProvince) {
            updateCities();
        } else if (wheel == mViewCity) {
            updateAreas();
        } else if (wheel == mViewDistrict) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mViewCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[] { "" };
        }
        mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mViewDistrict.setCurrentItem(0);
        mCurrentDistrictName = areas[0];
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mViewProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mViewCity.setCurrentItem(0);
        updateAreas();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure_area://确定
                showSelectedResult();
                break;
            case R.id.iv_exit_area: //退出
                finish();
                break;
        }
    }

    private void showSelectedResult() {
        getIntent().putExtra("city",mCurrentProviceName+","+mCurrentCityName+","
                +mCurrentDistrictName);
        setResult(2,getIntent());
        finish();
    }
}
