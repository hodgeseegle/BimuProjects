package com.can.bimuprojects.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.GlideUtil;

/**
 * Created by can on 2017/4/12.
 */

public class SearchView extends LinearLayout implements View.OnClickListener {

    private EditText etInput;
    private ImageView ivDelete;
    private Context mContext;
    private SearchViewListener mListener;
    private ImageView iv_search;
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);
        initViews();
    }

    private void initViews() {
        iv_search = (ImageView) findViewById(R.id.iv_search_layout);
        GlideUtil.loadDrawableImg(mContext,R.drawable.img_search_home,iv_search);
        etInput = (EditText) findViewById(R.id.et_search);
        ivDelete = (ImageView) findViewById(R.id.search_iv_delete);
        ivDelete.setOnClickListener(this);
        etInput.addTextChangedListener(new EditChangedListener());
        etInput.setOnClickListener(this);
        etInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    notifyStartSearching(etInput.getText().toString());
                }
                return true;
            }
        });
    }

    /**
     * 通知监听者 进行搜索操作
     *
     */
    private void notifyStartSearching(String text) {
        if (mListener != null) {
            mListener.onSearch(etInput.getText().toString());
        }
        AppUtils.hideSoftInput(mContext);
    }



    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
                ivDelete.setVisibility(VISIBLE);
                //更新autoComplete数据
                if (mListener != null) {
                    mListener.onRefreshAutoComplete(charSequence + "");
                }
            }else {
                ivDelete.setVisibility(GONE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_iv_delete:
                etInput.setText("");
                ivDelete.setVisibility(GONE);
                break;
        }
    }


    public interface SearchViewListener {

        void onRefreshAutoComplete(String text);

        void onSearch(String text);

//        /**
//         * 提示列表项点击时回调方法 (提示/自动补全)
//         */
//        void onTipsItemClick(String text);
    }

}