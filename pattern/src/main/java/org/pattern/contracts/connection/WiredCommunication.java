package org.pattern.contracts.connection;

/**
 * This interface represents the Communication methods of wire.
 * 
 * @author Anish Singh
 *
 */
public interface WiredCommunication {

	/**
	 * This method will send data to the wire.
	 * 
	 * @param data
	 */
	public void send(Object data);

	/**
	 * This method will receive data from the connected wire.
	 * 
	 * @return
	 */
	public Object receive();

}
