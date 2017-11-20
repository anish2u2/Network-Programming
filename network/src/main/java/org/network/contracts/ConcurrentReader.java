package org.network.contracts;

import java.io.InputStream;

import org.commons.contracts.Buffer;
import org.network.io.abstracts.writer.ByteArrayOutputWriter;

public interface ConcurrentReader {

	public void setInputStream(InputStream inputStream);

	public void setBuffer(Buffer buffer);
	
	public void setByteArrayOutputStream(ByteArrayOutputWriter byteArrayOutputStream);
	
	public int read();

}
