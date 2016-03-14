package cn.qaii.viewutil_lib.view;

import android.content.Context;

/**
 * 
 * LoadHelper
 * 
 * @author larry 2015-11-6 下午4:27:35 
 * @version 1.0.0
 *
 */
public class LoadHelper {

	private static DialogLoader mProgressDialog;

	public static void show(Context context) {
		mProgressDialog = new DialogLoader(context);
		if (!mProgressDialog.isShowing()) {
			mProgressDialog.show();
		}
	}

	/**
	 * 隐藏progressdialog
	 */
	public static void dismiss() {
		if (mProgressDialog != null) {
			// if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
			// }
		}
	}
}
