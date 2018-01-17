package control;

import java.util.Arrays;
import java.util.List;

import model.ColumnInfo;
import model.TableInfo;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class ImportContentHandler implements ContentHandler {
	
	private enum Tag {
		VERSION, DEFINITION, ENTRY, ENTRY_VALUE, VIEW_QUERY, NONE
	};
	
	private DatabaseImporter dbImporter;
	private List<String> generalBufferList;
	private TableInfo currentTable;
	private String version, generalBuffer;
	private Tag in;
	
	public ImportContentHandler(DatabaseImporter dbImporter) {
		this.dbImporter = dbImporter;
		generalBufferList = new java.util.ArrayList<>();
		generalBuffer = "";
		version = "";
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		switch(qName) {			
		case "database":
			dbImporter.createDatabase(atts.getValue("name"), atts);
			break;
			
		case "view":
			in = Tag.VIEW_QUERY;
			break;
			
		case "table":
			currentTable = new TableInfo(atts.getValue("name"), atts);
			break;
			
		case "data":
			break;
			
		case "definition":
			in = Tag.DEFINITION;
			break;
		
		case "column":
			currentTable.addColumn(new ColumnInfo(atts));
			break;
			
		case "entry":
			in = Tag.ENTRY;
			generalBufferList.clear();
			break;
			
		// Tag values only...
		case "version":
			in = Tag.VERSION;
			break;
		
		case "val":
			in = Tag.ENTRY_VALUE;
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("column")) {
			return;
		}
		
		switch(in) {
		case ENTRY_VALUE:
			generalBufferList.add(generalBuffer);
			generalBuffer = "";
			in = Tag.ENTRY;
			return;
			
		case ENTRY:
			dbImporter.insertInto(currentTable, generalBufferList);
			break;
		
		case DEFINITION:
			dbImporter.createTable(currentTable);
			break;
		
		case VIEW_QUERY:
			dbImporter.createView(generalBuffer);
			// fallthrough
		case VERSION:
//			generalBuffer = "";
			// fallthrough
		default:
			// do nothing
			break;
		}
		in = Tag.NONE;
	}
	
	@Override
	public void characters(char[] chars, int start, int len) throws SAXException {
		switch(in) {
		case VERSION:
			version += String.valueOf(Arrays.copyOfRange(chars, start, start+len));
			break;
		
		case VIEW_QUERY:
		case ENTRY_VALUE:
			// fallthrough
			generalBuffer += String.valueOf(Arrays.copyOfRange(chars, start, start+len));
			break;
		
		default:
			// do nothing
			break;
		}
	}

	@Override
	public void startDocument() throws SAXException {
		
	}
	
	@Override
	public void endDocument() throws SAXException {
		
	}
	
	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		
	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
		
	}

	@Override
	public void processingInstruction(String arg0, String arg1) throws SAXException {
		
	}

	@Override
	public void setDocumentLocator(Locator arg0) {
		
	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {
		
	}

	@Override
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
		
	}

}
