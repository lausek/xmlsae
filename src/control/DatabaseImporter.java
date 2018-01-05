package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import model.ImportSettings;
import view.atoms.CSelectedFile;

public class DatabaseImporter {

	private ImportSettings settings;

	public DatabaseImporter(ImportSettings settings) {
		this.settings = settings;
	}

	public void start() throws IOException {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		parserFactory.setValidating(true);
		
		for (CSelectedFile file : settings.getFiles()) {			
			try (InputStream stream = new FileInputStream(file.get())) {
				try {
					SAXParser parser = parserFactory.newSAXParser();
					XMLReader reader = parser.getXMLReader();
					reader.setErrorHandler(new ImportErrorHandler());
					reader.setContentHandler(new ImportContentHandler(this));
					
					reader.parse(new InputSource(stream));
					
				} catch (ParserConfigurationException | SAXException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void insertInto(String table, List<String> entry) {
		
	}
	
	public void createTable(String name, Attributes atts) throws SAXException {
		String query = "CREATE TABLE IF NOT EXISTS ?";
		String collation = atts.getValue("collation");
		String autoinc = atts.getValue("autoinc");
		try {
			
			if(collation != null) {
				query += " ";
			}
			
			if(autoinc != null) {
				query += " ";
			}
			
			PreparedStatement stmt = DatabaseActor.getConnection().newPreparedStatement(query);
		
			stmt.executeQuery();
			
		} catch(SQLException e) {
			throw new SAXException("Table couldn't be created");
		}
	}
	
	public void alterTable(String name, Attributes atts) { 
		
	}
	
	public void createView(String name, String selectQuery) throws SAXException {
		String query = "CREATE OR REPLACE VIEW ? AS ?";
		try {
			
			PreparedStatement stmt = DatabaseActor.getConnection().newPreparedStatement(query);
			stmt.setString(1, name);
			stmt.setString(2, query);
			
			stmt.executeQuery();
			
		} catch(SQLException e) {
			throw new SAXException("View couldn't be created");
		}
	}
	
	public void createDatabase(String name, Attributes atts) throws SAXException {
		String collation = atts.getValue("collation");
		String charset = atts.getValue("charset");
		try {
			String query = "CREATE DATABASE ?";
			
			if(charset != null) {
				query += " CHARACTER SET ?";
			}
			
			if(collation != null) {
				query += " COLLATE ?";
			}
			
			PreparedStatement stmt = DatabaseActor.getConnection().newPreparedStatement(query);
			stmt.setString(1, name);
			
			if(collation != null && charset != null) {
				stmt.setString(2, charset);
				stmt.setString(3, collation);
			} else if(charset != null) {
				stmt.setString(2, charset);
			} else if(collation != null) {
				// no charset given, take second place
				stmt.setString(2, collation);
			}
			
			stmt.executeQuery();
			
		} catch (SQLException e) {
			throw new SAXException("Database couldn't be created");
		}
	}

}
