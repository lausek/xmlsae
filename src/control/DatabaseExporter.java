package control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.ProcessSettings;

// TODO: Add constants for column index
public class DatabaseExporter {

	private ProcessSettings settings;

	public DatabaseExporter(ProcessSettings settings) {
		this.settings = settings;
	}

	public void start() throws IOException, SQLException {
		for (String db : settings.getDatabases()) {

			File file = new File(settings.getDirectory().getAbsolutePath() + "/" + db + ".xml");

			try (OutputStream stream = new FileOutputStream(file)) {
				
				// add temporary version
				write(stream, "<meta><version>1.0</version></meta>");
				
				DatabaseActor.getConnection().setCatalog(db);
				
				wrapDatabase(stream, db);

			}
		}
	}

	private void write(OutputStream stream, String db) throws IOException {
		stream.write(db.getBytes());
	}

	public void wrapDatabase(OutputStream stream, String db) throws IOException {

		RichConnection con = DatabaseActor.getConnection();

		try {
			Statement stat = con.newStatement();
			stat.executeQuery("SELECT @@character_set_database, @@collation_database");

			ResultSet result = stat.getResultSet();

			if (!result.next()) {
				// TODO: add description here
				throw new SQLException();
			}

			write(stream, "<database collation='" + result.getString(1) + "' charset='" + result.getString(2) + "'>");

			stat.executeQuery("SHOW TABLES");
			result = stat.getResultSet();
			while (result.next()) {
				wrapTable(stream, result.getString(1));
			}

			stat.executeQuery("SHOW FULL TABLES IN " + db + " WHERE TABLE_TYPE LIKE 'VIEW'");
			result = stat.getResultSet();
			while (result.next()) {
				wrapView(stream, result.getString(1));
			}

		} catch (SQLException e) {
			// TODO: Add Logger here
			e.printStackTrace();
		}

		write(stream, "</database>");

	}

	public void wrapTable(OutputStream stream, String table) throws IOException, SQLException {
		RichConnection con = DatabaseActor.getConnection();
		Statement stat = con.newStatement();
		ResultSet result;

		stat.executeQuery("SHOW TABLE STATUS LIKE '" + table + "'");
		result = stat.getResultSet();
		if (result.next()) {
			
			write(stream, "<table name='" + table + "' collation='" + result.getString(15) + "'>");

			if (settings.isDefinitionRequired()) {

				stat.executeQuery("SHOW COLUMNS FROM " + table);
				result = stat.getResultSet();

				// TODO: check if query was successful

				write(stream, "<definition>");

				while (result.next()) {
					write(stream, wrapColumn(result));
				}

				write(stream, "</definition>");

			}

			if (settings.isDataRequired()) {

			}

			write(stream, "</table>");
			
		}
	}

	public void wrapView(OutputStream stream, String view) throws IOException, SQLException {
		RichConnection con = DatabaseActor.getConnection();

		Statement stat = con.newStatement();
		stat.executeQuery("SHOW CREATE VIEW " + view);

		ResultSet result = stat.getResultSet();

		if (!result.next()) {
			// TODO: add description here
			throw new SQLException();
		}

		write(stream, "<view name='" + view + "'>");

		write(stream, "<![CDATA[" + result.getString(2) + "]]>");

		write(stream, "</view>");
	}

	public String wrapColumn(ResultSet result) throws SQLException {
		return "<column " + "name='" + result.getString(1) + "'" + "type='" + result.getString(2) + "'" + "key='"
				+ result.getString(3) + "'" + "default='" + result.getString(5) + "'" + "null='" + result.getString(3)
				+ "'" + "extra='" + result.getString(6) + "' />";
	}

}
