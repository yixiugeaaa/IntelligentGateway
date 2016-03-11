package cn.qaii.intelligentgateway.frame.json;

public class IntConvertor implements ValueConvertor {

	@Override
	public Object convert(String value, String format) {
		try {
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
