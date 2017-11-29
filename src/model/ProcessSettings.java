package model;

import java.util.List;

/**
 * Class for customizing export process.
 * 
 * @author lausek
 *
 */
public class ProcessSettings {
	
	private List<String> databases;
	private boolean isDefinitionRequired;
	private boolean isDataRequired;
	
	public ProcessSettings() {
		this.databases = new java.util.ArrayList<>();
	}
	
	public ProcessSettings(List<String> databases) {
		this.databases = databases;
	}
	
	/**
	 * Do we need to export definitions? (Tables, Views, Procedures, Triggers...)
	 * @return
	 */
	public boolean isDefinitionRequired() {
		return this.isDefinitionRequired;
	}

	/**
	 * 
	 * @param isDefinitionRequired
	 */
	public void setDefinitionRequired(boolean isDefinitionRequired) {
		this.isDefinitionRequired = isDefinitionRequired;
	}

	/**
	 * Do we need to export data? (Tables only)
	 * @return
	 */
	public boolean isDataRequired() {
		return this.isDataRequired;
	}

	/**
	 * 
	 * @param isDataRequired
	 */
	public void setDataRequired(boolean isDataRequired) {
		this.isDataRequired = isDataRequired;
	}
	
	public List<String> getDatabases() {
		return this.databases;
	}
	
}