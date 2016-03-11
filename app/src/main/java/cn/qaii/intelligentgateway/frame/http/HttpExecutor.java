package cn.qaii.intelligentgateway.frame.http;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.qaii.intelligentgateway.frame.constant.LConstants;
import cn.qaii.intelligentgateway.frame.constant.LContext;
import cn.qaii.intelligentgateway.frame.util.LLogger;

/**
 * 网络请求池
 * @author larry
 *
 */
public class HttpExecutor {
	
	private static HttpClient httpClient;
	
	private static final String COMMAND = "CMDId";
	private static final String JSON = "JSon";
	private static final String TOKEN = "Token";
	private static final String MD5 = "MD5";
	private static final String MD5_VALUE = "";//MD5加密值，目前没有用到，先默认空字符串
	
	/**
	 * 初始化HttpExecutor,开启应用时初始化
	 */
	public static void init() {
        HttpParams params =new BasicHttpParams();
        /* 从连接池中取连接的超时时间 */ 
        ConnManagerParams.setTimeout(params, 50000);
        /* 连接超时 */ 
        HttpConnectionParams.setConnectionTimeout(params, 50000); 
        /* 请求超时 */
        HttpConnectionParams.setSoTimeout(params, 100000);
        
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));//////////////////////////////////////////////////////////////

        ThreadSafeClientConnManager cm=new ThreadSafeClientConnManager(params, schemeRegistry);
        httpClient = new DefaultHttpClient(cm,params);
	}
	
	/**
	 * 发送请求
	 * @param command
	 * @param param
	 * @return
	 */
	public static String request(String command, Map<String, Object> param) {
		InputStream content = null;
		String requestJson = new GsonBuilder().disableHtmlEscaping().create().toJson(param);
		try {
			HttpPost request = new HttpPost(LConstants.BASE_URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			LLogger.e("本次请求串为 ： " + requestJson + " \n " + "基本参数 ： " + command + " : " + LContext.TOKEN + " : " + MD5_VALUE + " : " + LConstants.BASE_URL);
			params.add(new BasicNameValuePair(COMMAND, command));
			params.add(new BasicNameValuePair(JSON, requestJson));
			params.add(new BasicNameValuePair(TOKEN, LContext.TOKEN));
			params.add(new BasicNameValuePair(MD5, MD5_VALUE));
			
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			long start = System.currentTimeMillis();
			if(httpClient == null){
				init();
			}
			HttpResponse response = httpClient.execute(request);
			LLogger.e("本次请求[" + command + "]耗时:" + (System.currentTimeMillis() - start));
			if (response.getStatusLine().getStatusCode() == 200) {
				content = response.getEntity().getContent();
				return getResponseData(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				content.close();
			} catch (Exception e) {}
		}
		return null;
	}
	/**
	 * 获取返回值数据
	 * @param content
	 * @return
	 * @throws IOException
	 */
	private static String getResponseData(InputStream content) throws IOException {
		//获取返回值
		byte[] data = new byte[1024 * 8];
		StringBuffer strBuf = new StringBuffer();
		
		int count = 0;
		while((count = content.read(data)) != -1) {
			strBuf.append(new String(data, 0, count));
		}
		return strBuf.toString();
	}

}
