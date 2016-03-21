package cn.qaii.intelligentgateway.frame.constant;

import android.content.Context;

import cn.qaii.intelligentgateway.frame.util.PrefConstants;
import cn.qaii.intelligentgateway.frame.util.PrefUtils;
import cn.qaii.intelligentgateway.frame.util.StringUtil;

public class LContext {

	/*********** 全局变量 **************/
	public static Context context;
	public static String CLIENT_TYPE = "2000";///////////////////////////////////////////////
	public static String TOKEN = "";// 登录用户的验证机制
	public static int width;
	public static int height;public static boolean isNetworkConnected = true;//网络是否连接
	public static int networkType = 0;//网络类型



	public static void init(Context context) {
		if (context == null) {
			return;
		}
		LContext.context = context;
	}

	public static void initLogin(Context context,String accessToken,String account){
		if(StringUtil.isNull(accessToken) || StringUtil.isNull(account)){
			return;
		}
		PrefUtils.setPrefString(context, PrefConstants.ACCESS_TOKEN, accessToken);
		PrefUtils.setPrefString(context, PrefConstants.ACCOUNT, account);
	}

	public static void logout(Context context){
		PrefUtils.setPrefString(context, PrefConstants.ACCESS_TOKEN, "");
		PrefUtils.setPrefString(context, PrefConstants.ACCOUNT, "");
	}

	public static boolean isLogin(Context context){
		String accessToken = PrefUtils.getPrefString(context, PrefConstants.ACCESS_TOKEN, "");
		if(StringUtil.isNull(accessToken))
			return false;
		return true;
	}

	public static String getAccessToken(Context context){
		return PrefUtils.getPrefString(context, PrefConstants.ACCESS_TOKEN, "");
	}

	public static String getAccount(Context context){
		return PrefUtils.getPrefString(context, PrefConstants.ACCOUNT, "");
	}
}
