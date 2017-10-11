package control;
import java.util.List;
import java.util.function.Consumer;

import view.Display;
import view.Display.EnumScreen;

public class Control {

	private Display display;
	private Connection connection;
	private List<String> databases;
	
	private Consumer<Object> selectDatabases = new Consumer<Object>() {
		
		@SuppressWarnings("unchecked")
		@Override
		public void accept(Object obj) {
			databases = (List<String>) obj;
			
			display
				.setScreen(EnumScreen.SELECT_ACTION);
		}
		
	};
	
	private Consumer<Object> establishConnection = new Consumer<Object>() {
		
		@Override
		public void accept(Object obj) {
			connection = (Connection) obj;
			
			display
				.setScreen(EnumScreen.SELECT_DB)
				.getMainResult(selectDatabases);
		}
		
	};
	
	public static void main(String[] args) {
		new Control().run();
	}
	
	public void run() {
		display = new Display(this);
		
		display
			.setScreen(EnumScreen.LOGIN)
			.getMainResult(establishConnection);
		
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public List<String> getSelectedDB() {
		return databases;
	}

}