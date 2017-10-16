package control;

import java.util.List;

import org.apache.log4j.*;

import model.ExportSettings;
import model.LoggerSettings;

public class DBInterface {

	private static final Logger logger = Logger.getLogger(DBInterface.class);
	private static final String logFile = "log/DBInterface.log";
	/**
	 * 
	 * @param connection
	 */
	public DBInterface(Connection connection) {
		// TODO - implement DBInterface.DBInterface
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
		LoggerSettings.initLogger(logger, logFile);
		Process process = null;
		try{
			Runtime runtime = Runtime.getRuntime();
			String sqlDump = "/files/mysqldump";
			process = runtime.exec(sqlDump +" "+dbName + " --xml --single-transaction -u root > "+fileName);
			
			if(process.waitFor()==0){
				logger.debug("Successfully created backup of "+dbName);
			}else{
				logger.debug("Backup of "+dbName+" failed");
			}
		}catch(Exception e){
			logger.debug("Export: "+e.getMessage());
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
		LoggerSettings.initLogger(logger, logFile);
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
	 * 
	 * @param dbname
	 * @param filename
	 */
	public void importTo(String dbname, String filename) {
		//TODO: use files/mysql
		LoggerSettings.initLogger(logger, logFile);
		Process process = null;
		try{
			Runtime runtime = Runtime.getRuntime();
			String mysql = "/files/mysql";
			process = runtime.exec(mysql + " -u root -p "+dbname+" < "+filename);
			
			if(process.waitFor()==0){
				logger.debug("Successfully imported "+dbname);
			}else{
				logger.debug("Import of "+dbname+" failed");
			}
		}catch(Exception e){
			logger.debug("Import: "+e.getMessage());
		}finally{
			if(process!=null){
				process.destroy();
			}
		}
		
	}
	
	public void importTo(List<String> dbnames, String filename) {
		for (String dbname : dbnames) {
			importTo(dbname, filename);
		}
	}
	
}