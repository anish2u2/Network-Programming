package org.network.io;

import java.io.OutputStream;

import org.network.io.abstracts.AbstractStreamWriter;

public class StreamWriter extends AbstractStreamWriter {

	public StreamWriter(OutputStream out) {
		super(out);
	}

	public void setOutputStream(OutputStream outputStream) {

	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}
