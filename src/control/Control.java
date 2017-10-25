package control;

import java.util.List;
import java.util.function.Consumer;

import javax.swing.UIManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import view.Display;
import view.Display.AppScreen;

public class Control {
	
	private static final String LOG4J_PATH = "properties/propertiesControl.properties";
	private static Logger logger;
	
	private Display display;
	private DBInterface dataInterface;
	private List<String> databases;
	
	static {
		logger = Logger.getLogger("Control");
		PropertyConfigurator.configure(LOG4J_PATH);
	}
	
	private Consumer<Object> databasesCallback = new Consumer<Object>() {

		@SuppressWarnings("unchecked")
		@Override
		public void accept(Object obj) {
			databases = (List<String>) obj;
			// Leave will be done in SelectionScreen itself
		}

	};

	private Consumer<Object> connectionCallback = new Consumer<Object>() {

		@Override
		public void accept(Object obj) {
			dataInterface = new DBInterface((Connection) obj);
			
			// getMainResult looks better here
			display.setScreen(AppScreen.SELECT_DB).setCallback(databasesCallback);
		}

	};

	public static void main(String[] args) {
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

		display.setScreen(AppScreen.LOGIN).setCallback(connectionCallback);
	}

	public DBInterface getInterface() {
		return dataInterface;
	}

	// Will be used by ImportScreen and ExportScreen
	public List<String> getSelectedDB() {
		return databases;
	}

}
