package model;

import org.xml.sax.Attributes;

import control.xml.AttributeBuilder;

public class ColumnInfo {

	private String name, type, key, def, nil, extra;
	
	public ColumnInfo() {
		
	}
	
	public ColumnInfo(Attributes atts) {
		this.name = atts.getValue("name");
		this.type = atts.getValue("type");
		this.key = atts.getValue("key");
		this.def = atts.getValue("default");
		this.nil = atts.getValue("null");
		this.extra = atts.getValue("extra");
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setDefault(String def) {
		this.def = def;
	}

	public void setNull(String nil) {
		this.nil = nil;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public boolean isKey() {
		return key.equals("PRI");
	}

	public String getDefault() {
		if (def == null) {
			return "";
		}

		switch (def.toLowerCase()) {
		case "":
			// fallthrough
		case "null":
			return "";

		case "current_timestamp":
			return "DEFAULT " + def;

		default:
			return "DEFAULT '" + def + "'";
		}
	}

	public String getNull() {
		return nil.equals("NO") ? "NOT NULL" : "NULL";
	}

	public String getExtra() {
		return extra;
	}

	@Override
	public String toString() {
		return AttributeBuilder.newTag("column").set("name", name)
				.set("type", type).set("null", nil).set("key", key)
				.set("default", def).set("extra", extra).append("/").toString();
	}

}
