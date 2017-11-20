package org.network.server.io.helper;

import java.io.IOException;
import java.io.OutputStream;

import org.commons.contracts.Buffer;
import org.commons.utils.Base64Utility;
import org.network.io.abstracts.writer.ByteArrayOutputWriter;
import org.worker.contracts.Work;
import org.worker.manager.WorkersManager;

public class ConcurrentWriter implements org.network.contracts.ConcurrentWriter {

	private Object lock = new Object();

	private OutputStream outputStream;

	private ByteArrayOutputWriter byteArrayOutputStream;

	private Buffer buffer;

	Base64Utility bas64 = new Base64Utility();

	@Override
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	public void setByteArrayOutputStream(ByteArrayOutputWriter byteArrayOutputStream) {
		this.byteArrayOutputStream = byteArrayOutputStream;
	}

	@Override
	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public int writer() {
		try {
			startDeamonThread();
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
		return 0;
	}

	private boolean isBufferColsed() {
		return buffer.isBufferClosed();
	}

	private void startDeamonThread() {
		WorkersManager.getInstance().assignWroker(new Work() {

			@Override
			public void work() {
				try {
					while (!byteArrayOutputStream.isClosed()) {

						if (byteArrayOutputStream.size() > 0)
							byteArrayOutputStream.writeTo(outputStream);

					}
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void stopWork() {

			}
		});
	}

}
