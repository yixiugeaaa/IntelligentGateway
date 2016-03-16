package cn.qaii.intelligentgateway.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import cn.qaii.intelligentgateway.frame.constant.LContext;

/**
 * 基类 BaseActivity
 * 
 * @author larry 2015-4-11 下午2:32:56
 * @version 1.0.0
 *
 */
public class BaseSplashActivity extends SherlockFragmentActivity{

	protected Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}


	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		LContext.width = dm.widthPixels; // 当前屏幕像素
		LContext.height = dm.heightPixels; // 当前屏幕像素
		//MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//MobclickAgent.onPause(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
