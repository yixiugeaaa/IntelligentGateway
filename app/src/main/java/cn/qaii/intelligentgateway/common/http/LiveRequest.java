package cn.qaii.intelligentgateway.common.http;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qaii.intelligentgateway.common.http.parser.LiveParser;
import cn.qaii.intelligentgateway.frame.constant.LCommands;
import cn.qaii.intelligentgateway.frame.http.HttpResult;
import cn.qaii.intelligentgateway.frame.http.LHttpRequest;
import cn.qaii.intelligentgateway.model.LiveInfo;
import cn.qaii.intelligentgateway.frame.http.LHttpRequest.RequestCompleteListener;


public class LiveRequest extends LHttpRequest implements RequestCompleteListener{
	
	public static final int GET_LIVE_LIST_SUCCESSED = 1;//获取所有消息列表
	
	private Handler mHandler;
	private Message mMessage;
	
	private Map<String, Object> param;
	private String command;
	
	public LiveRequest(Context context, Handler handler){
		this.mHandler = handler;
		this.mMessage = mHandler.obtainMessage();
		this.setRequestCompleteListener(this);
	}
	
	/**
	 * 获取直播信息列表
	 */
	public void getLiveInfoList(int pageSize, int pageIndex,int analystId,String keyQuery){
		param = new HashMap<String, Object>();
		param.put("PageSize", pageSize);
		param.put("PageIndex", pageIndex);
		param.put("Category", "1");
		param.put("AnalystsId", analystId);
		param.put("KeyQuery", keyQuery);
		command = LCommands.GET_LIVE_INFO_ALL;
		execute();
	}
	
	@Override
	protected void initParams() {
		super.initParams();
		mParam = param;
		mCommand = command;
	}

	@Override
	public void requestSuccessed(String data) {
		if (command.equals(LCommands.GET_LIVE_INFO_ALL)) {
			mMessage.what = GET_LIVE_LIST_SUCCESSED;
			List<LiveInfo> infos = LiveParser.parseLiveInfoList(data);
			mMessage.obj = infos;
			mHandler.sendMessage(mMessage);
		}
	}

	@Override
	public void requestFailed(HttpResult result) {
		//请求失败,result的返回码可判断失败原因，进行如下操作
//		mMessage.what = CommonRequest.REQUEST_FAILED;
//		mHandler.sendMessage(mMessage);
	}

}
