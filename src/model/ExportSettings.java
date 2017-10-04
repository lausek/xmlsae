package model;
public class ExportSettings {

	private boolean isDefinitionRequired;
	private boolean isDataRequired;
	
	/**
	 * 
	 * @return
	 */
	public boolean getIsDefinitionRequired() {
		return this.isDefinitionRequired;
	}

	/**
	 * 
	 * @param isDefinitionRequired
	 */
	public void setIsDefinitionRequired(boolean isDefinitionRequired) {
		this.isDefinitionRequired = isDefinitionRequired;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean getIsDataRequired() {
		return this.isDataRequired;
	}

	/**
	 * 
	 * @param isDataRequired
	 */
	public void setIsDataRequired(boolean isDataRequired) {
		this.isDataRequired = isDataRequired;
	}

}