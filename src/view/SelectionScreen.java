package view;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import view.Display.EnumFatality;
import view.atoms.CTextField;
import view.atoms.CListItem;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class SelectionScreen extends Screen implements KeyListener {
	
	private List<CListItem> databases = new ArrayList<>();
	private JTextField textField;
	private DefaultListModel<CListItem> list;
	private JList<CListItem> jlist;
	
	public SelectionScreen(Display parent) {
		super(parent);
	}
	
	@Override
	public void build() {
		super.build();
		setLayout(null);
		
		list = new DefaultListModel<>();
		
		textField = new CTextField("database...");
		textField.setBounds(102, 33, 202, 20);
		textField.setColumns(10);
		textField.addKeyListener(this);
		add(textField);
		
		jlist = new JList<>();
		jlist.setModel(list);
		jlist.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//Event gets fired twice if this check doesn't happen
				if(jlist.getValueIsAdjusting()) {
					/* TODO: Doesn't work correctly if multiple items are selected 
					 * For facing this issue, hold mouse button down and 
					 * scroll from top to bottom. Some items will stay deselected.
					 * */
					jlist.getSelectedValue().toggleSelection();
				}
			}
			
		});
		// avoid printing JPanels as String
		jlist.setCellRenderer(new ListCellRenderer<CListItem>() {
			
			@Override
			public Component getListCellRendererComponent(JList<? extends CListItem> list, CListItem value, int index,
					boolean isSelected, boolean cellHasFocus) {
				return value;
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
			
			final ResultSet result = parent
				.getControl()
				.getConnection()
				.newStatement()
				.executeQuery("SHOW DATABASES");
			
			while(result.next()) {
				//TODO: interesting for logger?
				System.out.println(result.getString(1));
				databases.add(new CListItem(result.getString(1)));
			}
			
			reloadList();
			
		} catch(SQLException e) {
			parent.notice(EnumFatality.ERROR, "Couldn't fetch databases from server");
		}
		
	}
	
	private void reloadList() {
		//Loads all objects from 'databases' into list
		reloadList("");
	}
	
	private void reloadList(String query) {
		
		list.clear();
		
		databases
			.stream()
			.filter(db -> db.getTitle().contains(query))
			/* TODO: Na Pascal? Kommste noch mit? Kind regards, lausek */
			.forEach(list::addElement);
		
		//TODO: is this needed?
		revalidate();
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) { 
		reloadList(textField.getText());
	}
	
	@Override
	public void keyTyped(KeyEvent e) { }
	
	@Override
	public void keyPressed(KeyEvent e) { }
	
	@Override
	public void getMainResult(Consumer<Object> action) { }

}