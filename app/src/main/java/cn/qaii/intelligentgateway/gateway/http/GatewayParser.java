package cn.qaii.intelligentgateway.gateway.http;


import cn.qaii.intelligentgateway.frame.util.JsonUtil;
import cn.qaii.intelligentgateway.gateway.model.GatewayInfo;


/**
 * GatewayParser
 * 网关信息解析
 * Created by xiuge on 2016/3/23 11:33.
 */

public class GatewayParser {
	
	public static GatewayInfo parseGatewayInfo(String data){
		try {
			GatewayInfo info = JsonUtil.fromJson(data, GatewayInfo.class);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
