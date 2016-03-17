package cn.qaii.intelligentgateway.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseActivity;
import cn.qaii.intelligentgateway.frame.constant.LContext;
import cn.qaii.intelligentgateway.frame.http.LHttpRequest;
import cn.qaii.intelligentgateway.frame.util.StringUtil;
import cn.qaii.intelligentgateway.frame.util.ToastHelper;
import cn.qaii.intelligentgateway.user.http.UserRequest;
import cn.qaii.viewutil_lib.view.LoadHelper;


/**
 * RegisterActivity
 * (功能说明)
 * Created by xiuge on 2016/3/17 11:27.
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{
	
	private EditText etAccount;
	private EditText etKeyCode;
	private EditText etPassword;
	
	private Button btnRegister;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case UserRequest.LOGIN_SUCCESS:
				LoadHelper.dismiss();
				ToastHelper.toastShort(mContext, "登录成功");
				String accessToken = msg.obj.toString();
				LContext.initLogin(mContext, accessToken, etAccount.getText().toString());
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						setResult(501);
						finish();
					}
				}, 1000);
				break;
			case LHttpRequest.REQUEST_FAILED:
				LoadHelper.dismiss();
				ToastHelper.toastShort(mContext, msg.obj.toString());
				break;

			default:
				break;
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}
	
	private void initView(){
		etAccount = (EditText)findViewById(R.id.et_account);
		etKeyCode = (EditText)findViewById(R.id.et_key_code);
		etPassword = (EditText)findViewById(R.id.et_pwd);
		btnRegister = (Button)findViewById(R.id.btn_register);
		btnRegister.setOnClickListener(this);
	}
	
	private boolean check() {
		if (StringUtil.isNull(etAccount.getText().toString())) {
			ToastHelper.toastShort(mContext, "请输入手机号码");
			return false;
		}
		if (!StringUtil.isMobileNO(etAccount.getText().toString())) {
			ToastHelper.toastShort(mContext, "请输入正确格式的手机号码");
			return false;
		}
		if (StringUtil.isNull(etKeyCode.getText().toString())) {
			ToastHelper.toastShort(mContext, "请输入接收到的验证码");
			return false;
		}
		if (StringUtil.isNull(etPassword.getText().toString())) {
			ToastHelper.toastShort(mContext, "请输入密码");
			return false;
		}
		if (StringUtil.isPassword(etPassword.getText().toString())) {
			ToastHelper.toastShort(mContext, "请输入大于六位数的密码");
			return false;
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_register:
				if (check()) {
					LoadHelper.show(mContext);
					new UserRequest(mContext, mHandler).login(etAccount.getText().toString(), "");
				}
				break;
		}


	}
}
