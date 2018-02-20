package control.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.owasp.esapi.ESAPI;

import control.DatabaseActor;
import control.RichConnection;
import model.ColumnInfo;
import model.ExportSettings;
import model.TableInfo;

// TODO: Add constants for column index
public class DatabaseExporter {

	public final String XML_SIGNATURE = "<?xml version=\"1.0\" encoding=\"utf-8\"?><!DOCTYPE file SYSTEM \"media/standard.dtd\">";

	private ExportSettings settings;

	public DatabaseExporter(ExportSettings settings) {
		this.settings = settings;
	}

	public String start() {
		String protocol = "";

		for (String db : settings.getDatabases()) {

			File file = new File(settings.getDirectory().getAbsolutePath()
					+ "/" + db + ".xml");

			try (OutputStream stream = new FileOutputStream(file)) {

				OutputStreamWriter writer = new OutputStreamWriter(stream,
						"UTF-8");

				write(writer, XML_SIGNATURE);

				// add temporary version
				write(writer, "<file><meta><version>1.0</version></meta>");

				DatabaseActor.getConnection().setCatalog(db);

				wrapDatabase(writer, db);

				write(writer, "</file>");

				writer.close();

				protocol += "✓ " + db + "\n";

			} catch (SQLException | IOException e) {

				protocol += "✗ " + db + ": " + e.getMessage() + "\n";

			}
		}

		return protocol;
	}

	private void write(OutputStreamWriter writer, String db) throws IOException {
		writer.append(db);
	}

	private String escape(String val) {
		return ESAPI.encoder().encodeForXML(val);
	}

	public void wrapDatabase(OutputStreamWriter writer, String db)
			throws IOException, SQLException {

		RichConnection con = DatabaseActor.getConnection();

		Statement stat = con.newStatement();
		stat.executeQuery("SELECT @@character_set_database, @@collation_database");

		ResultSet result = stat.getResultSet();

		if (!result.next()) {
			// TODO: add description here
			throw new SQLException();
		}

		AttributeBuilder.newTag("database").set("name", db)
				.set("charset", result.getString(1))
				.set("collation", result.getString(2)).writeTo(writer);

		stat.executeQuery("SHOW FULL TABLES WHERE TABLE_TYPE NOT LIKE 'VIEW'");
		result = stat.getResultSet();
		while (result.next()) {
			wrapTable(writer, result.getString(1));
		}

		stat.executeQuery("SHOW FULL TABLES IN " + db
				+ " WHERE TABLE_TYPE LIKE 'VIEW'");

		result = stat.getResultSet();
		while (result.next()) {
			wrapView(writer, result.getString(1));
		}

		write(writer, "</database>");

	}

	public void wrapTable(OutputStreamWriter writer, String table)
			throws IOException, SQLException {
		TableInfo info = new TableInfo(table);

		AttributeBuilder.newTag("table").set("name", table)
				.set("collation", info.getCollation()).writeTo(writer);

		if (settings.isDefinitionRequired()) {

			write(writer, "<definition>");

			for (ColumnInfo column : info.getColumns()) {
				write(writer, column.toString());
			}

			write(writer, "</definition>");

		}

		if (settings.isDataRequired()) {
			
 			ResultSet result = DatabaseActor.getConnection().newStatement().executeQuery("SELECT * FROM " + table);
 			result.next();
			int columns = result.getMetaData().getColumnCount();

			write(writer, "<data>");

			while (result.next()) {
				write(writer, wrapEntry(result, columns));
			}

			write(writer, "</data>");

		}

		write(writer, "</table>");

	}

	public void wrapView(OutputStreamWriter writer, String view)
			throws IOException, SQLException {
		RichConnection con = DatabaseActor.getConnection();

		Statement stat = con.newStatement();
		stat.executeQuery("SHOW CREATE VIEW " + view);

		ResultSet result = stat.getResultSet();

		if (!result.next()) {
			// TODO: add description here
			throw new SQLException();
		}

		AttributeBuilder.newTag("view").set("name", view).writeTo(writer);

		write(writer, escape(result.getString(2)));

		write(writer, "</view>");
	}
	
	public String wrapEntry(ResultSet result, int columns) throws SQLException {
		String buffer = "<entry>";

		for (int i = 1; i <= columns; i++) {
			buffer += "<val>" + escape(result.getString(i)) + "</val>";
		}

		return buffer + "</entry>";
	}

}
