package org.network.signal;

import org.network.contracts.CommunicationChannel;
import org.pattern.contracts.behavioral.Signal;

public class IncommingSignal implements Signal<CommunicationChannel> {

	private CommunicationChannel communicationChannel;

	private Object lock = new Object();

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

}
