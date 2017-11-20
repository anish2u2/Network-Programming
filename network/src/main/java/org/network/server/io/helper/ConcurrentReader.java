package org.network.server.io.helper;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.commons.contracts.Buffer;
import org.commons.utils.Base64Utility;
import org.network.io.abstracts.writer.ByteArrayOutputWriter;
import org.worker.contracts.Work;
import org.worker.manager.WorkersManager;

public class ConcurrentReader implements org.network.contracts.ConcurrentReader {

	private Object lock = new Object();

	private Buffer buffer;

	private byte[] byteArray = new byte[4096];

	private DataInputStream inputStream;

	private Base64Utility baseUtility = new Base64Utility();

	private ByteArrayOutputWriter byteArrayOutputWriter;

	private int value = 0;

	public ByteArrayOutputWriter getByteArrayOutputWriter() {
		return byteArrayOutputWriter;
	}

	public void setByteArrayOutputWriter(ByteArrayOutputWriter byteArrayOutputWriter) {
		this.byteArrayOutputWriter = byteArrayOutputWriter;
	}

	@Override
	public void setInputStream(InputStream inputStream) {
		this.inputStream = new DataInputStream(inputStream);
	}

	@Override
	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public int read() {
		try {
			synchronized (lock) {
				value = inputStream.read(byteArray);
				byteArrayOutputWriter.write(byteArray);
				if (value == -1)
					byteArrayOutputWriter.setClosed(true);
				return 0;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	private void startDeamonThread() {
		WorkersManager.getInstance().assignWroker(new Work() {

			@Override
			public void work() {
				while (value != -1) {
					try {
						value = inputStream.read(byteArray);
						byteArrayOutputWriter.write(byteArray);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				byteArrayOutputWriter.setClosed(true);
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
