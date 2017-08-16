package com.can.bimuprojects.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.can.bimuprojects.R;

/**
 * Created by can on 2017/08/15.
 * 项目
 */

public class ProjectFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project,null);
        initView(view);
        setListener();
        initData();
        return view;
    }

    private TextView tv_title ;//标题
    /**
     * 初始化view
     * @param view
     */
    private void initView(View view) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tv_title.setText(getString(R.string.project));

        requestITData();
    }

    //请求网络数据
    private void requestITData() {

    }

    /**
     * 设置监听
     */
    private void setListener() {

    }


}
