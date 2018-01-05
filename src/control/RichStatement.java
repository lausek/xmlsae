package control;

import java.sql.SQLException;
import java.util.List;

public class RichStatement {

	private String query;
	private List<String> params;

	public RichStatement(String query) throws SQLException {
		this.query = query;
		this.params = new java.util.ArrayList<>();
	}

	public void setRaw(String raw) throws SQLException {
		// TODO: implement
		params.add(raw);
	}

	public void setString(String val) throws SQLException {
		params.add('"' + val + '"');
	}

	public void executeUpdate() throws SQLException {
		for (String param : params) {
			query = query.replaceFirst("\\?", param);
		}
		DatabaseActor.getConnection().newPreparedStatement(query).executeUpdate();
	}

}
