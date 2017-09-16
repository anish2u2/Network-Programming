package org.network.io.writer;

import java.io.DataOutputStream;
import java.io.OutputStream;

import org.network.io.abstracts.writer.AbstractStreamWriter;

public class StreamWriter extends AbstractStreamWriter {

	public StreamWriter(OutputStream outputStream) {
		dataOutputStream = new DataOutputStream(outputStream);
	}

	public void setOutputStream(OutputStream outputStream) {
		dataOutputStream = new DataOutputStream(outputStream);
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
	public void close() throws Exception {
		dataOutputStream.close();
	}

}
