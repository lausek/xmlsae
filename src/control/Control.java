package control;

import java.util.List;
import java.util.function.Consumer;
import javax.swing.UIManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import view.Display;
import view.Display.AppScreen;

public class Control {

	private Display display;
	private Connection connection;
	private List<String> databases;
	private static final String LOG4J_PATH = "properties/propertiesControl.properties";
	private static Logger logger;
	
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
		logger = Logger.getLogger("Control");
		PropertyConfigurator.configure(LOG4J_PATH);
		// Try to make program look like it is platform dependent
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
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