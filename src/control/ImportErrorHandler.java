package control;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class ImportErrorHandler implements ErrorHandler {
	
	@Override
	public void error(SAXParseException exception) throws SAXException {
		
	}
	
	@Override
	public void warning(SAXParseException exception) throws SAXException {
		
	}
	
	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		
	}
	
}