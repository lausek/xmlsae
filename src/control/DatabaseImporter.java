package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
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
	
	public void insertInto(TableInfo table, List<String> entry) throws SAXException {
		try {
			RichStatement stmt = table.getInsertStatement();
			// first ? is table name
			stmt.setRaw(table.getName());
			for(String val : entry) {
				stmt.setString(val);
			}
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SAXException("Couldn't insert");
		}
	}
	
	public void createTable(TableInfo table) throws SAXException {
		try {
			
			table.getCreateStatement().executeUpdate();
			
		} catch(SQLException e) {
			throw new SAXException("Table couldn't be created");
		}
	}
	
	public void createView(String name, String selectQuery) throws SAXException {
		String query = "CREATE OR REPLACE VIEW ? AS ?";
		try {
			
			RichStatement stmt = DatabaseActor.getConnection().newRichStatement(query);
			stmt.setRaw(name);
			stmt.setString(query);
			
			stmt.executeUpdate();
			
		} catch(SQLException e) {
			throw new SAXException("View couldn't be created");
		}
	}
	
	public void createDatabase(String name, Attributes atts) throws SAXException {
		String collation = atts.getValue("collation");
		String charset = atts.getValue("charset");
		try {
			String query = "CREATE DATABASE IF NOT EXISTS ?";
			
			if(charset != null) {
				query += " CHARACTER SET ?";
			}
			
			if(collation != null) {
				query += " COLLATE ?";
			}
			
			RichStatement stmt = DatabaseActor.getConnection().newRichStatement(query);
			stmt.setRaw(name);
			
			if(collation != null && charset != null) {
				stmt.setRaw(charset);
				stmt.setRaw(collation);
			} else if(charset != null) {
				stmt.setRaw(charset);
			} else if(collation != null) {
				// no charset given, take second place
				stmt.setRaw(collation);
			}
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new SAXException("Database couldn't be created");
		}
	}

}
