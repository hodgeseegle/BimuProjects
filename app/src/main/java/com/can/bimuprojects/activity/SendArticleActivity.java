package com.can.bimuprojects.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Request.SendArticleRequest;
import com.can.bimuprojects.Module.Request.UploadPicRequest;
import com.can.bimuprojects.Module.Response.SendArticleResponse;
import com.can.bimuprojects.Module.Response.UploadPicResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.BitmapBase64TransferUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.LoadDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SendArticleActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    @Bind(R.id.tvPublish)
    TextView publish;
    @Bind(R.id.cancelSendArticle)
    TextView tvCancel;
    @Bind(R.id.et_edit_article)
    EditText et; //编辑文本框
    @Bind(R.id.ll_edit_article)
    LinearLayout ll ; //图片框
    @Bind(R.id.tv_edit_brand)
    TextView tv_brand; //品牌名称
    @Bind(R.id.tv_edit_total)
    TextView tv_total ; //还差多少字


    private static final int REQUEST_PIC_CODE = 99;
    private String str_content = "";

    private List<String> list; //用来保存图片的集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_article);
        ButterKnife.bind(this);
        init();
    }

    private String bid ; //品牌bid
    private String brand ; //品牌名称

    private void init() {
        list = new ArrayList<>();

        publish.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        bid = getIntent().getStringExtra("id");
        brand = getIntent().getStringExtra("brand");
        tv_brand.setText(brand);
        et.addTextChangedListener(this);

        rowLayout = createImageLayout();
        ll.addView(rowLayout);
        insertImage();
    }


    private void startAlbum() {
        try {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
            intent.setType("image/*");
            intent.addCategory("android.intent.category.OPENABLE");
            startActivityForResult(intent, REQUEST_PIC_CODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            try {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra("outputX", 600);
                intent.putExtra("outputY", 300);
                intent.putExtra("scale", true);
                startActivityForResult(intent, REQUEST_PIC_CODE);
            } catch (Exception e2) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
         if (v == publish) {
            //发布
            if (isArticleValid()) {
                SendArticleRequest request = new SendArticleRequest();
                request.setUid(LoginUtils.getLoginUid());
                request.setBid(bid);
                request.setTitle("来自比目app");
                try {
                    request.setContent(et.getText().toString().trim()+"<br><br/>"+list2String(list));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LoadDialog.show(this,"正在发布...");
                HttpUtils.postWithoutUid(MethodConstant.SEND_ARTICLE, request, new ResponseHook() {
                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        if (receive.getStatus() == 200) {
                            ToastUtils.showLong(SendArticleActivity.this, "发布成功");
                            LoadDialog.dismiss(context);
                            SendArticleActivity.this.finish();
                        }
                        LoadDialog.dismiss(context);
                    }
                }, new ErrorHook() {
                    @Override
                    public void deal(Context context, VolleyError error) {
                        ToastUtils.showLong(SendArticleActivity.this, "发布成功");
                        LoadDialog.dismiss(context);
                        SendArticleActivity.this.finish();
                    }
                }, SendArticleResponse.class);

            }
        } else if (v == tvCancel) {
            back();
        }

    }


    /**
     * 判断文章该填的内容是否全部填完了
     *
     * @return
     */
    private boolean isArticleValid() {

        if (TextUtils.isEmpty(et.getText().toString().trim())) {
            ToastUtils.showLong(this, "帖子里似乎什么都没写呢");
            return false;
        }else{
            str_content = et.getText().toString().trim();
        }
        if(str_content!=null&&str_content.length()<20){
            return false;
        }

        return true;
    }

    LinearLayout rowLayout = null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_PIC_CODE:
                LoadDialog.show(SendArticleActivity.this,"图片上传中...");
                // 上传图片并获得图片的url ，然后插入到本地html中
                try {
                    Uri uri = data.getData();
                    Bitmap bitmap = getBitmapFormUri(uri);
                    String path = getRealPathFromURI(uri);
                    bitmap = rotateBitmap(readBitmapDegree(path),bitmap);

                    HttpUtils.post(MethodConstant.UPLOAD_PIC,
                            new UploadPicRequest(BitmapBase64TransferUtils.bitmapToBase64(bitmap), 1,
                                    LoginUtils.getLoginUid()),
                            new ResponseHook() {
                                @Override
                                public void deal(Context context, JsonReceive receive) {
                                    final UploadPicResponse response = (UploadPicResponse) receive
                                            .getResponse();
                                    if (response != null && response.getExe_success() == 1&&response.getUrl()!=null) {
                                        setImage(response.getUrl());
                                        LoadDialog.dismiss(context);
                                    }
                                }
                            },
                            new ErrorHook() {
                                @Override
                                public void deal(Context context, VolleyError error) {
                                    ToastUtils.showShort(SendArticleActivity.this, "图片上传失败");
                                    LoadDialog.dismiss(context);
                                }
                            }, UploadPicResponse.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    // 读取图像的旋转度
    private int readBitmapDegree(String path) {
        int degree = 0;
        if(path==null){
            return 0;
        }
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    // 旋转图片
    private Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return resizedBitmap;
    }

    //设置图片
    private void setImage(final String url){
        if(rowLayout!=null&&rowLayout.getChildCount()>0){
            rowLayout.removeView(rowLayout.getChildAt(rowLayout.getChildCount()-1));
            int i = list.size();
            if(i%3==0&&flag){
                rowLayout = null;
                rowLayout = createImageLayout();
                ll.addView(rowLayout);
                flag = true;
            }
        }
        final ImageView iv = new ImageView(SendArticleActivity.this);
        iv.setPadding(20,0,10,10);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        params.width = metric.widthPixels/3-10;
        params.height  = ll.getMeasuredHeight()/3-10;
        params.gravity = Gravity.CENTER;
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setLayoutParams(params);
        if(Util.isOnMainThread())
            Glide.with(SendArticleActivity.this).load(url).into(iv);
        //插入图片
        list.add(url);
        rowLayout.addView(iv);
        if(list.size()<9)
        insertImage();
    }

    private boolean flag = false;
    //插入图片
    private void insertImage(){
        final ImageView iv = new ImageView(SendArticleActivity.this);
        iv.setImageResource(R.drawable.img_add_picture);
        iv.setPadding(20,0,10,10);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        params.width = metric.widthPixels/3-10;
        params.height  = ll.getMeasuredHeight()/3-10;
        params.gravity = Gravity.CENTER;
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iv.setLayoutParams(params);
        int i = list.size();
        if(i%3==0){
            flag = false;
            rowLayout = null;
            rowLayout = createImageLayout();
            ll.addView(rowLayout);
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加图片
                if(list.size()==9){
                    ToastUtils.showShort(SendArticleActivity.this,"最多选择9张图");
                }else
                    startAlbum();
            }
        });
        rowLayout.addView(iv);
    }


    // 每4个图片一行
    public LinearLayout createImageLayout() {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        return linearLayout;
    }



    /**
     * 通过uri获取图片并进行压缩
     *
     * @param uri
     */
    public  Bitmap getBitmapFormUri( Uri uri) throws  IOException {
        InputStream input = getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        int originalWidth = onlyBoundsOptions.outWidth;
        int originalHeight = onlyBoundsOptions.outHeight;
        if ((originalWidth == -1) || (originalHeight == -1))
            return null;
        //图片分辨率以480x800为标准
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        float hh = metric.heightPixels/2;//这里设置高度为800f
        float ww = metric.widthPixels/2;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (originalWidth / ww);
        } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (originalHeight / hh);
        }
        if (be <= 0)
            be = 1;
        //比例压缩
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = be;//设置缩放比例
        bitmapOptions.inDither = true;//optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        input = getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();

        return compressImage(bitmap);//再进行质量压缩
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 75, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        back();
    }


    public void back() {
        //正文或者标题不为空，则提示保存
        if (!TextUtils.isEmpty(et.getText().toString())||list.size()>0) {
            new AlertDialog.Builder(this).setMessage("退出此次编辑?")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else {
            finish();
        }
    }

   private String list2String(List<String> list){
       String str = "";
       if(list==null){
           return  str;
       }
       for(int i=0;i<list.size();i++){
           str+="<img src=\""+list.get(i)+"\" width=\"100%\" /><br><br/> ";
       }
       return str;

   }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(et.getText().toString()!=null)
        str_content = et.getText().toString().trim();
        if(str_content!=null){
            if(str_content.length()>=20){
                tv_total.setText("点击右上角进行发布!");
            }else{
                tv_total.setText("还差"+(20-str_content.length())+"字，用力啊少年!");
            }
        }
    }
}
