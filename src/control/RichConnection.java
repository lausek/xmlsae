package control;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Symbolizes a connection to a mysql server. If a connection couldn't be
 * established, a SQLException will be thrown.
 * 
 * @author lausek
 *
 */
public class RichConnection {

	private static final String LOG4J_PATH = "properties/propertiesConnection.properties";
	private static Logger logger;

	static {
		logger = Logger.getLogger("Connection");
		PropertyConfigurator.configure(LOG4J_PATH);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	private Connection sqlConnection;
	private String host, user;

	/**
	 * 
	 * @param host
	 *            A host string in form <username>@<url>:<port>
	 * @param passwd
	 * @throws Exception
	 */
	public RichConnection(String hostString, char[] passwd) throws SQLException {

		String[] parts = hostString.split("@");

		user = !parts[0].isEmpty() ? parts[0] : "root";
		host = parts.length > 1 ? parts[1] : "localhost";

		logger.debug(user + "@" + host);
		sqlConnection = DriverManager.getConnection("jdbc:mysql://" + host, user, new String(passwd));

	}

	public void logout() {
		try {
			sqlConnection.close();
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
		}
	}

	public Statement newStatement() throws SQLException {
		return sqlConnection.createStatement();
	}
	
//	public RichStatement newRichStatement(String query) throws SQLException {
//		return new RichStatement(query);
//	}
	
	public PreparedStatement newPreparedStatement(String query) throws SQLException {
		return sqlConnection.prepareStatement(query);
	}
	
	public String getHost() {
		return host;
	}

	public String getUser() {
		return user;
	}

	public String getHostString() {
		return user + "@" + host;
	}
	
	public void setCatalog(String db) throws SQLException {
		sqlConnection.setCatalog(db);
	}

}
