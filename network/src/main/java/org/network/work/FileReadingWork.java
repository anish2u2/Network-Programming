package org.network.work;

import java.util.Calendar;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.contracts.SynchronizedStreamReaderWrapper;
import org.pattern.contracts.behavioral.Signal;
import org.worker.contracts.Work;

public class FileReadingWork implements Work, Init, Destroy {

	private SynchronizedStreamReaderWrapper reader;

	private long startTime;
	private long endTime;

	private Signal<Object> signal;

	private boolean halt = false;

	@Override
	public void destroy() {
		signal = null;
		reader = null;
	}

	@Override
	public void init() {

	}

	@Override
	public void work() {
		try {
			startTime = Calendar.getInstance().getTimeInMillis();
			while (!halt) {
				if (reader.read() == -1) {
					System.out.println("Got break statement");
					break;
				}
			}
			endTime = Calendar.getInstance().getTimeInMillis();
			signal.releaseSignal(null);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void stopWork() {
		halt = false;
	}

	public void setSynchronizedReaderWrapper(SynchronizedStreamReaderWrapper wrapper) {
		this.reader = wrapper;
	}

	public void setSignal(Signal<Object> signal) {
		this.signal = signal;
	}

	public int totalRunningTimeInMB() {
		return (int) ((endTime - startTime) / 1000);
	}

}
