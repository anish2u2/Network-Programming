package org.network.client.io.helper;

import java.io.IOException;
import java.io.OutputStream;

import org.commons.contracts.Buffer;
import org.commons.utils.Base64Utility;
import org.network.io.abstracts.writer.ByteArrayOutputWriter;
import org.process.batch.action.Process;
import org.worker.contracts.Work;
import org.worker.manager.WorkersManager;

public class ConcurrentWriter implements org.network.contracts.ConcurrentWriter {

	private Object lock = new Object();

	private OutputStream outputStream;

	private Buffer buffer;

	private ByteArrayOutputWriter byteArrayOutputWriter;

	Base64Utility bas64 = new Base64Utility();

	@Override
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
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

	private void startDeamonThread() {
		WorkersManager.getInstance().assignWroker(new Work() {

			@Override
			public void work() {
				try {
					byteArrayOutputWriter.writeTo(outputStream);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void stopWork() {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void setByteArrayOutputStream(ByteArrayOutputWriter byteArrayOutputStream) {
		this.byteArrayOutputWriter = byteArrayOutputStream;
	}

}
