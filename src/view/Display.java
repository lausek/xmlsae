package view;

import javax.swing.JFrame;

import control.Control;

@SuppressWarnings("serial")
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
		
		screens = new Screen[screenCount];
		
		screens[EnumScreen.LOGIN.ordinal()] = new LoginScreen(this);
		screens[EnumScreen.SELECT_DB.ordinal()] = new SelectionScreen(this);
		screens[EnumScreen.SELECT_ACTION.ordinal()] = null;
		screens[EnumScreen.IMPORT.ordinal()] = null;
		screens[EnumScreen.EXPORT.ordinal()] = null;
		
		setTitle("xmlsae");
		setBounds(0, 0, 400, 260);
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
	public Screen setScreen(EnumScreen screen) {
		Screen selected = screens[screen.ordinal()];
		
		if(selected == null) {
			System.out.println("Desch jetzt blöd");
		}
		
		if(currentScreen != null) {
			currentScreen.onLeave();
		}
		
		currentScreen = selected;
		
		setContentPane(currentScreen);
		
		currentScreen.onEnter();
		
		repaint();
		revalidate();
		
		return currentScreen;
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