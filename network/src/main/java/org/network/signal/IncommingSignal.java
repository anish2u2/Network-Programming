package org.network.signal;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.contracts.CommunicationChannel;
import org.pattern.contracts.behavioral.Signal;

public class IncommingSignal implements Signal<CommunicationChannel>, Init, Destroy {

	private CommunicationChannel communicationChannel;

	private Object lock;

	{
		init();
	}

	private static IncommingSignal incommingSignal;

	private IncommingSignal() {

	}

	public static Signal<CommunicationChannel> getInstance() {
		if (incommingSignal == null) {
			incommingSignal = new IncommingSignal();
		}
		return incommingSignal;
	}

	@Override
	public CommunicationChannel aquireSignal() {
		synchronized (lock) {
			try {
				lock.wait();
				CommunicationChannel communicationChannel = this.communicationChannel;
				this.communicationChannel = null;
				return communicationChannel;
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void releaseSignal(CommunicationChannel channel) {
		synchronized (lock) {
			this.communicationChannel = channel;
			lock.notifyAll();
		}
	}

	@Override
	public void destroy() {
		synchronized (lock) {
			lock.notifyAll();
		}
		lock = null;
		communicationChannel = null;
		incommingSignal = null;
	}

	@Override
	public void init() {
		lock = new Object();

	}

}
