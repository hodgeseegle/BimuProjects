package com.can.bimuprojects.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.activity.ChoiceRecommendActivity;
import com.can.bimuprojects.activity.MainActivity;
import com.can.bimuprojects.activity.RankingActivity;
import com.can.bimuprojects.adapter.HomeHotBrandAdapter;
import com.can.bimuprojects.adapter.InspectionAdapter;
import com.can.bimuprojects.utils.GlideUtil;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ViewUtil;
import com.can.bimuprojects.view.BrandGridView;
import com.can.bimuprojects.view.NoScrollListView;
import com.umeng.analytics.MobclickAgent;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.FindProjectTotalRequest;
import com.can.bimuprojects.Module.Request.HomePagerRequest;
import com.can.bimuprojects.Module.Response.FindProjectTotalResponse;
import com.can.bimuprojects.Module.Response.HomePagerResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.activity.ActivityInterest;
import com.can.bimuprojects.activity.ArticleDetailActivity;
import com.can.bimuprojects.activity.BrandActivity;
import com.can.bimuprojects.activity.ChooseTypeActivity;
import com.can.bimuprojects.activity.ExerciseActivity;
import com.can.bimuprojects.activity.FindProjectResultActivity;
import com.can.bimuprojects.activity.SearchActivity;
import com.can.bimuprojects.activity.SpecialActivity;
import com.can.bimuprojects.activity.SpecialArticleActivity;
import com.can.bimuprojects.adapter.HomeSpecialAdapter;
import com.can.bimuprojects.adapter.ViewPagerAdapter_Home;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.utils.UiUtils;
import com.can.bimuprojects.view.CircleDialog;
import com.can.bimuprojects.view.RefreshListView;
import com.can.bimuprojects.view.SeekBarRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by can on 2017/4/8.
 * 首页fragment
 */

public class HomePageFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, RefreshListView.OnRefreshListener, ViewPager.OnPageChangeListener,  AdapterView.OnItemClickListener, View.OnTouchListener,  ViewTreeObserver.OnScrollChangedListener {


    public static final int TYPE_REFRESH = 0x111; //刷新
    public static final int TYPE_LOADING = 0x222; //加载
    public static final int TYPE_AUTO_PLAY = 0x333; //自动轮播

    private String interest_id = ""; //选择兴趣行业返回的id
    private String str_area = "10平方米"; //面积

    private ViewPager vp_home;//头部显示控件
    private RefreshListView rlv; //下拉刷新上拉加载

    private ViewPagerAdapter_Home vp_adapter;//头部滚动适配器
    private List<HomePagerResponse.SlidingImgBean> list_head ; //头部数据

    private LinearLayout ll_home_container ; //滚动的圆点

    private RelativeLayout rl_money, rl_area, rl_interest;//显示:投资金额，面积大小，兴趣行业
    private TextView tv_money, tv_area, tv_interest;//投资金额，面积大小，兴趣行业，开店地址
    private LinearLayout ll_3_tag ; //3个标记
    private LinearLayout ll_search; //搜索框

    private LinearLayout rl_find_project_choose_area; //显示：面积大小选择
    private SeekBarRelativeLayout sb_single;//单向滑动
    private TextView tv_no_shop;//显示：无铺面

    private ImageView iv_find_project;//按钮：找项目
    private TextView tv_find_project ; //找项目的显示数量

    private TextView tv_lookall_special ; //查看全部：专题
    private BrandGridView lv_special ; //专题的横向listview
    private List<HomePagerResponse.ListBean> list_special ; //专题的数据集合
    private HomeSpecialAdapter adapter_special ; //专题的适配器

    private NoScrollListView lv_interest ; //猜你感兴趣的listview->热门品牌
    private List<HomePagerResponse.BrandlistBean.DataBean> list_interest ; //猜你感兴趣的数据集合
    private List<HomePagerResponse.BrandlistBean> list_guess ; //猜你感兴趣总数据
    private HomeHotBrandAdapter adapter_interest; //猜你感兴趣的数据集合

    private List<HomePagerResponse.InspectionlogBean> list_inspection ; //精选考察攻略的数据集合
    private InspectionAdapter adapter_inspecition; //精选考察攻略的适配器

    private int page =0; //精选考察攻略的请求页数
    private boolean hasMore =false ; //是否有更多的数据

    private TextView tv_select_raiders ; //精选考察攻略隐藏控件
    private TextView tv_choose_area_sure ; //确认选择面积

