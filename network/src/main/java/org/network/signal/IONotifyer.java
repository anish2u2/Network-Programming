package org.network.signal;

import org.pattern.contracts.behavioral.Notifyer;

public class IONotifyer implements Notifyer {

	private boolean flage;

	@Override
	public void notifyObject() {
		this.flage = true;
	}

	@Override
	public boolean isNotified() {

		return flage;
	}

}
