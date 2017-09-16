package org.network.contracts;

import java.io.InputStream;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface Reader extends Init, Destroy {

	public int read(byte[] buffer) throws Exception;

	public String readLine() throws Exception;

	public String read() throws Exception;

	public void setInputStream(InputStream inputStream);

	public long readLong() throws Exception;

	public void close() throws Exception;
}
