package org.network.work;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.worker.contracts.Work;

public class IOWork implements Init, Work, Destroy {

	private InputStream inputStream;

	private int value;

	private OutputStream outputStream;

	private byte[] data = new byte[1024];

	private Object lock = new Object();

	private int threadCounter;

	@Override
	public void destroy() {

	}

	@Override
	public void work() {
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();
			threadCounter++;
			while (readData() != -1) {

			}
			threadCounter--;
			if (threadCounter == 0)
				outputStream.close();
			System.out.println("Time tacken to complete:" + (Calendar.getInstance().getTimeInMillis() - startTime));
			System.out.println("Memory used:"
					+ ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000) + " M.B");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int readData() throws IOException {
		synchronized (lock) {
			value = inputStream.read(data);
			outputStream.write(data);
			outputStream.flush();
		}
		return value;

	}

	@Override
	public void stopWork() {
	}

	@Override
	public void init() {

	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;

	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

}
