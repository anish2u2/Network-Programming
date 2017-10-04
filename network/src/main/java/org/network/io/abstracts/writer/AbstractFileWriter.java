package org.network.io.abstracts.writer;

import java.io.DataOutputStream;
import java.io.OutputStream;

import org.network.contracts.FileWriter;

public abstract class AbstractFileWriter implements FileWriter {

	private DataOutputStream outputStream;

	private OutputStream os;

	public AbstractFileWriter() {

	}

	public AbstractFileWriter(OutputStream outputStream) {
		this.outputStream = new DataOutputStream(outputStream);
	}

	@Override
	public void init() {

	}

	@Override
	public void destroy() {
		outputStream = null;
	}

	@Override
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = new DataOutputStream(outputStream);
		this.os = outputStream;
	}

	@Override
	public void write(byte[] buffer) throws Exception {
		outputStream.write(buffer);
		outputStream.flush();
	}

	@Override
	public void write(String data) throws Exception {
		outputStream.writeUTF(data);
		outputStream.flush();
	}

	@Override
	public void write(long value) throws Exception {
		outputStream.writeLong(value);
		outputStream.flush();
	}

	@Override
	public void close() throws Exception {
		outputStream.close();
	}

	@Override
	public OutputStream getOutputStream() {
		return os;
	}

}
