package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.*;

/**
 * Class for interacting with the database. This object obtains an established
 * Connection and executes import and export tasks.
 * 
 * @author lausek
 *
 */
public class DatabaseActor {

	private static final String LOG4J_PATH = "properties/propertiesDBActor.properties";
	private static Logger logger;

	private static Connection connection;

	static {
		logger = Logger.getLogger("DatabaseActor");
		PropertyConfigurator.configure(LOG4J_PATH);
	}
	
	public DatabaseActor(Connection con) {
		setConnection(con);
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		connection.logout();
	}
	
	public static void setConnection(Connection con) {
		DatabaseActor.connection = con;
	}
	
	public static Connection getConnection() {
		return connection;
	}

	public static List<String> getDatabases() throws SQLException {
		final ResultSet result = connection.newStatement().executeQuery("SHOW DATABASES");
		List<String> asList = new java.util.ArrayList<>();
		while (result.next()) {
			asList.add(result.getString(1));
		}
		return asList;
	}

}
