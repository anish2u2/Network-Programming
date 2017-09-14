package org.network.io.abstracts;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.network.contracts.Writer;

public abstract class AbstractStreamWriter extends OutputStreamWriter implements Writer {

	public AbstractStreamWriter(OutputStream out) {
		super(out);
	}

	public void init() {

	}

	public void destroy() {

	}

	public void write(byte[] buffer) {
		
	}

	public void write(String data) {

	}

}
