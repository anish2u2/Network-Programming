package org.pattern.contracts.connection;

/**
 * This interface will represents the actual Connection instance of Bridge
 * pattern which will allow to control the on/off of bridge communication.
 * 
 * @author Anish Singh
 *
 */
public interface Connection {

	/**
	 * This method will on the bridge connection. So, the connected objects can
	 * communicate.
	 */
	public void onConnection();

	/**
	 * This method will off the connection but the bridge is not destroyed.
	 */
	public void offConnection();

}
