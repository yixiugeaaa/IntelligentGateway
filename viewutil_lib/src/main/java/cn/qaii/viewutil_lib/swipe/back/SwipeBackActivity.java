package cn.qaii.viewutil_lib.swipe.back;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import cn.qaii.viewutil_lib.R;

/**
 * 想要实现向右滑动删除Activity效果只需要继承SwipeBackActivity即可，如果当前页面含有ViewPager
 * 只需要调用SwipeBackLayout的setViewPager()方法即可
 * 
 */
public class SwipeBackActivity extends SherlockFragmentActivity {
	protected SwipeBackLayout layout;

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
				R.layout.layout_swipe_back, null);
		layout.attachToActivity(this);
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.base_slide_right_in,
				R.anim.base_slide_remain);
	}

	// Press the back button in mobile phone
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(0, R.anim.base_slide_right_out);
	}

}
