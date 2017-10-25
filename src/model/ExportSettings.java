package model;

/**
 * Class for customizing export process.
 * 
 * @author lausek
 *
 */
public class ExportSettings {

	private boolean isDefinitionRequired;
	private boolean isDataRequired;

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

}