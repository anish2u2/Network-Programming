package org.commons.utils;

import java.io.InputStream;

public class Util {

	public static InputStream getResourceAsStream(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	}

}
