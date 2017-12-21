package control;

import java.sql.SQLException;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.UIManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import model.ProcessSettings;
import view.Display;
import view.Display.AppScreen;

public class Control {

	private static final String LOG4J_PATH = "properties/propertiesControl.properties";
	private static Logger logger;

	private Display display;
	private DatabaseActor databaseActor;
	private List<String> databases;

	static {
		logger = Logger.getLogger("Control");
		PropertyConfigurator.configure(LOG4J_PATH);
	}
	
	public static void main(String[] args) {
		// Try to make program look like it is platform dependent
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		new Control().run();
	}

	public void run() {
		display = new Display(this);

		display.setScreen(AppScreen.LOGIN);
	}

	public DatabaseActor getInterface() {
		return databaseActor;
	}

	// Will be used by ImportScreen and ExportScreen
	public List<String> getSelectedDB() {
		return databases;
	}

	public Consumer<Object> databasesCallback = new Consumer<Object>() {

		@SuppressWarnings("unchecked")
		@Override
		public void accept(Object obj) {
			databases = (List<String>) obj;
			// Leave will be done in SelectionScreen itself
		}

	};

	public Consumer<Object> connectionCallback = new Consumer<Object>() {

		@Override
		public void accept(Object obj) {
			databaseActor = new DatabaseActor((RichConnection) obj);

			// getMainResult looks better here
			display.setScreen(AppScreen.SELECT_DB);
		}

	};
	
	public Consumer<Object> importCallback = new Consumer<Object>() {

		@Override
		public void accept(Object settings) {
			
		}
				
	};

	public Consumer<Object> exportCallback = new Consumer<Object>() {

		@Override
		public void accept(Object settings) {
			try {
				new DatabaseExporter((ProcessSettings)settings).start();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
	};
}
