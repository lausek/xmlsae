package control;

import java.util.List;
import org.apache.log4j.*;
import javax.swing.JOptionPane;

import model.ExportSettings;

public class DBInterface {

	private static final Logger logger = Logger.getLogger(DBInterface.class);
	
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
		Process process = null;
		try{
			Runtime runtime = Runtime.getRuntime();
			String sqlDump = "/files/mysqldump";
			process = runtime.exec(sqlDump +" "+dbName + " --xml --single-transaction -u root > "+fileName);
			
			if(process.waitFor()==0){
				logger.info("Successfully created backup of "+dbName);
			}else{
				logger.error("Backup of "+dbName+" failed");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(process!=null){
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
			//TODO: different filenames for each database
			exportTo(dbnames, filename, settings);
		}
	}

	/**
	 * 
	 * @param dbname
	 * @param filename
	 */
	public void importTo(String dbname, String filename) {
		// TODO - implement DBInterface.importTo
		//TODO: use files/mysql
		throw new UnsupportedOperationException();
	}

	public void importTo(List<String> dbnames, String filename) {

	}
	
}