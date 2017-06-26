package com.can.bimuprojects.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

import com.can.bimuprojects.Constant.AppConstant;
import com.can.bimuprojects.R;
import com.can.bimuprojects.view.ClipImageLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ClipImageActivity extends BaseActivity implements OnClickListener {
	private static final String KEY_PATH = "path";
	private ClipImageLayout mClipImageLayout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crop_image_layout);

		mClipImageLayout = (ClipImageLayout) findViewById(R.id.clipImageLayout);
		String path = getIntent().getStringExtra(KEY_PATH);

		// 有的系统返回的图片是旋转了，有的没有旋转，所以处理
		int degreee = readBitmapDegree(path);
		Bitmap bitmap =createBitmap(path);
		if (bitmap != null) {
			if (degreee == 0) {
			mClipImageLayout.setImageBitmap(bitmap);
			} else {
				mClipImageLayout.setImageBitmap(rotateBitmap(degreee, bitmap));
				bitmap.recycle();
			}
		} else {
			finish();
		}
		findViewById(R.id.okBtn).setOnClickListener(this);
		findViewById(R.id.cancleBtn).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.okBtn) {
			Bitmap bitmap = mClipImageLayout.clip();
			StringBuilder sb = new StringBuilder();

			if(isSDAvailable()){
				sb.append(Environment.getExternalStorageDirectory()
						.getAbsolutePath());
				sb.append(File.separator);
				sb.append(AppConstant.TMP_PATH);
			}else{
				File filesDir = getCacheDir();
				sb.append(filesDir.getAbsolutePath());
				sb.append(File.separator);
				sb.append(AppConstant.TMP_PATH);
			}

			saveBitmap(bitmap, sb.toString());
			bitmap.recycle();
			Intent intent = new Intent();
			intent.putExtra(AppConstant.RESULT_PATH, sb.toString());
			setResult(RESULT_OK, intent);
		}
		finish();
	}

	private  boolean isSDAvailable() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	private void saveBitmap(Bitmap bitmap, String path) {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
		}

		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			fOut.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (fOut != null)
					fOut.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建图片
	 *
	 * @param path
	 * @return
	 */
	private Bitmap createBitmap(String path) {
		if (path == null) {
			return null;
		}
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;// 这里一定要将其设置回false，因为之前我们将其设置成了true
		opts.inDither = true;//optional
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
		BitmapFactory.decodeFile(path,opts);
		int originalWidth = opts.outWidth;
		int originalHeight = opts.outHeight;
		if ((originalWidth == -1) || (originalHeight == -1))
			return null;
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
		opts.inSampleSize = be;//设置缩放比例
		opts.inJustDecodeBounds = false;
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeFile(path, opts);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return bitmap;
	}

	// 读取图像的旋转度
	private int readBitmapDegree(String path) {
		int degree = 0;
		if(path==null){
			return degree;
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
}
