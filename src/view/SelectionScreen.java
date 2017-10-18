package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import view.Display.AppScreen;
import view.Display.MessageFatality;
import view.atoms.CTextField;
import view.atoms.KeyHandler;
import view.atoms.KeyHandler.HandleTarget;
import view.atoms.CListItem;
import view.atoms.CSwitchArrow;
import view.atoms.CSwitchArrow.MoveDirection;

import javax.swing.DefaultListModel;
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
	private DefaultListModel<CListItem> list;
	private JList<CListItem> jlist;
	private CTextField filterField;
	private Consumer<Object> callback;

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

		list = new DefaultListModel<>();

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

				reloadList(null);

			} catch (SQLException e) {
				display.notice(MessageFatality.ERROR, "Couldn't fetch databases from server");
			}
		}

	}

	@Override
	public void onLeave(AppScreen to) {
		super.onLeave(to);

		// Push selected databases to control
		List<String> selected = new ArrayList<>();
		jlist.getSelectedValuesList().forEach(item -> selected.add(item.getName()));
		callback.accept(selected);
	}

	@Override
	public void addNavbar(JPanel navbar) {
		super.addNavbar(navbar);

		CSwitchArrow backArrow = new CSwitchArrow(display, AppScreen.LOGIN, MoveDirection.LEFT);
		navbar.add(backArrow, BorderLayout.WEST);

		CSwitchArrow forwardArrow = new CSwitchArrow(display, AppScreen.SELECT_ACTION, MoveDirection.RIGHT);
		forwardArrow.setCondition(x -> {
			if (jlist.getSelectedIndices().length == 0) {
				display.notice(MessageFatality.ERROR, "You have to select at least one database.");
				return false;
			}
			return true;
		});
		navbar.add(forwardArrow, BorderLayout.EAST);

	}

	private void reloadList(final String query) {

		// Loads all objects from 'databases' into list if query is null
		String realQuery = query != null ? query : "";

		list.clear();

		databases.stream().filter(db -> db.getTitle().contains(realQuery))
				.forEach(list::addElement);

	}

	@Override
	public void setCallback(Consumer<Object> action) {
		callback = action;
	}

}