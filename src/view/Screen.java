package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JPanel;

/**
 * Screens get managed by the Display class. Each screen has its own 
 * main functionality like 'log user in' or 'select database from server'.
 * 
 * ActionPerformed is meant to handle button clicks of child components.
 * TODO: Check if ActionPerformed is needed at all.
 * 
 * @author lausek
 *
 */
@SuppressWarnings("serial")
public class Screen extends JPanel implements ActionListener {

	protected Display parent;
	
	public Screen(Display parent) {
		this.build();
		this.parent = parent;
	}
	
	/**
	 * Append JComponents to Screen in this method
	 */
	public void build() { }
	
	/**
	 * Called when screen gets entered from another screen
	 */
	public void onEnter() { }
	
	/**
	 * Called when screen is left for another one
	 */
	public void onLeave() { }

	/**
	 * Override in subclasses
	 */
	public void getMainResult(Consumer<Object> action) { }
	
	/**
	 * Handle events of child components
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) { }

}