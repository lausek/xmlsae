package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import view.Display.MessageFatality;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MessageDialog extends JFrame {
	
	private JTextArea detailsArea;
	private JButton detailsButton;
	private JLabel messageLabel, iconLabel; 
	
	public MessageDialog() {

		JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(actionPanel, BorderLayout.SOUTH);

		detailsButton = new JButton("Details");
		detailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				detailsArea.setVisible(true);
			}
		});
		actionPanel.add(detailsButton);

		JButton confirmButton = new JButton("Ok");
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: close window here
			}
		});
		confirmButton.setHorizontalAlignment(SwingConstants.RIGHT);
		actionPanel.add(confirmButton);

		JPanel messagePanel = new JPanel();
		getContentPane().add(messagePanel, BorderLayout.CENTER);
		messagePanel.setLayout(null);

		iconLabel = new JLabel("New label");
		iconLabel.setBounds(10, 11, 65, 55);
		messagePanel.add(iconLabel);

		messageLabel = new JLabel("New label");
		messageLabel.setBounds(85, 11, 257, 55);
		messagePanel.add(messageLabel);

		detailsArea = new JTextArea();
		detailsArea.setBounds(10, 77, 351, 22);
		messagePanel.add(detailsArea);
	}
	
	public void display(MessageFatality fatality, String message, String details) {
		
		messageLabel.setText(message);
		
		detailsArea.setVisible(false);
		if(details == null) {
			detailsButton.setVisible(false);
		} else {
			detailsArea.setText(details);
		}
		
		switch(fatality) {
		case INFO:
			break;
		case SUCCESS:
			break;
		case WARNING:
			break;
		case ERROR:
			break;
		}
		
	}
	
}
