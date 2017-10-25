package model.database;

import java.util.ArrayList;
import java.util.List;

public class DataInfo extends InfoEntity {

	protected List<String> values = new ArrayList<>();
	
	public DataInfo(String tagValue) {
		super(tagValue);
	}

	@Override
	public StringBuilder toXML() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("<entry>");
		
		for(String value : values) {
			//TODO: escape for xml
			buffer.append("<value>"+value+"</value>");
		}
		
		buffer.append("</entry>");
		return buffer;
	}
	
	@Override
	public String toSQL() {
		return null;
	}
	
}
