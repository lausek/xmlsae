package view;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.RichConnection;

import view.Display.AppScreen;
import view.Display.MessageFatality;
import view.atoms.CPasswordField;
import view.atoms.CTextField;
import view.atoms.KeyHandler;
import view.atoms.KeyHandler.HandleTarget;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;

/**
 * A Screen child for login into a mysql server. If login is successful, this
 * object calls callback with a Connection object.
 * 
 * @author wn00086506
 *
 */
@SuppressWarnings("serial")
public class LoginScreen extends Screen {

	private static final int TF_WIDTH = 250;

	private CTextField tfUser, tfHost;
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

		KeyHandler keyHandler = new KeyHandler().handle(HandleTarget.TYPED, e -> {
			// getKeyCode doesn't seem to work on my keyboard sooo...
			if (e.getKeyChar() == '\n') {
				this.actionPerformed(null);
			}
		});

		setLayout(new BorderLayout(0, 0));

		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		verticalBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		verticalBox.add(Box.createVerticalGlue());

		JPanel connectionPanel = new JPanel();
		connectionPanel.setMaximumSize(new java.awt.Dimension(TF_WIDTH, 10));
		
		tfUser = new CTextField("user", "root");
		tfUser.setColumns(10);
		tfUser.addKeyListener(keyHandler);
		
		tfHost = new CTextField("host", "localhost");
		tfHost.setColumns(16);
		tfHost.addKeyListener(keyHandler);
		
		connectionPanel.add(tfUser);
		connectionPanel.add(new JLabel("@"));
		connectionPanel.add(tfHost);
		
		verticalBox.add(connectionPanel);
		verticalBox.add(Box.createVerticalStrut(20));
		
		JPanel passwordPanel = new JPanel();
		passwordPanel.setMaximumSize(new java.awt.Dimension(TF_WIDTH, 30));
		
		tfPassword = new CPasswordField("password");
		tfPassword.setColumns(29);
		tfPassword.addKeyListener(keyHandler);
	
		passwordPanel.add(tfPassword);
		verticalBox.add(passwordPanel);

		verticalBox.add(Box.createVerticalStrut(20));

		JButton btnLogin = new JButton("Login");
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.addActionListener(this);
		verticalBox.add(btnLogin);

		verticalBox.add(Box.createVerticalGlue());

		add(verticalBox);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// e could be null if this was called via an 'enter' hit
		try {
			// If password gets transferred into a String,
			// on could catch it out of the StringPool. We don't want that.
			char[] pw = tfPassword.getPassword();
			String hostString = tfUser.getText() + "@" + tfHost.getText();
			final RichConnection con = new RichConnection(hostString, pw);

			getStatusArea().setUsername(con.getHostString());

			// Security note from oracle
			Arrays.fill(pw, '0');

			callback.accept(con);
		} catch (SQLException e) {
			display.notice(MessageFatality.ERROR, "Connection couldn't be established", e.getMessage());
		}

	}

	@Override
	public void addNavbar(JPanel navbar) {
		// Screen would add StatusArea, but we don't want that on LoginScreen
	}

}