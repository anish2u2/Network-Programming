package org.network.work.parallel;

import java.io.OutputStream;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.work.parallel.buffer.ByteArrayBuffer;
import org.worker.contracts.Work;

public class WriterWork implements Work, Init, Destroy {

	private ByteArrayBuffer buffer;
	private OutputStream stream;

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public void work() {
		try {
			byte[] data = buffer.getBytes();
			org.logger.api.Logger.getInstance().info("Started writing");
			while (!(buffer.isBufferClosed() && data == null)) {
				if (data != null)
					stream.write(data, 0, data.length);
				stream.flush();
				data = buffer.getBytes();
			}
			org.logger.api.Logger.getInstance().info("writing done");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void stopWork() {

	}

	public void setBuffer(ByteArrayBuffer buffer) {
		this.buffer = buffer;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.stream = outputStream;
	}

}
