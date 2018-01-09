package control;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SecurityHandler {
	
	public static final String ESAPI_FILENAME = "ESAPI.properties";
	public static final String ESAPI_FETCH_URL = "https://raw.githubusercontent.com/ESAPI/esapi-java-legacy/develop/configuration/esapi/ESAPI.properties";
	
	public static final String VALIDATION_FILENAME = "validation.properties";
	public static final String VALIDATION_FETCH_URL = "https://raw.githubusercontent.com/ESAPI/esapi-java-legacy/develop/configuration/esapi/validation.properties";
	
	public static final String FOLDER = System.getProperty("user.home");
	
	public static void check() throws IOException {
		
		File folder = new File(FOLDER+"/esapi");
		
		if(!folder.exists()) {
			folder.mkdirs();
		} else if(!folder.isDirectory()) {
			folder.delete();
			folder.mkdirs();
		}
		
		List<String> files = Arrays.asList(folder.list());
		
		if(!files.contains(ESAPI_FILENAME)) {
			
		}
		
		if(!files.contains(VALIDATION_FILENAME)) {
			
		}
		
		
		File esapi = new File(folder.getPath()+"/"+ESAPI_FILENAME);
		esapi.createNewFile();
			
		download(ESAPI_FETCH_URL);
			
		download(VALIDATION_FETCH_URL);
		
	}
	
	private static void download(String url) {
		
	}
	
}
