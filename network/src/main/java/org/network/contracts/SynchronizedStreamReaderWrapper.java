package org.network.contracts;

import java.io.InputStream;

public interface SynchronizedStreamReaderWrapper {

	public int read() throws Exception;

	public void setInputStream(InputStream inputStream);

	public void setSynchronizedByteWriter(SynchronizedStreamWriterWrapper streamWriterWrapper);

	public void sendEOFSignal() throws Exception;

	//public int readUTF() throws Exception;

}
