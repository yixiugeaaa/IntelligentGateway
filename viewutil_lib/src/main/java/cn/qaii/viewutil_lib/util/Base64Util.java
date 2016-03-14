package cn.qaii.viewutil_lib.util;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.qaii.viewutil_lib.constant.LConstants;


public class Base64Util {
	public static String getBaseStr(String filePath) {
		String mResult = null;
		try {
			File file = new File(filePath);
			byte[] bytes = getByte(file);
			mResult = Base64.encodeToString(bytes, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mResult;
	}

	/**
	 * 将音频文件转换成Base64格式用于传输
	 * 
	 * @return
	 */
	public static String getBase(String name) {
		String mResult = null;
		try {
			File file = new File(LConstants.TEMP_PATH + name + ".png");
			byte[] bytes = getByte(file);
			mResult = Base64.encodeToString(bytes, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mResult;
	}

	public static byte[] getByte(File file) throws Exception {
		byte[] bytes = null;
		if (file != null) {
			try {
				InputStream is = new FileInputStream(file);
				int length = (int) file.length();
				if (length > Integer.MAX_VALUE) {// 当文件的长度超过了int的最大值
					System.out.println("this file is max ");
					return null;
				}
				bytes = new byte[length];
				int offset = 0;
				int numRead = 0;
				while (offset < bytes.length
						&& (numRead = is.read(bytes, offset, bytes.length
								- offset)) >= 0) {
					offset += numRead;
				}
				// 如果得到的字节长度和file实际的长度不一致就可能出错了
				if (offset < bytes.length) {
					System.out.println("file length is error");
					return null;
				}
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}

	/**
	 * 生成arm音频文件
	 * 
	 * @param base
	 * @param fileName
	 */
	public static void decodeToArm(String base, String fileName) {
		byte[] armBuff = Base64.decode(base, Base64.DEFAULT);
		createFile(armBuff, fileName);
	}

	/**
	 * 根据byte数组，生成文件
	 */
	public static void createFile(byte[] bfile, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File folder = new File(LConstants.TEMP_PATH);
			// 文件夹不存在则创建
			if (!folder.exists())
				folder.mkdir();

			file = new File(folder, fileName + ".png");
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将图片转换成字符串发送
	 * 
	 * @param bitmap
	 * @return
	 */
	public static String encodeImage(Bitmap bitmap) {
		if (bitmap == null)
			return "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos);
		byte[] bytes = baos.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	/**
	 * 将图片保存到本地 saveBitmapToFile(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param bitmap
	 * @return File
	 * @exception
	 * @since 1.0.0
	 */
	public static File saveBitmapToFile(Bitmap bitmap) {
		try {
			String fileName = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss",
					Locale.CHINA);
			Date curDate = new Date(System.currentTimeMillis());
			fileName = formatter.format(curDate);
			File root = new File(LConstants.APP_PATH);
			if (!root.exists()) {
				root.mkdirs();
			}
			if (root.exists() && !root.isDirectory()) {
				root.delete();
				root.mkdirs();
			}
			File file = new File(LConstants.TEMP_PATH);
			if (!file.exists()) {
				file.mkdirs();
			}
			if (file.exists() && !file.isDirectory()) {
				file.delete();
				file.mkdirs();
			}
			File f = new File(LConstants.TEMP_PATH, fileName + ".png");
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			return f;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
