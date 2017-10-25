package model.database;

public class ViewInfo extends InfoEntity {
	
	public ViewInfo(String tagValue) {
		super(tagValue);
	}

	@Override
	public StringBuilder toXML() {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("<view "+attributesToString()+" >");
		//TODO: add CDATA[]?
		buffer.append(tagValue);
		buffer.append("</view>");
		
		return buffer;
	}
	
	@Override
	public String toSQL() {
		return null;
	}
	
}
