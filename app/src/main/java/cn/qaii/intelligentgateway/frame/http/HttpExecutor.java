package cn.qaii.intelligentgateway.frame.http;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.Locale;
import java.util.Map;

import cn.qaii.intelligentgateway.frame.constant.LContext;
import cn.qaii.intelligentgateway.frame.http.HttpRequest.RequestListener;
import cn.qaii.intelligentgateway.frame.util.LLogger;
import cn.qaii.intelligentgateway.frame.util.PrefConstants;
import cn.qaii.intelligentgateway.frame.util.PrefUtils;


/**
 * 网络请求池
 * 
 * @author larry
 * 
 */
public class HttpExecutor {
	
	private static Context mContext;

	private static AsyncHttpClient mClient = new AsyncHttpClient();
	
	/**
	 * 初始化HttpExecutor,开启应用时初始化
	 */
	public static void init(Context context) {
		if (mClient == null) {
			mClient = new AsyncHttpClient();
			mClient.setTimeout(10000);
		}
		mContext = context;
	}

	/**
	 * 定义一个异步网络客户端 默认超时为10秒 当超过，默认重连次数为5次 默认最大连接数为10个 　　
	 */
	static {
		mClient.setTimeout(10000);
	}

	/**
	 * post 请求
	 * 
	 * @param url
	 *            API 地址
	 * @param params
	 *            请求的参数
	 * @param handler
	 *            数据加载句柄对象
	 */
	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler handler) {
		mClient.post(url, params, handler);
	}

	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler handler) {
		mClient.get(url, params, handler);
	}
	
	public static void cancel(Context context){
		mClient.cancelRequests(context, true);
	}

	/**
	 * 
	 * requestByPost(这里用一句话描述这个方法的作用) post请求方式 (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param command
	 * @param map
	 * @param requestListener
	 *            void
	 * @exception
	 * @since 1.0.0
	 */
	public static void requestByPost(final String command, Map<String, Object> map,
			final HttpRequest.RequestListener requestListener) {
		if(!LContext.isNetworkConnected){
			LLogger.e("网络未连接,请连接后重试");
			return;
		}
		final String requestJson = new GsonBuilder().disableHtmlEscaping().create().toJson(map);
		putBaseMap(map);
		try {
			String url = command;
			RequestParams params = new RequestParams();
			for (String key : map.keySet()) {
				params.put(key, map.get(key));
			}
			final long start = System.currentTimeMillis();
			LLogger.e("本次请求基本参数： " + requestJson + " \n " + "接口： " + command);
			mClient.post(url, params, new TextHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, Header[] headers, String response) {
//					LLogger.e("onSuccess" + response.toString());
					LLogger.e("本次请求[" + command + "]耗时:" + (System.currentTimeMillis() - start));
					requestListener.requestCompleted(response);
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
					LLogger.e("onFailure" + response);
					LLogger.e("本次请求[" + command + "]耗时:" + (System.currentTimeMillis() - start));
					requestListener.requestCompleted(null);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * requestByGet(这里用一句话描述这个方法的作用) 通过get方式请求网络
	 * (这里描述这个方法适用条件 – 可选)
	 * @param command
	 * @param map
	 * @param requestListener void
	 * @exception
	 * @since  1.0.0
	 */
	public static void requestByGet(final String command, Map<String, Object> map,
			final RequestListener requestListener) {
		if(!LContext.isNetworkConnected){
			LLogger.e("网络未连接,请连接后重试");
			return;
		}
		final String requestJson = new GsonBuilder().disableHtmlEscaping().create().toJson(map);
		//putBaseMap(map);
		try {
			String url = command;
			RequestParams params = new RequestParams();
			for (String key : map.keySet()) {
				params.put(key, map.get(key));
			}
			final long start = System.currentTimeMillis();
			LLogger.e("本次请求基本参数： " + requestJson + " \n " + "接口： " + command);
			mClient.get(url, params, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers, String response) {
//					LLogger.e("onSuccess" + response.toString());
					LLogger.e("本次请求[" + command + "]耗时:" + (System.currentTimeMillis() - start));
					requestListener.requestCompleted(response);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers, String response, Throwable throwable) {
					LLogger.e("onFailure" + response);
					LLogger.e("本次请求[" + command + "]耗时:" + (System.currentTimeMillis() - start));
					requestListener.requestCompleted(null);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 封装必传信息
	 * putBaseMap(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param map
	 * void
	 * @exception
	 * @since  1.0.0
	 */
	private static void putBaseMap(Map<String, Object> map){
		String token = PrefUtils.getPrefString(mContext, PrefConstants.ACCESS_TOKEN, "");
		String account=PrefUtils.getPrefString(mContext, PrefConstants.ACCOUNT, "");
		map.put("account", account);
		// 手机sdk版本
		int sdk = Build.VERSION.SDK_INT;
		String release = Build.VERSION.RELEASE;
		// 获取手机品牌
		String brand = Build.BRAND;
		String model = Build.MODEL;
		// 获取手机语言
		String locale = Locale.getDefault().getLanguage();
		// version code
		int versionCode = 0;
		try {
			versionCode = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		}
		// imei imsi
		/*TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		// 89860113817058951155
		String imei = tm.getSimSerialNumber();
		String phoneNumber = tm.getLine1Number();
		// 460011886607072
		String imsi = tm.getSubscriberId();
		map.put("sdk", sdk);
		map.put("release", release);
		map.put("brand", brand);
		map.put("model", model);
		map.put("locale", locale);123456
		map.put("versionCode", versionCode);
		map.put("imei", imei);
		map.put("imsi", imsi);
		map.put("phoneNumber", phoneNumber);
		LocationInfo location = BaiduMapManager.getLocationInfo(mContext);
		double latitude = 0,longitude = 0;
		if(location != null){
			latitude = location.getLat();
			longitude = location.getLng();
		}
		map.put("latitude", latitude);
		map.put("longitude", longitude);*/
	}

}
