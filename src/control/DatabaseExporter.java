package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.text.StringEscapeUtils;

import model.ExportSettings;

// TODO: Add constants for column index
// TODO: Escape string to prevent confusion with xml 
public class DatabaseExporter {
	
	public final String XML_SIGNATURE = "<?xml version=\"1.0\"?><!DOCTYPE file SYSTEM \"media/standard.dtd\">";
	
	private ExportSettings settings;

	public DatabaseExporter(ExportSettings settings) {
		this.settings = settings;
	}

	public void start() throws IOException, SQLException {
		for (String db : settings.getDatabases()) {

			File file = new File(settings.getDirectory().getAbsolutePath() + "/" + db + ".xml");

			try (OutputStream stream = new FileOutputStream(file)) {
				
				OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
				
				write(writer, XML_SIGNATURE);
				
				// add temporary version				
				write(writer, "<file><meta><version>1.0</version></meta>");

				DatabaseActor.getConnection().setCatalog(db);

				wrapDatabase(writer, db);

				write(writer, "</file>");
				
				writer.close();
				
			}
		}
	}

	private void write(OutputStreamWriter writer, String db) throws IOException {
		writer.append(db);
	}

	private String escape(String val) {
		return StringEscapeUtils.escapeXml10(val);
	}

	public void wrapDatabase(OutputStreamWriter writer, String db) throws IOException {

		RichConnection con = DatabaseActor.getConnection();

		try {
			Statement stat = con.newStatement();
			stat.executeQuery("SELECT @@character_set_database, @@collation_database");

			ResultSet result = stat.getResultSet();

			if (!result.next()) {
				// TODO: add description here
				throw new SQLException();
			}

			write(writer, "<database collation='" + result.getString(1) + "' charset='" + result.getString(2) + "'>");

			stat.executeQuery("SHOW TABLES");
			result = stat.getResultSet();
			while (result.next()) {
				wrapTable(writer, result.getString(1));
			}

			stat.executeQuery("SHOW FULL TABLES IN " + db + " WHERE TABLE_TYPE LIKE 'VIEW'");
			result = stat.getResultSet();
			while (result.next()) {
				wrapView(writer, result.getString(1));
			}

		} catch (SQLException e) {
			// TODO: Add Logger here
			e.printStackTrace();
		}

		write(writer, "</database>");

	}

	public void wrapTable(OutputStreamWriter writer, String table) throws IOException, SQLException {
		RichConnection con = DatabaseActor.getConnection();
		Statement stat = con.newStatement();
		ResultSet result;

		stat.executeQuery("SHOW TABLE STATUS LIKE '" + table + "'");
		result = stat.getResultSet();
		if (result.next()) {

			// TODO: remove collation if column 15 is null
			write(writer, "<table name='" + table + "' collation='" + result.getString(15) + "'>");

			if (settings.isDefinitionRequired()) {

				stat.executeQuery("SHOW COLUMNS FROM " + table);
				result = stat.getResultSet();

				// TODO: check if query was successful

				write(writer, "<definition>");

				while (result.next()) {
					write(writer, wrapColumn(result));
				}

				write(writer, "</definition>");

			}

			if (settings.isDataRequired()) {

				stat.executeQuery("SELECT * FROM " + table);
				result = stat.getResultSet();
				int columns = result.getMetaData().getColumnCount();

				write(writer, "<data>");

				while (result.next()) {
					write(writer, wrapEntry(result, columns));
				}

				write(writer, "</data>");

			}

			write(writer, "</table>");

		}
	}

	public void wrapView(OutputStreamWriter writer, String view) throws IOException, SQLException {
		RichConnection con = DatabaseActor.getConnection();

		Statement stat = con.newStatement();
		stat.executeQuery("SHOW CREATE VIEW " + view);

		ResultSet result = stat.getResultSet();

		if (!result.next()) {
			// TODO: add description here
			throw new SQLException();
		}

		write(writer, "<view name='" + view + "'>");

		write(writer, escape(result.getString(2)));

		write(writer, "</view>");
	}

	public String wrapColumn(ResultSet result) throws SQLException {
		return "<column name='" + result.getString(1) + "' type='" + result.getString(2) + "' key='"
				+ result.getString(3) + "' default='" + result.getString(5) + "' null='" + result.getString(3)
				+ "' extra='" + result.getString(6) + "' />";
	}

	public String wrapEntry(ResultSet result, int columns) throws SQLException {
		String buffer = "<entry>";

		for (int i = 1; i <= columns; i++) {
			buffer += "<val>" + escape(result.getString(i)) + "</val>";
		}

		return buffer + "</entry>";
	}

}
