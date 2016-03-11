package cn.qaii.intelligentgateway.frame.constant;

import java.io.File;

import android.os.Environment;

public class LConstants {

	// =========================本地相关============================
	public static final String APP_TAG = "cx_investor"; // 项目关键�?
	public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_TAG + File.separator; // 项目路径
	static final String APP_PATH_TEST = APP_TAG + File.separator; // 项目路径
	public static final String LOG_PATH = APP_PATH + "log" + File.separator + "cx_investor.log"; // 日志文件路径
	public static final String CRASH_PATH = APP_PATH + "crash" + File.separator; // 日志文件路径
	public static final String SOUND_PATH = APP_PATH + "sound" + File.separator; // 录音文件路径
	public static final String TEMP_PATH = APP_PATH + "temp" + File.separator; // 临时文件路径,比如缓存图片
	public static final String DOWNLOAD_APP_PATH = APP_PATH + "download" + File.separator; // 临时文件路径,比如缓存图片
	public static final String TEMP_PATH_TEST = APP_PATH_TEST + "temp" + File.separator; // 临时文件路径,比如缓存图片
	public static final String CHARSET = "UTF-8";//字符编码
	
//	public static final String SOUND_NAME = "longau.amr";//
//	public static final String IMAGE_NAME = "longau.png";//
	public static final String IMAGE_NAME = "longau_" + System.currentTimeMillis() + ".jpg";//
	
	// 新业务数据�?�?
//	public static String SERVER_NET_CHANNEL1 = "1,210.14.147.154,8066";//测试
	public static String SERVER_NET_CHANNEL1 = "1,211.155.90.20,8066";
	// 老业务数据�?�?
//	public static final String SERVER_NET_CHANNEL2 = "1,192.168.1.119,9999";
	public static String SERVER_NET_CHANNEL2 = "1,113.31.87.135,9999";//测试
//	public static String SERVER_NET_CHANNEL2 = "1,211.155.85.151,9999";
	// 音频服务器�?�?
	public static final String SERVER_NET_CHANNEL3 = "0,210.14.147.132,6001";

	public static String VOICE_IP = "211.155.90.21";
//	public static String VOICE_IP = "192.168.1.49";

	public static int VOICE_PORT = 1500;
	
	//==========================联网相关=============================
	public static String BASE_URL = "http://co.longau.com/req.aspx";//网络访问地址
//	public static String BASE_URL = "http://113.31.87.135:8585/req.aspx";//测试网络地址
	
	//快讯地址
	public static final String BASE_NEWS_URL = "http://leida.longau.com/ajax/liveNews/json";
	
//	public static String FEEDBACK_URL = "http://113.31.87.135:9090/page/wap/feedBack/";//反馈测试地址
	public static String FEEDBACK_URL = "http://www.caixinim.com/page/wap/feedBack/";//反馈网络地址
//	public static String HELP_URL = "http://113.31.87.135:9090/page/wap/help/0/";//帮助测试地址
	public static String HELP_URL = "http://www.caixinim.com/page/wap/help/0/";//帮助网络地址
//	public static String ABOUT_URL = "http://113.31.87.135:9090/page/wap/about/0/";//关于测试地址
	public static String ABOUT_URL = "http://www.caixinim.com/page/wap/about/0/";//关于网络地址
	
	public static final String BASE_KIND = "kind";
	public static final String BASE_CYCLE = "cycle";
	public static final String BASE_INDEX = "index";

}
