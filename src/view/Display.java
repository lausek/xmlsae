package view;

import javax.swing.JFrame;

import control.Control;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

@SuppressWarnings("serial")
public class Display extends JFrame {

	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 400;

	public enum MessageFatality {
		INFO, SUCCESS, WARNING, ERROR
	}

	public enum AppScreen {
		NONE, LOGIN, SELECT_DB, SELECT_ACTION, IMPORT, EXPORT
	}

	public static final int screenCount = AppScreen.values().length;
	
	private JPanel navbar, mainPanel, toolbar;
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
		
		getContentPane().setLayout(new java.awt.BorderLayout(0, 0));
		
		navbar = new JPanel();
		getContentPane().add(navbar, BorderLayout.NORTH);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.GREEN);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		toolbar = new JPanel();
		getContentPane().add(toolbar, BorderLayout.SOUTH);
		
		setTitle("xmlsae");
		setMinimumSize(new java.awt.Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
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
		
		// Clean every frame panel
		navbar.removeAll();
		toolbar.removeAll();
		mainPanel.removeAll();
		
		mainPanel.add(currentScreen);

		currentScreen.addNavbar(navbar);
		currentScreen.addToolbar(toolbar);
		
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