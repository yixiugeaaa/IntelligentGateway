package cn.qaii.intelligentgateway.frame.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.qaii.intelligentgateway.R;


/**
 * 
 * ToastHelper
 * 
 * @author larry 2015-4-10 下午6:17:47
 * @version 1.0.0
 * 
 */
public class ToastHelper {
	private static String oldMsg;
	protected static Toast toast = null;
	private static long oneTime = 0;
	private static long twoTime = 0;

	public static void toastShort(Context context, String msg) {
		try {
			if (toast == null) {
				toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
				LinearLayout toastView = (LinearLayout) toast.getView();
				ImageView ivAlert = new ImageView(context);
				ivAlert.setImageResource(R.drawable.iv_alert);
				toastView.addView(ivAlert, 0);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				oneTime = System.currentTimeMillis();
			} else {
				twoTime = System.currentTimeMillis();
				if (msg.equals(oldMsg)) {
					if (twoTime - oneTime > Toast.LENGTH_SHORT) {
						toast.show();
					}
				} else {
					oldMsg = msg;
					toast.setText(msg);
					toast.show();
				}
			}
			oneTime = twoTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void toastLong(Context context, String msg) {
		try {
			if (toast == null) {
				toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
				LinearLayout toastView = (LinearLayout) toast.getView();
				ImageView ivAlert = new ImageView(context);
				ivAlert.setImageResource(R.drawable.iv_alert);
				toastView.addView(ivAlert, 0);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				oneTime = System.currentTimeMillis();
			} else {
				twoTime = System.currentTimeMillis();
				if (msg.equals(oldMsg)) {
					if (twoTime - oneTime > Toast.LENGTH_SHORT) {
						toast.show();
					}
				} else {
					oldMsg = msg;
					toast.setText(msg);
					toast.show();
				}
			}
			oneTime = twoTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
