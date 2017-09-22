package org.network.io.abstracts.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ByteArrayOutputWriter extends ByteArrayOutputStream {

	@Override
	public synchronized void writeTo(OutputStream out) throws IOException {
		super.writeTo(out);
	}

	@Override
	public void write(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		super.write(b);
	}

}
