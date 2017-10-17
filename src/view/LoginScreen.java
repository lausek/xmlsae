package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;

import control.Connection;

import view.Display.AppScreen;
import view.Display.MessageFatality;
import view.atoms.CPasswordField;
import view.atoms.CTextField;
import view.atoms.KeyHandler;
import view.atoms.KeyHandler.HandleTarget;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;

@SuppressWarnings("serial")
public class LoginScreen extends Screen implements ActionListener {

	private Consumer<Object> callback;
	private CTextField tfUser;
	private CPasswordField tfPassword;
	private Component placeholder1;
	private Component placeholder2;

	public LoginScreen(Display parent) {
		super(parent);
	}

	@Override
	public AppScreen getScreenId() {
		return AppScreen.LOGIN;
	}

	@Override
	public void build() {
		super.build();

		KeyHandler keyHandler = new KeyHandler().handle(HandleTarget.TYPED, e -> {
			// getKeyCode doesn't seem to work on my keyboard sooo...
			if (e.getKeyChar() == '\n') {
				this.actionPerformed(null);
			}
		});

		tfUser = new CTextField("user@host...");
		tfUser.setColumns(16);
		tfUser.addKeyListener(keyHandler);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(tfUser);
		
		tfPassword = new CPasswordField("password...");
		tfPassword.setColumns(16);
		tfPassword.addKeyListener(keyHandler);
		
		placeholder1 = Box.createVerticalStrut(20);
		add(placeholder1);
		add(tfPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.addActionListener(this);
		
		placeholder2 = Box.createVerticalStrut(20);
		add(placeholder2);
		add(btnLogin);
	}

	@Override
	public void getMainResult(Consumer<Object> action) {
		callback = action;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// e could be null if this was called via an 'enter' hit
		try {
			String pw = new String(tfPassword.getPassword());
			final Connection con = new Connection(tfUser.getText(), pw);
			callback.accept(con);
		} catch (SQLException e) {
			display.notice(MessageFatality.ERROR, "Connection couldn't be established", e.getMessage());
		}

	}

}