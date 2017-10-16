package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import view.Display.MessageFatality;

public class MessageDialog {

	private static final Dimension NORMAL_SIZE = new Dimension(400, 140);
	private static final Dimension EXTENDED_SIZE = new Dimension(400, 240);

	private static JFrame frame;
	private static JTextArea detailsArea;
	private static JScrollPane scrollDetails;
	private static JButton detailsButton;
	private static JLabel messageLabel, iconLabel;

	static {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setSize(NORMAL_SIZE);
		frame.setResizable(false);

		JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		frame.getContentPane().add(actionPanel, BorderLayout.SOUTH);

		detailsButton = new JButton("Details");
		detailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (scrollDetails.isVisible()) {
					frame.setSize(NORMAL_SIZE);
					scrollDetails.setVisible(false);
				} else {
					frame.setSize(EXTENDED_SIZE);
					scrollDetails.setVisible(true);
				}
			}
		});
		actionPanel.add(detailsButton);

		JButton confirmButton = new JButton("Ok");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
			}
		});
		confirmButton.setHorizontalAlignment(SwingConstants.RIGHT);
		actionPanel.add(confirmButton);

		JPanel messagePanel = new JPanel();
		frame.getContentPane().add(messagePanel, BorderLayout.CENTER);
		messagePanel.setLayout(null);

		iconLabel = new JLabel();
		iconLabel.setBounds(10, 11, 65, 55);
		messagePanel.add(iconLabel);

		messageLabel = new JLabel();
		messageLabel.setBounds(85, 11, 257, 55);
		messagePanel.add(messageLabel);

		detailsArea = new JTextArea();
		detailsArea.setEditable(false);
		detailsArea.setBounds(10, 77, 351, 22);

		scrollDetails = new JScrollPane(detailsArea);
		scrollDetails.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollDetails.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollDetails.setBounds(10, 60, EXTENDED_SIZE.width - 20, 100);
		scrollDetails.setVisible(false);
		messagePanel.add(scrollDetails);
	}

	public static void display(JFrame displayOn, MessageFatality fatality, String message, String details) {

		frame.setSize(NORMAL_SIZE);
		frame.setLocationRelativeTo(displayOn);

		messageLabel.setText(message);

		scrollDetails.setVisible(false);

		// if no details where given, hide the details button
		if (details == null) {
			detailsButton.setVisible(false);
		} else {
			detailsArea.setText(details);
		}

		Icon nextIcon;
		switch (fatality) {
		
		case SUCCESS:
			nextIcon = UIManager.getIcon("OptionPane.questionIcon");
			break;
		case WARNING:
			nextIcon = UIManager.getIcon("OptionPane.warningIcon");
			break;
		case ERROR:
			nextIcon = UIManager.getIcon("OptionPane.errorIcon");
			break;
		case INFO:
		default:
			nextIcon = UIManager.getIcon("OptionPane.informationIcon");
			break;
		}
		
		iconLabel.setIcon(nextIcon);

		frame.setVisible(true);

	}

}
