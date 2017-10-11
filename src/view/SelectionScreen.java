package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import view.Display.EnumFatality;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class SelectionScreen extends Screen implements KeyListener {
	
	private List<String> databases = new ArrayList<>();
	private JTextField textField;
	private DefaultListModel<DatabaseListItem> list;
	
	public SelectionScreen(Display parent) {
		super(parent);
	}
	
	@Override
	public void build() {
		super.build();
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(102, 33, 202, 20);
		add(textField);
		textField.setColumns(10);
		textField.addKeyListener(this);
		
		JList<DatabaseListItem> jlist = new JList<>();
		list = new DefaultListModel<>();
		jlist.setModel(list);
		
		// avoid printing JPanels as String
		jlist.setCellRenderer(new ListCellRenderer<DatabaseListItem>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends DatabaseListItem> list, DatabaseListItem value, int index,
					boolean isSelected, boolean cellHasFocus) {
				return value.getPanel();
			}
			
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(102, 64, 201, 131);
		scrollPane.setViewportView(jlist);
		add(scrollPane);
		
	}
	
	@Override
	public void onEnter() {
		super.onEnter();
		
		// Load available databases from SQL server
		try {
			
			ResultSet result = parent
				.getControl()
				.getConnection()
				.newStatement()
				.executeQuery("SHOW DATABASES");
			
			while(result.next()) {
				System.out.println(result.getString(1));
				databases.add(result.getString(1));
			}
			
			initList();
			
		} catch(SQLException e) {
			parent.notice(EnumFatality.ERROR, "Couldn't fetch databases from server");
		}
		
	}
	
	private void initList() {
		
		for(String name : databases) {
			list.addElement(new DatabaseListItem(name));
		}
		
	}
	
	private void filterList(String query) {
		
		for(int i = 0; i < list.size(); i++) {
//			DatabaseListItem panel = list.getElementAt(i);
//			panel.setVisible(panel.getName().contains(query));
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		filterList(textField.getText());
	}
	
	@Override
	public void getMainResult(Consumer<Object> action) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) { }

	@Override
	public void keyReleased(KeyEvent e) {}

}

@SuppressWarnings("serial")
class DatabaseListItem extends JPanel {
	
	private JPanel panel;
	private JLabel nameLabel;
	
	public DatabaseListItem(String name) {
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.CYAN);
		
		nameLabel = new JLabel(name);
		panel.add(nameLabel);
	}
	
	public String getName() {
		return nameLabel.getText();
	}
	
	public JPanel getPanel() {
		return panel;
	}

	
	
}