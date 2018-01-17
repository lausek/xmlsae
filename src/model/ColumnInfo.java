package model;

import org.xml.sax.Attributes;

public class ColumnInfo {

	private String name, type, key, def, nil, extra;

	public ColumnInfo(Attributes atts) {
		this.name = atts.getValue("name");
		this.type = atts.getValue("type");
		this.key = atts.getValue("key");
		this.def = atts.getValue("default");
		this.nil = atts.getValue("null");
		this.extra = atts.getValue("extra");
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

}
