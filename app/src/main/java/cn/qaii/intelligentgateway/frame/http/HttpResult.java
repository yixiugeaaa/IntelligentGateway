package cn.qaii.intelligentgateway.frame.http;

/**
 * HttpResult 网络请求返回数据结构
 *
 * @author larry 2015-4-10 下午3:19:18
 * @version 1.0.0
 */
public class HttpResult {

    public static final String RESPONSE = "response";
    public static final String RESULT = "result";
    public static final String DESCRIPTION = "description";
    public static final String CODE = "code";
    public static final String NUMRETURN = "numReturn";

    // 非业务逻辑错误码应该小于0，成功为0，系统业务逻辑错误码大于0
    public static final int RESULT_CODE_SUCCESS = 1;// 请求成功
    public static final int RESULT_CODE_SERVER_EXCEPTION = -1;// 服务器异常
    public static final int RESULT_CODE_UNKNOW_EXCEPTION = -2;// 解析返回值的异常
    public static final int RESULT_CODE_CONNECTION_EXCEPTION = -999;// 网络异常

    public static final String RESULT_VALUE_SERVER_SUCCESS = "查询成功";// 服务器异常描述
    public static final String RESULT_VALUE_SERVER_FAILURE = "查询失败";// 服务器异常描述
    public static final String RESULT_VALUE_SERVER_EXCEPTION = "服务器返回值异常";// 服务器异常描述
    public static final String RESULT_VALUE_UNKNOW_EXCEPTION = "解析返回值时发生异常";// 解析返回值的异常描述
    public static final String RESULT_VALUE_CONNECTION_EXCEPTION = "连接不到服务器或发生网络异常，请检查网络设置或重试";// 网络异常描述

    private int code;
    private String description;
    private int numReturn;
    private String result;

    private boolean requestSuccessed;// 判断请求是否成功

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumReturn() {
        return numReturn;
    }

    public void setNumReturn(int numReturn) {
        this.numReturn = numReturn;
    }

    public boolean isRequestSuccessed() {
        return requestSuccessed;
    }

    public void setRequestSuccessed(boolean requestSuccessed) {
        this.requestSuccessed = requestSuccessed;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
