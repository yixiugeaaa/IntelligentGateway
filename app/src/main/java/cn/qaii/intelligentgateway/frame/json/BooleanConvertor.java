package cn.qaii.intelligentgateway.frame.json;
public class BooleanConvertor implements ValueConvertor {

	@Override
	public Object convert(String value, String format) {
		if(Integer.parseInt(value) == 1){
			return true;
		}
		return false;
	}
}
