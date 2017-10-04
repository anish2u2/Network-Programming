package org.network.server.io.helper;

import java.io.DataInputStream;
import java.io.InputStream;

import org.commons.contracts.Buffer;
import org.commons.utils.Base64Utility;

public class ConcurrentReader implements org.network.contracts.ConcurrentReader {

	private Object lock = new Object();

	private Buffer buffer;

	private byte[] byteArray = new byte[4096];

	private DataInputStream inputStream;

	private Base64Utility baseUtility = new Base64Utility();

	private int value = 0;

	@Override
	public void setInputStream(InputStream inputStream) {
		this.inputStream = new DataInputStream(inputStream);
	}

	@Override
	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public int read() {
		try {
			synchronized (lock) {
				value = inputStream.read(byteArray);
				baseUtility.convertByteToString(byteArray, buffer);
				if (value == -1)
					buffer.close();
				return 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

}
