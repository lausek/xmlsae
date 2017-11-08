package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import view.Display.AppScreen;
import view.atoms.CSelectedFile;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;

@SuppressWarnings("serial")
public class ImportScreen extends Screen implements ActionListener {

	private List<CSelectedFile> files;
	private JPanel filePanel, addFilePanel;

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

		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		JButton btnImport = new JButton("Import Database");

		filePanel = new JPanel();
		JScrollPane fileScrollPane = new JScrollPane(filePanel);
		fileScrollPane.setBorder(null);

		addFilePanel = new JPanel();
		JButton cmdAddFile = new JButton("+");
		addFilePanel.add(cmdAddFile);

		setLayout(new BorderLayout(0, 0));

		cmdAddFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				addFileOption();
			}

		});

		verticalBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		verticalBox.add(Box.createVerticalGlue());

		verticalBox.add(fileScrollPane);
		addFileOption();

		verticalBox.add(Box.createVerticalStrut(40));

		// TODO: add action for buttons
		btnImport.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font newButtonFont = new Font(btnImport.getFont().getName(), btnImport.getFont().getStyle(), 24);
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

		CSwitchArrow backArrow = new CSwitchArrow(display, AppScreen.SELECT_ACTION, MoveDirection.LEFT);
		navbar.add(backArrow, BorderLayout.WEST);
	}

	public void addFileOption() {
		CSelectedFile next = new CSelectedFile(this);

		if (files == null) {

			files = new java.util.ArrayList<>();

		}

		if (!files.isEmpty()) {
			// after we pushed the first one in
			if (files.get(files.size() - 1).isEmpty()) {
				return;
			}
		}

		files.add(next);

		filePanel.remove(addFilePanel);
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));
		filePanel.add(next);
		filePanel.add(addFilePanel);

		revalidate();
	}

	public void remove(CSelectedFile obj) {
		files.remove(obj);
		filePanel.remove(obj);

		if (files.size() == 0) {
			addFileOption();
		}

		revalidate();
	}

	// TODO: delete after class is done
	public static void main(String[] args) {
		new Display(null).setScreen(AppScreen.IMPORT);
	}

}
