package control;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import model.ExportSettings;

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
	public void exportTo(String dbname, String filename, ExportSettings settings) {
		logger.debug("Start export to "+dbname);
		// tables := SHOW TABLES 

		// foreach table
		// 		if isDefinitionRequired
		// 			show columns for
		// 		endif

		// 		if isDataRequired
		// 			SELECT * FROM
		// 		endif

	}

	/**
	 * exports more than 1 database
	 * 
	 * @param dbnames
	 * @param filename
	 * @param settings
	 */
	public void exportTo(List<String> dbnames, String filename, ExportSettings settings) {
		for (String dbname : dbnames) {
			String[] split = filename.split("\\.");
			split[0] += "_" + dbname;
			String newFileName = String.join(".", split);

			exportTo(dbname, newFileName, settings);

			logger.debug("Exported " + dbname + " on file " + newFileName);
		}
	}

	/**
	 * imports database by mysql
	 * 
	 * @param dbname
	 * @param filename
	 */
	public void importTo(String dbname, String filename) {
		logger.debug("Start import to "+dbname);
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

	/**
	 * validate given xml
	 * @param xml
	 * @return boolean
	 * @throws ParserConfigurationException
	 * @throws IOException
	 */
	public static boolean validateWithDTDUsingSAX(String xml) throws ParserConfigurationException, IOException{
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			factory.setNamespaceAware(true);
			XMLReader reader = factory.newSAXParser().getXMLReader();
			reader.setErrorHandler(
					new ErrorHandler() {
						@Override
						public void error(SAXParseException e) throws SAXException {
							logger.error("ERROR : " + e.getMessage(),e);
						}

						@Override
						public void fatalError(SAXParseException e) throws SAXException {
							logger.error("FATAL : " + e.getMessage(),e);
						}

						@Override
						public void warning(SAXParseException e) throws SAXException {
							logger.error("WARNING : " + e.getMessage(),e);
						}
					}
					);
			reader.parse(new InputSource(xml));
			logger.debug("Validating successfull");
			return true;
		} catch (ParserConfigurationException pce) {
			logger.error(pce.getMessage(), pce);
			throw pce;
		} catch (IOException io) {
			logger.error(io.getMessage(), io);
			throw io;
		} catch (SAXException se){
			logger.error(se.getMessage(), se);
			return false;
		}
	}



}
