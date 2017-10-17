package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.*;

import model.ExportSettings;

public class DBInterface {

	private static final Logger logger = Logger.getLogger(DBInterface.class);
	private Connection connection;
	
	/**
	 * 
	 * @param connection
	 */
	public DBInterface(Connection connection) {
		this.connection = connection;
	}

	public List<String> getDatabases() throws SQLException {
		final ResultSet result = connection.newStatement().executeQuery("SHOW DATABASES");
		List<String> asList = new java.util.ArrayList<>();
		while(result.next()) {
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
	public void exportTo(String dbName, String fileName, ExportSettings settings) {
		Process process = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			String sqlDump = "/files/mysqldump";
			process = runtime.exec(sqlDump + " " + dbName + " --xml --single-transaction -u root > " + fileName);

			if (process.waitFor() == 0) {
				logger.info("Successfully created backup of " + dbName);
			} else {
				logger.error("Backup of " + dbName + " failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (process != null) {
				process.destroy();
			}
		}

	}

	/**
	 * 
	 * @param dbnames
	 * @param filename
	 * @param settings
	 */
	public void exportTo(List<String> dbnames, String filename, ExportSettings settings) {
		for (String dbname : dbnames) {
			// TODO: different filenames for each database
			exportTo(dbname, filename, settings);
		}
	}

	/**
	 * 
	 * @param dbname
	 * @param filename
	 */
	public void importTo(String dbname, String filename) {
		// TODO - implement DBInterface.importTo
		// TODO: use files/mysql
		throw new UnsupportedOperationException();
	}

	public void importTo(List<String> dbnames, String filename) {

	}

}