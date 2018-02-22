package view;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.TextSymbols;

import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.awt.Component;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * 
 * General display object for holding the currently established connection and
 * which databases have been selected by the user. This object will be
 * automatically added to Screen in addNavbar method.
 * 
 * @author lausek
 *
 */
@SuppressWarnings("serial")
public class StatusArea extends JPanel {

	private static final String DEFAULT = "-";
	private static final Dimension MAXIMUM_SIZE = new Dimension(300, 20);

	private JLabel lbUsername, lbDatabases;
	private ImageIcon icon;

	public StatusArea() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 1, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 1, 0, 1, 1, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };

		JPanel left = new JPanel();
		left.setLayout(gridBagLayout);

		Component horizontalStrutLeft = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrutLeft = new GridBagConstraints();
		gbc_horizontalStrutLeft.insets = new Insets(0, 0, 5, 5);
		gbc_horizontalStrutLeft.gridx = 0;
		gbc_horizontalStrutLeft.gridy = 0;
		left.add(horizontalStrutLeft, gbc_horizontalStrutLeft);

		JLabel lblUsername = new JLabel(TextSymbols.get(TextSymbols.STATUS_CONNECTED));
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 1;
		gbc_lblUsername.gridy = 1;
		left.add(lblUsername, gbc_lblUsername);

		lbUsername = new JLabel();
		GridBagConstraints gbc_lbUsername = new GridBagConstraints();
		gbc_lbUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lbUsername.anchor = GridBagConstraints.WEST;
		gbc_lbUsername.gridx = 2;
		gbc_lbUsername.gridy = 1;
		lbUsername.setPreferredSize(MAXIMUM_SIZE);
		lbUsername.setMaximumSize(MAXIMUM_SIZE);
		left.add(lbUsername, gbc_lbUsername);

		Component horizontalStrutRight = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrutRight = new GridBagConstraints();
		gbc_horizontalStrutRight.insets = new Insets(0, 0, 5, 0);
		gbc_horizontalStrutRight.gridx = 3;
		gbc_horizontalStrutRight.gridy = 0;
		left.add(horizontalStrutRight, gbc_horizontalStrutRight);

		lbDatabases = new JLabel();
		GridBagConstraints gbc_lbDatabases = new GridBagConstraints();
		gbc_lbDatabases.insets = new Insets(0, 0, 5, 5);
		gbc_lbDatabases.anchor = GridBagConstraints.WEST;
		gbc_lbDatabases.gridx = 2;
		gbc_lbDatabases.gridy = 2;
		lbDatabases.setPreferredSize(MAXIMUM_SIZE);
		lbDatabases.setMaximumSize(MAXIMUM_SIZE);
		left.add(lbDatabases, gbc_lbDatabases);

		JLabel lblDatabases = new JLabel(TextSymbols.get(TextSymbols.STATUS_DATABASES));
		GridBagConstraints gbc_lblDatabases = new GridBagConstraints();
		gbc_lblDatabases.insets = new Insets(0, 0, 5, 5);
		gbc_lblDatabases.gridx = 1;
		gbc_lblDatabases.gridy = 2;
		left.add(lblDatabases, gbc_lblDatabases);
		
		icon = new ImageIcon();
		reloadLanguage();
		
		JButton cmdChooseLanguage = new JButton(icon);
		cmdChooseLanguage.setVerticalAlignment(SwingConstants.TOP);
		cmdChooseLanguage.setMargin(new Insets(2,2,2,2));
		GridBagConstraints gbc_cmdChooseLanguage = new GridBagConstraints();
		gbc_cmdChooseLanguage.gridheight = 2;
		gbc_cmdChooseLanguage.gridx = 3;
		gbc_cmdChooseLanguage.gridy = 1;
		left.add(cmdChooseLanguage, gbc_cmdChooseLanguage);
		cmdChooseLanguage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String nextLanguage = getLanguageDialog();

				if (nextLanguage != null && !nextLanguage.equals(TextSymbols.getLanguage())) {
					TextSymbols.setLanguage(nextLanguage);
					reloadLanguage();
					JOptionPane.showMessageDialog(null, TextSymbols.get(TextSymbols.CHOOSE_LANGUAGE_DONE));
				}
			}
		});
		cmdChooseLanguage.setSize(48, 48);

		lbUsername.setText(DEFAULT);
		lbDatabases.setText(DEFAULT);

		add(left);
		setVisible(true);
	}

	private String getLanguageDialog() {
		File langDir = new File("lang");
		Object[] languageList = new Object[langDir.listFiles().length];
		Object selected = null;
		int i = 0;
		for (File f : langDir.listFiles()) {
			languageList[i++] = new ImageIcon("media/img/lang/" + f.getName() + ".png");
			if (f.getName().equals(TextSymbols.getLanguage())) {
				selected = languageList[i - 1];
			}
		}

		ImageIcon result = (ImageIcon) JOptionPane.showInputDialog(null,
				TextSymbols.get(TextSymbols.CHOOSE_LANGUAGE_TITLE), TextSymbols.get(TextSymbols.CHOOSE_LANGUAGE_ASK),
				JOptionPane.PLAIN_MESSAGE, null, languageList, selected);

		if (result == null) {
			return null;
		}

		return Paths.get(result.getDescription()).getFileName().toString().substring(0, 2);
	}

	private void reloadLanguage() {
		try {
			Image img = ImageIO.read(new File("media/img/lang/" + TextSymbols.getLanguage() + ".png"));
			icon.setImage(img.getScaledInstance(20, 20, Image.SCALE_FAST));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setUsername(String user) {
		if (user == null || user.isEmpty()) {
			user = DEFAULT;
		}

		lbUsername.setText(user);
		lbUsername.setToolTipText(user);
	}

	public void setDatabases(List<String> dbs) {
		String text = (dbs == null || dbs.isEmpty()) ? DEFAULT : String.join(", ", dbs);

		lbDatabases.setText(text);
		lbDatabases.setToolTipText(text);
	}

}
