package cn.qaii.intelligentgateway.frame.http;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.qaii.intelligentgateway.frame.util.LLogger;
import cn.qaii.intelligentgateway.frame.util.StringUtil;


/**
 * 网络请求基层类
 * @author larry
 *
 */
public abstract class HttpRequest extends AsyncTask<Object, Object, HttpResult> {
	
	public Map<String, Object> mParam;
	public String mCommand;
	
	@Override
	protected HttpResult doInBackground(Object... params) {
		initParams();
		if(isCancelled()) {
			return null;
		}
		HttpResult result = request(mParam);
		if(result.isHttpSuccess() && result.getResultCode() >= 0) {
			onBackgroundSuccess(result);
		}
		return result;
	}
	
	private HttpResult request(final Map<String, Object> param) {
		String resultStr = HttpExecutor.request(mCommand, param);
		
		if(!StringUtil.isNull(resultStr)) {
			LLogger.e("请求返回值长度：" + resultStr.length() + "\n" + resultStr);
		}
		
		HttpResult result = parseResult(resultStr);
		return result;
	}

	@Override
	protected void onPostExecute(HttpResult result) {
		if(result == null) {
			//中途取消
		} else if(result.isHttpSuccess() && result.getResultCode() >= 0) {
			onRequestSuccess(result);
		} else {
			onRequestFail(result);
		}
	}

	public HttpResult parseResult(String resultStr) {
		
		HttpResult result = new HttpResult();
		
		if(StringUtil.isNull(resultStr)) {
			result.setResultCode(HttpResult.RESULT_CODE_CONNECTION_EXCEPTION);
			result.setResultMessage(HttpResult.RESULT_CODE_CONNECTION_EXCEPTION_VALUE);
			result.setHttpSuccess(false);
			return result;
		}
		result = parser(resultStr);
		
		return result;
	}
	
	private HttpResult parser(String str){
		HttpResult result = new HttpResult();
		try {
			if(str.substring(0, 1).equals("[")){//老接口
				JSONArray array = new JSONArray(str);
				result.setResultData(array.toString());
				result.setHttpSuccess(true);
				return result;
			}
			JSONObject object = new JSONObject(str);
			if(object.has(HttpResult.RETURN_CODE)){
				result.setNewInterf(true);//新接口
				int returnCode = object.getInt(HttpResult.RETURN_CODE);
				if(returnCode == 0){
					result.setResultCode(returnCode);
					result.setResultMessage(object.getString(HttpResult.RETURN_DES));
					result.setResultData(object.getString(HttpResult.RETURN_DATA));
					result.setHttpSuccess(true);
				}else{
					result.setResultCode(returnCode);
					result.setResultMessage(HttpResult.RESULT_CODE_SERVER_EXCEPTION_VALUE);
					result.setHttpSuccess(false);
				}
			}else{
				result.setNewInterf(false);//老接口
				//老接口暂不处理
			}
		} catch (JSONException e) {
//			LLogger.e("解析返回值时发生异常" + e);
			result.setResultCode(HttpResult.RESULT_CODE_UNKNOW_EXCEPTION);
			result.setResultMessage(HttpResult.RESULT_CODE_UNKNOW_EXCEPTION_VALUE);
			result.setHttpSuccess(false);
		}
		
		return result;
	}
	
	protected void onBackgroundSuccess(HttpResult result) {}
	
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
}
