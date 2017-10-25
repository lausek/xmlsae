package model.database;

import java.util.HashMap;

public abstract class InfoEntity {
	
	protected String tagValue;
	protected HashMap<String, String> attributes = new HashMap<String, String>();
	
	public InfoEntity(String tagValue) {
		this.tagValue = tagValue;
	}
	
	public void addAttribute(String key, String value) {
		attributes.put(key, value);
	}
	
	public String attributesToString() {
		StringBuilder buffer = new StringBuilder();
		
		attributes.forEach((key, value) -> {
			//TODO: check if value contains "
			buffer.append(" "+key+"=\""+value+"\"");
		});
		
		return buffer.toString();
	}
	
	//TODO: add ExportSettings to params?
	public abstract StringBuilder toXML();
	public abstract String toSQL();
	
}
