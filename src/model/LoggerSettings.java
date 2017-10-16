package model;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class LoggerSettings {
	
	public static void initLogger(Logger logger, String logFile){
		PatternLayout layout = new PatternLayout("%d %p - %m%n");
		ConsoleAppender consoleAppender = new ConsoleAppender( layout );
		logger.addAppender( consoleAppender );
		RollingFileAppender fileAppender;
		try {
			fileAppender = new RollingFileAppender(layout, logFile);
			fileAppender.setMaxFileSize("10MB");
			fileAppender.setMaxBackupIndex(10);
			logger.addAppender( fileAppender );
			// ALL | DEBUG | INFO | WARN | ERROR | FATAL | OFF
			logger.setLevel( Level.DEBUG);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	

}