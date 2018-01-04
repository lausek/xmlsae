package control;

import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DTDEntityResolver implements EntityResolver {

	public final String dtdFile = "file:media/standard.dtd";
	
	private InputSource source;
	
	@Override
	public InputSource resolveEntity(String arg0, String arg1) throws SAXException, IOException {
		if(source == null) {
			source = new InputSource(dtdFile);
		}
		return source;
	}
	
}
