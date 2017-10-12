package view.atoms;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * Custom Text Field
 * 
 * JTextField with placeholder functionality. Subscribes to its own FocusListener
 * and switches between standard token in a gray color with user input in black.
 * 
 * Make sure not to return placeholder by overriding getText method with a color check. 
 * Also don't use local getter and setter for text as they would confuse the placeholder - use
 * super methods instead.
 * 
 * @author lausek
 *
 */

@SuppressWarnings("serial")
public class CTextField extends JTextField implements FocusListener {
	
	private final Color NORMAL_COLOR = Color.BLACK;
	private final Color PLACEHOLDER_COLOR = Color.GRAY;
	
	private String placeholder;
	
	public CTextField() {
		this("");
	}
	
	public CTextField(String placeholder) {
		addFocusListener(this);
		this.placeholder = placeholder;
		
		//Fields should be empty; placeholder is active
		placeholderOn();
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		//If we gained focus and are still displaying a placeholder, we can turn it off
		if(getForeground().equals(PLACEHOLDER_COLOR)) {
			placeholderOff();
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		//If the users hasn't entered anything and left, activate placeholder again
		if(getText().isEmpty()) {
			placeholderOn();
		}
	}
	
	@Override
	public String getText() {
		//If we are displaying a placeholder, return should be empty
		if(getForeground().equals(PLACEHOLDER_COLOR)) {
			return "";
		}
		return super.getText();
	}
	
	@Override
	public void setText(String t) {
		super.setText(t);
		//TODO: Test if this works correctly
		placeholderOff();
	}
	
	private void placeholderOn() {
		super.setForeground(PLACEHOLDER_COLOR);
		//Don't use this.setText!! Will deactivate placeholder again!
		super.setText(placeholder);
	}
	
	/**
	 * Placeholder is identified by color of the foreground.
	 * Make sure we don't reset any user input
	 */
	private void placeholderOff() {
		super.setForeground(NORMAL_COLOR);
		//Don't use this.setText!! Will deactivate placeholder again!
		super.setText("");
	}
	
}
