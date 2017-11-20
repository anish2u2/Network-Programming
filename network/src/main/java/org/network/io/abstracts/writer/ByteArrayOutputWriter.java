package org.network.io.abstracts.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayOutputWriter extends ByteArrayOutputStream {

	private boolean isClosed = false;

	@Override
	public synchronized void writeTo(OutputStream out) throws IOException {
		super.writeTo(out);
	}

	@Override
	public void write(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		super.write(b);
	}

	@Override
	public void close() throws IOException {
		isClosed = true;
		super.close();
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
}
