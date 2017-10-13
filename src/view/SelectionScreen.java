package view;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import view.Display.AppScreen;
import view.Display.MessageFatality;
import view.atoms.CTextField;
import view.atoms.CListItem;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class SelectionScreen extends Screen implements KeyListener {

	private List<CListItem> databases = new ArrayList<>();
	private CTextField filterField;
	private DefaultListModel<CListItem> list;
	private JList<CListItem> jlist;

	public SelectionScreen(Display display) {
		super(display);
	}

	@Override
	public AppScreen getScreenId() {
		return AppScreen.SELECT_DB;
	}

	@Override
	public void build() {
		super.build();
		setLayout(null);

		list = new DefaultListModel<>();

		CSwitchArrow backArrow = new CSwitchArrow(display, AppScreen.LOGIN).setDirection(MoveDirection.LEFT);
		add(backArrow);

		CSwitchArrow forwardArrow = new CSwitchArrow(display, AppScreen.SELECT_ACTION)
				.setDirection(MoveDirection.RIGHT);
		add(forwardArrow);

		filterField = new CTextField("database...");
		filterField.setBounds(102, 33, 202, 20);
		filterField.setColumns(10);
		filterField.addKeyListener(this);
		add(filterField);

		jlist = new JList<>();
		jlist.setModel(list);
		jlist.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO: Click on same item doesn't reset
				// Event gets fired twice if this check doesn't happen
				if (jlist.getValueIsAdjusting()) {
					/*
					 * TODO: Doesn't work correctly if multiple items are selected For facing this
					 * issue, hold mouse button down and scroll from top to bottom. Some items will
					 * stay deselected.
					 */
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
	public void onEnter(AppScreen from) {
		super.onEnter(from);

		// If this screen gets reentered, clean up
		filterField.setText("");
		if (from == AppScreen.LOGIN) {
			// ...but only clean up databases if user reconnected
			databases.clear();
		}

		// Load available databases from SQL server
		try {

			final ResultSet result = display.getControl().getConnection().newStatement().executeQuery("SHOW DATABASES");

			while (result.next()) {
				// TODO: interesting for logger?
				databases.add(new CListItem(result.getString(1)));
			}

			reloadList();

		} catch (SQLException e) {
			display.notice(MessageFatality.ERROR, "Couldn't fetch databases from server");
		}

	}

	private void reloadList() {
		// Loads all objects from 'databases' into list
		reloadList("");
	}

	private void reloadList(String query) {

		list.clear();

		databases.stream().filter(db -> db.getTitle().contains(query))
				/* TODO: Na Pascal? Kommste noch mit? Kind regards, lausek */
				.forEach(list::addElement);

		// TODO: is this needed?
		revalidate();

	}

	@Override
	public void keyReleased(KeyEvent e) {
		reloadList(filterField.getText());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void getMainResult(Consumer<Object> action) {
	}

}