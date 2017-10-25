package model.database;

import java.util.List;

public class TableInfo extends InfoEntity {

	protected List<ColumnInfo> columns;
	protected List<DataInfo> data;

	public TableInfo(String tagValue) {
		super(tagValue);
	}

	@Override
	public StringBuilder toXML() {
		StringBuilder buffer = new StringBuilder();

		buffer.append("<table " + attributesToString() + ">");

		// TODO: check if data is required
		buffer.append("<definition>");
		for (ColumnInfo column : columns) {
			buffer.append(column.toXML());
		}
		buffer.append("</definition>");

		// TODO: check if data is required
		buffer.append("<data>");
		for (DataInfo entry : data) {
			buffer.append(entry.toXML());
		}
		buffer.append("</data>");

		buffer.append("</table>");

		return buffer;
	}

	@Override
	public String toSQL() {
		return null;
	}

}
