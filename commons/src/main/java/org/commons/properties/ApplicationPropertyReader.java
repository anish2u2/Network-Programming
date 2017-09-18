package org.commons.properties;

import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.commons.utils.Util;

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
		try (InputStream stream = Util.getResourceAsStream(PROP_FILE_NAME + ".properties")) {
			properties = new Properties();
			properties.load(stream);
			String propFileName = properties.getProperty("prop.files.name");
			if (propFileName != null) {
				String[] splitedPropFileName = propFileName.split(",");
				for (String file : splitedPropFileName)
					System.out.println("Props:" + file);
				for (String prop : splitedPropFileName) {
					try (InputStream subPropStream = Util.getResourceAsStream(prop + ".properties")) {
						Properties pro = new Properties();
						pro.load(subPropStream);
						for (Object key : pro.keySet()) {
							properties.put(key, pro.get(key));
						}
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
