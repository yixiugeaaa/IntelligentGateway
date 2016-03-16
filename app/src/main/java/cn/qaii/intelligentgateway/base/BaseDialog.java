package cn.qaii.intelligentgateway.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * BaseDialog
 * 
 * @author larry 2015-12-22 上午11:07:19 
 * @version 1.0.0
 *
 */
public class BaseDialog extends DialogFragment {

	@Override
	public void dismiss() {
		super.dismiss();
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

}
