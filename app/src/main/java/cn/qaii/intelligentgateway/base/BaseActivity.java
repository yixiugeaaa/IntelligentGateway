package cn.qaii.intelligentgateway.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;

import java.lang.reflect.Field;

import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.frame.constant.LContext;
import cn.qaii.intelligentgateway.frame.util.ActivityHolder;
import cn.qaii.intelligentgateway.frame.util.LLogger;
import cn.qaii.intelligentgateway.frame.util.StringUtil;
import cn.qaii.viewutil_lib.util.SystemBarTintManager;

/**
 * 基类 BaseActivity
 * 
 * @author larry 2015-4-11 下午2:32:56
 * @version 1.0.0
 *
 */
public class BaseActivity extends SherlockFragmentActivity{

	protected Context mContext;
	protected ActionBar mActionBar;
	protected FragmentManager mFManager;
	protected FragmentTransaction mFTransaction;
	
	protected LinearLayout mLayoutBack;
	protected ImageView mBackView;
	protected TextView tvTitle;
	protected LinearLayout mLayoutRight;
	protected TextView btnRight;
	
	protected SystemBarTintManager mBarTintManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		mFManager = getSupportFragmentManager();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
			setTranslucentStatus(true);
			mBarTintManager = new SystemBarTintManager(this);
			mBarTintManager.setStatusBarTintEnabled(true);
			mBarTintManager.setStatusBarTintResource(R.color.actionbar_color);// 通知栏所需颜色
		}
		initActionBar();
		setOverflowButtonAlways();
		ActivityHolder.getInstance().addActivity(this);
	}
	
	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = this.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	private void initActionBar() {
		if (getSupportActionBar() != null) {
			mActionBar = getSupportActionBar();
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayShowTitleEnabled(true);// 隐藏title

			mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			mActionBar.setCustomView(R.layout.view_actionbar_base); 
			mLayoutBack = (LinearLayout)mActionBar.getCustomView().findViewById(R.id.layout_back);
			mLayoutBack.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			mLayoutBack.setVisibility(View.VISIBLE);
			
			mLayoutRight = (LinearLayout)mActionBar.getCustomView().findViewById(R.id.layout_right);
			mLayoutRight.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

				}
			});
			
			mBackView = (ImageView)mActionBar.getCustomView().findViewById(R.id.btn_back);
			btnRight = (TextView)mActionBar.getCustomView().findViewById(R.id.btn_right);
			tvTitle = (TextView)mActionBar.getCustomView().findViewById(R.id.tv_title);
			String title = getIntent().getStringExtra("title");
			if(StringUtil.isNull(title)){
				tvTitle.setText(getTitle());
			}else{
				tvTitle.setText(title);
			}
			// 设置ActionBar的导航模式为List
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			// 隐藏Home logo
			mActionBar.setDisplayShowHomeEnabled(true);
			View homeIcon = findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home
					: R.id.abs__home);
			((View) homeIcon.getParent()).setVisibility(View.GONE);
			homeIcon.setVisibility(View.GONE);
		}
	}
	
	private void setOverflowButtonAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKey = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKey.setAccessible(true);
			menuKey.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 退出应用 exitAPP(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	protected void exitAPP() {
		LLogger.e("mHolder : " + ActivityHolder.getInstance().size());
		ActivityHolder.getInstance().finishAllActivity();
		//MobclickAgent.onKillProcess(this);//umeng打包工具
		System.exit(0);
	}
	
	/**
	 * 设置home点击事件，默认为返回
	 * setHomeClickListener(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param clickListener
	 * void
	 * @exception
	 * @since  1.0.0
	 */
	protected void setHomeClickListener(OnClickListener clickListener){
		mLayoutBack.setOnClickListener(clickListener);
	}

	/**
	 * 退到后台 exitHome(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	protected void exitHome() {
		Intent intent = new Intent(Intent.ACTION_MAIN);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
		intent.addCategory(Intent.CATEGORY_HOME);
		mContext.startActivity(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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
		//MobclickAgent.onResume(this);//umeng友盟打包工具
	}

	@Override
	protected void onPause() {
		super.onPause();
		//MobclickAgent.onPause(this);//umeng友盟打包工具
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityHolder.getInstance().finishActivity(this);
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
