package control;

import java.util.List;
import java.util.function.Consumer;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import view.Display;
import view.Display.AppScreen;

public class Control {

	private Display display;
	private DBInterface dataInterface;
	private List<String> databases;

	private Consumer<Object> selectDatabases = new Consumer<Object>() {

		@SuppressWarnings("unchecked")
		@Override
		public void accept(Object obj) {
			databases = (List<String>) obj;

			display.setScreen(AppScreen.SELECT_ACTION);
		}

	};

	private Consumer<Object> establishConnection = new Consumer<Object>() {

		@Override
		public void accept(Object obj) {
			dataInterface = new DBInterface((Connection) obj);

			display.setScreen(AppScreen.SELECT_DB).getMainResult(selectDatabases);
		}

	};

	public static void main(String[] args) {

		// Try to make program look like it is platform dependent
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// TODO: Add logger info
		} catch (ClassNotFoundException e) {
			// TODO: Add logger info
		} catch (InstantiationException e) {
			// TODO: Add logger info
		} catch (IllegalAccessException e) {
			// TODO: Add logger info
		}

		new Control().run();
	}

	public void run() {
		display = new Display(this);

		display.setScreen(AppScreen.LOGIN).getMainResult(establishConnection);
	}

	public DBInterface getInterface() {
		return dataInterface;
	}

	// TODO: not used yet; check if needed
	public List<String> getSelectedDB() {
		return databases;
	}

}