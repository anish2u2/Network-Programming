package org.network.client.io.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.commons.contracts.Buffer;
import org.commons.utils.Base64Utility;
import org.network.io.abstracts.writer.ByteArrayOutputWriter;
import org.process.batch.action.Process;
import org.worker.contracts.Work;

public class ConcurrentReader implements org.network.contracts.ConcurrentReader {

	private Object lock = new Object();

	private Buffer buffer;

	private byte[] byteArray = new byte[4096];

	private InputStream inputStream;

	private Base64Utility baseUtility = new Base64Utility();

	private int value = 0;

	private ByteArrayOutputWriter byteArrayOutputWriter;

	@Override
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public int read() {
		try {
			startDeamonThread();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	private void startDeamonThread() {
		org.process.batch.contracts.Process process = new Process();
		process.startProcess(new Work() {

			@Override
			public void work() {
				byte[] byt=new byte[1024];
				try {
					while (inputStream.read(byt)!=-1) {
						byteArrayOutputWriter.write(byt);
					}
					byteArrayOutputWriter.setClosed(true);
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
