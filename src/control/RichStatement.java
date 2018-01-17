package control;

import java.sql.SQLException;
import java.util.List;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.Codec;
import org.owasp.esapi.codecs.MySQLCodec;
import org.owasp.esapi.codecs.MySQLCodec.Mode;

public class RichStatement {
	
	private static Codec codec;
	
	private String query;
	private List<String> params;
	
	static {
		codec = new MySQLCodec(Mode.STANDARD);
	}
	
	public RichStatement(String query) throws SQLException {
		this.query = query;
		this.params = new java.util.ArrayList<>();
	}

	public void setRaw(String raw) throws SQLException {
		params.add(ESAPI.encoder().encodeForSQL(codec, raw));
	}

	public void setString(String val) throws SQLException {
		params.add('"' + ESAPI.encoder().encodeForSQL(codec, val) + '"');
	}

	public void executeUpdate() throws SQLException {
		for (String param : params) {
			query = query.replaceFirst("\\?", param);
		}
		System.out.println(query);
		DatabaseActor.getConnection().newPreparedStatement(query).executeUpdate();
	}

}
