package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import view.Display.AppScreen;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class ExportScreen extends Screen implements ActionListener {

	private JButton btnExport;
	private JCheckBox bxData, bxDefinition;

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

		verticalBox.add(Box.createVerticalStrut(40));

		btnExport.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font newButtonFont = new Font(btnExport.getFont().getName(), btnExport.getFont().getStyle(), 24);
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

		CSwitchArrow backArrow = new CSwitchArrow(display, AppScreen.SELECT_ACTION, MoveDirection.LEFT);
		navbar.add(backArrow, BorderLayout.WEST);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object source = arg0.getSource();
		if (source == bxData || source == bxDefinition) {
			btnExport.setEnabled(bxData.isSelected() || bxDefinition.isSelected());
		} else if (source == btnExport) {
			// TODO: select folder
		}
	}

	// TODO: delete after class is done
	public static void main(String[] args) {
		new Display(null).setScreen(AppScreen.EXPORT);
	}

}
