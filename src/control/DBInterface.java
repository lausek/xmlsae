package control;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.*;

import model.ExportSettings;

/**
 * Class for interacting with the database. This object obtains an established
 * Connection and executes import and export tasks.
 * 
 * @author lausek
 *
 */
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
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		connection.logout();
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
	public void exportTo(String dbName, String fileName, ExportSettings settings) {
		logger.debug("Start export");
		Process process = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			// TODO: make final
			String sqlDump = "/files/mysqldump";
			process = runtime.exec(sqlDump + " " + dbName + " --xml --single-transaction -u root > " + fileName);

			if (process.waitFor() == 0) {
				logger.debug("Successfully created backup of " + dbName);
			}
		} catch (Exception e) {
			logger.debug("Export failed: " + e.getMessage());
		} finally {
			if (process != null) {
				process.destroy();
			}
		}

	}

	/**
	 * exports more than 1 database
	 * 
	 * @param dbnames
	 * @param filename
	 * @param settings
	 */
	public void exportTo(List<String> dbnames, String filename, ExportSettings settings) {
		try {
			for (String dbname : dbnames) {
				String[] split = filename.split("\\.");
				split[0] += "_" + dbname;
				String newFileName = String.join(".", split);
				exportTo(dbname, newFileName, settings);
				logger.debug("Exported " + dbname + " on file " + newFileName);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}

	/**
	 * imports database by mysql
	 * 
	 * @param dbname
	 * @param filename
	 */
	public void importTo(String dbname, String filename) {
		logger.debug("Start import");
		Process process = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			String mysql = "/files/mysql";
			// TODO: This command needs an .sql file, idk if it works with others
			process = runtime.exec(mysql + " -u root -p " + dbname + " < " + filename);

			if (process.waitFor() == 0) {
				logger.debug("Successfully imported " + dbname);
			}
		} catch (Exception e) {
			logger.debug("Import failed: " + e.getMessage());
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
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
