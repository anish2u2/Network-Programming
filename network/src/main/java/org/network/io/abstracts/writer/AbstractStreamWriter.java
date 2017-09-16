package org.network.io.abstracts.writer;

import java.io.DataOutputStream;

import org.network.contracts.Writer;

public abstract class AbstractStreamWriter implements Writer {

	protected DataOutputStream dataOutputStream;

	public AbstractStreamWriter() {
	}

	public void init() {

	}

	public void destroy() {
		dataOutputStream = null;
	}

	@Override
	public void write(byte[] buffer) throws Exception {
		dataOutputStream.write(buffer);
		dataOutputStream.flush();
	}

	@Override
	public void write(String data) throws Exception {
		dataOutputStream.writeUTF(data);
		dataOutputStream.flush();
	}

	@Override
	public void write(long value) throws Exception {
		dataOutputStream.writeLong(value);
		dataOutputStream.flush();
	}

	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}

}
