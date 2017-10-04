package org.network.work;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import org.pattern.contracts.behavioral.Signal;
import org.worker.contracts.Work;

public class DemoWork implements Work {

	private InputStream inputStream;

	private OutputStream outputStream;

	private byte[] buffer = new byte[1024];

	Object lock = new Object();

	private long startTime;
	private long endTime;

	private Signal<Object> signal;

	@Override
	public void work() {
		int contentLength = 0;
		try {
			startTime = Calendar.getInstance().getTimeInMillis();
			synchronized (lock) {
				while ((contentLength = getInputStream().read(buffer)) != -1) {
					getOutputStream().write(buffer, 0, contentLength);
					getOutputStream().flush();
				}
			}
			endTime = Calendar.getInstance().getTimeInMillis() - startTime;
			signal.releaseSignal(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void stopWork() {

	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public int getWorkTime() {
		return (int) (endTime / 1000);
	}

	public void setSignal(Signal<Object> signal) {
		this.signal = signal;
	}

}
