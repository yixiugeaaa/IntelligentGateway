package cn.qaii.intelligentgateway.frame.constant;

public class LCommands {

	//public static final String BASE_URL = "http://192.168.4.66:8080/qaiim/";
	public static final String BASE_URL = "http://192.168.9.53:8080/qaiim/";
	public static final String REGISTER = BASE_URL + "register.o?";// 注册
	public static final String LOGIN = BASE_URL + "login.o?";// 登录
	public static final String BIND = BASE_URL+"bind.o?";//绑定网关设备
	public static final String GET_DEVICE=BASE_URL+"getbind.o";//获取绑定设备
	public static final String GET_USER_INFO = BASE_URL + "user/getUserInfo";// 获取用户信息
	public static final String GET_DATA = "http://www.qdtcn.com/qdtnet/query.do";// 查询日期接口
}
