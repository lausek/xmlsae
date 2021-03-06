package control.xml;

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

import control.DatabaseActor;
import control.RichStatement;
import model.ImportSettings;
import model.TableInfo;
import view.atoms.CSelectedFile;

public class DatabaseImporter {

	private ImportSettings settings;

	public DatabaseImporter(ImportSettings settings) {
		this.settings = settings;
	}

	public String start() {
		String protocol = "";
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		parserFactory.setValidating(true);

		for (CSelectedFile file : settings.getFiles()) {
			try (InputStream stream = new FileInputStream(file.get())) {

				SAXParser parser = parserFactory.newSAXParser();
				XMLReader reader = parser.getXMLReader();
				reader.setErrorHandler(new ImportErrorHandler());
				reader.setContentHandler(new ImportContentHandler(this));

				reader.parse(new InputSource(stream));

				protocol += "✓ " + file.get().getName() + "\n";

			} catch (IOException | ParserConfigurationException | SAXException e) {

				protocol += "✗ " + file.get().getName() + ": " + e.getMessage()
						+ "\n";

			}
		}

		return protocol;
	}

	public void insertInto(TableInfo table, List<String> entry)
			throws SAXException {
		try {
			RichStatement stmt = table.getInsertStatement();
			// first ? is table name
			stmt.setRaw(table.getName());
			for (String val : entry) {
				stmt.setString(val);
			}
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO: skip duplicates
			if (!e.getMessage().contains("Duplicate")) {
				throw new SAXException("Couldn't insert: " + e.getMessage());
			}
		}
	}

	public void createTable(TableInfo table) throws SAXException {
		try {

			table.getCreateStatement().executeUpdate();

		} catch (SQLException e) {
			throw new SAXException("Table couldn't be created: "
					+ e.getMessage());
		}
	}

	public void createView(String query) throws SAXException {
		try {

			RichStatement stmt = DatabaseActor.getConnection()
					.newRichStatement(query);

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new SAXException("View couldn't be created: "
					+ e.getMessage());
		}
	}

	public void createDatabase(String name, Attributes atts)
			throws SAXException {
		String collation = atts.getValue("collation");
		String charset = atts.getValue("charset");
		try {
			String query = "CREATE DATABASE IF NOT EXISTS ?";

			if (charset != null) {
				query += " CHARACTER SET ?";
			}

			if (collation != null) {
				query += " COLLATE ?";
			}

			RichStatement stmt = DatabaseActor.getConnection()
					.newRichStatement(query);
			stmt.setRaw(name);

			if (collation != null && charset != null) {
				stmt.setRaw(charset);
				stmt.setRaw(collation);
			} else if (charset != null) {
				stmt.setRaw(charset);
			} else if (collation != null) {
				// no charset given, take second place
				stmt.setRaw(collation);
			}

			stmt.executeUpdate();

			DatabaseActor.getConnection().setCatalog(name);

		} catch (SQLException e) {
			throw new SAXException("Database couldn't be created");
		}
	}

}
