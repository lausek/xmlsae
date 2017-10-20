package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import view.Display.AppScreen;
import view.Display.MessageFatality;
import view.atoms.CTextField;
import view.atoms.KeyHandler;
import view.atoms.KeyHandler.HandleTarget;
import view.atoms.CFilteredListModel;
import view.atoms.CListItem;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import javax.swing.Box;

@SuppressWarnings("serial")
public class SelectionScreen extends Screen {

	private List<CListItem> databases = new ArrayList<>();
	private CFilteredListModel<CListItem> list;
	private JList<CListItem> jlist;
	private CTextField filterField;

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
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		list = new CFilteredListModel<>();

		filterField = new CTextField("database...");
		filterField.setColumns(30);
		filterField.setMinimumSize(new java.awt.Dimension(240, 30));
		filterField.setMaximumSize(new java.awt.Dimension(240, 30));
		filterField.addKeyListener(new KeyHandler().handle(HandleTarget.RELEASE, e -> {
			reloadList(filterField.getText());
		}));
		add(filterField);

		jlist = new JList<>();
		jlist.setVisibleRowCount(6);
		jlist.setModel(list);
		jlist.addMouseListener(new java.awt.event.MouseListener() {
			@Override
			public void mouseClicked(MouseEvent obj) {
				// TODO: this doesn't work even if the target object is still the same
				int i = jlist.locationToIndex(obj.getPoint());
				list.get(i).toggleSelection();
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});

		add(Box.createVerticalStrut(20));
		// avoid printing JPanels as String
		jlist.setCellRenderer(new ListCellRenderer<CListItem>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends CListItem> list, CListItem value, int index,
					boolean isSelected, boolean cellHasFocus) {
				if (value.isSelected()) {
					value.setBackground(CListItem.COLOR_SELECTED);
				} else {
					value.setBackground(CListItem.COLOR_DESELECTED);
				}
				return value;
			}

		});

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
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

			// Load available databases from SQL server
			try {

				// Fetch database names from server and translate into a list of CListItems
				display.getControl().getInterface().getDatabases().forEach(db -> databases.add(new CListItem(db)));

				initList();

			} catch (SQLException e) {
				display.notice(MessageFatality.ERROR, "Couldn't fetch databases from server");
			}

			getStatusArea().setDatabases(null);

		} else {

			reloadList(null);

		}

	}

	@Override
	public void onLeave(AppScreen to) {
		super.onLeave(to);

		List<String> dbs = getSelectedItems();
		getStatusArea().setDatabases(dbs);
		callback.accept(dbs);
	}

	@Override
	public void addNavbar(JPanel navbar) {
		super.addNavbar(navbar);

		CSwitchArrow backArrow = new CSwitchArrow(display, AppScreen.LOGIN, MoveDirection.LEFT);
		navbar.add(backArrow, BorderLayout.WEST);

		CSwitchArrow forwardArrow = new CSwitchArrow(display, AppScreen.SELECT_ACTION, MoveDirection.RIGHT);
		forwardArrow.setCondition(x -> {
			if (getSelectedItems().size() == 0) {
				display.notice(MessageFatality.ERROR, "You have to select at least one database.");
				return false;
			}
			return true;
		});
		navbar.add(forwardArrow, BorderLayout.EAST);

	}

	private void initList() {
		list.clear();

		databases.stream().forEach(list::addElement);
	}

	private void reloadList(final String query) {
		
		// Loads all objects from 'databases' into list if query is null
		String realQuery = query != null ? query : "";
		
		list.filter(item -> item.getName().contains(realQuery));

	}

	private List<String> getSelectedItems() {
		// Filter List of selected CListItems into List of strings
		return list.getList().stream()
				.filter(x -> x.isSelected())
				.map(item -> item.getName())
				.collect(Collectors.toList());
	}

}