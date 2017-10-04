package org.network.client.io.helper;

import java.io.OutputStream;

import org.commons.contracts.Buffer;
import org.commons.utils.Base64Utility;

public class ConcurrentWriter implements org.network.contracts.ConcurrentWriter {

	private Object lock = new Object();

	private OutputStream outputStream;

	private Buffer buffer;

	Base64Utility bas64 = new Base64Utility();

	@Override
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public int writer() {
		try {
			synchronized (lock) {
				outputStream.write(bas64.convertStringToByte(buffer));
				outputStream.flush();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
		return 0;
	}

}
