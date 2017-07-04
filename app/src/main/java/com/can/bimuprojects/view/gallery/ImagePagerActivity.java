package com.can.bimuprojects.view.gallery;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.AddLoveListRequest;
import com.can.bimuprojects.Module.Request.BrandRequest;
import com.can.bimuprojects.Module.Request.SetUserNameRequest;
import com.can.bimuprojects.Module.Response.AddLoveListResponse;
import com.can.bimuprojects.Module.Response.OpenShopResponse;
import com.can.bimuprojects.Module.Response.SetUserNameResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.BrandActivity;
import com.can.bimuprojects.activity.MainActivity;
import com.can.bimuprojects.activity.OpenShopResultActivity;
import com.can.bimuprojects.activity.PersonalInformationProtectActivity;
import com.can.bimuprojects.adapter.BrandOpenShopAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.CircleDialog;

import java.util.ArrayList;
import java.util.List;

public class ImagePagerActivity extends FragmentActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index";

	private HackyViewPager mPager;
	private int pagerPosition;
	private TextView indicator;

	private TextView tv_open; //开店方案或前往心愿单

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initDialog();
		setListener();
		initData(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(isConsult){
			PrefUtils.putBoolean("update_consult",true);
			tv_open.setText(getString(R.string.entre_love_list));
			tv_open.setBackgroundColor(ContextCompat.getColor(this,R.color.color_zixun_brand));
		}
	}

	private Dialog dialog_login ; //登录弹窗
	//初始化view
	private void initView() {
		setContentView(R.layout.item_image_detail_pager);
		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		mPager = (HackyViewPager) findViewById(R.id.hvp_imagepager_activity);
		tv_open = (TextView) findViewById(R.id.tv_image_detail_openshop_process);
		dialog_login = AppUtils.showLoginDialog(this);
	}

	private Dialog dialog; //获取开店方案的dialog
	private ListView lv_dialog; //listview
	private TextView tv_dialog_cancle; //取消
	private TextView tv_dialog_sure; //确定
	private List<OpenShopResponse.DataBean> list_openshop = new ArrayList<>();//打开开店方案数据集合
	private boolean isHasOpenshopData = false; //是否请求过开店方案数据
	private BrandOpenShopAdapter adapter_dialog; //开店方案适配器
	private List<Boolean> list_boolean = new ArrayList<>(); //开店方案选择集合
	private LinearLayout ll_dialog; //选择先生女士
	private EditText et_dialog; //请输入您的姓氏
	private RadioGroup rg_dialog;
	private RadioButton rb_xiansheng;
	private RadioButton rb_nvshi;
	private int sex = 0; //0：先生 1：女士
	private ImageView iv_dialog;
	private TextView tv_dialog_open_shop_agree; //个人信息保护声明
	//初始化dialog
	private void initDialog() {
		dialog = new Dialog(this,R.style.style_dialog);
		View view_dialog = LayoutInflater.from(this).inflate(R.layout.dialog_open_shop_plan, null);
		lv_dialog = (ListView) view_dialog.findViewById(R.id.lv_brand_dialog);
		tv_dialog_cancle = (TextView) view_dialog.findViewById(R.id.tv_brand_dialog_cancle);
		tv_dialog_sure = (TextView) view_dialog.findViewById(R.id.tv_brand_dialog_sure);
		tv_dialog_open_shop_agree = (TextView) view_dialog.findViewById(R.id.tv_dialog_open_shop_agree);
		ll_dialog = (LinearLayout) view_dialog.findViewById(R.id.ll_dialog_open_shop_plan);
		et_dialog = (EditText) view_dialog.findViewById(R.id.et_dialog_open_shop_plan);
		rg_dialog = (RadioGroup) view_dialog.findViewById(R.id.rg_dialog_open_shop_plan);
		rb_xiansheng = (RadioButton) view_dialog.findViewById(R.id.rb_dialog_open_shop_plan_xiansheng);
		rb_nvshi = (RadioButton) view_dialog.findViewById(R.id.rb_dialog_open_shop_plan_nvshi);
		iv_dialog = (ImageView) view_dialog.findViewById(R.id.iv_dialog_open_shop_plan);
		if(Util.isOnMainThread())
			Glide.with(this).load(R.drawable.get_open_shop_plan).dontAnimate().into(iv_dialog);

		rg_dialog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int i) {
				if (i == R.id.rb_dialog_open_shop_plan_xiansheng) {//先生
					sex = 0;
				} else { //女士
					sex = 1;
				}
			}
		});
		lv_dialog.setOverScrollMode(View.OVER_SCROLL_NEVER);
		dialog.setContentView(view_dialog);
		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.dialogStyle);
		window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		WindowManager.LayoutParams params = window.getAttributes();
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		params.width = metric.widthPixels;
		params.height = metric.heightPixels;
		window.setAttributes(params);
		adapter_dialog = new BrandOpenShopAdapter(this, list_openshop, list_boolean);
		lv_dialog.setAdapter(adapter_dialog);
	}


	//设置监听
	private void setListener() {
		tv_open.setOnClickListener(this);
		tv_dialog_sure.setOnClickListener(this);
		tv_dialog_cancle.setOnClickListener(this);
		lv_dialog.setOnItemClickListener(this);
		tv_dialog_open_shop_agree.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.tv_image_detail_openshop_process: //获取开店方案或前往心愿单
				if(AppUtils.isNeedShowLoginDialog()){
					if(!dialog_login.isShowing()&&!this.isFinishing())
						dialog_login.show();
					break;
				}
				if(LoginUtils.getUserName().equals("")){
					ll_dialog.setVisibility(View.VISIBLE);
					iv_dialog.setVisibility(View.GONE);
				}else{
					ll_dialog.setVisibility(View.GONE);
					iv_dialog.setVisibility(View.VISIBLE);
				}
				if(isConsult){ //前往心愿单
					Intent intent = new Intent(this,MainActivity.class);
					intent.putExtra("love",true);
					startActivity(intent);
				}else { //获取开店方案
					if (!isHasOpenshopData)
						requestOpenShopData();
					else if (!dialog.isShowing())
						dialog.show();
				}
				break;
			case R.id.tv_dialog_open_shop_agree: //个人信息保护声明
				startActivity(new Intent(this,PersonalInformationProtectActivity.class));
				break;
			case R.id.tv_brand_dialog_cancle: //取消
				dialog.cancel();
				break;
			case R.id.tv_brand_dialog_sure: //确定
				dialog_sure();
				break;
			case R.id.fl_image_detail:
				finish();
				overridePendingTransition(0,
						android.R.anim.fade_out);
				break;
		}
	}

	/**
	 * 获取开店方案网络数据
	 */
	private void requestOpenShopData() {
		BrandRequest request = new BrandRequest();
		request.setBid(bid);
		request.setUid(LoginUtils.getUid());
		HttpUtils.postWithoutUid(MethodConstant.GET_OPEN_SHOP, request, new ResponseHook() {
			@Override
			public void deal(Context context, JsonReceive receive) {
				OpenShopResponse response = (OpenShopResponse) receive.getResponse();
				if (response != null) {
					isHasOpenshopData = true;
					if (!dialog.isShowing()) {
						dialog.show();
						list_openshop.addAll(response.getData());
						for (int i = 0; i < list_openshop.size(); i++) {
							list_boolean.add(i, true);
						}
						adapter_dialog.notifyDataSetChanged();
					}
				}
			}
		}, new ErrorHook() {
			@Override
			public void deal(Context context, VolleyError error) {

			}
		}, OpenShopResponse.class);
	}

	public Dialog showDialog(String content) {
		CircleDialog builder = new CircleDialog(this,content, View.inflate(this, R.layout.dialog_circle,null),R.style.circle_dialog);
		if(!this.isFinishing())
			builder.show();
		return builder;
	}

	/**
	 * 确定
	 */
	private void dialog_sure() {
		if (ll_dialog.getVisibility() == View.VISIBLE) {
			if (et_dialog.getText().toString()==null||et_dialog.getText().toString().equals("")||et_dialog.getText().toString().trim().equals("")) {
				ToastUtils.showShort(this,"请输入您的姓氏");
				return;
			}else {
				SetUserNameRequest request = new SetUserNameRequest();
				request.setUid(LoginUtils.getUid());
				request.setUser_name(et_dialog.getText().toString().trim());
				request.setUser_sex(sex + "");
				HttpUtils.postWithoutUid(MethodConstant.SET_USER_NAME, request, new ResponseHook() {
					@Override
					public void deal(Context context, JsonReceive receive) {
						SetUserNameResponse response = (SetUserNameResponse) receive.getResponse();
						if(response!=null){
							LoginUtils.setUserName(et_dialog.getText().toString().trim());
						}
					}
				}, new ErrorHook() {
					@Override
					public void deal(Context context, VolleyError error) {

					}
				}, SetUserNameResponse.class);
			}
		}
		PrefUtils.putBoolean("love_update",true);
		PrefUtils.putBoolean("update_consult",true);
		isConsult = true;
		consultShop(bid);
		list_boolean = adapter_dialog.getCheckState();
		for(int i =0;i<list_openshop.size();i++){
			if(list_boolean.get(i)) {
				consultShop(list_openshop.get(i).getBrand_id());
			}
		}
		dialog.dismiss();
		Intent intent_open_shop = new Intent(this, OpenShopResultActivity.class);
		intent_open_shop.putExtra("brand",bid);
		startActivity(intent_open_shop);
	}

	/**
	 * 咨询店铺
	 */
	private void consultShop(String id){
		AddLoveListRequest re = new AddLoveListRequest();
		re.setId(id);
		re.setType("3");
		re.setUid(LoginUtils.getUid());
		HttpUtils.postWithoutUid(MethodConstant.SET_LOVE_LIST, re, new ResponseHook() {
			@Override
			public void deal(Context context, JsonReceive receive) {

			}
		}, new ErrorHook() {
			@Override
			public void deal(Context context, VolleyError error) {

			}
		},AddLoveListResponse.class);
	}


	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		if(list_openshop.get(i)!=null) {
			Intent intent_dialog = new Intent(this, BrandActivity.class);
			intent_dialog.putExtra("index", list_openshop.get(i).getBrand_id());
			startActivity(intent_dialog);
		}
	}

	private boolean isFromBrand = false ; //是否从品牌图库进来
	private String bid;
	private boolean isConsult = false; //是否咨询过
	//初始化数据
	private void initData(Bundle savedInstanceState) {
		ArrayList<String> list = getIntent().getStringArrayListExtra("list");
		isFromBrand = getIntent().getBooleanExtra("brand",false);
		bid = getIntent().getStringExtra("bid");
		isConsult = getIntent().getBooleanExtra("consult",false);

		if(isFromBrand)
			tv_open.setVisibility(View.VISIBLE);
		else
			tv_open.setVisibility(View.GONE);

		if(isConsult){
			tv_open.setText(getString(R.string.entre_love_list));
			tv_open.setBackgroundColor(ContextCompat.getColor(this,R.color.color_zixun_brand));
		}else{
			tv_open.setText(getString(R.string.get_openshop_plan));
			tv_open.setBackgroundColor(ContextCompat.getColor(this,R.color.color_app_text_yes));
		}


		String [] strs = new String[list.size()];
		for(int i =0;i<list.size();i++){
			strs[i] = list.get(i);
		}
		ImagePagerAdapter mAdapter = new ImagePagerAdapter(
				getSupportFragmentManager(), strs);//////
		mPager.setAdapter(mAdapter);
		indicator = (TextView) findViewById(R.id.tv_indicator_imagepager);
		CharSequence text = getString(R.string.viewpager_indicator, 1, mPager
				.getAdapter().getCount());
		indicator.setText(text);
		// 更新下标
		mPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				CharSequence text = getString(R.string.viewpager_indicator,
						arg0 + 1, mPager.getAdapter().getCount());
				indicator.setText(text);
			}
		});
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}
		mPager.setCurrentItem(pagerPosition);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}
	private class ImagePagerAdapter extends FragmentStatePagerAdapter {

		public String[] fileList;

		public ImagePagerAdapter(FragmentManager fm, String[] fileList) {
			super(fm);
			this.fileList = fileList;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.length;
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList[position];
			return ImageDetailFragment.newInstance(url);
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0,
				android.R.anim.fade_out);
	}


}