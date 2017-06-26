package com.can.bimuprojects.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Entity.Other_search;
import com.can.bimuprojects.Module.Hook.DefaultErrorHook;
import com.can.bimuprojects.Module.Request.OtherSearchRequest;
import com.can.bimuprojects.Module.Response.OtherSearchResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.utils.UiUtils;
import com.can.bimuprojects.view.SearchView;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 搜索界面
 */
public class SearchActivity extends BaseActivity implements SearchView.SearchViewListener {

    private SearchView searchView;
    private ArrayAdapter<String> historyAdapter;
    private List<String> histories;
    private int deviceWidth;
    private Button btn_clear;
    private ListView lv_search_history;
    private LinearLayout ll_hot_search;
    private ImageView iv_back;
    private TextView tv_title;//标题

    private TextView tv_everyone_search ; //大家都在搜

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        setListener();
    }

    private void initData() {
        TextView emptyView = new TextView(this);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("无搜索记录");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setVisibility(View.GONE);
        emptyView.setTextColor(Color.parseColor("#999999"));
        emptyView.setTextSize(15);
        ((ViewGroup)lv_search_history.getParent()).addView(emptyView);
        lv_search_history.setEmptyView(emptyView);
        getHotData();
        initHistoryData();
    }

    private void initView() {
        setContentView(R.layout.activity_search);
        deviceWidth = AppUtils.getDeviceWidth(this);
        searchView = (SearchView) findViewById(R.id.custom_search_view);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        lv_search_history = (ListView) findViewById(R.id.lv_search_history);
        ll_hot_search = (LinearLayout) findViewById(R.id.ll_hot_search);
        iv_back = (ImageView) findViewById(R.id.iv_exit);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText(R.string.search);
        tv_everyone_search = (TextView) findViewById(R.id.tv_search_everyone_search);
    }

    private void setListener() {
        searchView.setSearchViewListener(this);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefUtils.getDefaultPref().edit().remove("histories").commit();
                if(null!=histories){
                    histories.clear();
                    historyAdapter.notifyDataSetChanged();
                }
            }
        });
        lv_search_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String text = histories.get(position);
                onSearch(text);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void initHistoryData() {
        histories = getHistoryData();
        if (histories != null) {
            historyAdapter = new ArrayAdapter<>(this, R.layout.search_history, histories);
            if (null != lv_search_history) {
                lv_search_history.setAdapter(historyAdapter);
            }
        }
    }


    private void getHotData() {
        OtherSearchRequest request = new OtherSearchRequest();
        request.setUid("");
        HttpUtils.postWithoutUid(MethodConstant.OTHER_SEARCH, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                OtherSearchResponse response = (OtherSearchResponse) receive.getResponse();
                if (response!=null&&response.getExe_success() == 1) {
                    List<Other_search> list = response.getOther_search();
                    setOtherSearchView(list);
                } else {
                    ToastUtils.showShort(SearchActivity.this, "请求失败");
                }
            }
        }, new DefaultErrorHook(), OtherSearchResponse.class);
    }

    private void setOtherSearchView(List<Other_search> hotList) {
        tv_everyone_search.setVisibility(View.VISIBLE);
        ll_hot_search.setVisibility(View.VISIBLE);
        LinearLayout ll = null;
        for (int i = 0; i < hotList.size(); i++) {
            int paddingH = UiUtils.dip2px(2);
            int paddingV = UiUtils.dip2px(3);
            if (i % 3 == 0) {
                ll = new LinearLayout(this);
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, UiUtils.dip2px(40)));
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.layout(0, UiUtils.dip2px(40) * i / 3, deviceWidth, UiUtils.dip2px(40) * i / 3 + UiUtils.dip2px(40));
            }
            final TextView textView = new TextView(this);
            textView.setBackgroundResource(R.drawable.selector_shape);
            textView.setLines(1);
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
            textView.setGravity(Gravity.CENTER);
            textView.setClickable(true);
            textView.setTextColor(ContextCompat.getColor(this,R.color.color_middle_text));
            textView.setText(hotList.get(i).getContent());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MobclickAgent.onEvent(SearchActivity.this,"ll_hot_search");
                    onSearch(textView.getText().toString().trim());
                }
            });
            textView.setPadding(paddingH, paddingV, paddingH, paddingV);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(deviceWidth / 3 - UiUtils.dip2px(3) * 4, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(UiUtils.dip2px(3), UiUtils.dip2px(3), UiUtils.dip2px(3), UiUtils.dip2px(3));
            textView.setLayoutParams(params);
            if (null != ll) {
                ll.addView(textView);
            }
            if (i % 3 == 0) {
                ll_hot_search.addView(ll);
            }
        }
    }

    private List<String> getHistoryData() {
        Set<String> histories = PrefUtils.get("histories");
        List<String> list = new ArrayList<>();
        if (null != histories) {
            list = new ArrayList<>(histories);
        }
        return list;
    }


    @Override
    public void onRefreshAutoComplete(String text) {

    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {
        if (!TextUtils.isEmpty(text)) {
            HashSet<String> historySet = (HashSet<String>) PrefUtils.get("histories");
            if (null == historySet) {
                historySet = new HashSet<>();
            }
            historySet.add(text);
            PrefUtils.put("histories", historySet);
            if(!histories.contains(text)){
                histories.add(text);
                historyAdapter.notifyDataSetChanged();
            }
//            Toast.makeText(this, "完成搜索", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra("content", text);
            startActivity(intent);
        }

    }


}
