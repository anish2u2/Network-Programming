package org.network.contracts;

public interface SynchronizedStreamWriterWrapper {

	public void writeBytes(byte[] buffer) throws Exception;

	public byte[] getBufferedBytes();

}
