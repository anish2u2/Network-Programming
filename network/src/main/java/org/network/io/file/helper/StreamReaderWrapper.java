package org.network.io.file.helper;

import java.io.InputStream;

import org.network.contracts.SynchronizedStreamReaderWrapper;
import org.network.contracts.SynchronizedStreamWriterWrapper;

public class StreamReaderWrapper implements SynchronizedStreamReaderWrapper {

	private InputStream stream;
	int value;
	byte[] buffer = new byte[1024];
	private SynchronizedStreamWriterWrapper byteArrayWriter;

	private Object lock = new Object();

	@Override
	public int read() throws Exception {
		synchronized (lock) {
			value = stream.read(buffer);
			byteArrayWriter.writeBytes(buffer);
			if (value == -1)
				byteArrayWriter.reachesEOF();
			return value;
		}
	}

	public void sendEOFSignal() throws Exception {
		synchronized (lock) {
			byteArrayWriter.reachesEOF();
		}
	}

	@Override
	public void setInputStream(InputStream inputStream) {
		this.stream = inputStream;
	}

	@Override
	public void setSynchronizedByteWriter(SynchronizedStreamWriterWrapper streamWriterWrapper) {
		byteArrayWriter = streamWriterWrapper;

	}

}
