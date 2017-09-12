package org.network.contracts;

import java.io.InputStream;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface FileReader extends Reader, Init, Destroy {

	public void readFile(String toFileLocation);

	public InputStream readFile();

}
