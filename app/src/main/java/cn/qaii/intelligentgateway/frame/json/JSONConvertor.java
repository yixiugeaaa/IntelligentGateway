package cn.qaii.intelligentgateway.frame.json;

import android.content.Context;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qaii.intelligentgateway.frame.util.LLogger;
import cn.qaii.intelligentgateway.frame.util.StringUtil;
import cn.qaii.intelligentgateway.model.LiveInfo;


/**
 * 解析服务器返回json对应字段属性
 * @author larry
 *
 */
public class JSONConvertor {
	
	private static final String XML_PATH = "json";
	public static final String PARAM_NAME_NAME = "name";
	public static final String PARAM_NAME_TYPE = "type";
	
	public static final String VALUE_TYPE_STR_INT = "int";
	public static final String VALUE_TYPE_STR_STRING = "string";
	public static final String VALUE_TYPE_STR_BOOLEAN = "boolean";
	public static final String VALUE_TYPE_STR_DATE = "date";
	public static final String VALUE_TYPE_STR_DOUBLE = "double";

	private static final Map<String, List<PropertyInfo>> beanInfos = new HashMap<String, List<PropertyInfo>>();//
	private static Map<String, ValueConvertor> commonConvertors = new HashMap<String, ValueConvertor>();//json解析，字符串转其他类型的变量
	private static Map<String, ValueConvertor> selfConvertors = new HashMap<String, ValueConvertor>();
	
	public static void init(Context context) {
		commonConvertors.put(VALUE_TYPE_STR_INT, new IntConvertor());
		commonConvertors.put(VALUE_TYPE_STR_STRING, new StringConvertor());
		commonConvertors.put(VALUE_TYPE_STR_BOOLEAN, new BooleanConvertor());
		commonConvertors.put(VALUE_TYPE_STR_DATE, new DateConvertor());
		commonConvertors.put(VALUE_TYPE_STR_DOUBLE, new DoubleConvertor());
		
		try {
			String[] xmls = context.getAssets().list(XML_PATH);
			SAXReader saxReader = new SAXReader();
			
			for(int i = 0; i < xmls.length; i++) {
				Document document = saxReader.read(context.getAssets().open(XML_PATH + File.separator + xmls[i]));
				Element root = document.getRootElement();
				String className = root.attributeValue("class");
				List<?> properties = root.elements("property");
				List<PropertyInfo> infos = new ArrayList<PropertyInfo>(properties.size());
				beanInfos.put(className, infos);
			
				for(int j = 0; j < properties.size(); j++) {
					Element info = (Element) properties.get(j);
					String name = info.attributeValue("name");
					String key = info.attributeValue("key");
					String valueType = info.attributeValue("type");
					String format = info.attributeValue("format");
					String convertor = info.attributeValue("convertor");
					if(StringUtil.isNull(name) ) {
						LLogger.e( "解析json配置文件时异常[" + xmls + "] property[" + j + "]的name不能为空");
					} else if(StringUtil.isNull(valueType)) {
						LLogger.e("解析json配置文件时异常[" + xmls + "] property[" + j + "]的valueType不能为空");
					} else if(StringUtil.isNull(key)) {
						LLogger.e( "解析json配置文件时异常[" + xmls + "] property[" + j + "]的key不能为空");
					} else {
						infos.add(new PropertyInfo(className, name, key, valueType.toLowerCase(), format, convertor));
					}
				}
			}
		} catch (Exception e) {
			LLogger.e( "解析json配置文件时发生异常+e");
		}
	}
	/**
	 * 解析[10]此类的数�?
	 * @param data
	 * @return
	 */
	public static int convert(String data){
		try {
			JSONArray array = new JSONArray(data);
			if(array.length() == 0){
				return 0;
			}
			return array.getInt(0);
		} catch (JSONException e) {
			LLogger.e( "解析json数据时发生异常"+ e);
		}
		return 0;
	}
	
