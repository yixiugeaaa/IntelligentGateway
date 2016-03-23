package cn.qaii.intelligentgateway.gateway.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.Map;

import cn.qaii.intelligentgateway.frame.constant.LCommands;
import cn.qaii.intelligentgateway.frame.http.HttpResult;
import cn.qaii.intelligentgateway.frame.http.LHttpRequest;
import cn.qaii.intelligentgateway.frame.http.LHttpRequest.RequestCompleteListener;


public class GatewayRequest extends LHttpRequest implements RequestCompleteListener {
	public static final int  BIND_DEVICE_SUCCESS= 200;// 登录成功
	public static final int GET_DEVICE_SUCCESS = 201;// 获取用户信息成功
	public static final int ADD_ADDRESS_SUCCESS = 102;// 添加地址成功
	public static final int GET_ADDRESS_LIST_SUCCESS = 103;// 获取地址列表成功
	public static final int GET_DEFAULT_ADDRESS_LIST_SUCCESS = 104;// 获取默认地址成功

	private Handler mHandler;
	private Message mMessage;
	private Context mContext;

	private Map<String, Object> param;
	private String command;

	public GatewayRequest(Context context, Handler handler) {
		this.mContext = context;
		this.mHandler = handler;
		this.mMessage = mHandler.obtainMessage();
		this.setRequestCompleteListener(this);
	}

	/**
	 * 绑定网关
	 * @param deviceId
	 */
	public void bindDevice(String deviceId){
		param = new HashMap<String, Object>();
		param.put("deviceId", deviceId);
		command = LCommands.BIND;
		requestByGet(param);
	}



	/**
	 * 获取设备信息
	 * @param
	 * @return 
	 */
	public void getGatewayInfo(){
		param = new HashMap<String, Object>();
		command = LCommands.BIND;
		requestByGet(param);
	}


	@Override
	protected void initParams() {
		super.initParams();
		mParam = param;
		mCommand = command;
		mBaseContext = mContext;
	}

	
	@Override
	public void requestSuccessed(HttpResult result) {
		if (command.equals(LCommands.BIND)) {
			mMessage.what = BIND_DEVICE_SUCCESS;
			mMessage.obj = result.getResult();
			mHandler.sendMessage(mMessage);
		}else if (command.equals(LCommands.GET_DEVICE)) {
			mMessage.what = GET_DEVICE_SUCCESS;
			mMessage.obj = GatewayParser.parseGatewayInfo(result.getResult());
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
