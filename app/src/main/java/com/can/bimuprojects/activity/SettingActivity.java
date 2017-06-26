package com.can.bimuprojects.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.Constant.MethodConstant;
import com.can.bimuprojects.Module.Hook.DefaultErrorHook;
import com.can.bimuprojects.Module.Request.ModifyPersonalSign;
import com.can.bimuprojects.Module.Request.ModifyUserNameRequest;
import com.can.bimuprojects.Module.Request.QueryUpdateRequest;
import com.can.bimuprojects.Module.Request.UploadPicRequest;
import com.can.bimuprojects.Module.Response.ModifyUserNameResponse;
import com.can.bimuprojects.Module.Response.QueryUpdateResponse;
import com.can.bimuprojects.Module.Response.UploadPicResponse;
import com.can.bimuprojects.R;
import com.can.bimuprojects.application.BimuApplication;
import com.can.bimuprojects.network.beans.ErrorHook;
import com.can.bimuprojects.network.beans.JsonReceive;
import com.can.bimuprojects.network.beans.ResponseHook;
import com.can.bimuprojects.utils.AppUtils;
import com.can.bimuprojects.utils.BitmapBase64TransferUtils;
import com.can.bimuprojects.utils.HttpUtils;
import com.can.bimuprojects.utils.LoginUtils;
import com.can.bimuprojects.utils.PrefUtils;
import com.can.bimuprojects.utils.ToastUtils;
import com.can.bimuprojects.view.ClearEditText;
import com.can.bimuprojects.view.LoadDialog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private static final int REQUEST_PIC_CODE = 99;
    private final int CROP_RESULT_CODE = 77;
    @Bind(R.id.setting_btn_exit)
    Button btnExit;
    @Bind(R.id.iv_exit)
    ImageView ivBack;
    @Bind(R.id.setting_tv_check_update)
    TextView tvCheckUpdate;
    @Bind(R.id.setting_tv_user_agreement)
    TextView tvUserAgreement;
    @Bind(R.id.setting_tv_clear_cache)
    TextView tvClearCache;
    @Bind(R.id.setting_rl_modify_head_icon)
    RelativeLayout rlModifyHeadIcon;
    @Bind(R.id.setting_rl_modify_nick_name)
    RelativeLayout rlModifyNickName;
    @Bind(R.id.setting_rl_modify_pwd)
    RelativeLayout rlModifyPwd;
    @Bind(R.id.setting_rl_modify_signature)
    RelativeLayout rlModifySignature;
    @Bind(R.id.tv_title)
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppUtils.isNeedShowLoginDialog())
            btnExit.setText(R.string.login_phone);
        else
            btnExit.setText(R.string.exit_current_uid);
    }

    private void init() {
        ButterKnife.bind(this);
        tv_title.setText(R.string.person_data);
        btnExit.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvCheckUpdate.setOnClickListener(this);
        tvClearCache.setOnClickListener(this);
        tvUserAgreement.setOnClickListener(this);
        rlModifyHeadIcon.setOnClickListener(this);
        rlModifyNickName.setOnClickListener(this);
        rlModifyPwd.setOnClickListener(this);
        rlModifySignature.setOnClickListener(this);
        initSignatureDialog();
        initUserNameDialog();
        dialog_modify_signature.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_modify_signature, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        dialog_modify_user_name.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_modify_user_name, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    private Dialog dialog_modify_signature; //修改个性签名
    private TextView tv_modify_signature_sure; //确认
    private TextView tv_modify_signature_cancle ; //取消
    private ClearEditText et_modify_signature; //编辑栏
    private Dialog dialog_is_new ; //当前为最新版本
    /**
     * 初始化dialog
     */
    private void initSignatureDialog() {
        dialog_is_new = ToastUtils.getDialog(this,"提示","您当前的版本已是最新版本",View.inflate(this, R.layout.dialog_circle,null),R.style.circle_dialog,true,false);
        //个性签名
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_modify_signature,null);
        tv_modify_signature_sure = (TextView) view.findViewById(R.id.tv_sure_modify_signature);
        tv_modify_signature_cancle = (TextView) view.findViewById(R.id.tv_cancle_modify_signature);
        et_modify_signature = (ClearEditText) view.findViewById(R.id.et_modify_signature);
        tv_modify_signature_sure.setOnClickListener(this);
        tv_modify_signature_cancle.setOnClickListener(this);
        dialog_modify_signature = new Dialog(this,R.style.circle_dialog);
        dialog_modify_signature.setCanceledOnTouchOutside(false);
        dialog_modify_signature.setContentView(view);
        Window window = dialog_modify_signature.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = width*3/4;
        window.setAttributes(params);
    }


    private Dialog dialog_modify_user_name; //修改个性签名
    private TextView tv_modify_user_name_sure; //确认
    private TextView tv_modify_user_name_cancle ; //取消
    private ClearEditText et_modify_user_name; //编辑栏
    /**
     * 初始化dialog
     */
    private void initUserNameDialog() {
        //个性签名
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_modify_user_name,null);
        tv_modify_user_name_sure = (TextView) view.findViewById(R.id.tv_sure_user_name);
        tv_modify_user_name_cancle = (TextView) view.findViewById(R.id.tv_cancle_user_name);
        et_modify_user_name = (ClearEditText) view.findViewById(R.id.et_modify_user_name);
        tv_modify_user_name_sure.setOnClickListener(this);
        tv_modify_user_name_cancle.setOnClickListener(this);
        dialog_modify_user_name = new Dialog(this,R.style.circle_dialog);
        dialog_modify_user_name.setCanceledOnTouchOutside(false);
        dialog_modify_user_name.setContentView(view);
        Window window = dialog_modify_user_name.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = width*3/4;
        window.setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_rl_modify_signature: //修改个性签名
                if(!dialog_modify_signature.isShowing()){
                    dialog_modify_signature.show();
                }
                break;
            case R.id.setting_btn_exit:
                //退出登录状态
                removeALLActivity();
                Intent intent = new Intent(SettingActivity.this,LoginOrRegisterActivity.class);
                intent.putExtra(AppConstant.QUIT_LOGIN,true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                LoginUtils.setLoginStatus(true);
                if(PrefUtils.get("uid","").equals(""))
                    AppUtils.setSNWithoutLogin(BimuApplication.getContext());
                else
                    LoginUtils.setLoginUid(PrefUtils.get("uid",""));
                finish();
                break;
            case R.id.iv_exit:
                finish();
                break;
            case R.id.setting_tv_check_update:
                //检查更新
                queryUpdate();
                break;
            case R.id.setting_tv_clear_cache:
                //清除缓存
                deleteCache();
                ToastUtils.showShort(SettingActivity.this, "清除缓存成功");
                break;
            case R.id.setting_rl_modify_head_icon:
                modifyHeadIcon();
                break;
            case R.id.setting_rl_modify_nick_name://修改用户名
                if(!dialog_modify_user_name.isShowing())
                    dialog_modify_user_name.show();
                break;
            case R.id.setting_rl_modify_pwd:
                startActivity(new Intent(SettingActivity.this, FindPwdActivity.class)
                        .putExtra("title", "修改密码"));
                break;
            case R.id.setting_tv_user_agreement:
                startActivity(new Intent(SettingActivity.this, ProtocalActivity.class));
                break;

            case R.id.tv_sure_modify_signature: //确定个性签名
                modifySignature();
                break;
            case R.id.tv_cancle_modify_signature: //取消个性签名
                dialog_modify_signature.cancel();
                break;
            case R.id.tv_sure_user_name: //确定用户名
                ModifyNickName();
                break;
            case R.id.tv_cancle_user_name : //取消用户名
                dialog_modify_user_name.cancel();
                break;
        }
    }

    private void modifySignature() {
        String s = et_modify_signature.getText().toString().trim();
        if (et_modify_signature != null && !TextUtils.isEmpty(s)) {
            ModifyPersonalSign request = new ModifyPersonalSign();
            request.setUid(LoginUtils.getLoginUid());
            request.setSign_content(s);
            HttpUtils.post(MethodConstant.MODIFY_PERSONAL_SIGN, request, new
                    ResponseHook() {
                        @Override
                        public void deal(Context context, JsonReceive receive) {
                            ModifyUserNameResponse response =
                                    (ModifyUserNameResponse)
                                            receive.getResponse();
                            if (response != null && response.getExe_success() ==
                                    1) {
                                ToastUtils.showShort(SettingActivity.this, "修改成功");
                                dialog_modify_signature.dismiss();
                            }
                        }
                    }, new ErrorHook() {
                @Override
                public void deal(Context context, VolleyError error) {
                    ToastUtils.showShort(SettingActivity.this, "修改失败，请重试");
                }
            }, ModifyUserNameResponse.class);
        }
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
                startActivityForResult(intent, REQUEST_PIC_CODE);
            } catch (Exception e2) {
                e.printStackTrace();
            }
        }
    }

    //// 把绝对路径转换成content开头的URI
    public Uri geturi(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
    }

    private void modifyHeadIcon() {
        startAlbum();
    }

    private void ModifyNickName() {
        String s = et_modify_user_name.getText().toString().trim();
        if (et_modify_user_name != null && !TextUtils.isEmpty(s)) {
            ModifyUserNameRequest request = new ModifyUserNameRequest();
            request.setUid(LoginUtils.getLoginUid());
            request.setNew_name(s);
            HttpUtils.post(MethodConstant.MODIFY_USER_NAME, request, new
                    ResponseHook() {
                        @Override
                        public void deal(Context context, JsonReceive receive) {
                            ModifyUserNameResponse response =
                                    (ModifyUserNameResponse)
                                            receive.getResponse();
                            if (response != null && response.getExe_success() ==
                                    1) {
                                ToastUtils.showShort(SettingActivity.this, "修改成功");
                                dialog_modify_user_name.dismiss();
                            }
                        }
                    }, new ErrorHook() {
                @Override
                public void deal(Context context, VolleyError error) {
                    ToastUtils.showShort(SettingActivity.this, "修改失败，请重试");
                }
            }, ModifyUserNameResponse.class);
        }
    }

    private void queryUpdate() {
        HttpUtils.postWithoutUid(MethodConstant.GET_UPDATE_INFO, new QueryUpdateRequest(1), new
                ResponseHook
                        () {

                    @Override
                    public void deal(Context context, JsonReceive receive) {
                        final QueryUpdateResponse response = (QueryUpdateResponse) receive.getResponse();
                        if (response!=null&&response.getExe_success() == 1) {
                            String force_update = response.getForce_update()+"";
                            float apk_version = 0;
                            if(response.getApk_version()!=null&&!response.getApk_version().equals(""))
                                apk_version= (int)Math.floor(Double.parseDouble(response.getApk_version()));
                            String apk_url = "";
                            if(response.getApk_url()!=null)
                                apk_url = response.getApk_url().trim()+"";
                            String  update_text = response.getUpdate_text()+"";
                            float current_version = AppUtils.getAppVersionCode(SettingActivity.this);
                            if(apk_version>current_version) {

                                if (force_update.equals("1")) {
                                    final String finalApk_url = apk_url;
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(SettingActivity.this)
                                            .setTitle("新版本更新提示")
                                            .setMessage(update_text)
                                            .setCancelable(true)
                                            .setPositiveButton(getString(R.string.update_noti), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    updateAPP(finalApk_url);
                                                }
                                            });
                                    AlertDialog alertDialog = dialog.create();
                                    alertDialog.show();
                                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff0000"));
                                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#999999"));
                                } else {
                                    final String finalApk_url1 = apk_url;
                                    AlertDialog.Builder dialog = new AlertDialog.Builder(SettingActivity.this)
                                            .setTitle("更新提示")
                                            .setMessage(update_text)
                                            .setPositiveButton(getString(R.string.update_noti), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    updateAPP(finalApk_url1);
                                                }
                                            })
                                            .setNegativeButton("稍后再说", null);
                                    AlertDialog alertDialog = dialog.create();
                                    alertDialog.show();
                                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#ff0000"));
                                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#999999"));
                                }
                            }else {
                                if(!dialog_is_new.isShowing())
                                    dialog_is_new.show();
                            }
                        }else{
                            if(!dialog_is_new.isShowing())
                                dialog_is_new.show();
                        }
                    }
                }, new DefaultErrorHook(), QueryUpdateResponse.class);
    }

    private void updateAPP(String apk_url) {
        new DownloadTask().execute(apk_url);
    }


    class DownloadTask extends AsyncTask<String, Integer, File> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(SettingActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setTitle("下载中");
            progressDialog.setProgress(0);
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.show();
            super.onPreExecute();
        }


        @Override
        protected File doInBackground(String... params) {
            File file = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                int length = conn.getContentLength();
                int downLength = 0;
                InputStream is = conn.getInputStream();
                File dir = new File(Environment.getExternalStorageDirectory(),
                        "bimu");
                if (!dir.exists())
                    dir.mkdir();
                file = new File(dir, "new.apk");
                FileOutputStream fos = new FileOutputStream(file);
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buff = new byte[1024];
                int l;
                while ((l = bis.read(buff)) != -1) {
                    fos.write(buff, 0, l);
                    downLength += l;
                    publishProgress(100 * downLength / length);
                }
                fos.close();
                bis.close();
                is.close();
            } catch (Exception e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!SettingActivity.this.isFinishing())
                        showDialog("下载失败");
                    }
                });
                progressDialog.dismiss();
                this.cancel(true);
            }
            return file;
        }

        @Override
        protected void onPostExecute(File file) {
            progressDialog.dismiss();
            AppUtils.installApk(SettingActivity.this, file);
            super.onPostExecute(file);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
            super.onProgressUpdate(values);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CROP_RESULT_CODE:
                String path = data.getStringExtra(AppConstant.RESULT_PATH);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                if (bitmap == null) {
                    if(!this.isFinishing())
                    showDialog("亲,找到【设置】在【应用管理权限】中为【比目】打开读写存储权限哦");
                }
                LoadDialog.show(SettingActivity.this,"头像上传中");
                try {
                    HttpUtils.post(MethodConstant.UPLOAD_PIC,
                            new UploadPicRequest(BitmapBase64TransferUtils.bitmapToBase64(bitmap), 0,
                                    LoginUtils.getLoginUid()),
                            new ResponseHook() {
                                @Override
                                public void deal(Context context, JsonReceive receive) {
                                    UploadPicResponse response = (UploadPicResponse) receive
                                            .getResponse();
                                    if (receive.getStatus() != 200) {
                                        ToastUtils.showShort(SettingActivity.this, "上传失败");
                                        LoadDialog.dismiss(context);
                                        return;
                                    }
                                    if (response != null && response.getExe_success() == 1) {
                                        ToastUtils.showShort(SettingActivity.this, "头像上传成功");
                                        LoadDialog.dismiss(context);
                                    }
                                }
                            },
                            new ErrorHook() {
                                @Override
                                public void deal(Context context, VolleyError error) {
                                    ToastUtils.showShort(SettingActivity.this, "头像上传失败");
                                    LoadDialog.dismiss(context);
                                }
                            }, UploadPicResponse.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case REQUEST_PIC_CODE:
                Intent intent = new Intent(this,ClipImageActivity.class);
                Uri uri = geturi(data);
                intent.putExtra("path",getRealPathFromURI(uri));
                startActivityForResult(intent, CROP_RESULT_CODE);
                break;
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();
        }
        return res;
    }

    //清除缓存
    public void deleteCache() {
        String cacheDir =
                BimuApplication.getContext().getCacheDir().toString() + "/image_manager_disk_cache";
        File cache = new File(cacheDir);
        for (File item : cache.listFiles()) {
            item.delete();
        }
    }


}
