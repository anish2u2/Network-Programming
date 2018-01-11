package org.network.work.parallel;

import java.io.InputStream;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.work.parallel.buffer.ByteArrayBuffer;
import org.worker.contracts.Work;

public class ReaderWork implements Work, Init, Destroy {

	private InputStream inputStream;

	private ByteArrayBuffer buffer;

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public void work() {
		try {
			byte[] data = new byte[1024];
			org.logger.api.Logger.getInstance().info("Reading started.");
			while (inputStream.read(data) != -1) {
				buffer.write(data);
			}
			buffer.closeBuffer();
			org.logger.api.Logger.getInstance().info("Reading completed");
		} catch (Exception ex) {
			ex.printStackTrace();
			buffer.closeBuffer();
		} finally {
			buffer.closeBuffer();
			try {
				inputStream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void stopWork() {

	}

	public void setByteArrayBuffer(ByteArrayBuffer buffer) {
		this.buffer = buffer;
	}

}
