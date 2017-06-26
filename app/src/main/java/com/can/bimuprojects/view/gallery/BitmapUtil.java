package com.can.bimuprojects.view.gallery;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;


/**
 * Collection of utility functions used in this package.
 */
public class BitmapUtil {
    private static final String TAG = "Util";
    public static final int DIRECTION_LEFT = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_UP = 2;
    public static final int DIRECTION_DOWN = 3;

    public static final String REVIEW_ACTION = "com.android.camera.action.REVIEW";

    private BitmapUtil() {
    }

    // Rotates the bitmap by the specified degree.
    // If a new bitmap is created, the original bitmap is recycled.
    public static Bitmap rotate(Bitmap b, int degrees) {
        if (degrees != 0 && b != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees,
                    (float) b.getWidth() / 2, (float) b.getHeight() / 2);
            try {
                Bitmap b2 = Bitmap.createBitmap(
                        b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if (b != b2) {
                    b.recycle();
                    b = b2;
                }
            } catch (OutOfMemoryError ex) {
                // We have no memory to rotate. Return the original bitmap.
            }
        }
        return b;
    }

    /*
     * Compute the sample size as a function of minSideLength
     * and maxNumOfPixels.
     * minSideLength is used to specify that minimal width or height of a
     * bitmap.
     * maxNumOfPixels is used to specify the maximal size in pixels that is
     * tolerable in terms of memory usage.
     *
     * The function returns a sample size based on the constraints.
     * Both size and minSideLength can be passed in as IImage.UNCONSTRAINED,
     * which indicates no care of the corresponding constraint.
     * The functions prefers returning a sample size that
     * generates a smaller bitmap, unless minSideLength = IImage.UNCONSTRAINED.
     *
     * Also, the function rounds up the sample size to a power of 2 or multiple
     * of 8 because BitmapFactory only honors sample size this way.
     * For example, BitmapFactory downsamples an image by 2 even though the
     * request is 3. So we round up the sample size to avoid OOM.
     */
    public static int computeSampleSize(BitmapFactory.Options options,
            int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
            int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == IImage.UNCONSTRAINED) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == IImage.UNCONSTRAINED) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == IImage.UNCONSTRAINED) &&
                (minSideLength == IImage.UNCONSTRAINED)) {
            return 1;
        } else if (minSideLength == IImage.UNCONSTRAINED) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static <T>  int indexOf(T [] array, T s) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(s)) {
                return i;
            }
        }
        return -1;
    }

    public static void closeSilently(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (Throwable t) {
            // do nothing
        }
    }

    public static Bitmap makeBitmap(byte[] jpegData, int maxNumOfPixels) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length,
                    options);
            if (options.mCancel || options.outWidth == -1
                    || options.outHeight == -1) {
                return null;
            }
            options.inSampleSize = computeSampleSize(options, IImage.UNCONSTRAINED, maxNumOfPixels);
            options.inJustDecodeBounds = false;
            options.inDither = false;
            options.inPreferredConfig = Config.ARGB_8888;
            return BitmapFactory.decodeByteArray(jpegData, 0, jpegData.length,
                    options);
        } catch (OutOfMemoryError ex) {
            Log.i(TAG, "Got oom exception ", ex);
            return null;
        }
    }

    public static void Assert(boolean cond) {
        if (!cond) {
            throw new AssertionError();
        }
    }



    public static Animation slideOut(View view, int to) {
        view.setVisibility(View.INVISIBLE);
        Animation anim;
        switch (to) {
            case DIRECTION_LEFT:
                anim = new TranslateAnimation(0, -view.getWidth(), 0, 0);
                break;
            case DIRECTION_RIGHT:
                anim = new TranslateAnimation(0, view.getWidth(), 0, 0);
                break;
            case DIRECTION_UP:
                anim = new TranslateAnimation(0, 0, 0, -view.getHeight());
                break;
            case DIRECTION_DOWN:
                anim = new TranslateAnimation(0, 0, 0, view.getHeight());
                break;
            default:
                throw new IllegalArgumentException(Integer.toString(to));
        }
        anim.setDuration(500);
        view.startAnimation(anim);
        return anim;
    }

    public static Animation slideIn(View view, int from) {
        view.setVisibility(View.VISIBLE);
        Animation anim;
        switch (from) {
            case DIRECTION_LEFT:
                anim = new TranslateAnimation(-view.getWidth(), 0, 0, 0);
                break;
            case DIRECTION_RIGHT:
                anim = new TranslateAnimation(view.getWidth(), 0, 0, 0);
                break;
            case DIRECTION_UP:
                anim = new TranslateAnimation(0, 0, -view.getHeight(), 0);
                break;
            case DIRECTION_DOWN:
                anim = new TranslateAnimation(0, 0, view.getHeight(), 0);
                break;
            default:
                throw new IllegalArgumentException(Integer.toString(from));
        }
        anim.setDuration(500);
        view.startAnimation(anim);
        return anim;
    }

    public static <T> T checkNotNull(T object) {
        if (object == null) throw new NullPointerException();
        return object;
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a == null ? false : a.equals(b));
    }

    public static boolean isPowerOf2(int n) {
        return (n & -n) == n;
    }

    public static int nextPowerOf2(int n) {
        n -= 1;
        n |= n >>> 16;
        n |= n >>> 8;
        n |= n >>> 4;
        n |= n >>> 2;
        n |= n >>> 1;
        return n + 1;
    }

    public static float distance(float x, float y, float sx, float sy) {
        float dx = x - sx;
        float dy = y - sy;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public static int clamp(int x, int min, int max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }
    

	public static ContentValues videoContentValues = null;

	public static String getRecordingTimeFromMillis(long millis) {
		String strRecordingTime = null;
		int seconds = (int) (millis / 1000);
		int minutes = seconds / 60;
		int hours = minutes / 60;

		if (hours >= 0 && hours < 10)
			strRecordingTime = "0" + hours + ":";
		else
			strRecordingTime = hours + ":";

		if (hours > 0)
			minutes = minutes % 60;

		if (minutes >= 0 && minutes < 10)
			strRecordingTime += "0" + minutes + ":";
		else
			strRecordingTime += minutes + ":";

		seconds = seconds % 60;

		if (seconds >= 0 && seconds < 10)
			strRecordingTime += "0" + seconds;
		else
			strRecordingTime += seconds;

		return strRecordingTime;

	}


	public static int getRotationAngle(Activity activity) {
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;

		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;

		case Surface.ROTATION_90:
			degrees = 90;
			break;

		case Surface.ROTATION_180:
			degrees = 180;
			break;

		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}
		return degrees;
	}


	/**
	 * 通过文件路径获取文件的创建时间
	 */
		public static String getFileCreatedTime(String filePath) {
			String string = "";
			File file = new File(filePath);
			Date date = new Date(file.lastModified());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日 HH:mm:ss");
			string = sdf.format(date);
			return string;
		}

		/**
		 *  通过文件路径获取文件的大小,并自动格式化
		 */
		public static String getVideoLength(String filePath) {
			String string = "0B";
			DecimalFormat df = new DecimalFormat("#.00");
			File file = new File(filePath);
			long length = file.length();
			if (length == 0) { // 文件大小为0,直接返回0B
				return string;
			}
			if (length < 1024) { // 文件小于1KB,单位为 B
				string = df.format((double) length) + "B";
			} else if (length < 1048576) {// 文件小于1M,单 位为 KB
				string = df.format((double) length / 1024) + "K";
			} else if (length < 1073741824) {// 文件小于1G,单位为 MB
				string = df.format((double) length / 1048576) + "M";
			} else {
				string = df.format((double) length / 1073741824) + "G";
			}
			return string;
		}
		
		/**
		 * 从路径中提取文件名(包括后缀)
		 */
		 public static String getFileName(String pathandname){  
		        int start=pathandname.lastIndexOf("/");  
		        int end=pathandname.length();  
		        if(start!=-1 && end!=-1){  
		            return pathandname.substring(start+1,end);    
		        }else{  
		            return null;  
		        }  
		    } 
		 
		 /**
		  * 视频总长度格式化  如 00:12表示12秒
		  */
		public static StringBuilder getVideoLength(int length){
			StringBuilder string = new StringBuilder();
			int qian = 0;
			int bai = 0;
			int shi = 0;
			int ge = 0;
			qian = length/60/10;
			bai = length/60%10;
			shi = length%60/10;
			ge = length%10/1;
			string.append(qian).append(bai).append(':').append(shi).append(ge);
			return string;
		}

		
		/**
		 * 公共吐司Toast
		 */
		public static Toast getPublicToast(String string , Context context){
			return Toast.makeText(context,
				     string, Toast.LENGTH_SHORT);
		}
		
		/**
		 * 将assets下的文件保存到sd卡
		 * 
		 * @param context
		 *            上下文
		 * @param assetsPath
		 *            assets下的路径
		 * @param sdCardPath
		 *            sd卡的路径
		 */
		public static   void putAssetsToSDCard(Context context, String assetsPath,
				String sdCardPath) {
			try {
				String mString[] = context.getAssets().list(assetsPath);
				if (mString.length == 0) { // 说明assetsPath为空,或者assetsPath是一个文件
					InputStream mIs = context.getAssets().open(assetsPath); // 读取流
					byte[] mByte = new byte[1024];
					int bt = 0;
					File file = new File(sdCardPath + File.separator
							+ assetsPath.substring(assetsPath.lastIndexOf('/')));
					if (!file.exists()){
						file.createNewFile(); // 创建文件
					}else{
						return; //已经存在直接退出
					}
					FileOutputStream fos = new FileOutputStream(file); // 写入流
					while ((bt = mIs.read(mByte)) != -1) { // assets为文件,从文件中读取流
						fos.write(mByte, 0, bt);// 写入流到文件中
					}
					fos.flush();// 刷新缓冲区
					mIs.close();// 关闭读取流
					fos.close();// 关闭写入流
				} else { // 当mString长度大于0,说明其为文件夹
					sdCardPath = sdCardPath + File.separator + assetsPath;
					File file = new File(sdCardPath);
					if (!file.exists())
						file.mkdirs(); // 在sd下创建目录
					for (String stringFile : mString) { // 进行递归
						putAssetsToSDCard(context, assetsPath + File.separator
								+ stringFile, sdCardPath);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 获取sd卡的路径
		 * 
		 * @return 路径的字符串
		 */
		public static String getSDPath() {
			File sdDir = null;
			boolean sdCardExist = Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
			if (sdCardExist) {
				sdDir = Environment.getExternalStorageDirectory();// 获取外存目录
			}
			return sdDir.toString();
		}
		
		
		private static int imgWidth;
		private static int imgHeight;
		private static int[] imgPixels;
		/**
		 * 对图像进行预处理
		 * @param 
		 * img 需要处理的图片
		 * @return 处理后的图片
		 */
		public static Bitmap doPretreatment(Bitmap img) {
			imgWidth = img.getWidth();
			imgHeight = img.getHeight();
			imgPixels = new int[imgWidth * imgHeight];
			img.getPixels(imgPixels, 0, imgWidth, 0, 0, imgWidth, imgHeight);
			int[] p = new int[2];
			int maxGrayValue = 0, minGrayValue = 255;
			// 计算最大及最小灰度值
			getMinMaxGrayValue(p);
			minGrayValue = p[0];
			maxGrayValue = p[1];
			// 计算迭代法阈值
			int T1 = getIterationHresholdValue(minGrayValue, maxGrayValue);
			Bitmap result = binarization(T1);
			return result;
		}
		
		// 计算最大最小灰度,保存在数组中
		private static void getMinMaxGrayValue(int[] p) {
			int minGrayValue = 255;
			int maxGrayValue = 0;
			for (int i = 0; i < imgHeight - 1; i++) {
				for (int j = 0; j < imgWidth - 1; j++) {
					int gray = imgPixels[i * imgWidth + imgHeight];
					if (gray < minGrayValue)
						minGrayValue = gray;
					if (gray > maxGrayValue)
						maxGrayValue = gray;
				}
			}
			p[0] = minGrayValue;
			p[1] = maxGrayValue;
		}
		
		// 利用迭代法计算阈值
		private static int getIterationHresholdValue(int minGrayValue,
				int maxGrayValue) {
			int T1;
			int T2 = (maxGrayValue + minGrayValue) / 2;
			do {
				T1 = T2;
				double s = 0, l = 0, cs = 0, cl = 0;
				for (int i = 0; i < imgHeight; i++) {
					for (int j = 0; j < imgWidth; j++) {
						int gray = imgPixels[imgWidth * i + j];
						if (gray < T1) {
							s += gray;
							cs++;
						}
						if (gray > T1) {
							l += gray;
							cl++;
						}
					}
				}
				T2 = (int) (s / cs + l / cl) / 2;
			} while (T1 != T2);
			return T1;
		}
		
		// 针对单个阈值二值化图片
		private static Bitmap binarization(int T) {
			// 用阈值T1对图像进行二值化
			for (int i = 0; i < imgHeight; i++) {
				for (int j = 0; j < imgWidth; j++) {
					int gray = imgPixels[i * imgWidth + j];
					if (gray < T) {
						// 小于阈值设为白色
						imgPixels[i * imgWidth + j] = Color.rgb(0, 0, 0);
					} else {
						// 大于阈值设为黑色
						imgPixels[i * imgWidth + j] = Color.rgb(255, 255, 255);
					}
				}
			}
			Bitmap result = Bitmap
					.createBitmap(imgWidth, imgHeight, Config.RGB_565);
			result.setPixels(imgPixels, 0, imgWidth, 0, 0, imgWidth, imgHeight);
			return result;
		}
		
		/**
		 * 将地址转为路径
		 */
		public static String uriToPath(Uri uri,Activity activity) {
			if (uri.toString().contains("file")) {
				return uri.getPath();
			}
			String[] proj = { MediaStore.Images.Media.DATA };
			@SuppressWarnings("deprecation")
			Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null,
					null);
			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			String img_path = actualimagecursor
					.getString(actual_image_column_index);
			return img_path;
		}
		
		
		/**
	     * Gets the content:// URI from the given corresponding path to a file
	     * @param context
	     * @return content Uri
	     */
	    public static Uri getImageContentUri(Context context,File file) {
	        String filePath = file.getAbsolutePath();
	        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
	                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
	                new String[] { filePath }, null);
	        if (cursor != null && cursor.moveToFirst()) {
	            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
	            Uri baseUri = Uri.parse("content://media/external/images/media");
	            return Uri.withAppendedPath(baseUri, "" + id);
	        } else {
	            if (file.exists()) {
	                ContentValues values = new ContentValues();
	                values.put(MediaStore.Images.Media.DATA, filePath);
	                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	            } else {
	                return null;
	            }
	        }
	    }
	
    
}
