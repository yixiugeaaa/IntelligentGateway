package cn.qaii.intelligentgateway.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;

/**
 * 
 * BaseFragment fragment 基类
 * 
 * @author larry 2015-4-13 上午9:40:16
 * @version 1.0.0
 *
 */
public class BaseFragment extends SherlockFragment {

	protected Activity mContext;

	@Override
	public void onAttach(Activity activity) {
		mContext = activity;
		super.onAttach(activity);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	/**
	 * 释放imageview资源 releaseImageView(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param imageView
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void releaseImageView(ImageView imageView) {
		if (imageView == null)
			return;
		Drawable drawable = imageView.getDrawable();
		if (drawable != null && drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			Bitmap bitmap = bitmapDrawable.getBitmap();
			if (bitmap != null && !bitmap.isRecycled()) {
				bitmap.recycle();
			}
		}
	}

}
