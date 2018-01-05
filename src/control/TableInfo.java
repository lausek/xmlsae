package control;

import java.util.List;

import org.xml.sax.Attributes;

public class TableInfo {
	
	private String name;
	private Attributes atts;
	private List<Attributes> columns;
	
	public TableInfo(String name, Attributes atts) {
		this.name = name;
		this.atts = atts;
		this.columns = new java.util.ArrayList<>();
	}
	
	public void addColumn(Attributes atts) {
		this.columns.add(atts);
	}
	
	public String getName() {
		return this.name;
	}
}
