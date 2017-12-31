package org.logger.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import org.commons.contracts.ApplicationPropertyReader;
import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

/**
 * This class will allow you to log info,warn,error,exception.
 * 
 * @author Anish Singh
 *
 */

public class Logger implements Init, Destroy {

	private static Logger logger;

	private String loggingStream;

	private boolean isLogInfoEnabled;

	private boolean isLogErrorEnabled;

	private boolean isLogWarnEnabled;

	private String loggerFileName;

	private static String prefixFilePath;

	private OutputStreamWriter dataOutputStream;

	{
		init();
	}

	private Logger() {
		if (logger != null) {
			throw new RuntimeException("Object already created.");
		}
	}

	/**
	 * This will set the prefix file path to generate the applog file there.
	 * 
	 * @param prefixFilePath
	 */
	public void setPrefixFilePath(String prefixFilePath) {
		Logger.prefixFilePath = prefixFilePath;
	}

	/**
	 * Return the instance of the Logger.
	 * 
	 * @return
	 */
	public static Logger getInstance() {
		if (logger == null) {
			logger = new Logger();
		}
		return logger;
	}

	public void destroy() {
		logger = null;
		loggerFileName = null;
		loggingStream = null;
	}

	public void init() {
		ApplicationPropertyReader propReader = org.commons.properties.ApplicationPropertyReader.getInstance();
		loggingStream = propReader.getMessage("org.logger.default.logging.stream");
		System.out
				.println("org.logger.default.logging.info" + propReader.getMessage("org.logger.default.logging.info"));
		isLogInfoEnabled = "true".equals(propReader.getMessage("org.logger.default.logging.info")) ? true : false;
		isLogWarnEnabled = "true".equals(propReader.getMessage("org.logger.default.logging.warn")) ? true : false;
		isLogErrorEnabled = "true".equals(propReader.getMessage("org.logger.default.logging.error")) ? true : false;
		loggerFileName = propReader.getMessage("org.logger.default.logging.file.name");
		System.out.println("Log file Name:" + loggerFileName);
		System.out.println("Log info Enabled:" + isLogInfoEnabled);
		if (loggingStream != null && "CONSOLE".equals(loggingStream)) {
			dataOutputStream = new OutputStreamWriter(System.out);
		} else {
			try {
				if (prefixFilePath != null) {
					if (!prefixFilePath.endsWith(File.separator))
						prefixFilePath = prefixFilePath + File.separator;
					loggerFileName = prefixFilePath + loggerFileName;
					System.out.println("Logger file path:" + loggerFileName);
				}
				File logFile = new File(loggerFileName);
				if (!logFile.exists())
					logFile.createNewFile();
				dataOutputStream = new OutputStreamWriter(new FileOutputStream(logFile));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Writes the message to the configured stream.
	 * 
	 * @param message
	 */
	public void info(Object message) {
		if (isLogInfoEnabled)
			write(getLocalTime("Info") + message.toString());
	}

	private String getLocalTime(String typeOfmessage) {
		return "[" + typeOfmessage + " " + Calendar.getInstance().getTime() + "] ";
	}

	/**
	 * Writes the warning to the configured stream.
	 * 
	 * @param message
	 */
	public void warn(Object message) {
		if (isLogWarnEnabled)
			write(getLocalTime("Warn") + message.toString());
	}

	/**
	 * writes the error message to the configured stream.
	 * 
	 * @param message
	 */
	public void error(Object message) {
		if (isLogErrorEnabled)
			write(getLocalTime("Error") + message.toString());
	}

	/**
	 * Writes the exception message to the configured stream.
	 * 
	 * @param ex
	 */
	public void exception(Exception ex) {
		String message = getLocalTime("Exception");
		for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
			message = message + stackTraceElement.getClassName() + ".";
			message = message + stackTraceElement.getMethodName() + ".";
			message = message + stackTraceElement.getLineNumber() + "/n";
		}
		write(message);
	}

	private void write(String message) {
		try {
			dataOutputStream.write(message + "/n");
			dataOutputStream.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
