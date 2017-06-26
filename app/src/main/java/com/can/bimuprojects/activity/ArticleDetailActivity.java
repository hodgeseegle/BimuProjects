package com.can.bimuprojects.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.ArticleCommentRequest;
import com.can.bimuprojects.Module.Request.ArticleDetailRequest;
import com.can.bimuprojects.Module.Request.ArticlePraisedRequest;
import com.can.bimuprojects.Module.Request.RelatedBrandRequest;
import com.can.bimuprojects.Module.Response.ArticleDetailResponse;
import com.can.bimuprojects.Module.Response.ArticlePraisedResponse;
import com.can.bimuprojects.Module.Response.CommentResponse;
import com.can.bimuprojects.Module.Response.RelatedBrandResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.adapter.ArticleDetailAdapter;
import com.can.bimuprojects.adapter.RelatedBrandAdapter;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.DateUtils;
import com.can.bimuprojects.utils.GlideRoundTransform;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.LoadDialog;
import com.can.bimuprojects.view.LoadMoreListView;
import com.can.bimuprojects.view.NumImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by can on 2017.4.18
 * 文章详情页
 */
public class ArticleDetailActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, TextView.OnEditorActionListener, LoadMoreListView.OnRefreshListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        initData();
    }

    private LoadMoreListView lv ; //listview集合控件
    private ImageView iv_exit; //退出按钮
    private ImageView iv_open_share ; //打开分享按钮
    private TextView tv_brand; //相关品牌
    private EditText et ; //写评论框
    private NumImageView niv ; //带数字的imageview
    private ImageView iv_parised ; //收藏
    private ImageView iv_share ; //分享按钮
    /**
     * 初始化view
     */
    private void initView() {
        setContentView(R.layout.activity_article_detail);
        lv = (LoadMoreListView) findViewById(R.id.lv_article_detail);
        iv_exit = (ImageView) findViewById(R.id.iv_exit);
        iv_open_share = (ImageView) findViewById(R.id.iv_right);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.dian).into(iv_open_share);
        tv_brand = (TextView) findViewById(R.id.tv_article_detail_related_brand);
        et = (EditText) findViewById(R.id.et_article_detail);
        niv = (NumImageView) findViewById(R.id.niv_article_detail);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.comment).into(niv);
        iv_share = (ImageView) findViewById(R.id.iv_article_detail_share);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.share).into(iv_share);
        iv_parised = (ImageView) findViewById(R.id.iv_article_detail_parised);
        if(Util.isOnMainThread())
            Glide.with(this).load(R.drawable.praised).into(iv_parised);
        initHeadView();
        initDialogView();
    }

    private Dialog dialog ; //相关品牌弹出框
    private ListView lv_dialog;//相关品牌集合控件
    private List<RelatedBrandResponse.BrandsBean> list_dialog;//相关品牌集合
    private RelatedBrandAdapter adapter_dialog ; //相关品牌适配器
    //初始化相关品牌弹出框
    private void initDialogView() {
        dialog = new Dialog(this,R.style.style_dialog);
        View view_dialog = LayoutInflater.from(this).inflate(R.layout.dialog_related_brand,null);
        lv_dialog = (ListView) view_dialog.findViewById(R.id.lv_article_detail_related_brand);
        lv_dialog.setOverScrollMode(View.OVER_SCROLL_NEVER);
        dialog.setContentView(view_dialog);

        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialogStyle);
        window.setGravity(Gravity.BOTTOM );
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        params.width = metric.widthPixels;
        params.height = metric.heightPixels * 4 / 5;
        window.setAttributes(params);
    }

    private View view_head;//头部view
    private TextView tv_head_title; //头部标题
    private ImageView iv_head_person ;//头部头像
    private TextView tv_head_name ; //头部名字
    private TextView tv_head_date ; //头部时间
    private WebView wv ; //头部webview
    //初始化头部view
    private void initHeadView() {
        view_head = LayoutInflater.from(this).inflate(R.layout.headview_atricle_detail,null);
        tv_head_title = (TextView) view_head.findViewById(R.id.tv_title_article_detail);
        tv_head_name = (TextView) view_head.findViewById(R.id.tv_article_detail_person);
        tv_head_date = (TextView) view_head.findViewById(R.id.tv_article_detail_date);
        iv_head_person = (ImageView) view_head.findViewById(R.id.iv_person_atricle_detail);
        wv = (WebView) view_head.findViewById(R.id.wv_article_detail);
        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress>=100){
                    if(index!=0)
                    handler.sendEmptyMessageDelayed(2,100);
                }
            }
        });
    }


    private ArticleDetailAdapter adapter ; //适配器
    private List<ArticleDetailResponse.CommentsBean> list ; //数据集合
    private boolean hasParised ; //是否收藏
    private int page = 0;
    private String author_id ; //作者id
    private String comment_pos ; //从消息列表进来 回复或评论的id
    private int index =0 ; //从消息列表进来，滚动到哪个位置

    /**
     * 初始化数据
     */
    private void initData() {
        list = new ArrayList<>();
        adapter = new ArticleDetailAdapter(this,list);
        lv.setAdapter(adapter);

        list_dialog = new ArrayList<>();
        adapter_dialog = new RelatedBrandAdapter(this,list_dialog);
        lv_dialog.setAdapter(adapter_dialog);

        requestITData();
    }

    private String id ; //文章id
    /**
     * 请求网络数据
     */
    private void requestITData() {
        id = getIntent().getStringExtra("id");
        comment_pos = getIntent().getStringExtra("comment_pos");
        LoadDialog.show(this,"数据加载中...");
        ArticleDetailRequest request = new ArticleDetailRequest();
        request.setId(id);
        request.setUid(LoginUtils.getUid());
        request.setTimestamp(page+"");
        HttpUtils.postWithoutUid(MethodConstant.ARTICLE_DETAIL, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                LoadDialog.dismiss(context);
                ArticleDetailResponse response = (ArticleDetailResponse) receive.getResponse();
                if(response!=null){
                    setData(response);
                    if(response.getComments().size()>=10)
                        hasMore = true;
                    else
                        hasMore = false;
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {
                LoadDialog.dismiss(context);
            }
        },ArticleDetailResponse.class);
    }

    private int comment_num ;//评论数量
    private boolean scroll1or0; //滑动到评论或者头部
    //设置数据
    private void setData(ArticleDetailResponse response) {
        List<ArticleDetailResponse.CommentsBean> bean = response.getComments();
        if(bean!=null){
            for(int i =0;i<bean.size();i++){
                String comment_id = bean.get(i).getComment_id();
                if(comment_id!=null&&comment_id.equals(comment_pos)){
                    index = i+1;
                    break;
                }
            }
        }
        str_share_content = response.getSummary();
        author_id = response.getAuthor_id();
        String regex = "src=\"(.*?)\"";
        Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        Matcher ma = pa.matcher(response.getArticle());
        if (ma.find())
        {
            str_img = ma.group();
        }
        if(str_img!=null){
            str_img = str_img.substring(5,str_img.length()-1);
        }
       // wv.loadData(response.getArticle(), "text/html;charset=UTF-8", null);
        wv.loadDataWithBaseURL(null, getHtmlData(response.getArticle()), "text/html", "utf-8", null);
        if(response.getHas_praised()==1){
            hasParised = true;
            if(!this.isFinishing()&&Util.isOnMainThread())
                Glide.with(this).load(R.drawable.img_love_select).into(iv_parised);
        }else{
            hasParised = false;
            if(!this.isFinishing()&&Util.isOnMainThread())
                Glide.with(this).load(R.drawable.praised).into(iv_parised);
        }
        comment_num = response.getComments().size();
        niv.setNum(response.getComments().size());
        if(comment_num>0){
            hasMore = true;
        }else{
            hasMore = false;
        }
        lv.addHeaderView(view_head);
        String type = response.getType();
        if(type!=null&&type.equals("1"))
            tv_head_title.setVisibility(View.GONE);
        if(response.getSummary()!=null)
        tv_head_title.setText(response.getSummary());
        if(response.getAuthor_nickname()!=null)
        tv_head_name.setText(response.getAuthor_nickname());
        if(Util.isOnMainThread()&&!this.isFinishing())
        Glide.with(this).load(response.getAuthor_image()).transform(new GlideRoundTransform(this)).into(iv_head_person);
        if(response.getPublish_time()!=null)
        tv_head_date.setText(DateUtils.timestampToString(response.getPublish_time()));
        list.addAll(response.getComments());
        adapter.notifyDataSetChanged();
        requestDialogData();
    }

    //自适应页面大小 不能左右滑动
    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    private int brands = 0 ; //品牌数
    /**
     * 请求相关品牌数据
     */
    private void requestDialogData() {
        RelatedBrandRequest request = new RelatedBrandRequest();
        request.setAid(id);
        request.setUid(LoginUtils.getUid());
        HttpUtils.postWithoutUid(MethodConstant.GET_ARTICLE_ABOUT, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                RelatedBrandResponse response = (RelatedBrandResponse) receive.getResponse();
                if(receive.getStatus()==200&&response!=null){
                    brands = response.getBrands().size();
                    if(brands>0){
                        tv_brand.setVisibility(View.VISIBLE);
                        tv_brand.setText(getString(R.string.related_brand)+" "+brands);
                    }
                    list_dialog.addAll(response.getBrands());
                    adapter_dialog.notifyDataSetChanged();
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        },RelatedBrandResponse.class);
    }


    /**
     * 设置监听
     */
    private void setListener() {
        iv_open_share.setOnClickListener(this);
        iv_exit.setOnClickListener(this);
        iv_head_person.setOnClickListener(this);
        tv_brand.setOnClickListener(this);
        lv_dialog.setOnItemClickListener(this);
        et.setImeOptions(EditorInfo.IME_ACTION_SEND);
        et.setOnEditorActionListener(this);
        iv_share.setOnClickListener(this);
        iv_parised.setOnClickListener(this);
        niv.setOnClickListener(this);
        lv.setOnItemClickListener(this);
        et.setOnClickListener(this);
        lv.setOnRefreshListener(this);
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    if(showLoginDialog())
                        return;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(1);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_right: //打开分享
                share();
                break;

            case R.id.iv_exit : //退出
                setResult(1);
                this.finish();
                break;

            case R.id.iv_person_atricle_detail: //点击头像
                Intent intent_person = new Intent(this,PersonalPageActivity.class);
                intent_person.putExtra(AppConstant.UID,author_id);
                startActivity(intent_person);
                break;

            case R.id.tv_article_detail_related_brand: //相关品牌
                if(!dialog.isShowing()&&brands!=0){
                    MobclickAgent.onEvent(this,"tv_article_detail_related_brand");
                    if(!this.isFinishing())
                    dialog.show();
                }
                break;

            case R.id.iv_article_detail_share: //右下角分享
                share();
                break;

            case R.id.iv_article_detail_parised: //收藏
                if(showLoginDialog())
                    break;
                MobclickAgent.onEvent(this,"iv_article_detail_parised");
                setParised();
                break;

            case R.id.niv_article_detail://滑动到评论
                if(!scroll1or0){
                    scroll1or0 = true;
                    lv.setSelection(1);
                }else if(scroll1or0){
                    lv.setSelection(0);
                    scroll1or0 = false;
                }
                break;
            case R.id.et_article_detail://点击edittext
                if(showLoginDialog())
                    break;
                type_type=0;
                replay_id = 0;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if(requestCode==AppConstant.LOGIN_REQUEST) {
            requestITData();
        }
    }

    private String str_share_content;//分享标题
    private String str_img ; //图片集合

    /**
     * 分享
     */
    private void share() {
        UMImage umImage = new UMImage(this, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
        if(str_img!=null){
            umImage = new UMImage(this,str_img);
        }
        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(umImage)
                .withText(getString(R.string.share_article_title))
                .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ToastUtils.showShort(ArticleDetailActivity.this,"分享成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtils.showShort(ArticleDetailActivity.this,"分享失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtils.showShort(ArticleDetailActivity.this,"分享已取消");
                    }
                })
                .withTitle(str_share_content)
                .withTargetUrl("http://app.bimuwang.com/bimu/interface/" +
                        "share_article.php?article_id=" + id+"&from=singlemessage&isappinstalled=1")
                .open();
    }

    /**
     * 设置收藏
     */
    private void setParised() {
        if(hasParised){
            hasParised = false;
            if(!this.isFinishing()&&Util.isOnMainThread())
                Glide.with(this).load(R.drawable.praised).into(iv_parised);
        }else{
            hasParised = true;
            if(!this.isFinishing()&&Util.isOnMainThread())
                Glide.with(this).load(R.drawable.img_love_select).into(iv_parised);
        }
        ArticlePraisedRequest request = new ArticlePraisedRequest();
        request.setUid(LoginUtils.getUid());
        request.setId(id);
        request.setType(6);
        HttpUtils.postWithoutUid(MethodConstant.FOCUS, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {

            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        }, ArticlePraisedResponse.class);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lv_article_detail_related_brand://点击相关品牌
                MobclickAgent.onEvent(this,"lv_article_detail_related_brand");
                Intent intent_brand = new Intent(this,BrandActivity.class);
                intent_brand.putExtra("index",list_dialog.get(i).getBrand_id());
                startActivity(intent_brand);
                break;
            case R.id.lv_article_detail://点击评论区
                if(i-1<list.size()){
                    if(showLoginDialog())
                        break;
                    type_type = 1;
                    replay_id = Integer.parseInt(list.get(i-1).getComment_id());
                    content = et.getText().toString();
                    AppUtils.showSoftInput(ArticleDetailActivity.this, et);
                }
                break;
        }
    }

    /**
     * 发送评论
     */
    private void sendComment(int type,int reply_id){
        ArticleCommentRequest request = new ArticleCommentRequest();
        request.setUid(LoginUtils.getUid());
        request.setText(et.getText().toString());
        request.setArticle_id(id);
        request.setIs_replay(type);
        request.setRe_comment_id(reply_id);
        this.type_type = 0;
        this.replay_id = 0;
        HttpUtils.postWithoutUid(MethodConstant.COMMENT, request, new ResponseHook() {
            @Override
            public void deal(Context context, JsonReceive receive) {
                CommentResponse response = (CommentResponse) receive.getResponse();
                if(response!=null){
                    page=0;
                    ArticleDetailRequest re = new ArticleDetailRequest();
                    re.setId(id);
                    re.setUid(LoginUtils.getUid());
                    re.setTimestamp(page+"");
                    HttpUtils.postWithoutUid(MethodConstant.ARTICLE_DETAIL, re, new ResponseHook() {
                        @Override
                        public void deal(Context context, JsonReceive receive) {
                            ArticleDetailResponse response = (ArticleDetailResponse) receive.getResponse();
                            if(response!=null){
                                comment_num++;
                                niv.setNum(comment_num);
                                ToastUtils.show(ArticleDetailActivity.this,"评论成功",Toast.LENGTH_SHORT);
                                list.clear();
                                list.addAll(response.getComments());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }, new ErrorHook() {
                        @Override
                        public void deal(Context context, VolleyError error) {

                        }
                    },ArticleDetailResponse.class);
                }
            }
        }, new ErrorHook() {
            @Override
            public void deal(Context context, VolleyError error) {

            }
        },CommentResponse.class);
    }

    private int type_type=0;
    private int replay_id =0;
    private String content ="";
    private boolean hasMore = true;
    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        content = et.getText().toString();
        if(!content.equals("")) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
            if (i == KeyEvent.ACTION_DOWN || i == EditorInfo.IME_ACTION_SEND) {
                sendComment(type_type,replay_id);
                et.setText("");
                et.clearFocus();
                return true;
            }
        }
        type_type=0;
        replay_id = 0;
        return false;
    }

    /**
     * 主线程处理事件
     */
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 1:
                    lv.completeRefresh();
                    page++;
                    ArticleDetailRequest request = new ArticleDetailRequest();
                    request.setId(id);
                    request.setUid(LoginUtils.getUid());
                    request.setTimestamp(page+"");
                    HttpUtils.postWithoutUid(MethodConstant.ARTICLE_DETAIL, request, new ResponseHook() {
                        @Override
                        public void deal(Context context, JsonReceive receive) {
                            ArticleDetailResponse response = (ArticleDetailResponse) receive.getResponse();
                            if(response!=null&&response.getComments().size()>0){
                                list.addAll(response.getComments());
                                adapter.notifyDataSetChanged();
                            }else{
                                hasMore = false;
                                page=0;
                            }
                        }
                    }, new ErrorHook() {
                        @Override
                        public void deal(Context context, VolleyError error) {

                        }
                    },ArticleDetailResponse.class);
                    break;
                case 2: //跳转到评论
                    if(comment_pos!=null){
                        scroll1or0 = true;
                        lv.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                lv.setSelection(index);
                                lv.requestFocusFromTouch();
                                lv.setSelected(true);
                            }
                        },200);

                    }
                    break;
            }
            return false;
        }
    });

    @Override
    public void onLoadingMore() {
        if(hasMore)
        handler.sendEmptyMessageDelayed(1,500);
        else
            lv.completeRefresh();
    }
}
