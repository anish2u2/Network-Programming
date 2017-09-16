package org.network.io.reader;

import java.io.DataInputStream;
import java.io.InputStream;

import org.network.io.abstracts.reader.AbstractStreamReader;

public class StreamReader extends AbstractStreamReader {

	public StreamReader() {

	}

	public StreamReader(InputStream inputStream) {
		dataInputStream = new DataInputStream(inputStream);
	}

	@Override
	public void setInputStream(InputStream inputStream) {
		dataInputStream = new DataInputStream(inputStream);
	}

	@Override
	public void init() {

		super.init();
	}

	@Override
	public void destroy() {

		super.destroy();
	}

	@Override
	public long readLong() throws Exception {
		return dataInputStream.readLong();
	}

	@Override
	public void close() throws Exception {
		dataInputStream.close();
	}

}
