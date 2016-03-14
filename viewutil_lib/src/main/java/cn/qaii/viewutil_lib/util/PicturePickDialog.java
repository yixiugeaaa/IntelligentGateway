package cn.qaii.viewutil_lib.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.qaii.viewutil_lib.R;


public class PicturePickDialog extends Dialog {

	private Activity mContext;

	public PicturePickDialog(Activity context) {
		super(context);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_pick_picture);

		TextView take_from_camera = (TextView) findViewById(R.id.user_auth_fail_description);
		TextView take_from_album = (TextView) findViewById(R.id.pic_album_take);
		TextView cancel = (TextView) findViewById(R.id.pic_cancel);
		getWindow().setWindowAnimations(R.style.picture_dialog_style);
		getWindow().setGravity(Gravity.BOTTOM);// 靠底部显示
		getWindow().setLayout(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);// 设置dialog的宽度和高度 我设的宽度是填充父窗口，高度内容包裹
		getWindow().getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		        getWindow().setAttributes(lp);
		take_from_camera.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				PictureUtil.doTakePhoto(mContext);
				PicturePickDialog.this.dismiss();
			}
		});

		take_from_album.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				PictureUtil.doPickPhotoFromGallery(mContext);

				PicturePickDialog.this.dismiss();
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				PicturePickDialog.this.dismiss();
			}
		});

	}

}
