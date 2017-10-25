package view;

import javax.swing.JFrame;

import control.Control;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.Box;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

/**
 * An object for managing Screens. The currently displayed screen can be changed
 * by calling setScreen with a AppScreen id.
 * 
 * @author lausek
 *
 */
@SuppressWarnings("serial")
public class Display extends JFrame {

	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 400;
	public static final int SCREEN_COUNT = AppScreen.values().length;

	private static Image appIcon = null;

	static {
		try {
			appIcon = javax.imageio.ImageIO.read(new File("media/img/app_128x128.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public enum MessageFatality {
		INFO, SUCCESS, WARNING, ERROR
	}

	public enum AppScreen {
		NONE, LOGIN, SELECT_DB, SELECT_ACTION, IMPORT, EXPORT
	}

	private JPanel navbar, mainPanel, toolbar;
	private Screen currentScreen;
	private Screen[] screens;
	private Control parent;

	public static Image getAppIcon() {
		return appIcon;
	}

	public Display(Control parent) {
		this.parent = parent;

		screens = new Screen[SCREEN_COUNT];

		// TODO: allocate other screens here too
		screens[AppScreen.LOGIN.ordinal()] = new LoginScreen(this);
		screens[AppScreen.SELECT_DB.ordinal()] = new SelectionScreen(this);
		screens[AppScreen.SELECT_ACTION.ordinal()] = new ActionScreen(this);
		screens[AppScreen.IMPORT.ordinal()] = new ImportScreen(this);
		screens[AppScreen.EXPORT.ordinal()] = new ExportScreen(this);

		getContentPane().setLayout(new java.awt.BorderLayout(0, 0));

		navbar = new JPanel();
		navbar.setLayout(new java.awt.BorderLayout(0, 0));
		getContentPane().add(navbar, BorderLayout.NORTH);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(0, 0));

		// Add placeholders to center main
		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)), BorderLayout.SOUTH);
		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)), BorderLayout.NORTH);
		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)), BorderLayout.WEST);
		mainPanel.add(Box.createRigidArea(new Dimension(20, 20)), BorderLayout.EAST);

		getContentPane().add(mainPanel, BorderLayout.CENTER);

		toolbar = new JPanel();
		getContentPane().add(toolbar, BorderLayout.SOUTH);

		setTitle("xmlsae");
		setMinimumSize(new java.awt.Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setIconImage(appIcon);

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

			// Remove old center component
			BorderLayout layout = (BorderLayout) mainPanel.getLayout();
			mainPanel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
		}

		// Clean every frame panel
		navbar.removeAll();
		toolbar.removeAll();

		// Set new center component, old one will be removed in if above
		mainPanel.add(BorderLayout.CENTER, selected);

		selected.addNavbar(navbar);
		selected.addToolbar(toolbar);

		selected.onEnter(oldScreenId);

		repaint();
		revalidate();

		currentScreen = selected;

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