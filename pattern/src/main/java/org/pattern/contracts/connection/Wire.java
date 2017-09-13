package org.pattern.contracts.connection;

/**
 * This interface will represents a single wire for a connection.
 * 
 * @author Anish Singh
 *
 */
public interface Wire {

	/**
	 * This method will return the {@ WiredCommunication} object which will be
	 * responsible for actual communication.
	 * 
	 * @return
	 */
	public WiredCommunication getCommunicationDetails();

}
