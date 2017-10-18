package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.Display.AppScreen;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;

@SuppressWarnings("serial")
public class ExportScreen extends Screen {
	
	private JTextField tfUserLog;
	private JTextField tf_choosedDB;
	private JLabel labelUserLog;
	private JLabel label_choosedDB;

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
		
		CSwitchArrow backArrow = new CSwitchArrow(display, AppScreen.SELECT_ACTION).setDirection(MoveDirection.LEFT);
		add(backArrow);
		
		//ToDo: get Textfield for logged User and DB
		tfUserLog = new JTextField();
		tfUserLog.setEditable(false);
		tfUserLog.setBounds(375, 65, 175, 25);
		tfUserLog.setColumns(10);
		add(tfUserLog);
		
		tf_choosedDB = new JTextField();
		tf_choosedDB.setEditable(false);
		tf_choosedDB.setBounds(375, 100, 175, 25);
		tf_choosedDB.setColumns(10);
		add(tf_choosedDB);
		
		labelUserLog = new JLabel("Eingeloggt als");
		labelUserLog.setBounds(250, 65, 100, 25);
		add(labelUserLog);
		
		label_choosedDB = new JLabel("Datenbank:");
		label_choosedDB.setBounds(250, 100, 100, 25);
		add(label_choosedDB);

		//ToDo: add action for buttons
		JButton btnExport = new JButton("Export Database");
		Font newButtonFont = new Font(btnExport.getFont().getName(),btnExport.getFont().getStyle(),24);
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnExport.setBounds(250, 300, 300, 75);
		btnExport.setFont(newButtonFont);
		add(btnExport);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 10, 10);
		add(panel);
	}
	
	public static void main(String[] args) {
		new Display(null).setScreen(AppScreen.EXPORT);
	}

}
