package org.network.work;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.contracts.SynchronizedStreamReaderWrapper;
import org.pattern.contracts.behavioral.Signal;
import org.worker.contracts.Work;

public class FileReadingWork implements Work, Init, Destroy {

	private SynchronizedStreamReaderWrapper reader;

	private Signal<Object> signal;

	private boolean halt = false;

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public void work() {
		try {
			while (!halt) {
				if (reader.read() == -1) {
					break;
				}
			}
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

}
