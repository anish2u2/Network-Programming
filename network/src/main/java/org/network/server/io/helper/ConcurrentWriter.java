package org.network.server.io.helper;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.commons.contracts.Buffer;
import org.commons.utils.Base64Utility;

public class ConcurrentWriter implements org.network.contracts.ConcurrentWriter {

	private Object lock = new Object();

	private BufferedWriter outputStream;

	private Buffer buffer;

	Base64Utility bas64 = new Base64Utility();

	@Override
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = new BufferedWriter(new OutputStreamWriter(outputStream));
	}

	@Override
	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public int writer() {
		try {
			synchronized (lock) {
				outputStream.write((String) buffer.pull());
				outputStream.flush();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
		return 0;
	}

}
