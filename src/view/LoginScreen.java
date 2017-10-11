package view;

import java.util.function.Consumer;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class LoginScreen extends Screen {

	public LoginScreen(Display parent) {
		super(parent);
	}
	
	@Override
	public void build() {
		this.add(new JButton());
	}
	
	@Override
	public void getMainResult(Consumer<Object> action) {
		
	}

}