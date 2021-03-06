package cn.qaii.intelligentgateway.frame.http;


public class LHttpRequest extends HttpRequest {

	private RequestCompleteListener requestCompleteListener;
	
	public static final int REQUEST_FAILED = -101;//请求失败

	@Override
	protected void initParams() {}

	@Override
	protected void onRequestFail(HttpResult result) {
//		switch (result.getStatus()) {
//		case HttpResult.RESULT_CODE_CONNECTION_EXCEPTION:
//			// 网络异常
//			break;
//		case HttpResult.RESULT_CODE_SERVER_EXCEPTION:
//			// 服务器异常
//			break;
//		case HttpResult.RESULT_CODE_UNKNOW_EXCEPTION:
//			// 解析返回值异常
//			break;
//		default:
//			break;
//		}
		requestCompleteListener.requestFailed(result);
	}

	@Override
	protected void onRequestSuccess(HttpResult result) {
		requestCompleteListener.requestSuccessed(result);
	}

	public interface RequestCompleteListener {
		public void requestSuccessed(HttpResult result);

		public void requestFailed(HttpResult result);
	}

	public RequestCompleteListener getRequestCompleteListener() {
		return requestCompleteListener;
	}

	public void setRequestCompleteListener(
			RequestCompleteListener requestCompleteListener) {
		this.requestCompleteListener = requestCompleteListener;
	}

}
