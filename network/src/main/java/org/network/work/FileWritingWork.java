package org.network.work;

import java.io.OutputStream;
import java.util.Calendar;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.contracts.SynchronizedStreamWriterWrapper;
import org.worker.contracts.Work;

public class FileWritingWork implements Work, Init, Destroy {

	private SynchronizedStreamWriterWrapper writer;

	private Object lock = new Object();

	private OutputStream outputStream;

	private long startTime;
	private long endTime;

	private int numberOfWorkersExecuting;

	private boolean halt = false;

	@Override
	public void destroy() {
		outputStream = null;
		writer = null;
	}

	@Override
	public void init() {

	}

	@Override
	public void work() {
		try {
			numberOfWorkersExecuting++;
			startTime = Calendar.getInstance().getTimeInMillis();
			while (!halt) {
				synchronized (lock) {
					byte[] buffer = writer.getBufferedBytes();
					if (buffer != null) {
						outputStream.write(buffer);
						outputStream.flush();
					}
				}
			}
			endTime = Calendar.getInstance().getTimeInMillis();
			System.out.println("value-1:" + numberOfWorkersExecuting);
			numberOfWorkersExecuting--;
			System.out.println("value:" + numberOfWorkersExecuting);
			if (numberOfWorkersExecuting == 0) {
				System.out.println("closing connection.");
//				outputStream.write(-1);
//				outputStream.flush();
//				outputStream.close();
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

	public int totalRunningTimeInMB() {
		return (int) ((endTime - startTime) / 1000);
	}

}