	/**
	 * 解析实体类数�?
	 * @param cls
	 * @param data
	 * @return
	 */
	public static <T> List<T> convert(Class<T> cls, String data) { 
		List<T> list = new ArrayList<T>();
		int x=5;
		LLogger.e(getType(new LiveInfo().getStartTime()));
		String className = cls.getName();
		List<PropertyInfo> infos = beanInfos.get(className);
		if(infos == null) {
			return list;
		}
		try {
			JSONArray array = new JSONArray(data);
			if(array.length() == 0){
				return list;
			}
			for(int i = 0;i < array.length();i++){
				JSONObject jsonObject = new JSONObject(array.get(i).toString());
				T t = cls.newInstance();
				for(PropertyInfo info : infos){
					Method method = getMethod(cls, info);
					ValueConvertor convertor = getConvertor(info);
					method.invoke(t, convertor.convert(jsonObject.get(info.getKey()).toString(), info.getFormat()));
				}
				list.add(t);
			}
			
			LLogger.e( "解析json数据的结果[" + className + "]:" + list.size());
			return list;
		} catch (Exception e) {
			LLogger.e( "解析json数据时发生异常 "+ e);
			return list;
		}
	}
	
	public static String getType(Object o){
		return o.getClass().toString();
		}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Method getMethod(Class cls, PropertyInfo info) {
		Method method = null;
		String name = info.getName();
		if(getParameterType(info.getValueType()).equals(boolean.class) && name.startsWith("is") && name.length() > 2 && name.charAt(2) >= 'A' && name.charAt(2) <= 'Z') {
			name = name.substring(2);
		} else if(name.length() == 1) {
			name = name.toUpperCase();
		} else if(name.charAt(1) < 'A' || name.charAt(1) > 'Z') {
			name = name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		try {
			method = cls.getMethod("set" + name, getParameterType(info.getValueType()));
		} catch (Exception e) {
			try {
				method = cls.getMethod("set" + name, getParameterType2(info.getValueType()));
			} catch (Exception e1) {
				LLogger.e( "解析json数据时发生异常: [" + info.getClassName() + "] set" + name + "(" + info.getValueType() + ")方法不存在");
			}
		}
		return method;
	}

	private static ValueConvertor getConvertor(PropertyInfo info) {
		ValueConvertor convertor = null;
		if(!StringUtil.isNull(info.getConverter())) {
			convertor = selfConvertors.get(info.getConverter());
			if(convertor == null) {
				try {
					convertor = (ValueConvertor) Class.forName(info.getConverter()).newInstance();
				} catch (Exception e) {
					LLogger.e( "解析json数据 [" + info.getClassName() + "][" + info.getName() + "]时不能初始化转换 : " + info.getConverter() + e);
				}
				if(convertor == null) {
					convertor = commonConvertors.get(info.getValueType());
				} else {
					selfConvertors.put(info.getConverter(), convertor);
				}
			}
		} else {
			convertor = commonConvertors.get(info.getValueType());
		}
		return convertor;
	}
	
	private static Class<?> getParameterType(String type) {
		if(VALUE_TYPE_STR_INT.equals(type)) {
			return int.class;
		} else if(VALUE_TYPE_STR_STRING.equals(type)) {
			return String.class;
		} else if(VALUE_TYPE_STR_BOOLEAN.equals(type)) {
			return boolean.class;
		} else if(VALUE_TYPE_STR_DATE.equals(type)) {
			return Date.class;
		} else if(VALUE_TYPE_STR_DOUBLE.equals(type)) { 
			return double.class;
		}
		return null;
	}
	
	private static Class<?> getParameterType2(String type) {
		if(VALUE_TYPE_STR_INT.equals(type)) {
			return Integer.class;
		} else if(VALUE_TYPE_STR_STRING.equals(type)) {
			return String.class;
		} else if(VALUE_TYPE_STR_BOOLEAN.equals(type)) {
			return Boolean.class;
		} else if(VALUE_TYPE_STR_DATE.equals(type)) {
			return Date.class;
		} else if(VALUE_TYPE_STR_DOUBLE.equals(type)) { 
			return Double.class;
		}
		return null;
	}
}
