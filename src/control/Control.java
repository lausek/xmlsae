package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

import javax.swing.UIManager;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import control.xml.DatabaseExporter;
import control.xml.DatabaseImporter;
import model.ExportSettings;
import model.ImportSettings;
import view.Display;
import view.Display.AppScreen;
import view.Display.MessageFatality;

public class Control {

	private static final String SETTINGS_PATH = "properties/xmlsae.properties";
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
		
		try {
			Properties properties = new Properties();
			InputStream inp = new FileInputStream(new File(SETTINGS_PATH));
			properties.load(inp);
			
			TextSymbols.load(properties.getProperty("language"));
		} catch (IOException e2) {
			TextSymbols.load("en");
			logger.error(e2.getMessage(), e2);
		}
		
		try {
			SecurityHandler.check();
		} catch (IOException e1) {
			System.out.println(TextSymbols.get(TextSymbols.FILES_DOWNLOAD_FAILED));
			System.exit(1);
		}
		
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
			String protocol = new DatabaseImporter((ImportSettings)settings).start();
			display.notice(MessageFatality.INFO, TextSymbols.get(TextSymbols.IMPORT_LOG), protocol);
		}
				
	};

	public Consumer<Object> exportCallback = new Consumer<Object>() {

		@Override
		public void accept(Object settings) {
			String protocol = new DatabaseExporter((ExportSettings)settings).start();
			display.notice(MessageFatality.INFO, TextSymbols.get(TextSymbols.EXPORT_LOG), protocol);
		}
				
	};
}
