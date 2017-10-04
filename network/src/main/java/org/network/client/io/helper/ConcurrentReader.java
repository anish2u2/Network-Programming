package org.network.client.io.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.commons.contracts.Buffer;
import org.commons.utils.Base64Utility;

public class ConcurrentReader implements org.network.contracts.ConcurrentReader {

	private Object lock = new Object();

	private Buffer buffer;

	private byte[] byteArray = new byte[4096];

	private BufferedReader inputStream;

	private Base64Utility baseUtility = new Base64Utility();

	private int value = 0;

	@Override
	public void setInputStream(InputStream inputStream) {
		this.inputStream = new BufferedReader(new InputStreamReader(inputStream));
	}

	@Override
	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public int read() {
		try {
			synchronized (lock) {
				String line = inputStream.readLine();
				if ("/n".equals(line)) {
					buffer.push(line);
					return 0;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

}
