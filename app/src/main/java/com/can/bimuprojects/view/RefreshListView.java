package com.can.bimuprojects.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.can.bimuprojects.R;
import com.can.bimuprojects.utils.DateUtils;
import com.can.bimuprojects.utils.PrefUtils;

/**
 * Created by can on 2017/4/12.
 * 下拉刷新 上拉加载更多
 */
public class RefreshListView extends ListView implements OnScrollListener{

	private View headerView;//headerView
	private ImageView iv_arrow;
	private ProgressBar pb_rotate;
	private TextView tv_state,tv_time;
	private View footerView;
	private int footerViewHeight;

	private int headerViewHeight;//headerView高

	private int downY;//按下时y坐标

	private final int PULL_REFRESH = 0;//下拉刷新的状态
	private final int RELEASE_REFRESH = 1;//松开刷新的状态
	private final int REFRESHING = 2;//正在刷新的状态
	private int currentState = PULL_REFRESH;

	private RotateAnimation upAnimation,downAnimation;

	private boolean isLoadingMore = false;//当前是否正在处于加载更多

	public RefreshListView(Context context) {
		super(context);
		init();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		setOnScrollListener(this);
		initHeaderView();
		initRotateAnimation();
		initFooterView();
	}


	/**
	 * 初始化headerView
	 */
	private void initHeaderView() {
		headerView = View.inflate(getContext(), R.layout.layout_header, null);
		iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
		pb_rotate = (ProgressBar) headerView.findViewById(R.id.pb_rotate);
		tv_state = (TextView) headerView.findViewById(R.id.tv_state);
		tv_time = (TextView) headerView.findViewById(R.id.tv_time);

		headerView.measure(0, 0);
		headerViewHeight = headerView.getMeasuredHeight();
		headerView.setPadding(0, -headerViewHeight, 0, 0);

		addHeaderView(headerView);
		String str = PrefUtils.get("refresh_time","");
		if(str.equals(""))
			tv_time.setText("最后刷新："+DateUtils.timestampToString(System.currentTimeMillis()/1000));
		else
		tv_time.setText("最后刷新："+DateUtils.timestampToString(str));
	}

	/**
	 * 初始化旋转动画
	 */
	private void initRotateAnimation() {
		upAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		upAnimation.setDuration(300);
		upAnimation.setFillAfter(true);
		downAnimation = new RotateAnimation(-180, -360,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		downAnimation.setDuration(300);
		downAnimation.setFillAfter(true);
	}

	private void initFooterView() {
		footerView = View.inflate(getContext(), R.layout.layout_footer, null);
		footerView.measure(0, 0);//主动通知系统去测量该view;
		footerViewHeight = footerView.getMeasuredHeight();
		footerView.setPadding(0, -footerViewHeight, 0, 0);
		addFooterView(footerView);
	}

	private float xDistance;
	private float yDistance;
	private float xLast;
	private float yLast;
	//滑动事件处理：当左右滑动的距离>上下滑动距离，不进行上下滑动
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				xDistance = yDistance = 0f;
				xLast = ev.getX();
				yLast = ev.getY();

				downY = (int) ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float curX = ev.getX();
				final float curY = ev.getY();

				xDistance += Math.abs(curX - xLast);
				yDistance += Math.abs(curY - yLast);
				xLast = curX;
				yLast = curY;

				if(xDistance > yDistance){
					return false;
				}
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downY = (int) ev.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				if(currentState==REFRESHING){
					break;
				}
				int moveY = (int) ev.getY();
				int diff = (moveY-downY)/2;
				int paddingTop = -headerViewHeight+diff;
				if(getFirstVisiblePosition()==0&&-headerViewHeight<paddingTop){
					if(paddingTop>0&&currentState==PULL_REFRESH){
						currentState = RELEASE_REFRESH;
						refreshHeaderView();
					}else if(paddingTop<0&&currentState==RELEASE_REFRESH){
						currentState = PULL_REFRESH;
						refreshHeaderView();
					}
					headerView.setPadding(0,paddingTop,0,0);
					return true;
				}
				break;
			case MotionEvent.ACTION_UP:
				 if (currentState==RELEASE_REFRESH) {
						 headerView.setPadding(0, 0, 0, 0);
						 currentState = REFRESHING;
						 refreshHeaderView();
						 if(listener!=null){
							 listener.onPullRefresh();
					 }
				}else if(currentState==PULL_REFRESH){
					 //隐藏headerView
					 headerView.setPadding(0, -headerViewHeight, 0, 0);
				 }
				break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 根据currentState来更新headerView
	 */
	private void refreshHeaderView(){
		switch (currentState) {
			case PULL_REFRESH:
				tv_state.setText("下拉可以刷新");
				iv_arrow.startAnimation(downAnimation);
				break;
			case RELEASE_REFRESH:
				tv_state.setText("松开立即刷新");
				iv_arrow.startAnimation(upAnimation);
				break;
			case REFRESHING:
				iv_arrow.clearAnimation();//因为向上的旋转动画有可能没有执行完
				iv_arrow.setVisibility(View.INVISIBLE);
				pb_rotate.setVisibility(View.VISIBLE);
				tv_state.setText("正在刷新...");
				PrefUtils.put("refresh_time",System.currentTimeMillis()/1000+"");
				break;
		}
	}

	/**
	 * 完成刷新操作，重置状态,在你获取完数据并更新完adater之后，去在UI线程中调用该方法
	 */
	public void completeRefresh(){
		if(isLoadingMore){
			//重置footerView状态
			footerView.setPadding(0, -footerViewHeight, 0, 0);
			isLoadingMore = false;
		}else {
			//重置headerView状态
			headerView.setPadding(0, -headerViewHeight, 0, 0);
			currentState = PULL_REFRESH;
			pb_rotate.setVisibility(View.INVISIBLE);
			iv_arrow.setVisibility(View.VISIBLE);
			tv_state.setText("下拉可以刷新");
			String lastTime  = PrefUtils.get("refresh_time",System.currentTimeMillis()/1000+"");
			tv_time.setText("最后刷新："+DateUtils.timestampToString(lastTime));
		}
	}

	private OnRefreshListener listener;
	public void setOnRefreshListener(OnRefreshListener listener){
		this.listener = listener;
	}
	public interface OnRefreshListener{
		void onPullRefresh();
		void onLoadingMore();
	}

	/**
	 * SCROLL_STATE_IDLE:闲置状态，就是手指松开
	 * SCROLL_STATE_TOUCH_SCROLL：手指触摸滑动，就是按着来滑动
	 * SCROLL_STATE_FLING：快速滑动后松开
	 */
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
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount) {
	}

}
