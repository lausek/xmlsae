package view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import view.Display.EnumFatality;

@SuppressWarnings("serial")
public class SelectionScreen extends Screen {
	
	private List<String> databases = new ArrayList<>();
	
	public SelectionScreen(Display parent) {
		super(parent);
	}
	
	@Override
	public void build() {
		super.build();
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
			
		} catch(SQLException e) {
			parent.notice(EnumFatality.ERROR, "Couldn't fetch databases from server");
		}
		
	}
	
	@Override
	public void getMainResult(Consumer<Object> action) {
		
	}
	
}