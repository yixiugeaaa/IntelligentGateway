package cn.qaii.intelligentgateway.user.http;


import cn.qaii.intelligentgateway.frame.util.JsonUtil;
import cn.qaii.intelligentgateway.user.model.UserInfo;

/**
 * 
 * UserParser
 * 
 * @author larry 2015-12-21 上午11:07:00 
 * @version 1.0.0
 *
 */
public class UserParser {
	
	public static UserInfo parseUserInfo(String data){
		try {
			UserInfo info = JsonUtil.fromJson(data, UserInfo.class);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
