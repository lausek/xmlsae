package model.database;

import java.util.ArrayList;
import java.util.List;

public class DatabaseInfo extends InfoEntity {

	protected List<InfoEntity> dbObjects = new ArrayList<>();

	public DatabaseInfo(String tagValue) {
		super(tagValue);
	}
	
	public void addObject(InfoEntity obj) {
		dbObjects.add(obj);
	}
	
	@Override
	public StringBuilder toXML() {
		StringBuilder buffer = new StringBuilder();

		buffer.append("<database " + attributesToString() + ">");

		for (InfoEntity dbObjects : dbObjects) {
			buffer.append(dbObjects);
		}

		buffer.append("</database>");

		return buffer;
	}

	@Override
	public String toSQL() {
		return null;
	}

}
