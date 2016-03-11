package cn.qaii.intelligentgateway.frame.json;

public class StringConvertor implements ValueConvertor {

	@Override
	public Object convert(String value, String format) {
		return value;
	}
}
