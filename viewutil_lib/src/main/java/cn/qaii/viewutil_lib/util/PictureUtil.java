package cn.qaii.viewutil_lib.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.qaii.viewutil_lib.constant.LConstants;


/**
 * @Description 调用系统拍照或进入图库中选择照片,再进行裁剪,压缩.
 */
public class PictureUtil {
	/** 用来请求照相功能的常量 */
	public static final int CAMERA_WITH_DATA = 168;
	/** 用来请求图片选择器的常量 */
	public static final int PHOTO_PICKED_WITH_DATA = CAMERA_WITH_DATA + 1;
	/** 用来请求图片裁剪的 */
	public static final int PHOTO_CROP = PHOTO_PICKED_WITH_DATA + 1;
	/** 拍照的照片存储位置 */
	public static final File PHOTO_DIR = new File(
			Environment.getExternalStorageDirectory() + "/DCIM/Camera/");

	public static File mCurrentPhotoFile;// 照相机拍照得到的图片

	public File file; // 截图后得到的图片
	public static Uri imageUri; // 拍照后的图片路径
	public static Uri imageCaiUri; // 裁剪后的图片路径

	private static int compressPercent = 100;// 压缩百分比

	/**
	 * 得到当前图片文件的路径
	 * 
	 * @return
	 */
	public static File getmCurrentPhotoFile() {
		if (mCurrentPhotoFile == null) {
			if (!PHOTO_DIR.exists()) {
				PHOTO_DIR.mkdirs(); // 创建照片的存储目录
			}
			mCurrentPhotoFile = new File(PHOTO_DIR, getCharacterAndNumber()
					+ ".jpg"/* 此处可更换文件后缀 */);
			if (!mCurrentPhotoFile.exists()) // 判断存储文件是否存在>不存在则创建
				try {
					mCurrentPhotoFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return mCurrentPhotoFile;
	}

	/**
	 * 开始启动照片选择框
	 * 
	 * @param context
	 * @param iscrop
	 *            是否裁剪
	 */
	public static void doPickPhotoAction(final Activity context) {

		PicturePickDialog ppd = new PicturePickDialog(context);

		ppd.show();

	}

	/**
	 * 调用拍照功能
	 */
	public static void doTakePhoto(Activity context) {
		try {

			File tempFile = new File(LConstants.TEMP_PATH);
			if (!tempFile.exists()) {
				tempFile.mkdirs(); // 创建照片的存储目录
			}
			Uri tempUri = Uri.fromFile(new File(LConstants.TEMP_PIC_PATH));
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
			context.startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(context, "没有找到照相机", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 调用相册程序
	 * 
	 * @param context
	 * @param iscrop
	 */
	public static void doPickPhotoFromGallery(Context context) {
		try {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			((Activity) context).startActivityForResult(intent,
					PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(context, "没有找到相册", Toast.LENGTH_SHORT).show();
		}
	}

	public static Uri getImageUri() {
		File temporaryFile = new File(PHOTO_DIR, getCharacterAndNumber()
				+ ".jpg");

		System.out.println(temporaryFile.getAbsoluteFile() + "-->"
				+ temporaryFile.length());

		imageUri = Uri.fromFile(temporaryFile);
		return imageUri;
	}

	public static Uri getImageCaiUri() {
		File temporaryFile = new File(LConstants.TEMP_PATH, getCharacterAndNumber()
				+ ".jpg");
		imageCaiUri = Uri.fromFile(temporaryFile);
		return imageCaiUri;
	}

	/**
	 * 获得相机保存图片的路径
	 * 
	 * @return
	 */
	public static Uri getCameraImageCaiUri() {

		// File temporaryFile = new File(PHOTO_DIR,
		// getCharacterAndNumber()+".png");
		System.out.println(imageUri.getPath() + "-->");
		imageUri = Uri.fromFile(mCurrentPhotoFile);
		return imageUri;
	}

	// 得到系统当前时间并转化为字符串
	@SuppressLint("SimpleDateFormat")
	public static String getCharacterAndNumber() {
		String rel = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date curDate = new Date();
		rel = formatter.format(curDate);
		return rel;
	}

	public static void saveMyBitmap(Bitmap mBitmap, File file) {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			mBitmap.compress(CompressFormat.PNG, compressPercent, baos);
			byte[] bitmapData = baos.toByteArray();

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(bitmapData);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 压缩图片(第二次)
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.PNG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		while (baos.toByteArray().length / 1024 > 200) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(CompressFormat.PNG, compressPercent, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	// 压缩图片(第一次)
	public static Bitmap comp(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(CompressFormat.PNG, 100, baos);
		if (baos.toByteArray().length / 1024 > 200) {// 判断如果图片大于200KB,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
			baos.reset();// 重置baos即清空baos
			image.compress(CompressFormat.PNG, compressPercent, baos);// 这里压缩50%，把压缩后的数据存放到baos中
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		// 开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		// 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 250f;// 这里设置高度为800f
		float ww = 375f;// 这里设置宽度为480f
		// float hh = 156f;//这里设置高度为800f
		// float ww = 216f;//这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		isBm = new ByteArrayInputStream(baos.toByteArray());
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
		return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
	}

	public static String compressJpgImage(String srcPath) throws IOException {
		try {
			String outPath = LConstants.TEMP_PATH + getCharacterAndNumber()
					+ ".jpg";

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			// get bitmap size
			BitmapFactory.decodeFile(srcPath, options);

			int w = options.outWidth;
			int h = options.outHeight;

			Log.e("PictureUtil","src bitmap w/h:" + w + "/" + h);

			int sampleSize = 1;
			sampleSize = getSampleSize(w, h);

			options.inJustDecodeBounds = false;
			options.inSampleSize = sampleSize;
			options.inPreferredConfig = Config.RGB_565;
			Bitmap newBitmap = BitmapFactory.decodeFile(srcPath, options);
			// 以下注释为调用部分
			int angle = getExifOrientation(srcPath);
			if (angle != 0) { // 如果照片出现了 旋转 那么 就更改旋转度数
				Matrix matrix = new Matrix();
				matrix.postRotate(angle);
				newBitmap = Bitmap.createBitmap(newBitmap, 0, 0,
						newBitmap.getWidth(), newBitmap.getHeight(), matrix,
						true);
			}

			File tempFile = new File(LConstants.TEMP_PATH);
			if (!tempFile.exists()) {
				tempFile.mkdirs(); // 创建照片的存储目录
			}

			File outFile = new File(outPath);

			if (!outFile.exists()) {
				outFile.createNewFile();
			}

			newBitmap.compress(CompressFormat.JPEG, 75, new FileOutputStream(
					outFile));
			newBitmap.recycle();
			return outPath;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static int getSampleSize(int w, int h) {
		// default 800*480
		int wi = (int) Math.ceil(w / 400.0);

		int hi = (int) Math.ceil(h / 600.0);

		return wi > hi ? wi : hi;

		// double i = Math.sqrt(w * w + h * h);
		//
		// return (int) (i > s ? Math.ceil(i / s) : 1);
	}

	/**
	 * Try to return the absolute file path from the given Uri
	 *
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public static String getRealFilePath(final Context context, final Uri uri) {
		if (null == uri)
			return null;
		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null)
			data = uri.getPath();
		else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = context.getContentResolver().query(uri,
					new String[] { ImageColumns.DATA }, null, null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	/**
	 * 得到 图片旋转 的角度
	 * 
	 * @param filepath
	 * @return
	 */
	private static int getExifOrientation(String filepath) {
		int degree = 0;
		ExifInterface exif = null;
		try {
			exif = new ExifInterface(filepath);
		} catch (IOException ex) {
			Log.e("PictureUtil","cannot read exif" + ex);
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

}