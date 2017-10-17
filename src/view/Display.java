package view;

import javax.swing.JFrame;

import control.Control;

@SuppressWarnings("serial")
public class Display extends JFrame {

	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 500;

	public enum MessageFatality {
		INFO, SUCCESS, WARNING, ERROR
	}

	public enum AppScreen {
		NONE, LOGIN, SELECT_DB, SELECT_ACTION, IMPORT, EXPORT
	}

	public static final int screenCount = AppScreen.values().length;

	private Screen currentScreen;
	private Screen[] screens;
	private Control parent;

	public Display(Control parent) {
		this.parent = parent;

		screens = new Screen[screenCount];

		// TODO: allocate other screens here too
		screens[AppScreen.LOGIN.ordinal()] = new LoginScreen(this);
		screens[AppScreen.SELECT_DB.ordinal()] = new SelectionScreen(this);
		screens[AppScreen.SELECT_ACTION.ordinal()] = null;
		screens[AppScreen.IMPORT.ordinal()] = null;
		screens[AppScreen.EXPORT.ordinal()] = null;
		
		setTitle("xmlsae");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public Control getControl() {
		return parent;
	}

	public Screen getCurrentScreen() {
		return currentScreen;
	}

	/**
	 * 
	 * @param SCREEN
	 */
	public Screen setScreen(AppScreen screen) {
		Screen selected = screens[screen.ordinal()];

		AppScreen oldScreenId = AppScreen.NONE;

		if (currentScreen != null) {
			// Leave current screen for screen.ordinal()
			currentScreen.onLeave(screen);
			// Save screen id for next onEnter call
			oldScreenId = currentScreen.getScreenId();
		}

		currentScreen = selected;

		setContentPane(currentScreen);

		currentScreen.onEnter(oldScreenId);

		repaint();
		revalidate();

		return currentScreen;
	}

	/**
	 * 
	 * @param fatality
	 * @param message
	 */
	public void notice(MessageFatality fatality, String message) {
		notice(fatality, message, null);
	}

	/**
	 * 
	 * @param fatality
	 * @param message
	 * @param details
	 */
	public void notice(MessageFatality fatality, String message, String details) {
		MessageDialog.display(this, fatality, message, details);
	}

}