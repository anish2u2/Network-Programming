package org.network.io.file.helper;

import java.io.InputStream;

import org.network.contracts.SynchronizedStreamReaderWrapper;
import org.network.contracts.SynchronizedStreamWriterWrapper;

public class StringReaderWrapper implements SynchronizedStreamReaderWrapper {

	@Override
	public int read() throws Exception {

		return 0;
	}

	@Override
	public void setInputStream(InputStream inputStream) {

	}

	@Override
	public void setSynchronizedByteWriter(SynchronizedStreamWriterWrapper streamWriterWrapper) {

	}

	@Override
	public void sendEOFSignal() throws Exception {

	}

}
