package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class TextSymbols {

	public static final int APPLICATION_NAME = 1, FILES_DOWNLOAD_FAILED = 2, EXPORT_LOG = 3, IMPORT_LOG = 4,
			SELECT_AT_LEAST = 5, EXPORT_DATABASE = 6, IMPORT_DATABASE = 7, EXPORT = 8, IMPORT = 9, EXPORT_DOTS = 10,
			EXPORT_DATA = 11, EXPORT_DEFINITION = 12, EXPORT_SAVE_TO = 13, EXPORT_SAVE_TO_SELECT = 14,
			IMPORT_NO_FILES = 15, LOGIN_USER = 16, LOGIN_HOST = 17, LOGIN_PASSWORD = 18, LOGIN_SUBMIT = 19,
			LOGIN_CONNECTION_FAILED = 20, MESSAGE_DETAILS = 21, MESSAGE_CONFIRM = 22, SELECTION_SEARCH = 23,
			STATUS_CONNECTED = 24, STATUS_DATABASES = 25;

	protected static Properties text;

	static {
		text = new Properties();
	}

	public static void load(String language) {

		if (language == null || language.isEmpty()) {
			return;
		}

		try {
			InputStream inp = new FileInputStream(new File("lang/" + language.toLowerCase()));
			text.load(inp);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}

	public static String get(int symbol) {
		return text.getProperty(String.valueOf(symbol));
	}

}
