package org.network.contracts;

public interface SynchronizedStreamWriterWrapper {

	public void writeBytes(byte[] buffer) throws Exception;

	public byte[] getBufferedBytes();
	
	public void reachesEOF() throws Exception ;

	//public void writeString(String utfData) throws Exception;

	//public String getUTFString();

}
