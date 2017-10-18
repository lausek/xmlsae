package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.Display.AppScreen;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;

@SuppressWarnings("serial")
public class ActionScreen extends Screen implements ActionListener {
	
	private JTextField tfUserLog;
	private JTextField tf_choosedDB;
	private JLabel labelUserLog;
	private JLabel label_choosedDB;
	
	public ActionScreen(Display display) {
		super(display);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AppScreen getScreenId() {
		// TODO Auto-generated method stub
		return AppScreen.SELECT_ACTION;
		
	}
	
	@Override
	public void build() {
		super.build();
		setLayout(null);
		
		setLayout(new BorderLayout(0, 0));
		
		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		verticalBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		verticalBox.add(Box.createVerticalGlue());

		//TODO: add action for buttons
		
		JButton btnImport = new JButton("Import Database");
		btnImport.setAlignmentX(Component.CENTER_ALIGNMENT);
		Font newButtonFont = new Font(btnImport.getFont().getName(),btnImport.getFont().getStyle(),24);
		btnImport.setMaximumSize(new Dimension(300, 75));
		btnImport.setMinimumSize(new Dimension(300, 75));
		btnImport.setPreferredSize(new Dimension (300, 75));
		btnImport.addActionListener(this);
		btnImport.setFont(newButtonFont);
		verticalBox.add(btnImport);
		
		verticalBox.add(Box.createVerticalStrut(20));
		
		JButton btnExport = new JButton("Export Database");
		btnExport.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExport.setMaximumSize(new Dimension(300, 75));
		btnExport.setMinimumSize(new Dimension(300, 75));
		btnExport.setPreferredSize(new Dimension (300, 75));
		btnExport.addActionListener(this);
		btnExport.setFont(newButtonFont);
		verticalBox.add(btnExport);
		
		verticalBox.add(Box.createVerticalGlue());
		add(verticalBox);

	}
	
	@Override
	public void addNavbar(JPanel navbar) {
		super.addNavbar(navbar);

		CSwitchArrow backArrow = new CSwitchArrow(display, AppScreen.SELECT_DB, MoveDirection.LEFT);
		navbar.add(backArrow, BorderLayout.WEST);
		
		JPanel LogData = new JPanel();
		
		labelUserLog = new JLabel("Benutzer:");
		LogData.add(labelUserLog);
		
		//TODO: add textfield for logged user and DB
		tfUserLog = new JTextField();
		tfUserLog.setEditable(false);
		tfUserLog.setColumns(10);
		LogData.add(tfUserLog);
		
		label_choosedDB = new JLabel("Datenbank:");
		LogData.add(label_choosedDB);
		navbar.add(LogData, BorderLayout.CENTER);
		
		tf_choosedDB = new JTextField();
		tf_choosedDB.setEditable(false);
		tf_choosedDB.setColumns(10);
		LogData.add(tf_choosedDB);
		
	}
	
	public static void main(String[] args) {
		new Display(null).setScreen(AppScreen.SELECT_ACTION);
	}
}
