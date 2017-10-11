package control;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch(Exception e) {
			e.printStackTrace();
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
		password = passwd;
		
		sqlConnection = DriverManager.getConnection("jdbc:mysql://"+host, user, password);
		
		//TODO: Add null check?
		
	}
	
	//TODO: Is this needed?
	public Connection(String host) throws SQLException {
		this(host, "");
	}

	public void logout() {
		try {
			sqlConnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
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

}