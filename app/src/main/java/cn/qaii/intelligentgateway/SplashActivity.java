package cn.qaii.intelligentgateway;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.actionbarsherlock.view.Window;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.qaii.intelligentgateway.base.BaseSplashActivity;

/**
 * 启动页 SplashActivity
 * 
 * @author larry 2015-4-20 下午6:40:04
 * @version 1.0.0
 *
 */
public class SplashActivity extends BaseSplashActivity {

	private LinearLayout adLayout;
	// 异步加载图片
	private ImageLoader mImageLoader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
			finish();
			return;
		}
		mImageLoader = ImageLoader.getInstance();
		setContentView(R.layout.activity_splash);
		adLayout = (LinearLayout) findViewById(R.id.adLayout);
		// 将广告条加入到布局中
		adLayout.setVisibility(View.GONE);
		ImageView imageView = new ImageView(mContext);
		LinearLayout.LayoutParams params =  new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		imageView.setLayoutParams(params);
		imageView.setScaleType(ScaleType.FIT_XY);
		// 异步加载图片
		String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png";
		/*mImageLoader.displayImage(url, imageView, new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				start();
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				adLayout.setVisibility(View.VISIBLE);
				adLayout.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.pic_enter_anim_alpha));
				start();
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
			}
		});*/
		imageView.setScaleType(ScaleType.FIT_CENTER);
		adLayout.addView(imageView);
		start();
	}

	/**
	 * 打开应用 start(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public void start() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				//if(LContext.isLogin(mContext)){
					startActivity(new Intent(mContext, MainActivity.class));
				//}else{
				//	startActivity(new Intent(mContext, LoginActivity.class));
				//}
				finish();
			}
		}, 1000);
	}

	@Override
	public void onBackPressed() {
	}
	
}
