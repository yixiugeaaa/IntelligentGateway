package cn.qaii.intelligentgateway.frame.util;

import android.content.Context;

import cn.qaii.intelligentgateway.frame.constant.LContext;
import cn.qaii.intelligentgateway.frame.database.LDaoManager;
import cn.qaii.intelligentgateway.frame.http.HttpExecutor;
import cn.qaii.intelligentgateway.frame.json.JSONConvertor;


/**
 * 应用开启时先检查并初始化基础数据
 * @author larry
 *
 */
public class LLoader {

	public static boolean isInit = false;

	public static void initApp(Context context) {
		if (isInit) {
			return;
		}
		isInit = true;
		LContext.init(context);
		LDaoManager.init(context);
		HttpExecutor.init();
		JSONConvertor.init(context);
		LLogger.e("数据初始化完毕");
	}
}
