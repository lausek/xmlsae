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

import model.ProcessSettings;

import control.DatabaseExporter;
import view.Display.AppScreen;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;

@SuppressWarnings("serial")
public class ExportScreen extends Screen implements ActionListener {

	public ExportScreen(Display display) {
		super(display);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AppScreen getScreenId() {
		// TODO Auto-generated method stub
		return AppScreen.EXPORT;

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
		JButton btnExport = new JButton("Export Database");
		btnExport.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font newButtonFont = new Font(btnExport.getFont().getName(), btnExport
				.getFont().getStyle(), 24);
		btnExport.setMaximumSize(new Dimension(300, 75));
		btnExport.setMinimumSize(new Dimension(300, 75));
		btnExport.setPreferredSize(new Dimension(300, 75));
		btnExport.addActionListener(this);
		btnExport.setFont(newButtonFont);
		verticalBox.add(btnExport);

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

		ProcessSettings settings = new ProcessSettings();

		DatabaseExporter exporter = new DatabaseExporter(settings);

		try {
			exporter.start();
		} catch (IOException e) {
			// TODO: Add logger here
			e.printStackTrace();
		}

	}

}
