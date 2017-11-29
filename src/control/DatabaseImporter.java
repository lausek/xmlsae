package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import model.ProcessSettings;

public class DatabaseImporter {

	private ProcessSettings settings;

	public DatabaseImporter(ProcessSettings settings) {
		this.settings = settings;
	}

	public void start() throws IOException {
		for (String dbfile : settings.getSelectedFiles()) {

			File file = new File(dbfile);

			try (InputStream stream = new FileInputStream(file)) {

				// TODO: implement parser and validator

			}
		}
	}

}
