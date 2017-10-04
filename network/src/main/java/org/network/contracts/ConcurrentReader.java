package org.network.contracts;

import java.io.InputStream;

import org.commons.contracts.Buffer;

public interface ConcurrentReader {

	public void setInputStream(InputStream inputStream);

	public void setBuffer(Buffer buffer);

	public int read();

}
