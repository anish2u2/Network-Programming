package org.network.io.file.helper;

import java.io.ByteArrayOutputStream;

import org.network.contracts.SynchronizedStreamWriterWrapper;

public class StreamWriterWrapper implements SynchronizedStreamWriterWrapper {

	private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	private Object lock = new Object();

	@Override
	public void writeBytes(byte[] buffer) throws Exception {
		synchronized (lock) {
			byteArrayOutputStream.write(buffer, 0, buffer.length);
			byteArrayOutputStream.flush();
		}
	}

	@Override
	public byte[] getBufferedBytes() {
		synchronized (lock) {
			byte[] buffer = byteArrayOutputStream.toByteArray();
			this.byteArrayOutputStream = new ByteArrayOutputStream();
			return buffer;
		}
	}

	@Override
	public void reachesEOF() throws Exception {
		synchronized (lock) {
			byteArrayOutputStream.write(-1);
			byteArrayOutputStream.flush();
		}
	}

}
