package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.*;

import model.ExportSettings;

public class DBInterface {

	private static final String LOG4J_PATH = "properties/propertiesDBI.properties";
	private static Logger logger;

	private Connection connection;

	static {
		logger = Logger.getLogger("DBInterface");
		PropertyConfigurator.configure(LOG4J_PATH);
	}

	/**
	 * 
	 * @param connection
	 */
	public DBInterface(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public List<String> getDatabases() throws SQLException {
		final ResultSet result = connection.newStatement().executeQuery("SHOW DATABASES");
		List<String> asList = new java.util.ArrayList<>();
		while (result.next()) {
			asList.add(result.getString(1));
		}
		return asList;
	}

	/**
	 * exports database by mysqldump
	 * 
	 * @param dbname
	 * @param filename
	 * @param settings
	 */
	public void exportTo(String dbname, String filename, ExportSettings settings) {
		logger.debug("Start export to "+dbname);
		// tables := SHOW TABLES 
		
		// foreach table
		// 		if isDefinitionRequired
		// 			show columns for
		// 		endif
		
		// 		if isDataRequired
		// 			SELECT * FROM
		// 		endif
		
	}

	/**
	 * exports more than 1 database
	 * 
	 * @param dbnames
	 * @param filename
	 * @param settings
	 */
	public void exportTo(List<String> dbnames, String filename, ExportSettings settings) {
		for (String dbname : dbnames) {
			String[] split = filename.split("\\.");
			split[0] += "_" + dbname;
			String newFileName = String.join(".", split);
			
			exportTo(dbname, newFileName, settings);
			
			logger.debug("Exported " + dbname + " on file " + newFileName);
		}
	}

	/**
	 * imports database by mysql
	 * 
	 * @param dbname
	 * @param filename
	 */
	public void importTo(String dbname, String filename) {
		logger.debug("Start import to "+dbname);
	}

	/**
	 * import more than 1 database
	 * 
	 * @param dbnames
	 * @param filename
	 */
	public void importTo(List<String> dbnames, String filename) {
		for (String dbname : dbnames) {
			importTo(dbname, filename);
		}
	}

}
