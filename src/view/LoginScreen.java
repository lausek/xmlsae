package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.function.Consumer;

import javax.swing.JButton;

import control.Connection;
import view.Display.EnumFatality;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginScreen extends Screen {
	
	private Consumer<Object> callback;
	private JTextField tfUser;
	private JTextField tfPassword;
	
	public LoginScreen(Display parent) {
		super(parent);
	}

	@Override
	public void build() {
		super.build();
		setLayout(null);
		
		tfUser = new JTextField();
		tfUser.setBounds(122, 65, 155, 20);
		add(tfUser);
		tfUser.setColumns(10);
		
		tfPassword = new JTextField();
		tfPassword.setColumns(10);
		tfPassword.setBounds(122, 96, 155, 20);
		add(tfPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Connection con = new Connection(tfUser.getText(), tfPassword.getText());
					callback.accept(con);
				} catch(SQLException e) {
					parent.notice(EnumFatality.ERROR, "Connection couldn't be established", e.getMessage());
				}
				
			}
		});
		btnLogin.setBounds(122, 138, 155, 23);
		add(btnLogin);
	}
	
	@Override
	public void getMainResult(Consumer<Object> action) {
		callback = action;
	}
}