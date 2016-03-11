package cn.qaii.intelligentgateway.frame.json;

public class PropertyInfo {

	private String className;

	private String name;
	
	private String key;
	
	private String valueType;
	
	private String format;
	
	private String converter;

	public PropertyInfo(String className, String name, String key, String valueType, String format, String converter) {
		super();
		this.className = className;
		this.name = name;
		this.key = key;
		this.valueType = valueType;
		this.format = format;
		this.converter = converter;
	}
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getConverter() {
		return converter;
	}

	public void setConverter(String converter) {
		this.converter = converter;
	}
}
