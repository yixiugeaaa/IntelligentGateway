package cn.qaii.intelligentgateway.user.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

import cn.qaii.intelligentgateway.frame.constant.LCommands;
import cn.qaii.intelligentgateway.frame.http.HttpResult;
import cn.qaii.intelligentgateway.frame.http.LHttpRequest;
import cn.qaii.intelligentgateway.frame.http.LHttpRequest.RequestCompleteListener;


public class UserRequest extends LHttpRequest implements RequestCompleteListener {
	public static final int LOGIN_SUCCESS = 100;// 登录成功
	public static final int GET_USER_INFO_SUCCESS = 101;// 获取用户信息成功
	public static final int REGISTER_SUCESS=102;//注册成功
	public static final int ADD_ADDRESS_SUCCESS = 102;// 添加地址成功
	public static final int GET_ADDRESS_LIST_SUCCESS = 103;// 获取地址列表成功
	public static final int GET_DEFAULT_ADDRESS_LIST_SUCCESS = 104;// 获取默认地址成功
	public static final int ADD_FAVORITE_SUCCESS = 105;// 收藏成功
	public static final int GET_FAVORITE_LIST_SUCCESS = 106;// 获取收藏列表成功

	private Handler mHandler;
	private Message mMessage;
	private Context mContext;

	private Map<String, Object> param;
	private String command;

	public UserRequest(Context context, Handler handler) {
		this.mContext = context;
		this.mHandler = handler;
		this.mMessage = mHandler.obtainMessage();
		this.setRequestCompleteListener(this);
	}
	
	/**
	 * 登录
	 * login(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * void
	 * @exception
	 * @since  1.0.0
	 */
	public void login(String phone, String password) {
		param = new HashMap<String, Object>();
		param.put("phone", phone);
		param.put("password",password);
		command = LCommands.LOGIN;
		requestByGet(param);
	}
	
	/**
	 * 获取用户信息
	 * getUserInfo(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param accessToken
	 * void
	 * @exception
	 * @since  1.0.0
	 */
	public void getUserInfo(String accessToken) {
		param = new HashMap<String, Object>();
		param.put("accessToken", accessToken);
		command = LCommands.GET_USER_INFO;
		requestByGet(param);
	}

	public void register(String tel,String verification,String pwd){
		param = new HashMap<String, Object>();
		param.put("phone", tel);
		param.put("verification",verification);
		param.put("password",pwd);
		command = LCommands.REGISTER;
		requestByGet(param);
	}

	/*public void addAddress(int areaCode, String areaName, String address,
			String zipCode,String personName,String phone,int status) {
		param = new HashMap<String, Object>();
		param.put("areaCode", areaCode);
		param.put("areaName", areaName);
		param.put("address", address);
		param.put("zipCode", zipCode);
		param.put("personName", personName);
		param.put("phone", phone);
		param.put("status", status);
		command = LCommands.ADD_ADDRESS;
		requestByPost(param);
	}

	public void getAddressList() {
		param = new HashMap<String, Object>();
		command = LCommands.GET_ADDRESS_LIST;
		requestByGet(param);
	}
	

	public void getDefaultAddress() {
		param = new HashMap<String, Object>();
		command = LCommands.GET_DEFAULT_ADDRESS_LIST;
		requestByGet(param);
	}
	

	public void addFavorite(int objectId, int type, String title,
			String introduce,String displayUrl) {
		param = new HashMap<String, Object>();
		param.put("objectId", objectId);
		param.put("type", type);
		param.put("title", title);
		param.put("introduce", introduce);
		param.put("displayUrl", displayUrl);
		command = LCommands.ADD_FAVORITE;
		requestByPost(param);
	}
	
	*/
	/*public void getFavoriteList(int pageNo, int pageSize,int type) {
		param = new HashMap<String, Object>();
		param.put("pageNo", pageNo);
		param.put("pageSize", pageSize);
		param.put("type", type);
		command = LCommands.GET_FAVORITE_LIST;
		requestByGet(param);
	}*/
	
	@Override
	protected void initParams() {
		super.initParams();
		mParam = param;
		mCommand = command;
		mBaseContext = mContext;
	}

	@Override
	public void requestSuccessed(HttpResult result) {
		if (command.equals(LCommands.LOGIN)) {
			mMessage.what = LOGIN_SUCCESS;
			mMessage.obj = result.getResult();
			mHandler.sendMessage(mMessage);
		}else if (command.equals(LCommands.GET_USER_INFO)) {
			mMessage.what = GET_USER_INFO_SUCCESS;
			mMessage.obj = UserParser.parseUserInfo(result.getResult());
			mHandler.sendMessage(mMessage);
		}else if (command.equals(LCommands.REGISTER)){
			mMessage.what = REGISTER_SUCESS;
			mMessage.obj = result.getDescription();
			mHandler.sendMessage(mMessage);
		}/*else if (command.equals(LCommands.ADD_ADDRESS)) {
			mMessage.what = ADD_ADDRESS_SUCCESS;
			mMessage.obj = result.getResult();
			mHandler.sendMessage(mMessage);
		}else if (command.equals(LCommands.GET_ADDRESS_LIST)) {
			mMessage.what = GET_ADDRESS_LIST_SUCCESS;
			mMessage.obj = UserParser.parserAddressList(result.getResult());
			mHandler.sendMessage(mMessage);
		}else if (command.equals(LCommands.GET_DEFAULT_ADDRESS_LIST)) {
			mMessage.what = GET_DEFAULT_ADDRESS_LIST_SUCCESS;
			mMessage.obj = UserParser.parseAddressInfo(result.getResult());
			mHandler.sendMessage(mMessage);
		}else if (command.equals(LCommands.ADD_FAVORITE)) {
			mMessage.what = ADD_FAVORITE_SUCCESS;
			mMessage.obj = result.getResult();
			mHandler.sendMessage(mMessage);
		}else if (command.equals(LCommands.GET_FAVORITE_LIST)) {
			mMessage.what = GET_FAVORITE_LIST_SUCCESS;
			mMessage.obj = UserParser.parserFavoriteList(result.getResult());
			mHandler.sendMessage(mMessage);
		}*/
	}

	@Override
	public void requestFailed(HttpResult result) {
		if(mHandler == null)
			return;
		// 请求失败,result的返回码可判断失败原因，进行如下操作
		mMessage.what = REQUEST_FAILED;
		mMessage.obj = result.getDescription();
		mHandler.sendMessage(mMessage);
	}
}
