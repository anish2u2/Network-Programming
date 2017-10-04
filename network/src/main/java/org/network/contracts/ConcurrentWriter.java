package org.network.contracts;

import java.io.OutputStream;

import org.commons.contracts.Buffer;

public interface ConcurrentWriter {

	public void setOutputStream(OutputStream outputStream);

	public void setBuffer(Buffer buffer);

	public int writer();

}
