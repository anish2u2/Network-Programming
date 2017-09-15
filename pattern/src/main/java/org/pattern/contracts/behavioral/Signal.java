package org.pattern.contracts.behavioral;

/**
 * This interface will allow you to act as Signaling and will be used for signal
 * purpose.
 * 
 * @author Anish Singh
 *
 * @param <T>
 */
public interface Signal<T> {

	/**
	 * This method when invoked the the invoked thread will wait until
	 * releaseSignal is not called by any other thread.
	 * 
	 * @return
	 */
	public T aquireSignal();

	/**
	 * THis method allow you to release signal/ transmit signal whichever
	 * threads are waiting for this signal.
	 * 
	 * @param signal
	 */
	public void releaseSignal(T signal);

}
