package cn.qaii.intelligentgateway.frame.json;

public class ColumnInfo {

	private String name;
	
	private String type;
	
	private int index;

	public ColumnInfo(String name, String type, int index) {
		super();
		this.name = name;
		this.type = type;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
