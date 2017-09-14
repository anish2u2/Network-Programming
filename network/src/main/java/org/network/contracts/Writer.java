package org.network.contracts;

import java.io.OutputStream;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface Writer extends Init, Destroy {

	public void setOutputStream(OutputStream outputStream);

	public void write(byte[] buffer);

	public void write(String data);

}
