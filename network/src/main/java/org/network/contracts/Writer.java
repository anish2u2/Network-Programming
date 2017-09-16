package org.network.contracts;

import java.io.OutputStream;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface Writer extends Init, Destroy {

	public void setOutputStream(OutputStream outputStream);

	public void write(byte[] buffer) throws Exception;

	public void write(String data) throws Exception;

	public void write(long value) throws Exception;

	public void close() throws Exception;
}
