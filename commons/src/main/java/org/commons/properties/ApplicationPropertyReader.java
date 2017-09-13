package org.commons.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public class ApplicationPropertyReader implements org.commons.contracts.ApplicationPropertyReader, Init, Destroy {

	private static ApplicationPropertyReader applicationPropertyReader;

	private Properties properties;

	private ApplicationPropertyReader() {

	}

	static {

		if (applicationPropertyReader == null) {
			applicationPropertyReader = new ApplicationPropertyReader();
			applicationPropertyReader.init();
		}

	}

	public static org.commons.contracts.ApplicationPropertyReader getInstance() {
		return applicationPropertyReader;
	}

	public String getMessage(String key) {

		return properties.getProperty(key);
	}

	public String getMessage(String key, Locale locale) {

		return null;
	}

	public String getMessage(String key, Object[] args, Locale locale) {

		return null;
	}

	public void destroy() {
		properties.clear();
		applicationPropertyReader = null;
	}

	public void init() {
		try (InputStream stream = new FileInputStream(new File(PROP_FILE_NAME + ".properties"))) {
			properties = new Properties();
			properties.load(stream);
			String propFileName = properties.getProperty("prop.files.name");
			if (propFileName != null) {
				String[] splitedPropFileName = propFileName.split(",");
				for (String prop : splitedPropFileName) {
					try (InputStream subPropStream = new FileInputStream(new File(prop + ".properties"))) {
						properties.load(subPropStream);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
