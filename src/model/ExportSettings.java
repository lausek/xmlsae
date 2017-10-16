package model;

public class ExportSettings {

	private boolean isDefinitionRequired;
	private boolean isDataRequired;

	/**
	 * 
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
	 * 
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