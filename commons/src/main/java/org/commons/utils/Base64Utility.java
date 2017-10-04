package org.commons.utils;

import org.apache.commons.codec.binary.Base64;
import org.commons.contracts.Buffer;
import org.commons.contracts.ByteToStringConverter;
import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public class Base64Utility implements ByteToStringConverter, Init, Destroy {

	@Override
	public void convertByteToString(byte[] byteArray, Buffer<String> buffer) {
		buffer.push(Base64.encodeBase64String(byteArray));
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public byte[] convertStringToByte(Buffer<String> buffer) {
		return Base64.decodeBase64(buffer.pull());
	}

}
