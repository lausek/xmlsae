package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.xml.sax.Attributes;

import control.DatabaseActor;
import control.RichStatement;

public class TableInfo {

	private String name, collation;
	private List<ColumnInfo> columns;

	public TableInfo(String name) {
		this.name = name;
		this.columns = new java.util.ArrayList<>();
	}

	public TableInfo(String name, Attributes atts) {
		this(name);
		this.collation = atts.getValue("collation");
	}

	public String getCollation() {
		if (collation == null) {
			try {
				Statement stat = DatabaseActor.getConnection().newStatement();
				ResultSet result = stat.executeQuery("SHOW TABLE STATUS LIKE '" + name + "'");
				result.next();
				collation = result.getString(15);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return collation;
	}

	public List<ColumnInfo> getColumns() {
		if (columns.isEmpty()) {
			try {
				Statement stat = DatabaseActor.getConnection().newStatement();
				ResultSet result = stat.executeQuery("SHOW COLUMNS FROM " + name);
				while (result.next()) {
					ColumnInfo column = new ColumnInfo();
					column.setName(result.getString(1));
					column.setType(result.getString(2));
					column.setKey(result.getString(3));
					column.setDefault(result.getString(4));
					column.setNull(result.getString(5));
					column.setExtra(result.getString(6));
					addColumn(column);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return columns;
	}

	public void addColumn(ColumnInfo column) {
		this.columns.add(column);
	}

	public String getName() {
		return this.name;
	}

	public RichStatement getInsertStatement() throws SQLException {
		String query = "INSERT INTO ? VALUES (";
		for (int i = 0; i < getColumns().size(); i++) {
			query += "?";
			if (i != getColumns().size() - 1) {
				query += ",";
			}
		}
		query += ")";
		return DatabaseActor.getConnection().newRichStatement(query);
	}

	private String getCreateQuery() {
		List<ColumnInfo> keys = new java.util.ArrayList<>();
		String query = "CREATE TABLE IF NOT EXISTS ? (";
		int i = 1;

		for (ColumnInfo column : getColumns()) {
			// name and type
			if (1 < i) {
				query += ", ";
			}

			query += "\n ? ?";

			if (column.getDefault() != null) {
				query += " ? ";
			}

			if (column.getNull() != null) {
				query += " ? ";
			}

			if (column.getExtra() != null) {
				query += " ? ";
			}

			if (column.isKey()) {
				keys.add(column);
			}

			i++;

		}

		if (!keys.isEmpty()) {
			query += ", PRIMARY KEY (";
			int counter = 0;
			for (ColumnInfo key : keys) {
				counter++;
				query += "?";
				if (counter != keys.size()) {
					query += ",";
				}
			}
			query += ")\n";
		}

		query += ")";

		if (getCollation() != null && !getCollation().equals("null")) {
			query += " COLLATE ?";
		}

		return query;
	}

	public RichStatement getCreateStatement() throws SQLException {
		List<ColumnInfo> keys = new java.util.ArrayList<>();
		String query = getCreateQuery();
		RichStatement stmt = DatabaseActor.getConnection().newRichStatement(
				query);
		stmt.setRaw(this.name);

		for (ColumnInfo column : getColumns()) {
			stmt.setRaw(column.getName());
			stmt.setRaw(column.getType());

			// if (column.getKey() != null) {
			// stmt.setRaw(column.getKey());
			// }

			if (column.getDefault() != null) {
				stmt.setRaw(column.getDefault());
			}

			if (column.getNull() != null) {
				stmt.setRaw(column.getNull());
			}

			if (column.getExtra() != null) {
				stmt.setRaw(column.getExtra());
			}

			if (column.isKey()) {
				keys.add(column);
			}

		}

		for (ColumnInfo key : keys) {
			stmt.setRaw(key.getName());
		}

		if (getCollation() != null) {
			stmt.setRaw(getCollation());
		}

		return stmt;
	}
}
