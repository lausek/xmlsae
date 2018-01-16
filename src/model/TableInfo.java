package model;

import java.sql.SQLException;
import java.util.List;

import org.xml.sax.Attributes;

import control.DatabaseActor;
import control.RichStatement;

public class TableInfo {

	private String name, collation;
	private List<ColumnInfo> columns;

	public TableInfo(String name, Attributes atts) {
		this.name = name;
		this.collation = atts.getValue("collation");
		this.columns = new java.util.ArrayList<>();
	}

	public void addColumn(ColumnInfo column) {
		this.columns.add(column);
	}

	public String getName() {
		return this.name;
	}

	public RichStatement getInsertStatement() throws SQLException {
		String query = "INSERT INTO ? VALUES (";
		for (int i = 0; i < columns.size(); i++) {
			query += "?";
			if (i != columns.size()-1) {
				query += ",";
			}
		}
		query += ")";
		return DatabaseActor.getConnection().newRichStatement(query);
	}

	private String getCreateQuery() {
		String query = "CREATE TABLE IF NOT EXISTS ? (";
		int i = 1;

		for (ColumnInfo column : columns) {
			// name and type
			if (1 < i) {
				query += ", ";
			}

			query += "\n ? ?";

			if (column.getKey() != null) {
				query += " ? ";
			}

			if (column.getDefault() != null) {
				query += " ? ";
			}

			if (column.getNull() != null) {
				query += " ? ";
			}

			if (column.getExtra() != null) {
				query += " ? ";
			}

			i++;

		}
		query += ")";

		if (collation != null) {
			query += " COLLATE ?";
		}

		return query;
	}

	public RichStatement getCreateStatement() throws SQLException {
		String query = getCreateQuery();
		RichStatement stmt = DatabaseActor.getConnection().newRichStatement(query);
		stmt.setRaw(this.name);

		for (ColumnInfo column : columns) {
			stmt.setRaw(column.getName());
			stmt.setRaw(column.getType());

			if (column.getKey() != null) {
				stmt.setRaw(column.getKey());
			}
			
			if (column.getDefault() != null) {
				stmt.setRaw(column.getDefault());
			}

			if (column.getNull() != null) {
				stmt.setRaw(column.getNull());
			}

			if (column.getExtra() != null) {
				stmt.setRaw(column.getExtra());
			}
		}

		if (collation != null) {
			stmt.setRaw(collation);
		}

		return stmt;
	}
}
