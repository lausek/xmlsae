package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.Statement;

import model.ProcessSettings;

public class DatabaseExporter {

	private ProcessSettings settings;
	
	public DatabaseExporter(ProcessSettings settings) {
		this.settings = settings;
	}
	
	public void start() throws IOException {
		for(String db : settings.getDatabases()) {
			
			File file = new File(db+".xml");
			
			try(OutputStream stream = new FileOutputStream(file)) {
				
				wrapDatabase(stream, db);
				
			}
		}
	}
	
	private void write(OutputStream stream, String db) throws IOException {
		stream.write(db.getBytes());
	}
	
	public void wrapDatabase(OutputStream stream, String db) throws IOException {
		
		Connection con = DatabaseActor.getConnection();
		
		try {
			Statement stat = con.newStatement();
			stat.execute("");

			
		} catch (SQLException e) {
			// TODO: Add Logger here
			e.printStackTrace();
		}
		
		write(stream, "<database collation='' charset=''>");
		
		
		
		write(stream, "</database>");
	}
	
}
