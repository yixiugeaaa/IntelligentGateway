package cn.qaii.intelligentgateway.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.view.Window;

import cn.qaii.intelligentgateway.MainActivity;
import cn.qaii.intelligentgateway.R;
import cn.qaii.intelligentgateway.base.BaseActivity;
import cn.qaii.intelligentgateway.frame.constant.LContext;
import cn.qaii.intelligentgateway.frame.http.LHttpRequest;
import cn.qaii.intelligentgateway.frame.util.StringUtil;
import cn.qaii.intelligentgateway.frame.util.ToastHelper;
import cn.qaii.intelligentgateway.user.http.UserRequest;
import cn.qaii.viewutil_lib.view.LoadHelper;


/**
 * LoginActivity
 * (功能说明)
 * Created by xiuge on 2016/3/17 11:26.
 */

public class LoginActivity extends BaseActivity implements OnClickListener{
	
	private EditText etAccount;
	private EditText etPassword;
	private Button btnLogin;
	
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
						Intent intent =new Intent(mContext, MainActivity.class);
						startActivity(intent);
						finish();
					}
				}, 500);
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}
	
	private void initView(){
		etAccount = (EditText)findViewById(R.id.et_account);
		etPassword = (EditText)findViewById(R.id.et_key_code);
		btnLogin = (Button)findViewById(R.id.btn_login);

		findViewById(R.id.layout_right).setVisibility(View.VISIBLE);
		findViewById(R.id.layout_back).setVisibility(View.INVISIBLE);
		((TextView)findViewById(R.id.tv_title)).setText(getTitle());
		((TextView)findViewById(R.id.btn_right)).setText("注册");
		btnLogin.setOnClickListener(this);
		findViewById(R.id.layout_right).setOnClickListener(this);
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
		if (StringUtil.isNull(etPassword.getText().toString())) {
			ToastHelper.toastShort(mContext, "请输入密码");
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
		Intent intent=null;
		switch (v.getId()){
			case R.id.btn_login:
				if(check()){
					LoadHelper.show(mContext);
					new UserRequest(mContext, mHandler).login(etAccount.getText().toString(), etPassword.getText().toString());
				}
				break;
			case R.id.layout_right:
				intent = new Intent(mContext, RegisterActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
	}
}
