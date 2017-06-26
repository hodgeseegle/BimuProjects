package com.can.bimuprojects.view.gallery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.OrientationEventListener;


public class ImageManager {
	private static final String TAG = "ImageManager";

	private static final Uri STORAGE_URI = Images.Media.EXTERNAL_CONTENT_URI;
	private static final Uri VIDEO_STORAGE_URI = Uri
			.parse("content://media/external/video/media");

	private ImageManager() {
	}

	public static class ImageListParam implements Parcelable {
		public DataLocation mLocation;
		public int mInclusion;
		public int mSort;
		public String mBucketId;

		public boolean mIsEmptyImageList;

		public ImageListParam() {
		}

		public void writeToParcel(Parcel out, int flags) {
			out.writeInt(mLocation.ordinal());
			out.writeInt(mInclusion);
			out.writeInt(mSort);
			out.writeString(mBucketId);
			out.writeInt(mIsEmptyImageList ? 1 : 0);
		}

		private ImageListParam(Parcel in) {
			mLocation = DataLocation.values()[in.readInt()];
			mInclusion = in.readInt();
			mSort = in.readInt();
			mBucketId = in.readString();
			mIsEmptyImageList = (in.readInt() != 0);
		}

		@SuppressLint("DefaultLocale")
		@Override
		public String toString() {
			return String.format("ImageListParam{loc=%s,inc=%d,sort=%d,"
					+ "bucket=%s,empty=%b}", mLocation, mInclusion, mSort,
					mBucketId, mIsEmptyImageList);
		}

		public static final Creator<ImageListParam> CREATOR = new Creator<ImageListParam>() {
			public ImageListParam createFromParcel(Parcel in) {
				return new ImageListParam(in);
			}

			public ImageListParam[] newArray(int size) {
				return new ImageListParam[size];
			}
		};

		public int describeContents() {
			return 0;
		}
	}

	// Location
	public static enum DataLocation {
		NONE, INTERNAL, EXTERNAL, ALL
	}

	// Inclusion
	public static final int INCLUDE_IMAGES = (1 << 0);
	public static final int INCLUDE_VIDEOS = (1 << 2);

	// Sort
	public static final int SORT_ASCENDING = 1;
	public static final int SORT_DESCENDING = 2;


	/**
	 * Matches code in MediaProvider.computeBucketValues. Should be a common
	 * function.
	 */
	@SuppressLint("DefaultLocale")
	public static String getBucketId(String path) {
		return String.valueOf(path.toLowerCase().hashCode());
	}


	public static int roundOrientation(int orientationInput) {
		int orientation = orientationInput;

		if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
			orientation = 0;
		}

		orientation = orientation % 360;
		int retVal;
		if (orientation < (0 * 90) + 45) {
			retVal = 0;
		} else if (orientation < (1 * 90) + 45) {
			retVal = 90;
		} else if (orientation < (2 * 90) + 45) {
			retVal = 180;
		} else if (orientation < (3 * 90) + 45) {
			retVal = 270;
		} else {
			retVal = 0;
		}

		return retVal;
	}

	public static Uri addImage(ContentResolver cr, String title,
			long dateTaken, Location location, String directory,
			String filename, Bitmap source, byte[] jpegData, int[] degree) {
		OutputStream outputStream = null;
		String filePath = directory + "/" + filename;
		try {
			File dir = new File(directory);
			if (!dir.exists())
				dir.mkdirs();
			File file = new File(directory, filename);
			outputStream = new FileOutputStream(file);
			if (source != null) {
				source.compress(CompressFormat.JPEG, 75, outputStream);
				degree[0] = 0;
			} else {
				outputStream.write(jpegData);
				degree[0] = getExifOrientation(filePath);
			}
		} catch (FileNotFoundException ex) {
			Log.w(TAG, ex);
			return null;
		} catch (IOException ex) {
			Log.w(TAG, ex);
			return null;
		} finally {
			BitmapUtil.closeSilently(outputStream);
		}
		long size = new File(directory, filename).length();
		ContentValues values = new ContentValues(9);
		values.put(Images.Media.TITLE, title);
		values.put(Images.Media.DISPLAY_NAME, filename);
		values.put(Images.Media.DATE_TAKEN, dateTaken);
		values.put(Images.Media.MIME_TYPE, "image/jpeg");
		values.put(Images.Media.ORIENTATION, degree[0]);
		values.put(Images.Media.DATA, filePath);
		values.put(Images.Media.SIZE, size);

		if (location != null) {
			values.put(Images.Media.LATITUDE, location.getLatitude());
			values.put(Images.Media.LONGITUDE, location.getLongitude());
		}

		return cr.insert(STORAGE_URI, values);
	}

	public static int getExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			Log.e(TAG, "cannot read exif", ex);
		}
		if (exif != null) {
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
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
			}
		}
		return degree;
	}


	
	private static class EmptyImageList implements IImageList {
		public void close() {
		}
		public int getCount() {
			return 0;
		}
		public IImage getImageAt(int i) {
			return null;
		}
	}

	public static ImageListParam getImageListParam(DataLocation location,
			int inclusion, int sort, String bucketId) {
		ImageListParam param = new ImageListParam();
		param.mLocation = location;
		param.mInclusion = inclusion;
		param.mSort = sort;
		param.mBucketId = bucketId;
		return param;
	}



	private static Cursor query(ContentResolver resolver, Uri uri,
			String[] projection, String selection, String[] selectionArgs,
			String sortOrder) {
		try {
			if (resolver == null) {
				return null;
			}
			return resolver.query(uri, projection, selection, selectionArgs,
					sortOrder);
		} catch (UnsupportedOperationException ex) {
			return null;
		}
	}

	public static boolean isMediaScannerScanning(ContentResolver cr) {
		boolean result = false;
		Cursor cursor = query(cr, MediaStore.getMediaScannerUri(),
				new String[] { MediaStore.MEDIA_SCANNER_VOLUME }, null, null,
				null);
		if (cursor != null) {
			if (cursor.getCount() == 1) {
				cursor.moveToFirst();
				result = "external".equals(cursor.getString(0));
			}
			cursor.close();
		}
		return result;
	}

}