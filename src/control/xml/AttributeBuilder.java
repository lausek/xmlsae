package control.xml;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class AttributeBuilder {

	protected String tagName, text;

	public static AttributeBuilder newTag(String tagName) {
		return new AttributeBuilder(tagName);
	}

	protected AttributeBuilder(String tagName) {
		this.tagName = tagName;
		this.text = "<" + tagName;
	}

	public AttributeBuilder set(String name, String value) {
		if (value != null) {
			text += " " + name + "='" + value + "'";
		}
		return this;
	}
	
	public AttributeBuilder append(String app) {
		text += app;
		return this;
	}
	
	@Override
	public String toString() {
		return text + ">";
	}
	
	public void writeTo(OutputStreamWriter writer) throws IOException {
		writer.append(this.toString());
	}

}
