package control;
import java.util.List;
import java.util.function.Consumer;

import view.Display;
import view.Display.EnumScreen;

public class Control {

	private Display display;
	private Connection connection;
	private List<String> databases;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Control().run();
	}
	
	public void run() {
		display = new Display(this);
		
		display.setScreen(EnumScreen.LOGIN);
		display.getCurrentScreen().getMainResult(new Consumer<Object>() {
			@Override
			public void accept(Object obj) {
				connection = (Connection) obj;
			}
		});
	}
	
	/**
	 * Wenn connection null -> setScreen(SCREEN_LOGIN)
	 */
	public void getConnection() {
		// TODO - implement Control.getConnection
		throw new UnsupportedOperationException();
	}

	/**
	 * Wenn selectedDB null -> setScreen(SCREEN_SELECT_DB)
	 */
	public void getSelectedDB() {
		// TODO - implement Control.getSelectedDB
		throw new UnsupportedOperationException();
	}

}