package org.commons.contracts;

public interface ByteToStringConverter {

	public void convertByteToString(byte[] byteArray, org.commons.contracts.Buffer<String> buffer);

	public byte[] convertStringToByte(Buffer<String> buffer);

}
