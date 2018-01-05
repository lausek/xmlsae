package control;

import java.sql.SQLException;
import java.util.List;

import org.xml.sax.Attributes;

public class TableInfo {
	
	private String name;
	private Attributes atts;
	private List<Attributes> columns;
	
	public TableInfo(String name, Attributes atts) {
		this.name = name;
		this.atts = atts;
		this.columns = new java.util.ArrayList<>();
	}
	
	public void addColumn(Attributes atts) {
		this.columns.add(atts);
	}
	
	public String getName() {
		return this.name;
	}
	
	public Attributes getAttributes() {
		return this.atts;
	}
	
	public RichStatement getInsertStatement() throws SQLException {
		String query = "INSERT INTO ? VALUES (";
		for(int i = 0; i < columns.size(); i++) {
			query += "?";
			if(i != columns.size()) {
				query += ",";
			}
		}
		query += ")";
		return DatabaseActor.getConnection().newRichStatement(query);
	}
	
	private String getCreateQuery() {
		String query = "CREATE TABLE IF NOT EXISTS ? (";
		String collation = atts.getValue("collation");
		
		for(Attributes column : columns) {
			// name and type
			query += "\n ? ?";
			
			if(column.getValue("key") != null) {
				query += " ? ";
			}
			
			if(column.getValue("default") != null) {
				query += " ? ";
			}
			
			if(column.getValue("null") != null) {
				query += " ? ";
			}
			
			if(column.getValue("extra") != null) {
				query += " ? ";
			}
			
		}
		query += ")";
		
		if(collation != null) {
			query += " COLLATE ?";
		}
		
		return query;
	}
	
	public RichStatement getCreateStatement() throws SQLException {
		String query = getCreateQuery();
		String collation = atts.getValue("collation");
		RichStatement stmt = DatabaseActor.getConnection().newRichStatement(query);
		stmt.setRaw(this.name);
		
		for(Attributes column : columns) {
			stmt.setRaw(column.getValue("name"));
			stmt.setRaw(column.getValue("type"));
			
			if(column.getValue("key") != null) {
				stmt.setRaw(column.getValue("key"));
			}
			
			if(column.getValue("default") != null) {
				stmt.setRaw(column.getValue("default"));
			}
			
			if(column.getValue("null") != null) {
				stmt.setRaw(column.getValue("null"));
			}
			
			if(column.getValue("extra") != null) {
				stmt.setRaw(column.getValue("extra"));
			}
		}
		
		if(collation != null) {
			stmt.setRaw(collation);
		}
		
		return stmt;
	}
}
