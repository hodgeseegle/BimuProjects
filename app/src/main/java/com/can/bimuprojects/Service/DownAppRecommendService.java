package com.can.bimuprojects.Service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import com.can.bimuprojects.utils.PrefUtils;

import java.io.File;

/**
 * Created by can on 2017/5/16.
 * 后台下载apk
 */

public class DownAppRecommendService extends Service {

    public static final String DOWNLOAD_URL = "apk";
    public static final String DOWNLOAD_FOLDER_NAME = "bimu";
    public static final String DOWNLOAD_FILE_NAME = "bimu.apk";


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String downloadUrl = intent.getExtras().getString(DOWNLOAD_URL);
            if (downloadUrl != null) {
                // 注册下载完成广播
                DownloadCompleteReceiver completeReceiver = new DownloadCompleteReceiver();
                registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                // 下载
                new Thread(new DownloadRunable(downloadUrl)).start();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    // 下载
    class DownloadRunable implements Runnable{
        private String mDownloadUrl;

        public DownloadRunable(String url) {
            mDownloadUrl = url;
        }

        @Override
        public void run() {
            startDownload();
        }

        // 开始下载
        private void startDownload(){

            File folder = Environment.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME);
            if (!folder.exists()||!folder.isDirectory() ) {
                folder.mkdirs();
            }

            DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mDownloadUrl));
            request.setMimeType("application/vnd.android.package-archive");
            // 存储的目录
            request.setDestinationInExternalPublicDir(DOWNLOAD_FOLDER_NAME, DOWNLOAD_FILE_NAME);
            // 设定只有在 WIFI 情况下才能下载
            //request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
            request.setTitle(PrefUtils.get("app_name",""));
            request.setVisibleInDownloadsUi(true);
            manager.enqueue(request);
        }
    }


    // 下载完成
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (intent.getAction() == DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
                checkStatus(context, completeDownloadId);
            }
        }

        private void checkStatus(Context context,Long completeDownloadId){
            DownloadManager mManager  = (DownloadManager) context.getSystemService(Service.DOWNLOAD_SERVICE);
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(completeDownloadId);
            Cursor cursor = mManager.query(query);
            if (cursor.moveToFirst()) {
                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

                switch (status) {
                    case DownloadManager.STATUS_RUNNING:
                        break;

                    case DownloadManager.STATUS_SUCCESSFUL:
                        String apkFilePath = new StringBuilder(Environment.getExternalStorageDirectory().getAbsolutePath())
                                .append(File.separator).append(DownAppRecommendService.DOWNLOAD_FOLDER_NAME).append(File.separator)
                                .append(DownAppRecommendService.DOWNLOAD_FILE_NAME).toString();
                        installApk(context, apkFilePath);

                        // 停止下载Service
                        DownAppRecommendService.this.stopSelf();
                        break;

                    default:
                        break;
                }
            }
            cursor.close();
        }

        // 安装APk
        private  void installApk(Context context, String file) {
            File apkFile = new File(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }
}
