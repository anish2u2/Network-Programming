package org.network.signal;

import org.pattern.contracts.behavioral.Notifyer;

/**
 * {@link Notifyer} Implementation.
 * 
 * @author Anish Singh
 *
 */
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

	@Override
	public void reset() {
		flage = false;
	}

}
