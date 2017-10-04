package view;
import java.util.List;

import control.Control;

/**
 * Screen ist eine enum
 */
public class Display {
	
	public enum EnumScreen {
		LOGIN, SELECT_DB, SELECT_ACTION,
		IMPORT, EXPORT
	}
	
	private List<Screen> screens;
	private Control parent;

	public Display() {
		// TODO - implement Display.Display
		// TODO: Create JFrame
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param SCREEN
	 */
	public void setScreen(EnumScreen screen) {
		// TODO - implement Display.setScreen
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param fatality
	 * @param message
	 */
	public void notice(int fatality, String message) {
		// TODO - implement Display.notice
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param fatality
	 * @param message
	 * @param details
	 */
	public void notice(int fatality, String message, String details) {
		// TODO - implement Display.notice
		throw new UnsupportedOperationException();
	}

}