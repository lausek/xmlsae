package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import control.TextSymbols;
import view.Display.AppScreen;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;

/**
 * Screen for choosing between import and export.
 * 
 * @author lausek
 *
 */
@SuppressWarnings("serial")
public class ActionScreen extends Screen {

	private JButton btnImport, btnExport;

	public ActionScreen(Display display) {
		super(display);
	}

	@Override
	public AppScreen getScreenId() {
		return AppScreen.SELECT_ACTION;

	}

	@Override
	public void onEnter(AppScreen from) {
		if(display.getControl().getSelectedDB().isEmpty()) {
			btnExport.setToolTipText(TextSymbols.get(TextSymbols.SELECT_AT_LEAST));
			btnExport.setEnabled(false);
		} else {
			btnExport.setToolTipText(null);
			btnExport.setEnabled(true);
		}
	}

	@Override
	public void build() {
		setLayout(null);

		setLayout(new BorderLayout(0, 0));

		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		verticalBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		verticalBox.add(Box.createVerticalGlue());

		btnImport = new JButton(TextSymbols.get(TextSymbols.IMPORT_DATABASE));
		btnImport.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnImport.setMaximumSize(new Dimension(300, 75));
		btnImport.setMinimumSize(new Dimension(300, 75));
		btnImport.setPreferredSize(new Dimension(300, 75));
		btnImport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				display.setScreen(AppScreen.IMPORT);
			}
		});
		btnImport.setFont(btnImport.getFont().deriveFont(24f));
		btnImport.setIcon(new javax.swing.ImageIcon("media/img/arrow_up_32x32.png"));
		btnImport.setIconTextGap(20);
		verticalBox.add(btnImport);

		verticalBox.add(Box.createVerticalStrut(20));

		btnExport = new JButton(TextSymbols.get(TextSymbols.EXPORT_DATABASE));
		btnExport.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExport.setMaximumSize(new Dimension(300, 75));
		btnExport.setMinimumSize(new Dimension(300, 75));
		btnExport.setPreferredSize(new Dimension(300, 75));
		btnExport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				display.setScreen(AppScreen.EXPORT);
			}
		});
		btnExport.setFont(btnImport.getFont().deriveFont(24f));
		btnExport.setIcon(new javax.swing.ImageIcon("media/img/arrow_down_32x32.png"));
		btnExport.setIconTextGap(20);
		verticalBox.add(btnExport);

		verticalBox.add(Box.createVerticalGlue());
		add(verticalBox);

	}

	@Override
	public void addNavbar(JPanel navbar) {
		super.addNavbar(navbar);

		CSwitchArrow backArrow = new CSwitchArrow(display, AppScreen.SELECT_DB, MoveDirection.LEFT);
		navbar.add(backArrow, BorderLayout.WEST);

		CSwitchArrow forwardArrow = new CSwitchArrow(display, AppScreen.SELECT_ACTION, MoveDirection.RIGHT);
		forwardArrow.setEnabled(false);
		navbar.add(forwardArrow, BorderLayout.EAST);
		
	}

	@Override
	public void addToolbar(JPanel toolbar) {
	}

	@Override
	public void onLeave(AppScreen to) {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

}
