package cn.qaii.intelligentgateway.frame.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertor implements ValueConvertor {

	@Override
	public Object convert(String value, String format) {
		try {
			long time = Long.parseLong(value) * 1000;
			Date date = new Date(time);
			String str = new SimpleDateFormat().format(date);
			return new SimpleDateFormat().parse(str);
		} catch (ParseException e) {
			return null;
		}
	}
}
