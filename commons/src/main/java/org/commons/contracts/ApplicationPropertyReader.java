package org.commons.contracts;

import java.util.Locale;

/**
 * This interface allow you to read application properties .
 * 
 * @author Anish Singh
 *
 */
public interface ApplicationPropertyReader {

	public String PROP_FILE_NAME = "commons";

	public String getMessage(String key);

	public String getMessage(String key, Locale locale);

	public String getMessage(String key, Object[] args, Locale locale);

}
