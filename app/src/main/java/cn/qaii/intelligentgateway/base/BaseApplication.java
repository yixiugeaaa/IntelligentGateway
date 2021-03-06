package cn.qaii.intelligentgateway.base;

import android.app.Application;

import cn.qaii.intelligentgateway.frame.util.LLoader;
import cn.qaii.intelligentgateway.frame.util.LLogger;
import cn.qaii.viewutil_lib.photoview.LImageLoader;


public class BaseApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		LLogger.isDebug = true;//false 禁止打印log
		LLoader.initApp(getApplicationContext());//初始化应用信息
		LImageLoader.initConfig(getApplicationContext());
	}
}
