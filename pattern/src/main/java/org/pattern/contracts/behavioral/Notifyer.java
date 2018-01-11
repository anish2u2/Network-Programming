package org.pattern.contracts.behavioral;

/**
 * This interface allows you to notify Object without blocking the object.
 * 
 * @author Anish Singh
 *
 */

public interface Notifyer {

	/**
	 * This method will be used to notify Objects.
	 * 
	 */
	public void notifyObject();

	/**
	 * This method will return true if the Object is notified.
	 * 
	 * @return
	 */
	public boolean isNotified();

	/**
	 * This method will reset the flag to false;
	 */
	public void reset();

}
