package model;

import java.util.List;

/**
 * Class for customizing export process.
 * 
 * @author lausek
 *
 */
public abstract class Settings {
	
	protected List<String> databases;
	protected boolean isDefinitionRequired;
	protected boolean isDataRequired;
	
	public Settings() {
		this.databases = new java.util.ArrayList<>();
	}
	
	public Settings(List<String> databases) {
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