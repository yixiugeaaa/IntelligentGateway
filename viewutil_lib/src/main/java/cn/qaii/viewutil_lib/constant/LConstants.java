package cn.qaii.viewutil_lib.constant;

import android.os.Environment;

import java.io.File;

/**
 * 
 * LConstants 全局常量管理类，比如缓存目录等
 * 
 * @author larry 2015-4-10 下午2:35:55
 * @version 1.0.0
 *
 */
public class LConstants {

	// =========================本地相关============================
	public static final String APP_TAG = "maya"; // 项目关键字
	public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ File.separator
			+ APP_TAG + File.separator; // 项目路径
	public static final String LOG_PATH = APP_PATH + "log" + File.separator; // 日志文件路径
	public static final String CRASH_PATH = APP_PATH + "crash" + File.separator; // 异常文件路径
	public static final String SOUND_PATH = APP_PATH + "sound" + File.separator; // 录音文件路径
	public static final String TEMP_PATH = APP_PATH + "temp" + File.separator; // 临时文件路径,比如缓存图片
	public static final String DOWNLOAD_APP_PATH = APP_PATH + "download"
			+ File.separator; // 临时文件路径,比如缓存图片
	public static final String CHARSET = "UTF-8";// 字符编码

	public static final String TEMP_PIC_PATH = TEMP_PATH + "temp.jpg";

	public static final String DESCRIPTOR = "com.umeng.share";

}
