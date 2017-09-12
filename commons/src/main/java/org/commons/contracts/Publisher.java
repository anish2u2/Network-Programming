package org.commons.contracts;

/**
 * This interface represents Event publisher to the listeners which are
 * registered to this Publisher.
 * 
 * @author Anish Singh
 *
 */
public interface Publisher {

	/**
	 * This method will publish the event.
	 */
	public void publish();

}
