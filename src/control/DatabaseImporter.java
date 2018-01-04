package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import model.ProcessSettings;

public class DatabaseImporter {

	private ProcessSettings settings;

	public DatabaseImporter(ProcessSettings settings) {
		this.settings = settings;
	}

	public void start() throws IOException {
		for (String dbfile : settings.getDatabases()) {

			File file = new File(dbfile);

			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setValidating(true);
//			parserFactory.setSchema(schema);
			
			try (InputStream stream = new FileInputStream(file)) {
				
				try {
					SAXParser parser = parserFactory.newSAXParser();
					XMLReader reader = parser.getXMLReader();
					reader.setEntityResolver(new DTDEntityResolver());
					
					reader.parse(new InputSource(stream));
					
				} catch (ParserConfigurationException | SAXException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
