package org.network.contracts;

import java.io.File;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface FileWriter extends Writer, Init, Destroy {

	public void writeFile(File file);

	public void writeFile(String fullyQualifiedFilePath);

}
