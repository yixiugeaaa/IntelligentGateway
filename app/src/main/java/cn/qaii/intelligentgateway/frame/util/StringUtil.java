package cn.qaii.intelligentgateway.frame.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * @author larry
 *
 */
public class StringUtil {
	
	public static final String STRING_YES = "Y";
	public static final String STRING_NO = "N";

	/**
	 * 是否为空字符串
	 * @param str
	 * @return
	 */
	public static final boolean isNull(String str) {
		return str == null || "".equals(str.trim());
	}
	
	public static final boolean isEqual(String str1, String str2){
		if(str1 == null || str2 == null){
			return false;
		}
		
		return str1.equals(str2);
	}

	public static boolean ynToBoolean(String str) {
		return STRING_YES.equals(str) ? true : false;
	}

	public static Boolean ynToBooleanObject(String str) {
		if(STRING_YES.equals(str)) {
			return true;
		} else if(STRING_NO.equals(str)) {
			return false;
		} else {
			return null;
		}
	}
	
	/**
	 * 判断是否是当天数据
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(new Date(System.currentTimeMillis()));
		String dateStr = format.format(date);
		if(today.equals(dateStr)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是不是数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断用户名是否合法
	 * */
	public static boolean accountIsLegal(String str) {
		try {
			Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_-]*$");
			return pattern.matcher(str).matches();
		} catch (Exception e) {

		}
		return false;
	}
	
	/**
	 * 判断是否是合法手机号
	 */
	public static boolean isMobileNO(String mobiles) {
		try {
			Pattern p = Pattern.compile("^1\\d{10}$");
			return p.matcher(mobiles).matches();
		} catch (Exception e) {

		}
		return false;
	}

	/**
	 * 验证输入的邮箱格式是否符合
	 */
	public static boolean isEmail(String email) {
		try {
			String emailPattern = "[a-zA-Z0-9][a-zA-Z0-9._-]{2,16}[a-zA-Z0-9]@[a-zA-Z0-9]+.[a-zA-Z0-9]+";
			return Pattern.matches(emailPattern, email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 验证输入的邮箱格式是否符合
	 */
	public static boolean isPassword(String password) {
		try {
			if(password.length()<6)
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String sumStr(String string){
		byte [] stringArr =string.getBytes() ;
		byte sum=0;
		for (int i=0;i<string.length();i++){
			sum+=stringArr[i];
		}
		byte[] sumStr=new byte[1];
		sumStr[0]=sum;
		return string+(new String(sumStr));
	}
	
}
