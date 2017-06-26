package com.can.bimuprojects.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.can.bimuprojects.R;

/**
 * Created by can on 2017/4/12.
 * 加载更多
 */
public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    private View footerView;
    private int footerViewHeight;
    private boolean isLoadingMore = false;
    private static final int MAX_Y_OVERSCROLL_DISTANCE = 50;
    private int mMaxYOverscrollDistance;
    public LoadMoreListView(Context context) {
        super(context);
        initFooterView();
        initBounceListView();
        setOnScrollListener(this);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFooterView();
        initBounceListView();
        setOnScrollListener(this);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFooterView();
        initBounceListView();
        setOnScrollListener(this);
    }

    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.layout_footer, null);
        footerView.measure(0, 0);//主动通知系统去测量该view;
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        addFooterView(footerView);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState==OnScrollListener.SCROLL_STATE_IDLE
                && getLastVisiblePosition()==(getCount()-1) &&!isLoadingMore){
            isLoadingMore = true;
            footerView.setPadding(0, 0, 0, 0);//显示出footerView
            setSelection(getCount());//让listview最后一条显示出来

            if(listener!=null){
                listener.onLoadingMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
    public void completeRefresh(){
        if(isLoadingMore){
            footerView.setPadding(0, -footerViewHeight, 0, 0);
            isLoadingMore = false;
        }
    }
    private OnRefreshListener listener;
    public void setOnRefreshListener(OnRefreshListener listener){
        this.listener = listener;
    }
    public interface OnRefreshListener{
        void onLoadingMore();
    }
    private void initBounceListView(){
        final DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        final float density = metrics.density;
        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                   int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        //实现的本质就是在这里动态改变了maxOverScrollY的值
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }

}
