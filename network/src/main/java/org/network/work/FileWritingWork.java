package org.network.work;

import java.io.OutputStream;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.contracts.SynchronizedStreamWriterWrapper;
import org.worker.contracts.Work;

public class FileWritingWork implements Work, Init, Destroy {

	private SynchronizedStreamWriterWrapper writer;

	private OutputStream outputStream;

	private boolean halt = false;

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public void work() {
		try {
			while (!halt) {
				synchronized (outputStream) {
					byte[] buffer = writer.getBufferedBytes();
					if (buffer != null) {
						outputStream.write(buffer);
						outputStream.flush();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void stopWork() {
		halt = true;
	}

	public void setSynchronizedWriterWrapper(SynchronizedStreamWriterWrapper streamWriterWrapper) {
		this.writer = streamWriterWrapper;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

}
