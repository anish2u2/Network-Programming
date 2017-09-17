package org.network.file;

import java.io.File;
import java.io.IOException;

public class Filehelper {

	public static File createFile(String toLocation, String fileName) {

		File file = new File(createDirectory(toLocation), fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	public static File createDirectory(String directory) {
		File dir = new File(directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		return dir;
	}

}
