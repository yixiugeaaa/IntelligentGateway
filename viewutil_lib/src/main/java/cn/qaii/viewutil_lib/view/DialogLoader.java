package cn.qaii.viewutil_lib.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import cn.qaii.viewutil_lib.R;


/**
 * 
 * DialodUpdateApp
 * 
 * @author larry 2015-11-6 下午4:24:43 
 * @version 1.0.0
 *
 */
public class DialogLoader extends Dialog {

	public DialogLoader(Context context) {
		super(context, R.style.dialog_style);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_refresh);
		setCanceledOnTouchOutside(false);
		setCancelable(true);
	}

}
