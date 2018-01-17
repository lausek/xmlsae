package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class SecurityHandler {

	public static final String ESAPI_FILENAME = "ESAPI.properties";
	public static final String ESAPI_FETCH_URL = "https://raw.githubusercontent.com/ESAPI/esapi-java-legacy/develop/configuration/esapi/ESAPI.properties";

	public static final String VALIDATION_FILENAME = "validation.properties";
	public static final String VALIDATION_FETCH_URL = "https://raw.githubusercontent.com/ESAPI/esapi-java-legacy/develop/configuration/esapi/validation.properties";

	public static final String FOLDER = System.getProperty("user.home");

	public static void check() throws IOException {

		File folder = new File(FOLDER + "/esapi");

		if (!folder.exists()) {
			folder.mkdirs();
		} else if (!folder.isDirectory()) {
			folder.delete();
			folder.mkdirs();
		}

		List<String> files = Arrays.asList(folder.list());

		if (!files.contains(ESAPI_FILENAME)) {

			writeFile(folder.getAbsolutePath(), ESAPI_FILENAME, ESAPI_FETCH_URL);

		}

		if (!files.contains(VALIDATION_FILENAME)) {
			
			writeFile(folder.getAbsolutePath(), VALIDATION_FILENAME, VALIDATION_FETCH_URL);
			
		}
		
	}

	private static void writeFile(String folderPath, String filename, String fetchUrl) throws IOException {

		String targetPath = folderPath + "/" + filename;
		String localPath = "properties/" + filename;
		
		File outfile = new File(targetPath);
		outfile.createNewFile();

		try (FileOutputStream out = new FileOutputStream(outfile)) {
			
			try {
				copy(read(localPath), out);				
			} catch(IOException e) {
				copy(download(fetchUrl), out);
			}
			
		}

	}
	
	private static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[16];
		while(-1 < in.read(buffer)) {
			out.write(buffer);
		}
	}

	private static InputStream download(String url) throws IOException {
		URL request = new URL(url);
		return request.openStream();
	}

	private static InputStream read(String uri) throws FileNotFoundException {
		File local = new File(uri);
		return new FileInputStream(local);
	}

}
