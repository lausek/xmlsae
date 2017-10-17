package control;

import java.util.List;

import org.apache.log4j.*;

import model.ExportSettings;

public class DBInterface {

	private static final String LOG4J_PATH = "properties/propertiesDBI.properties";
	private static Logger logger;
	/**
	 * 
	 * @param connection
	 */
	public DBInterface(Connection connection) {
		logger = Logger.getLogger("DBInterface");
		PropertyConfigurator.configure(LOG4J_PATH);
		throw new UnsupportedOperationException();
	}

	public List<String> getDatabases() {
		// TODO - implement DBInterface.getDatabases
		throw new UnsupportedOperationException();
	}

	/**
	 * exports database by mysqldump
	 * @param dbname
	 * @param filename
	 * @param settings
	 */
	public void exportTo(String dbName, String fileName, ExportSettings settings) {
		logger.debug("Start export");
		Process process = null;
		try{
			Runtime runtime = Runtime.getRuntime();
			String sqlDump = "/files/mysqldump";
			process = runtime.exec(sqlDump +" "+dbName + " --xml --single-transaction -u root > "+fileName);
			
			if(process.waitFor()==0){
				logger.debug("Successfully created backup of "+dbName);
			}
		}catch(Exception e){
			logger.debug("Export failed: "+e.getMessage());
		}finally{
			if(process!=null){
				process.destroy();
			}
		}
		
	}
	
	/**
	 * exports more than 1 database
	 * @param dbnames
	 * @param filename
	 * @param settings
	 */
	public void exportTo(List<String> dbnames, String filename, ExportSettings settings) {
		try{
			for (String dbname : dbnames) {
				String[] split = filename.split("\\.");
				split[0]+="_"+dbname;
				String newFileName = String.join(".", split);
				exportTo(dbname, newFileName, settings);
				logger.debug("Exported "+dbname+" on file "+newFileName);
			}
		}catch(Exception e){
			logger.debug(e.getMessage());
		} 
	}

	/**
	 * imports database by mysql
	 * @param dbname
	 * @param filename
	 */
	public void importTo(String dbname, String filename) {
		logger.debug("Start import");
		Process process = null;
		try{
			Runtime runtime = Runtime.getRuntime();
			String mysql = "/files/mysql";
			//TODO: This command needs an .sql file, idk if it works with others
			process = runtime.exec(mysql + " -u root -p "+dbname+" < "+filename);
			
			if(process.waitFor()==0){
				logger.debug("Successfully imported "+dbname);
			}
		}catch(Exception e){
			logger.debug("Import failed: "+e.getMessage());
		}finally{
			if(process!=null){
				process.destroy();
			}
		}
		
	}
	
	/**
	 * import more than 1 database
	 * @param dbnames
	 * @param filename
	 */
	public void importTo(List<String> dbnames, String filename) {
		for (String dbname : dbnames) {
			importTo(dbname, filename);
		}
	}
	
}