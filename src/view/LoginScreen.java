package view;

import javax.swing.JButton;

/**
 * Screen f�r Login
 */
public class LoginScreen extends Screen {

	public LoginScreen(Display parent) {
		super(parent);
	}
	
	@Override
	public void build() {
		this.add(new JButton());
	}

	public Object getMainResult() {
		return null;
	}

}