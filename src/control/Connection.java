package control;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Connection {
	
	private static final String LOG4J_PATH = "properties/propertiesConnection.properties";
	private static Logger logger;

	static {
		logger = Logger.getLogger("Connection");
		PropertyConfigurator.configure(LOG4J_PATH);
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			logger.debug(e);
		}
	}

	private java.sql.Connection sqlConnection;
	private String host, user, password;

	/**
	 * 
	 * @param host
	 * @param passwd
	 * @throws Exception
	 */
	public Connection(String hostString, String passwd) throws SQLException {

		String[] parts = hostString.split("@");

		user = !parts[0].isEmpty() ? parts[0] : "root";
		host = parts.length > 1 ? parts[1] : "localhost";
		// TODO: should we really save that?
		password = passwd;

		logger.debug(user+"@"+host);
		sqlConnection = DriverManager.getConnection("jdbc:mysql://" + host, user, password);

	}

	public void logout() {
		try {
			sqlConnection.close();
		} catch (SQLException e) {
			logger.debug(e.getMessage());
		}
	}

	public Statement newStatement() throws SQLException {
		return sqlConnection.createStatement();
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
	
}
