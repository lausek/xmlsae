package view;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;

/**
 * 
 * General display object for holding the currently established connection 
 * and which databases have been selected by the user. This object will 
 * be automatically added to Screen in addNavbar method.
 * 
 * @author lausek
 *
 */
@SuppressWarnings("serial")
public class StatusArea extends JPanel {
	
	private static final String DB_DEFAULT = "-";
	private static final Dimension MAXIMUM_SIZE = new Dimension(300, 20);
	
	private JLabel lbUsername, lbDatabases;
	
	public StatusArea() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{1, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[] {1, 0, 1, 1, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		Component horizontalStrutLeft = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrutLeft = new GridBagConstraints();
		gbc_horizontalStrutLeft.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrutLeft.gridx = 0;
		gbc_horizontalStrutLeft.gridy = 0;
		add(horizontalStrutLeft, gbc_horizontalStrutLeft);
		
		JLabel lblUsername = new JLabel("Connected:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 1;
		add(lblUsername, gbc_lblUsername);
		
		lbUsername = new JLabel();
		GridBagConstraints gbc_lbUsername = new GridBagConstraints();
		gbc_lbUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lbUsername.anchor = GridBagConstraints.WEST;
		gbc_lbUsername.gridx = 2;
		gbc_lbUsername.gridy = 1;
		lbUsername.setPreferredSize(MAXIMUM_SIZE);
		lbUsername.setMaximumSize(MAXIMUM_SIZE);
		add(lbUsername, gbc_lbUsername);
		
		Component horizontalStrutRight = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrutRight = new GridBagConstraints();
		gbc_horizontalStrutRight.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrutRight.gridx = 3;
		gbc_horizontalStrutRight.gridy = 0;
		add(horizontalStrutRight, gbc_horizontalStrutRight);
		
		lbDatabases = new JLabel();
		GridBagConstraints gbc_lbDatabases = new GridBagConstraints();
		gbc_lbDatabases.insets = new Insets(0, 0, 5, 5);
		gbc_lbDatabases.anchor = GridBagConstraints.WEST;
		gbc_lbDatabases.gridx = 2;
		gbc_lbDatabases.gridy = 2;
		lbDatabases.setPreferredSize(MAXIMUM_SIZE);
		lbDatabases.setMaximumSize(MAXIMUM_SIZE);
		add(lbDatabases, gbc_lbDatabases);
		
		JLabel lblDatabases = new JLabel("Databases:");
		GridBagConstraints gbc_lblDatabases = new GridBagConstraints();
		gbc_lblDatabases.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatabases.gridx = 1;
		gbc_lblDatabases.gridy = 2;
		add(lblDatabases, gbc_lblDatabases);
		
		lbDatabases.setText(DB_DEFAULT);
		
		setVisible(true);
	}
	
	public void setUsername(String user) {
		lbUsername.setText(user);
		lbUsername.setToolTipText(user);
	}
	
	public void setDatabases(List<String> dbs) {
		String text = dbs == null ? DB_DEFAULT : String.join(", ", dbs);
		
		lbDatabases.setText(text);
		lbDatabases.setToolTipText(text);
	}
	
}
