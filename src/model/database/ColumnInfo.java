package model.database;

public class ColumnInfo extends InfoEntity {

	public ColumnInfo(String tagValue) {
		super(tagValue);
	}

	@Override
	public StringBuilder toXML() {
		return new StringBuilder("<column " + attributesToString() + "></column>");
	}

	@Override
	public String toSQL() {
		return null;
	}

}
