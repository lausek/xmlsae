package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.ExportSettings;
import view.Display.AppScreen;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class ExportScreen extends Screen implements ActionListener {

	private JButton btnExport, btnSelectDirectory;
	private JCheckBox bxData, bxDefinition;
	private File saveTo;

	public ExportScreen(Display display) {
		super(display);
	}

	@Override
	public AppScreen getScreenId() {
		return AppScreen.EXPORT;
	}

	@Override
	public void build() {
		super.build();

		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		btnExport = new JButton("Export");
		JPanel settings = new JPanel();

		setLayout(new BorderLayout(0, 0));

		verticalBox.add(Box.createVerticalGlue());
		verticalBox.add(settings);

		settings.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel = new JPanel();
		settings.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JLabel lbExportSettings = new JLabel("Export...");
		panel.add(lbExportSettings);

		bxData = new JCheckBox("Data");
		bxData.setSelected(true);
		bxData.addActionListener(this);
		panel.add(bxData);

		bxDefinition = new JCheckBox("Definition");
		bxDefinition.setSelected(true);
		bxDefinition.addActionListener(this);
		panel.add(bxDefinition);

		JPanel pDirectory = new JPanel();
		panel.add(pDirectory);

		JLabel lbDirectory = new JLabel("Save to");
		pDirectory.add(lbDirectory);

		btnSelectDirectory = new JButton("...");
		btnSelectDirectory.addActionListener(this);
		pDirectory.add(btnSelectDirectory);

		verticalBox.add(Box.createVerticalStrut(40));

		btnExport.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font newButtonFont = new Font(btnExport.getFont().getName(), btnExport.getFont().getStyle(), 24);
		btnExport.setMaximumSize(new Dimension(300, 75));
		btnExport.setMinimumSize(new Dimension(300, 75));
		btnExport.setPreferredSize(new Dimension(300, 75));
		btnExport.addActionListener(this);
		btnExport.setFont(newButtonFont);
		btnExport.setEnabled(false);
		verticalBox.add(btnExport);

		verticalBox.add(Box.createVerticalGlue());
		add(verticalBox);
	}

	@Override
	public void addNavbar(JPanel navbar) {
		super.addNavbar(navbar);

		CSwitchArrow backArrow = new CSwitchArrow(display, AppScreen.SELECT_ACTION, MoveDirection.LEFT);
		navbar.add(backArrow, BorderLayout.WEST);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source == bxData || source == bxDefinition) {
			
			btnExport.setEnabled((bxData.isSelected() || bxDefinition.isSelected()) && saveTo != null);
			
		} else if(source == btnSelectDirectory) {
			
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				saveTo = chooser.getSelectedFile();
			}
			
			btnExport.setEnabled(saveTo != null);
			
		} else if (source == btnExport) {
			
			ExportSettings settings = new ExportSettings(display.getControl().getSelectedDB());
			settings.setDirectory(saveTo);
			settings.setDefinitionRequired(bxDefinition.isSelected());
			settings.setDataRequired(bxData.isSelected());
			
			callback.accept(settings);

		}
	}

}
