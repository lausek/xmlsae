package view;

import javax.swing.JFrame;

import control.Control;

/**
 * Screen ist eine enum
 */
public class Display extends JFrame {
	
	public enum EnumFatality {
		INFO, SUCCESS, WARNING, ERROR
	}
	
	public enum EnumScreen {
		LOGIN, SELECT_DB, SELECT_ACTION,
		IMPORT, EXPORT
	}
	
	public static final int screenCount = EnumScreen.values().length;
	
	private Screen currentScreen;
	private Screen[] screens;
	private Control parent;

	public Display(Control parent) {
		this.parent = parent;
		
		screens = new Screen[screenCount-1];
		
		// TODO: build panels
		screens[EnumScreen.LOGIN.ordinal()] = new LoginScreen(this);
		
		setTitle("Geiles Programm");
		setBounds(0, 0, 800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public Screen getCurrentScreen() {
		return currentScreen;
	}
	
	/**
	 * 
	 * @param SCREEN
	 */
	public void setScreen(EnumScreen login) {
		Screen selected = screens[login.ordinal()];
		
		if(selected == null) {
			System.out.println("Desch jetzt blöd");
		}
		
		if(currentScreen != null) {
			currentScreen.onLeave();
		}
		
		currentScreen = selected;
		
		setContentPane(currentScreen);
		currentScreen.onEnter();
	}

	/**
	 * 
	 * @param fatality
	 * @param message
	 */
	public void notice(EnumFatality fatality, String message) {
		notice(fatality, message, null);
	}

	/**
	 * 
	 * @param fatality
	 * @param message
	 * @param details
	 */
	public void notice(EnumFatality fatality, String message, String details) {
		// TODO - implement Display.notice
		throw new UnsupportedOperationException();
	}

}