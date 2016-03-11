package cn.qaii.intelligentgateway.frame.http;

public class HttpResult {
	
	public static final String RETURN_CODE = "Code";
	public static final String RETURN_DES = "Des";
	public static final String RETURN_TYPE = "Type";
	public static final String RETURN_DATA = "Data";
	
	//非业务逻辑错误码应该小于0，成功为0，系统业务逻辑错误码大于0
	public static final int RESULT_CODE_SUCCESS = 0;//请求成功
	public static final int RESULT_CODE_SERVER_EXCEPTION = -1;//服务器异常
	public static final int RESULT_CODE_UNKNOW_EXCEPTION = -2;//解析返回值的异常
	public static final int RESULT_CODE_CONNECTION_EXCEPTION = -999;//网络异常
	
	public static final String RESULT_CODE_SERVER_EXCEPTION_VALUE = "服务器返回值异常";//服务器异常描述
	public static final String RESULT_CODE_UNKNOW_EXCEPTION_VALUE = "解析返回值时发生异常";//解析返回值的异常描述
	public static final String RESULT_CODE_CONNECTION_EXCEPTION_VALUE = "连接不到服务器或发生网络异常，请检查网络设置或重试";//网络异常描述
	
	private boolean isNewInterf;//判断是不是新接口
	
	private boolean isHttpSuccess;//判断请求是否成功

	private int resultCode;
	
	private String resultMessage;
	
	private String resultData;

	public boolean isNewInterf() {
		return isNewInterf;
	}

	public void setNewInterf(boolean isNewInterf) {
		this.isNewInterf = isNewInterf;
	}

	public boolean isHttpSuccess() {
		return isHttpSuccess;
	}

	public void setHttpSuccess(boolean isHttpSuccess) {
		this.isHttpSuccess = isHttpSuccess;
	}
	
	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public String getResultData() {
		return resultData;
	}

	public void setResultData(String resultData) {
		this.resultData = resultData;
	}

	@Override
	public String toString() {
		return "HttpResult [isNewInterf=" + isNewInterf + ", isHttpSuccess="
				+ isHttpSuccess + ", resultCode=" + resultCode
				+ ", resultMessage=" + resultMessage + ", resultData="
				+ resultData + "]";
	}
	
}