    private ImageView iv_search ; //搜索图标

    private Handler handler = new Handler(new Handler.Callback() { //主线程
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case TYPE_REFRESH: //下拉刷新
                    rlv.completeRefresh();
                    if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                        ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
                        break;
                    }else{
                        rlv.completeRefresh();
                        page = 0;
                        requestITdata(page,false);
                    }
                    break;
                case TYPE_AUTO_PLAY:
                    vp_home.setCurrentItem(vp_home.getCurrentItem() + 1);
                    break;
                case TYPE_LOADING: //上拉加载
                    rlv.completeRefresh();
                    if (!AppUtils.isNetworkOK(BimuApplication.getContext())) {
                        ToastUtils.showShort(BimuApplication.getContext(), "网络无法连接");
                        break;
                    }else{
                        page++;
                        requestITdata(page,true);
                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepager, container, false);
        initView(view);
        initHeadView();
        initDialog();
        setListener();
        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean flag = PrefUtils.getBoolean("update_home",false);
        if(flag){
            page = 0;
            requestITdata(page,false);
        }
        PrefUtils.putBoolean("update_home",false);
    }

    private Dialog dialog_money ; //金额弹窗
    private LinearLayout ll_moneys ; //金额展示控件
    LinearLayout rowLayout = null; //金额子控件
    private String [] strs_money ; //金额数组
    private String str_money ; //金额
    private int int_money ; //金额
    //弹窗初始化
    private void initDialog() {
        strs_money = getResources().getStringArray(R.array.money_lists);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        dialog_money = new Dialog(getContext(),R.style.MyDialogStyleBottom);
        View sure = LayoutInflater.from(getContext()).inflate(R.layout.dialog_chose_money,null);
        ll_moneys = (LinearLayout) sure.findViewById(R.id.ll_home_moneys);
        sure.findViewById(R.id.btn_choose_money).setOnClickListener(this);
        Window wm_complain = dialog_money.getWindow();
        wm_complain.setContentView(sure);
        WindowManager.LayoutParams params_complain = wm_complain.getAttributes();
        params_complain.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        params_complain.width = metrics.widthPixels;
        params_complain.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        wm_complain.setAttributes(params_complain);
        str_money = strs_money[0];
        int_money = 0;
        addAllMoneyTextViews();
    }

    //添加金额
    private void addAllMoneyTextViews() {
        ll_moneys.removeAllViews();
        for(int i =0 ;i<strs_money.length;i++){
            addMoneyTextView(i,strs_money[i]);
        }
    }

    /**
     * 初始化控件
     */
    private void initView(View view) {
        rlv = (RefreshListView) view.findViewById(R.id.rlv_home_pager);
        tv_select_raiders = (TextView) view.findViewById(R.id.tv_home_select_raiders);
        ll_search = (LinearLayout) view.findViewById(R.id.ll_home_head_search_click);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        GlideUtil.loadDrawableImg(getContext(),R.drawable.img_search_home,iv_search);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        list_head = new ArrayList<>();
        list_inspection = new ArrayList<>();
        adapter_inspecition = new InspectionAdapter(getContext(),list_inspection);
        rlv.setAdapter(adapter_inspecition);
        sb_single.initSeekBar();
        handler.post(switchTask); //打开自动轮播
        requestITdata(page,false);
    }

