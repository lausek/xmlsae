package control;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.text.*;
import org.apache.commons.lang3.StringEscapeUtils;

public class RichStatement {
	
	private Statement stmt;
	private String query;
	private int insertIndex;
	
	public RichStatement(String query) throws SQLException {
		this.query = query;
		this.stmt = DatabaseActor.getConnection().newStatement();
	}
	
	public void setRaw(String raw) {
		
	}
	
	public void setString() {
		
	}
	
}
