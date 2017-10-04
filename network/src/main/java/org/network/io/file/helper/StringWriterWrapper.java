package org.network.io.file.helper;

import org.network.contracts.SynchronizedStreamWriterWrapper;

public class StringWriterWrapper implements SynchronizedStreamWriterWrapper {

	@Override
	public void writeBytes(byte[] buffer) throws Exception {

	}

	@Override
	public byte[] getBufferedBytes() {

		return null;
	}

	@Override
	public void reachesEOF() throws Exception {

	}

}
