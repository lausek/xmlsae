package model;

import java.util.List;

import view.atoms.CSelectedFile;

public class ImportSettings extends Settings {
	
	private List<CSelectedFile> importFiles;
	
	public ImportSettings(List<String> databases) {
		this.databases = databases;
	}
	
	public void setFiles(List<CSelectedFile> importFiles) {
		this.importFiles = importFiles;
	}
	
	public List<CSelectedFile> getFiles() {
		return this.importFiles;
	}
	
}
