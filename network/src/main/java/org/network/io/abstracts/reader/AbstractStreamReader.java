package org.network.io.abstracts.reader;

import java.io.DataInputStream;

import org.network.contracts.Reader;

public abstract class AbstractStreamReader implements Reader {

	protected DataInputStream dataInputStream;

	@Override
	public void init() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public int read(byte[] buffer) throws Exception {
		return dataInputStream.read(buffer);
	}

	@SuppressWarnings("deprecation")
	@Override
	public String readLine() throws Exception {

		return dataInputStream.readLine();
	}

	@Override
	public String read() throws Exception {
		return dataInputStream.readUTF();
	}

}
