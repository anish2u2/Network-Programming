package org.network.contracts;

import java.io.OutputStream;

import org.commons.contracts.Buffer;
import org.network.io.abstracts.writer.ByteArrayOutputWriter;

public interface ConcurrentWriter {

	public void setOutputStream(OutputStream outputStream);

	public void setBuffer(Buffer buffer);
	
	public void setByteArrayOutputStream(ByteArrayOutputWriter byteArrayOutputStream);

	public int writer();

}
