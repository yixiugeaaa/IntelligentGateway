package cn.qaii.intelligentgateway.frame.json;

public class DoubleConvertor implements ValueConvertor {

	@Override
	public Object convert(String value, String format) {
		try {
			return Double.valueOf(value);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
