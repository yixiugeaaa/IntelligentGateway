package cn.qaii.intelligentgateway.common.http.parser;


import cn.qaii.intelligentgateway.common.model.ResultInfo;
import cn.qaii.intelligentgateway.frame.util.JsonUtil;

/**
 * 
 * UserParser
 * 
 * @author larry 2015-12-21 上午11:07:00 
 * @version 1.0.0
 *
 */
public class ResultParser {
	
	public static ResultInfo parseInfo(String data){
		try {
			ResultInfo info = JsonUtil.fromJson(data, ResultInfo.class);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
