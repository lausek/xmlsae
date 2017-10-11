package control;

import java.util.List;
import javax.swing.JOptionPane;

import model.ExportSettings;

public class DBInterface {

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
	 * 
	 * @param dbname
	 * @param filename
	 */
	public void exportTo(String dbname, String filename) {
		// TODO - implement DBInterface.exportTo
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param dbname
	 * @param filename
	 * @param settings
	 */
	public void exportTo(String dbname, String filename, ExportSettings settings) {
		// TODO - implement DBInterface.exportTo
		// TODO - Create list and call exportTo(dbnames)
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * @param dbnames
	 * @param filename
	 */
	public void exportTo(List<String> dbnames, String filename) {
		
	}
	
	/**
	 * 
	 * @param dbnames
	 * @param filename
	 * @param settings
	 */
	public void exportTo(List<String> dbnames, String filename, ExportSettings settings) {
		
	}

	/**
	 * 
	 * @param dbname
	 * @param filename
	 */
	public void importTo(String dbname, String filename) {
		// TODO - implement DBInterface.importTo
		throw new UnsupportedOperationException();
	}
	
	public void importTo(List<String> dbnames, String filename) {
		
	}
	
	private String mySqlDump(String dbName, String fileName){
		Process process = null;
		try{
			Runtime runtime = Runtime.getRuntime();
			String sqlDump = "/files/mysqldump";
			process = runtime.exec(sqlDump +" "+dbName + " --xml --single-transaction -u root > "+fileName);
			
			//wenn erfolgreich ausgeführt
			if(process.waitFor()==0){
				//TODO: log4j einbinden
			//wenn nicht erfoglreich ausgeführt
			}else{
				//TODO: log4j einbinden
			}
			
			
			
		}catch(Exception e){
			
		}
		return "";
	}
	

}