    private LinearLayout ll_item1,ll_item2,ll_item3,ll_item4; //全部品牌，行业攻略，实时排行，精选推荐
    private ImageView iv_item1,iv_item2,iv_item3,iv_item4 ; //同上
    /**
     * 初始化头部view
     */
    private void initHeadView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.headview_homepager, null);
        rlv.addHeaderView(view);

        ll_item1 = (LinearLayout) view.findViewById(R.id.ll_item1);
        ll_item2 = (LinearLayout) view.findViewById(R.id.ll_item2);
        ll_item3 = (LinearLayout) view.findViewById(R.id.ll_item3);
        ll_item4 = (LinearLayout) view.findViewById(R.id.ll_item4);
        iv_item1 = (ImageView) view.findViewById(R.id.iv_item1);
        iv_item2 = (ImageView) view.findViewById(R.id.iv_item2);
        iv_item3 = (ImageView) view.findViewById(R.id.iv_item3);
        iv_item4 = (ImageView) view.findViewById(R.id.iv_item4);
        GlideUtil.loadDrawableImg(getContext(),R.drawable.img_all_brand,iv_item1);
        GlideUtil.loadDrawableImg(getContext(),R.drawable.img_trade,iv_item2);
        GlideUtil.loadDrawableImg(getContext(),R.drawable.img_time_ranking,iv_item3);
        GlideUtil.loadDrawableImg(getContext(),R.drawable.img_hot_recommend,iv_item4);

        ll_home_container = (LinearLayout)view.findViewById(R.id.ll_home_container);
        ll_3_tag = (LinearLayout) view.findViewById(R.id.ll_home_head_three);
        vp_home = (ViewPager) view.findViewById(R.id.vp_home_pager);
        rl_money = (RelativeLayout) view.findViewById(R.id.rl_home_find_project_money);
        rl_area = (RelativeLayout) view.findViewById(R.id.rl_find_project_area);
        rl_interest = (RelativeLayout) view.findViewById(R.id.rl_find_project_interest);
        tv_area = (TextView) view.findViewById(R.id.tv_find_project_area);
        tv_interest = (TextView) view.findViewById(R.id.tv_find_project_interest);
        tv_money = (TextView) view.findViewById(R.id.tv_find_project_money);
        rl_find_project_choose_area = (LinearLayout) view.findViewById(R.id.rl_home_find_project_area_choose);
        tv_choose_area_sure = (TextView) view.findViewById(R.id.tv_home_find_project_sure_shop);
        tv_no_shop = (TextView) view.findViewById(R.id.tv_home_find_project_no_shop);
        sb_single = (SeekBarRelativeLayout) view.findViewById(R.id.sb_single);
        iv_find_project = (ImageView) view.findViewById(R.id.iv_home_find_project);
        tv_find_project = (TextView) view.findViewById(R.id.tv_home_find_project);
        ImageView iv_home_money = (ImageView) view.findViewById(R.id.iv_home_money);
        ImageView iv_home_area = (ImageView) view.findViewById(R.id.iv_home_area);
        ImageView iv_home_interest = (ImageView) view.findViewById(R.id.iv_home_interest);
        if(Util.isOnMainThread()){
            Glide.with(getContext()).load(R.drawable.money).into(iv_home_money);
            Glide.with(getContext()).load(R.drawable.shop).into(iv_home_area);
            Glide.with(getContext()).load(R.drawable.interest).into(iv_home_interest);
            Glide.with(getContext()).load(R.drawable.img_home_btn_bg).into(iv_find_project);
        }
        //头部大图
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(metrics.widthPixels, (int) (metrics.widthPixels*1.067));
        vp_home.setLayoutParams(params);
        initSpecialView();
    }

    /**
     * 添加footview
     */
    private void addFootView(){
        View view_foot =  LayoutInflater.from(getContext()).inflate(R.layout.item_tv_foot,null);
        view_foot.findViewById(R.id.tv_item_tv_foot).setOnClickListener(this);
        rlv.addFooterView(view_foot);
    }

    private LinearLayout ll_head_specail,ll_head_interest;//专题和猜你感兴趣
    private LinearLayout ll_special_tags ; //猜你感兴趣标签
    private HorizontalScrollView hl_special;
    /**
     * 初始化专题和猜你感兴趣view
     */
    private void initSpecialView() {
        list_special = new ArrayList<>();
        list_interest = new ArrayList<>();
        list_guess = new ArrayList<>();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.headview_special,null);
        hl_special = (HorizontalScrollView) view.findViewById(R.id.hl_special);
        ll_special_tags = (LinearLayout) view.findViewById(R.id.ll_guess_interest);
        ll_head_interest = (LinearLayout) view.findViewById(R.id.ll_home_head_interest);
        ll_head_specail = (LinearLayout) view.findViewById(R.id.ll_home_head_special);
        tv_lookall_special = (TextView) view.findViewById(R.id.tv_look_all_special);
        lv_special = (BrandGridView) view.findViewById(R.id.lv_home_special);
        adapter_special = new HomeSpecialAdapter(getContext(),list_special);
        lv_special.setAdapter(adapter_special);
        lv_interest = (NoScrollListView) view.findViewById(R.id.lv_home_guess_interest);
        adapter_interest = new HomeHotBrandAdapter(getContext(),list_interest);
        lv_interest.setAdapter(adapter_interest);
        rlv.addHeaderView(view);
        addFootView();
        initSelectRaidersView();
    }

    private View view_selectRaiders;//精选考察攻略标题
    /**
     * 精选考察攻略标题
     */
    private void initSelectRaidersView() {
        view_selectRaiders = LayoutInflater.from(getContext()).inflate(R.layout.headview_selectraiders,null);
       // rlv.addHeaderView(view_selectRaiders);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        rl_interest.setOnClickListener(this);
        rl_area.setOnClickListener(this);
        rl_money.setOnClickListener(this);
        rl_find_project_choose_area.setOnClickListener(this);
        tv_no_shop.setOnClickListener(this);
        sb_single.setOnSeekBarChangeListener(this);
        iv_find_project.setOnClickListener(this);
        rlv.setOnRefreshListener(this);
        vp_home.addOnPageChangeListener(this);
        tv_lookall_special.setOnClickListener(this);
        tv_choose_area_sure.setOnClickListener(this);
        rlv.getViewTreeObserver().addOnScrollChangedListener(this);
        rlv.setOnItemClickListener(this);
        ll_search.setOnClickListener(this);
        lv_special.setOnItemClickListener(this);
        lv_interest.setOnItemClickListener(this);
        ll_item1.setOnClickListener(this);
        ll_item2.setOnClickListener(this);
        ll_item3.setOnClickListener(this);
        ll_item4.setOnClickListener(this);

        vp_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        y1= motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = motionEvent.getX();
                        y2 = motionEvent.getY();
                        break;
                    case  MotionEvent.ACTION_UP :
                        x2 = motionEvent.getX();
                        y2 = motionEvent.getY();
                        if(Math.abs(x1-x2)<=10&&Math.abs(y1-y2)<=10){
                            int item = vp_home.getCurrentItem();
                            int position = item%list_head.size();
                            int type = list_head.get(item%list_head.size()).getType();
                            if(type==4){ //活动
                                Intent intent = new Intent(getContext(),ExerciseActivity.class);
                                intent.putExtra("id",list_head.get(position).getId());
                                startActivity(intent);
                            }else if(type==3){ //文章
                                Intent intent = new Intent(getContext(),ArticleDetailActivity.class);
                                intent.putExtra("id",list_head.get(position).getId());
                                startActivity(intent);
                            }else if(type==5){ //品牌
                                Intent intent = new Intent(getContext(),BrandActivity.class);
                                intent.putExtra("index",list_head.get(position).getId());
                                startActivity(intent);
                            }else if(type==100){ //网页链接
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                String url = list_head.get(position).getId();
                                Uri uri = Uri.parse(url);
                                intent.setData(uri);
                                Pattern pattern = Pattern
                                        .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
                                if(pattern.matcher(url).matches())
                                    startActivity(intent);
                            }
                        }
                        break ;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_home_find_project_money: //点击:投资金额
                MobclickAgent.onEvent(getContext(),"rl_home_find_project_money");
               // rl_find_project_choose_money.setVisibility(View.VISIBLE);
                if(!dialog_money.isShowing()&&!getActivity().isFinishing())
                    dialog_money.show();
                break;
            case R.id.rl_find_project_area: //点击：面积大小
                MobclickAgent.onEvent(getContext(),"rl_find_project_area");
                rl_find_project_choose_area.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_find_project_interest://点击：兴趣行业
                MobclickAgent.onEvent(getContext(),"rl_find_project_interest");
                Intent intent_interest = new Intent(getContext(), ActivityInterest.class);
                intent_interest.putExtra("more",true);
                startActivityForResult(intent_interest,1);
                break;
            case R.id.btn_choose_money://点击：确定投资金额
                if(str_money!=null) {
                    Map<String, String> map_value = new HashMap<>();
                    map_value.put("money", str_money);
                    MobclickAgent.onEventValue(getContext(), "rl_home_find_project_money_choose", map_value, 100);
                }
                if(int_money!=0) {
                    str_money = str_money.replaceAll("¥","");
                }
                tv_money.setText(str_money);
                dialog_money.dismiss();
                setTotalProject();
                break;
            case R.id.tv_home_find_project_sure_shop://点击：确定选择面积
                    tv_area.setText(str_area);
                if(str_area!=null) {
                    Map<String, String> map_value = new HashMap<>();
                    map_value.put("area", str_area);
                    MobclickAgent.onEventValue(getContext(), "rl_home_find_project_area_choose", map_value, 100);
                }
                rl_find_project_choose_area.setVisibility(View.GONE);
                setTotalProject();
                break;
            case R.id.tv_home_find_project_no_shop: //点击：没有铺面
                tv_area.setText("暂无");
                rl_find_project_choose_area.setVisibility(View.GONE);
                setTotalProject();
                break;
            case R.id.iv_home_find_project://点击：找项目
                MobclickAgent.onEvent(getContext(),"btn_home_find_project");
                if(hasProject)
                    findProject();
                break;
            case R.id.ll_home_head_search_click: //点击：搜索栏
                MobclickAgent.onEvent(getContext(), "ll_home_head_search_click");
                Intent intent_search = new Intent(getContext(), SearchActivity.class);
                startActivity(intent_search);
                break;
            case R.id.tv_look_all_special://点击：查看全部 专题
                MobclickAgent.onEvent(getContext(), "tv_look_all_special");
                Intent intent_special = new Intent(getContext(), SpecialActivity.class);
                startActivity(intent_special);
                break;
            case R.id.tv_item_tv_foot://查看全部品牌
                Intent intent = new Intent(getContext(), FindProjectResultActivity.class);
                intent.putExtra("amount","");
                intent.putExtra("cid","");
                intent.putExtra("area","");
                intent.putExtra("interest","");
                intent.putExtra("isFind",true);
                startActivity(intent);
                break;
            case R.id.ll_item1: //全部品牌:跳转到项目
                Intent intent_find = new Intent(getContext(), MainActivity.class);
                intent_find.putExtra("find",true);
                startActivity(intent_find);
                break;
            case R.id.ll_item2: //行业攻略
                Intent intent_trade = new Intent(getContext(),ActivityInterest.class);
                intent_trade.putExtra(ActivityInterest.TRADE,true);
                startActivity(intent_trade);
                break;
            case R.id.ll_item3: //实时排行
                startActivity(new Intent(getContext(), RankingActivity.class));
                break;
            case R.id.ll_item4: //精选推荐
                startActivity(new Intent(getContext(), ChoiceRecommendActivity.class));
                break;
        }
    }

    private boolean hasProject = false ; //是否有项目
    private void setTotalProject(){
        FindProjectTotalRequest request = new FindProjectTotalRequest();
        request.setUid(LoginUtils.getUid());
        String amount = tv_money.getText().toString();
        if(amount!=null){
            amount = amount.trim().replace("万","").replaceAll("以上","").replaceAll("以下","");
            if(amount.equals("10")) {
                amount = "0.1-10";
            }
            if(amount.equals("不限")) {
                amount = "0-1000";
            }
            if(amount.equals("100")){
                amount = "100-10000";
            }
        }
        String area = tv_area.getText().toString();
        if(area!=null){
            if(area.equals("暂无"))
                area = "";
        }
        request.setArea(area);
        request.setAmount(amount);
        request.setCid(interest_id);
        request.setType("0");
        request.setPage("0");
        HttpUtils.postWithoutUid(MethodConstant.GET_FIND_PROJECT, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                FindProjectTotalResponse reponse = (FindProjectTotalResponse) receive.getResponse();
                if(reponse!=null){
                    String total = reponse.getTotal();
                    if(!total.equals("0")){
                        hasProject = true;
                        tv_find_project.setText("发现"+total+"个好项目");
                    }else{
                        hasProject = false;
                        tv_find_project.setText("搜索");
                    }
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
            }
        }, FindProjectTotalResponse.class);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode==1) {  //选择兴趣行业的返回
            tv_interest.setText(data.getStringExtra("string"));
            interest_id = data.getStringExtra("class");
            setTotalProject();
        }
    }

    /**
     * 跳转到找项目界面
     */
    private void findProject() {
        if(tv_money.getText().toString().equals("")&&tv_area.getText().toString().equals("")&&tv_interest.getText().equals("")){
            showDialog("请选择条件");
        }else{
            Intent intent = new Intent(getContext(), FindProjectResultActivity.class);
            intent.putExtra("amount",tv_money.getText().toString());
            intent.putExtra("cid",interest_id);
            intent.putExtra("area",tv_area.getText().toString());
            intent.putExtra("interest",tv_interest.getText().toString());
            startActivity(intent);
        }
    }
    public Dialog showDialog(String content) {
        CircleDialog builder = new CircleDialog(getContext(),content,View.inflate(getContext(),R.layout.dialog_circle,null),R.style.circle_dialog);
        builder.show();
        return builder;
    }

    /**
     * 请求网络数据
     */
    private void requestITdata(int p, final boolean loadMore) {
        HomePagerRequest request = new HomePagerRequest();
        request.setUid(LoginUtils.getLoginUid());
        request.setP(""+p);
        HttpUtils.postWithoutUid(MethodConstant.GET_HOMEPAGER, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                HomePagerResponse response = (HomePagerResponse) receive.getResponse();
                if (response != null && response.getExe_success() == 1) {
                    if(page==0){
                        list_inspection.clear();
                        list_special.clear();
                        list_interest.clear();
                        list_head.clear();
                        ll_home_container.removeAllViews();
                        ll_3_tag.removeAllViews();
                        ll_special_tags.removeAllViews();
                        hl_special.fullScroll(View.FOCUS_LEFT);
                        list_guess.clear();
                    }
                    if(!loadMore){
                        if(response.getSliding_img()!=null&&response.getSliding_img().size()!=0){ //添加头部view
                            list_head.addAll(response.getSliding_img());
                            ArrayList<ImageView> img_list = new ArrayList<>();
                            for (int i = 0; i < response.getSliding_img().size(); i++) {
                                //添加圆点
                                ImageView dot = new ImageView(context);
                                if (i == 0) {
                                    dot.setImageResource(R.drawable.bullet_white);//设置当前页的圆点
                                } else {
                                    dot.setImageResource(R.drawable.bullet_red);//其余页的圆点
                                }
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UiUtils.dip2px(18), UiUtils.dip2px(18));
                                dot.setLayoutParams(params);
                                ll_home_container.addView(dot);//将圆点添加到容器中
                                //添加viewpager图片
                                ImageView iv = new ImageView(getContext());
                                if(Util.isOnMainThread()){
                                    Glide.with(getContext()).load(response.getSliding_img().get(i).getImg_url()).centerCrop().dontAnimate().into(iv);
                                    img_list.add(iv);
                                }
                            }
                            vp_adapter = new ViewPagerAdapter_Home(getContext(), img_list);
                            vp_home.setAdapter(vp_adapter);
                            if(response.getSliding_img().size()>1) {
                                vp_home.setCurrentItem(4998);
                                for (int i = 0; i < response.getSliding_img().size(); i++) {
                                    ImageView iv = new ImageView(getContext());
                                    if(Util.isOnMainThread()){
                                        Glide.with(getContext()).load(response.getSliding_img().get(i).getImg_url()).centerCrop().dontAnimate().into(iv);
                                        img_list.add(iv);
                                    }
                                }
                                vp_adapter.notifyDataSetChanged();
                            }
                        }
                        if(response.getLike()!=null&&response.getLike().size()!=0){//设置头部三个标记数据
                            setLikeData(response.getLike());
                        }

                        if(response.getList()!=null&&response.getList().size()!=0){ //添加专题数据
                            list_special.addAll(response.getList());
                            adapter_special.notifyDataSetChanged();
                            ll_head_specail.setVisibility(View.VISIBLE);
                        }
                        if(response.getBrandlist()!=null&&response.getBrandlist().size()!=0){ //设置猜你感兴趣数据
                            list_interest.addAll(response.getBrandlist().get(0).getData());
                            list_guess.addAll(response.getBrandlist());
                            adapter_interest.notifyDataSetChanged();
                            ll_head_interest.setVisibility(View.VISIBLE);
                            for(int i =0;i<response.getBrandlist().size();i++){
                                if(i==0)
                                    ll_special_tags.addView(addTagView(response.getBrandlist().get(i).getName(),i,true));
                                else
                                    ll_special_tags.addView(addTagView(response.getBrandlist().get(i).getName(),i,false));
                            }
                        }
                    }
//                    if (response.getInspectionlog()!=null&&response.getInspectionlog().size() >= 5)
//                        hasMore = true;
//                    else
//                        hasMore = false;
//                    if (response.getInspectionlog()!=null&&response.getInspectionlog().size() != 0) { //设置精选考察攻略数据
//                        list_inspection.addAll(response.getInspectionlog());
//                        adapter_inspecition.notifyDataSetChanged();
//                    }

                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
            }
        }, HomePagerResponse.class);
    }

    //设置头部三个标记数据
    private void setLikeData(List<HomePagerResponse.LikeBean> like) {
        ll_3_tag.removeAllViews();
        if(like.size()==3)
        for(int i =0;i<like.size();i++){
            ll_3_tag.addView(getTV(like.get(i).getType(),like.get(i).getTname(),like.get(i).getTid()));
        }
    }

    /**
     * 添加猜你感兴趣标签
     */
    private View addTagView(String content, final int index,boolean flag){
        View  view = LayoutInflater.from(getContext()).inflate(R.layout.item_guess_interest_tag,null);
        TextView tv = (TextView) view.findViewById(R.id.tv_item_guess_interest);
        ImageView iv = (ImageView) view.findViewById(R.id.iv_item_guess_interest);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT,LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,20,0);
        view.setLayoutParams(params);
        tv.setText(content);
        if(flag){
            tv.setTextColor(ContextCompat.getColor(getContext(),R.color.color_white));
            Glide.with(getContext()).load(R.drawable.img_home_btn_bg).centerCrop().into(iv);
        }else{
            tv.setTextColor(ContextCompat.getColor(getContext(),R.color.color_black));
            Glide.with(getContext()).load(R.drawable.img_bg_mine).centerCrop().into(iv);
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeGuessInterest(index);
            }
        });
        return  view;
    }

    /**
     * 更换猜你感兴趣数据
     * @param index 下标
     */
    private void changeGuessInterest(int index) {
        list_interest.clear();
        list_interest.addAll(list_guess.get(index).getData());
        adapter_interest.notifyDataSetChanged();

        ll_special_tags.removeAllViews();
        for(int i = 0 ; i<list_guess.size();i++){
            if(i==index)
                ll_special_tags.addView(addTagView(list_guess.get(i).getName(),i,true));
            else
                ll_special_tags.addView(addTagView(list_guess.get(i).getName(),i,false));
        }
    }

    /**
     * 添加textview
     * @param content
     * @return
     */
    private View getTV(final int type , final String content, final String bid){
        View  view = LayoutInflater.from(getContext()).inflate(R.layout.item_find_tags,null);
        TextView tv = (TextView) view.findViewById(R.id.tv_item_find_tags);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT,LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,20,0);
        view.setLayoutParams(params);
        tv.setText(content);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobclickAgent.onEvent(getContext(),"ll_home_head_three");
                if(type==1){ //品牌页
                    Intent intent = new Intent(getContext(),BrandActivity.class);
                    intent.putExtra("index",bid);
                    startActivity(intent);
                }else if(type==4){ //分类选择页
                    Intent intent = new Intent(getContext(),ChooseTypeActivity.class);
                    intent.putExtra("notFind",true);
                    intent.putExtra("cid",bid);
                    intent.putExtra("project",content);
                    startActivity(intent);
                }

            }
        });
        return view;
    }

    /**
     * 添加textiew
     */
    private void addMoneyTextView(final int i , String string){
        final TextView iv = new TextView(getContext());
        iv.setPadding(UiUtils.dip2px(5),UiUtils.dip2px(10),UiUtils.dip2px(5),UiUtils.dip2px(10));
        iv.setText(string);
        iv.setGravity(Gravity.CENTER);
        iv.setTextColor(ContextCompat.getColor(getContext(),R.color.color_black));
        iv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_white));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        params.width = 0;
        params.weight = 1;
        params.height  = ll_moneys.getMeasuredHeight()/3-10;
        params.setMargins(0,0,UiUtils.dip2px(3),0);
        iv.setLayoutParams(params);
        if(i%3==0){
            rowLayout = null;
            rowLayout = createImageLayout();
            ll_moneys.addView(rowLayout);
        }
        if(int_money==i){
            iv.setTextColor(ContextCompat.getColor(getContext(),R.color.color_white));
            iv.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_blue));
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_money = strs_money[i];
                int_money = i;
                addAllMoneyTextViews();
            }
        });
        rowLayout.addView(iv);
    }

    // 每4个图片一行
    public LinearLayout createImageLayout() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setPadding(0,10,0,0);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }


    //无限轮播
    private boolean isSwitchTask = false; //
    private int page_id;
    private Runnable switchTask = new Runnable() {
        public void run() {
            if (isSwitchTask) {
                vp_home.setCurrentItem(page_id);
                page_id++;
            }
            isSwitchTask = true;
            handler.postDelayed(switchTask, 3000);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        str_area = (i+10)/10*10+"平方米";
        tv_area.setText(str_area);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onPullRefresh() {
        handler.sendEmptyMessageDelayed(TYPE_REFRESH,500);
    }

    @Override
    public void onLoadingMore() {
        if (hasMore)
        handler.sendEmptyMessageDelayed(TYPE_LOADING,500);
        else
            rlv.completeRefresh();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        page_id = position;
        for (int i = 0; i < ll_home_container.getChildCount(); i++) {
            ImageView imageView = (ImageView) ll_home_container.getChildAt(i);
            if (i == position%ll_home_container.getChildCount()) {
                imageView.setImageResource(R.drawable.bullet_white);
            } else {
                imageView.setImageResource(R.drawable.bullet_red);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * item点击事件
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_home_special://点击专题
                int type_sp = list_special.get(i).getType();
                MobclickAgent.onEvent(getContext(),"lv_home_special");
                if(type_sp==3){
                    Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                    intent.putExtra("id",list_special.get(i).getId());
                    startActivity(intent);
                }else if(type_sp==1){
                    Intent intent = new Intent(getContext(), SpecialArticleActivity.class);
                    intent.putExtra("id",list_special.get(i).getId());
                    startActivity(intent);
                }
                break;

            case R.id.lv_home_guess_interest://点击热门品牌
                MobclickAgent.onEvent(getContext(),"lv_home_guess_interest");
                Intent intent_hot = new Intent(getContext(), BrandActivity.class);
                intent_hot.putExtra("index",list_interest.get(i).getBrand_id());
                startActivity(intent_hot);
                break;

            case R.id.rlv_home_pager: //点击考察攻略
                if(i>=4&&i-4<list_inspection.size()) {
                    MobclickAgent.onEvent(getContext(),"rlv_home_pager");
                    String type = list_inspection.get(i-4).getType();
                    if(type!=null&&type.equals("1")){ //文章列表
                        Intent intent = new Intent(getContext(),SpecialArticleActivity.class);
                        intent.putExtra("id", list_inspection.get(i - 4).getArticle_id());
                        startActivity(intent);
                    }else if(type!=null&&type.equals("3")){ //文章详情(图文)
                        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                        intent.putExtra("id", list_inspection.get(i - 4).getArticle_id());
                        startActivity(intent);
                    }else if(type!=null&&type.equals("4")){ //文章详情(大图)
                        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                        intent.putExtra("id", list_inspection.get(i - 4).getArticle_id());
                        startActivity(intent);
                    }else if(type!=null&&type.equals("2")){ //文章详情(客户端的发帖)
                        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
                        intent.putExtra("id", list_inspection.get(i - 4).getArticle_id());
                        intent.putExtra("title",false);
                        startActivity(intent);
                    }
                }
                break;
        }
    }

    private float x1,x2,y1,y2;
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getId()==R.id.vp_home_pager){
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:
                    x1 = motionEvent.getX();
                    y1= motionEvent.getY();
                    break ;
                case MotionEvent.ACTION_MOVE:
                    break ;
                case  MotionEvent.ACTION_UP :
                    x2 = motionEvent.getX();
                    y2 = motionEvent.getY();
                    if(Math.abs(x1-x2)<=10&&Math.abs(y1-y2)<=10){
                        int item = vp_home.getCurrentItem();
                        int position = item%list_head.size();
                        int type = list_head.get(item%list_head.size()).getType();
                        if(type==4){ //活动
                            Intent intent = new Intent(getContext(),ExerciseActivity.class);
                            intent.putExtra("id",list_head.get(position).getId());
                            startActivity(intent);
                        }else if(type==3){ //文章
                            Intent intent = new Intent(getContext(),ArticleDetailActivity.class);
                            intent.putExtra("id",list_head.get(position).getId());
                            startActivity(intent);
                        }else if(type==5){ //品牌
                            Intent intent = new Intent(getContext(),BrandActivity.class);
                            intent.putExtra("index",list_head.get(position).getId());
                            startActivity(intent);
                        }else if(type==100){ //网页链接
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            String url = list_head.get(position).getId();
                            Uri uri = Uri.parse(url);
                            intent.setData(uri);
                            Pattern pattern = Pattern
                                    .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
                            if(pattern.matcher(url).matches())
                                startActivity(intent);
                        }
                    }
                    break ;
            }
        }
        return false;
    }

    @Override
    public void onScrollChanged() {
        if (rlv.getFirstVisiblePosition()==3){//显示控件
           // tv_select_raiders.setVisibility(View.VISIBLE);
        }else if(rlv.getFirstVisiblePosition()==2){//隐藏控件
          //  tv_select_raiders.setVisibility(View.GONE);
        }
    }

    /**
     * 双击返回顶部
     */
    public  void setListView2Top(){
        ViewUtil.stopListView(rlv,0);
        tv_select_raiders.setVisibility(View.GONE);
    }

}
