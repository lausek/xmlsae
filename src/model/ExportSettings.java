package model;

import java.io.File;
import java.util.List;

public class ExportSettings extends Settings {

	private File directory;
	
	public ExportSettings(List<String> databases) {
		this.databases = databases;
	}
	
	public void setDirectory(File directory) {
		this.directory = directory;
	}
	
	public File getDirectory() {
		return this.directory;
	}
	
}
