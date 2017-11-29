package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import control.DatabaseImporter;
import model.ProcessSettings;
import view.Display.AppScreen;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;

@SuppressWarnings("serial")
public class ImportScreen extends Screen implements ActionListener {

	public ImportScreen(Display display) {
		super(display);
	}

	@Override
	public AppScreen getScreenId() {
		return AppScreen.IMPORT;

	}

	@Override
	public void build() {
		super.build();
		setLayout(null);

		setLayout(new BorderLayout(0, 0));

		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		verticalBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		verticalBox.add(Box.createVerticalGlue());

		// TODO: add action for buttons
		JButton btnImport = new JButton("Import Database");
		btnImport.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font newButtonFont = new Font(btnImport.getFont().getName(), btnImport
				.getFont().getStyle(), 24);
		btnImport.setMaximumSize(new Dimension(300, 75));
		btnImport.setMinimumSize(new Dimension(300, 75));
		btnImport.setPreferredSize(new Dimension(300, 75));
		btnImport.addActionListener(this);
		btnImport.setFont(newButtonFont);
		verticalBox.add(btnImport);

		verticalBox.add(Box.createVerticalGlue());
		add(verticalBox);

	}

	@Override
	public void addNavbar(JPanel navbar) {
		super.addNavbar(navbar);

		CSwitchArrow backArrow = new CSwitchArrow(display,
				AppScreen.SELECT_ACTION, MoveDirection.LEFT);
		navbar.add(backArrow, BorderLayout.WEST);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {

		// TODO: Select File

		ProcessSettings settings = new ProcessSettings(display.getControl()
				.getSelectedDB());

		DatabaseImporter importer = new DatabaseImporter(settings);

		try {
			importer.start();
		} catch (IOException e) {
			// TODO: Add Logger here
			e.printStackTrace();
		}
	}

}
