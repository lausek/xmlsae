package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;

import control.Connection;
import view.Display.AppScreen;
import view.Display.MessageFatality;
import view.atoms.CPasswordField;
import view.atoms.CTextField;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LoginScreen extends Screen implements ActionListener, KeyListener {

	private Consumer<Object> callback;
	private CTextField tfUser;
	private CPasswordField tfPassword;

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
		setLayout(null);

		tfUser = new CTextField("user@host...");
		tfUser.setBounds(147, 65, 155, 20);
		tfUser.setColumns(10);
		tfUser.addKeyListener(this);
		add(tfUser);

		tfPassword = new CPasswordField("password...");
		tfPassword.setColumns(10);
		tfPassword.setBounds(147, 96, 155, 20);
		tfPassword.addKeyListener(this);
		add(tfPassword);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(this);
		btnLogin.setBounds(147, 138, 155, 23);
		add(btnLogin);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 10, 10);
		add(panel);
	}

	@Override
	public void getMainResult(Consumer<Object> action) {
		callback = action;
	}

	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) {
		// getKeyCode doesn't seem to work on my keyboard soo...
		if (e.getKeyChar() == '\n') {
			this.actionPerformed(null);
		}
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