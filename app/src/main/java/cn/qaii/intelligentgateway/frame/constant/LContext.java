package cn.qaii.intelligentgateway.frame.constant;

import android.content.Context;

public class LContext {

	/*********** 全局变量 **************/
	public static Context context;
	public static String CLIENT_TYPE = "2000";///////////////////////////////////////////////
	public static String TOKEN = "";// 登录用户的验证机制
	public static int width;
	public static int height;

	public static void init(Context context) {
		if (context == null) {
			return;
		}
		LContext.context = context;
	}
}
