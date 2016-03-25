package cn.qaii.intelligentgateway.frame.http;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.qaii.intelligentgateway.frame.constant.LCommands;
import cn.qaii.intelligentgateway.frame.util.LLogger;
import cn.qaii.intelligentgateway.frame.util.StringUtil;


/**
 * 
 * HttpRequest
 * 
 * @author larry 2015-11-3 下午2:36:38 
 * @version 1.0.0
 *
 */
public abstract class HttpRequest {
	
	public Map<String, Object> mParam;
	public String mCommand;
	public Context mBaseContext;
	
	/**
	 * 
	 * requestByPost(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param map
	 * void
	 * @exception
	 * @since  1.0.0
	 */
	protected void requestByPost(final Map<String, Object> map) {
		initParams();
		HttpExecutor.requestByPost(mCommand, map, new RequestListener() {

			@Override
			public void requestCompleted(String data) {
				HttpResult result;
				if (StringUtil.isNull(data)) {
					result = new HttpResult();
					result.setCode(HttpResult.RESULT_CODE_CONNECTION_EXCEPTION);
					result.setDescription(HttpResult.RESULT_VALUE_CONNECTION_EXCEPTION);
					result.setRequestSuccessed(false);
					onRequestFail(result);
				} else {
					LLogger.e("请求返回值长度：" + data.length() + "\n" + data);
					parseResult(data);
				}
			}
		});
	}
	
	/**
	 * 
	 * requestByGet(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param map
	 * void
	 * @exception
	 * @since  1.0.0
	 */
	protected void requestByGet(final Map<String, Object> map) {
		initParams();
		HttpExecutor.requestByGet(mCommand, map, new RequestListener() {

			@Override
			public void requestCompleted(String data) {
				HttpResult result;
				if (StringUtil.isNull(data)) {
					result = new HttpResult();
					result.setCode(HttpResult.RESULT_CODE_CONNECTION_EXCEPTION);
					result.setDescription(HttpResult.RESULT_VALUE_CONNECTION_EXCEPTION);
					result.setRequestSuccessed(false);
					onRequestFail(result);
				} else {
					LLogger.e("请求返回值长度：" + data.length() + "\n" + data);
					parseResult(data);
				}
			}
		});
	}

	/**
	 * 解析只返回操作结果的命令
	 * @param data
	 */
	public void result(String data){
		HttpResult result = new HttpResult();
		try {
			JSONObject object = new JSONObject(data);
			result.setResult(data);
			if(object.getInt(HttpResult.RESULT)==HttpResult.RESULT_CODE_SUCCESS){
				result.setRequestSuccessed(true);
				onRequestSuccess(result);
			}else {
				result.setRequestSuccessed(false);
				onRequestFail(result);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 统一解析返回值
	 * parseResult(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param data
	 * void
	 * @exception
	 * @since  1.0.0
	 */
	public void parseResult(String data) {
		HttpResult result = new HttpResult();
		try {
			JSONObject object = new JSONObject(data);
			if (object.has(HttpResult.RESPONSE)) {
				JSONObject subObject = new JSONObject(object.getString(HttpResult.RESPONSE));
				result.setDescription(subObject.getString(HttpResult.DESCRIPTION));
				result.setNumReturn(subObject.getInt(HttpResult.NUMRETURN));
				result.setResult(subObject.getString(HttpResult.RESULT));
				result.setCode(subObject.getInt(HttpResult.CODE));
				if (result.getCode() == HttpResult.RESULT_CODE_SUCCESS) {
					result.setRequestSuccessed(true);
					onRequestSuccess(result);
				} else {
					result.setRequestSuccessed(false);
					onRequestFail(result);
				}
			} else {
				result.setCode(HttpResult.RESULT_CODE_SERVER_EXCEPTION);
				result.setDescription(HttpResult.RESULT_VALUE_SERVER_EXCEPTION);
				result.setRequestSuccessed(false);
				onRequestFail(result);
			}
		} catch (Exception e) {
			if(StringUtil.isEqual(mCommand, LCommands.GET_DATA)){
				result.setCode(HttpResult.RESULT_CODE_SUCCESS);
				result.setDescription(HttpResult.RESULT_VALUE_SERVER_SUCCESS);
				result.setResult(data);
				result.setRequestSuccessed(true);
				onRequestSuccess(result);
			}else{
				e.printStackTrace();
				result.setCode(HttpResult.RESULT_CODE_UNKNOW_EXCEPTION);
				result.setDescription(HttpResult.RESULT_VALUE_UNKNOW_EXCEPTION);
				result.setRequestSuccessed(false);
				onRequestFail(result);
			}
		}
	}
	
	/**
	 * 请求失败，网络连接不上，或服务器端发生异常，抑或是发生未知异常
	 * @param result
	 */
	protected abstract void onRequestFail(HttpResult result);
	
	protected abstract void initParams();
	
	/**
	 * 网络请求成功时的回调
	 * 注意，这里是网络请求成功，不代表请求的业务逻辑成功，
	 * 请求的业务逻辑处理结果，需要对result.getResultCode()的值来判断
	 * @param result
	 */
	protected abstract void onRequestSuccess(HttpResult result);
	
	public interface RequestListener {
		public void requestCompleted(String data);
	}
}
