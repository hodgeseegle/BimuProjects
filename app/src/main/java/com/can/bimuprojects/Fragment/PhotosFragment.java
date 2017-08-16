package com.can.bimuprojects.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.PhotosAdapter;
import com.can.bimuprojects.utils.PrefUtils;

import java.util.ArrayList;

/**
 * Created by can on 2017/6/13.
 * 图库fragment
 */

public class PhotosFragment extends Fragment {

    private GridView gv; //图片集合控件
    private ArrayList<String> imgLists ; //图片集合
    private PhotosAdapter adapter; //适配器

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos,container,false);
        gv = (GridView) view.findViewById(R.id.gv_photos);
        return view;
    }

    private String bid ;//品牌id
    private boolean consult ; //是否咨询过
    private String str_logo; //品牌logo
    private String name ; //品牌名称
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        imgLists = new ArrayList<>();
        imgLists = getArguments().getStringArrayList("list");
        bid = getArguments().getString("bid");
        consult = getArguments().getBoolean("consult");
        str_logo = getArguments().getString("logo");
        name = getArguments().getString("name");
        PrefUtils.putBoolean("update_consult",consult);
        if(imgLists!=null){
            adapter = new PhotosAdapter(getContext(),bid,consult,imgLists,str_logo,name);
            gv.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        consult = PrefUtils.getBoolean("update_consult",false);
        adapter.setData(consult);
    }
}

