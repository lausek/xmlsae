package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JPanel;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import view.Display.AppScreen;

/**
 * Screens get managed by the Display class. Each screen has its own main
 * functionality like 'log user in' or 'select database from server'.
 * 
 * ActionPerformed is meant to handle button clicks of child components. TODO:
 * Check if ActionPerformed is needed at all.
 * 
 * @author lausek
 *
 */
@SuppressWarnings("serial")
public class Screen extends JPanel implements ActionListener {

	protected Display display;

	public Screen(Display display) {
		this.display = display;
		this.build();
	}
	
	/**
	 * Get screen identificator. Required by onEnter and onLeave method 
	 * to do conditional cleaning.
	 * @return
	 */
	public AppScreen getScreenId() {
		throw new NotImplementedException();
	}

	/**
	 * Append JComponents to Screen in this method
	 */
	public void build() {
	}
	
	/**
	 * Add navigation icons to main frame
	 */
	public void addNavbar(JPanel navbar) {
	}
	
	/**
	 * Add buttons to bottom of main frame
	 */
	public void addToolbar(JPanel toolbar) {
	}
	
	/**
	 * Called when screen gets entered from another screen
	 */
	public void onEnter(AppScreen from) {
	}

	/**
	 * Called when screen is left for another one
	 */
	public void onLeave(AppScreen to) {
	}

	/**
	 * Override in subclasses
	 */
	public void setCallback(Consumer<Object> action) {
	}

	/**
	 * Handle events of child components
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

}