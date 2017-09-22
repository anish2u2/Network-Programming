package org.network.signal;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public class Signal implements org.pattern.contracts.behavioral.Signal<Object>, Init, Destroy {

	private Object lock;

	private Object signal;

	{
		init();
	}

	@Override
	public Object aquireSignal() {
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return signal;
	}

	@Override
	public void releaseSignal(Object signal) {
		synchronized (lock) {
			this.signal = signal;
			lock.notifyAll();
		}
	}

	@Override
	public void destroy() {
		lock = null;
		signal = null;
	}

	@Override
	public void init() {
		lock = new Object();
	}

}
