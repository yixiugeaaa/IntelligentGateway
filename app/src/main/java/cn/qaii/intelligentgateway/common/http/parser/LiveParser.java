package cn.qaii.intelligentgateway.common.http.parser;

import java.util.List;

import cn.qaii.intelligentgateway.frame.util.JsonUtil;
import cn.qaii.intelligentgateway.model.LiveInfo;

/**
 * 解析直播数据的解析类
 * @author larry
 *
 */
public class LiveParser {
	
	/**
	 * 解析直播信息数据列表
	 * @param data
	 * @return
	 */
	public static List<LiveInfo> parseLiveInfoList(String data){
		List<LiveInfo> infos = JsonUtil.getList(data,LiveInfo.class);
		return infos;
	}

}
