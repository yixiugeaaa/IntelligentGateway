package cn.qaii.viewutil_lib.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;

/**
 * 
 * JsonUtil
 * 
 * @author larry 2015-11-3 下午2:49:21 
 * @version 1.0.0
 *
 */
public class JsonUtil {

	/**
	 * 对象转换成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * json字符串转成对象
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Type type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

	/**
	 * 解析数组
	 * 
	 * @param array
	 * @param class1
	 * @return
	 * @throws JSONException
	 */
	public static <T> List<T> getList(String json, Class<T> class1){
		List<T> list = new ArrayList<T>();
		try {
			if (json != null && json.startsWith("\ufeff")) {
				json = json.substring(1);
			}
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				list.add(JsonUtil.fromJson(array.get(i).toString(), class1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * json字符串转成对象
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static <T> T fromJson(String str, Class<T> type) {
		Gson gson = new Gson();
		return gson.fromJson(str, type);
	}

}
