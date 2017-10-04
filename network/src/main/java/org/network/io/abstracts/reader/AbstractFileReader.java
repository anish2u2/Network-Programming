package org.network.io.abstracts.reader;

import java.io.DataInputStream;
import java.io.InputStream;

import org.network.contracts.FileReader;

public abstract class AbstractFileReader implements FileReader {

	private InputStream inputStream;

	private DataInputStream dis;

	public AbstractFileReader() {

	}

	public AbstractFileReader(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public void init() {

	}

	@Override
	public void destroy() {
		inputStream = null;
	}

	@Override
	public int read(byte[] buffer) throws Exception {

		return inputStream.read(buffer);
	}

	@SuppressWarnings("deprecation")
	@Override
	public String readLine() throws Exception {

		return dis.readLine();
	}

	@Override
	public String read() throws Exception {

		return dis.readUTF();
	}

	@Override
	public void setInputStream(InputStream inputStream) {
		this.inputStream = new DataInputStream(inputStream);
		this.dis = new DataInputStream(inputStream);
	}

	@Override
	public long readLong() throws Exception {

		return dis.readLong();
	}

	@Override
	public void close() throws Exception {
		inputStream.close();
	}

	@Override
	public InputStream getInputStream() {
		return inputStream;
	}
}
