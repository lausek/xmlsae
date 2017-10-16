package control;

import java.util.List;
import java.util.function.Consumer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.LoggerSettings;
import org.apache.log4j.Logger;
import view.Display;
import view.Display.AppScreen;

public class Control {

	private Display display;
	private Connection connection;
	private List<String> databases;
	private static final Logger logger = Logger.getLogger(Control.class);
	private static final String logFile = "log/Control.log";
	
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
			connection = (Connection) obj;

			display.setScreen(AppScreen.SELECT_DB).getMainResult(selectDatabases);
		}

	};

	public static void main(String[] args) {
		LoggerSettings.initLogger(logger, logFile);

		// Try to make program look like it is platform dependent
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			logger.debug(e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.debug(e.getMessage());
		} catch (InstantiationException e) {
			logger.debug(e.getMessage());
		} catch (IllegalAccessException e) {
			logger.debug(e.getMessage());
		}

		new Control().run();
	}

	public void run() {
		display = new Display(this);

		display.setScreen(AppScreen.LOGIN).getMainResult(establishConnection);
	}

	public Connection getConnection() {
		return connection;
	}

	public List<String> getSelectedDB() {
		return databases;
	}

}	