package org.pattern.contracts.behavioral;

/**
 * This interface allow implementor class to act as like mediator between two
 * Objects.
 * 
 * T has to be the DataType as what they are communicating to each other.
 * 
 * @author Anish Singh
 *
 * @param <T>
 */

public interface Mediator<T> {

	/**
	 * This method allow talk to other object which is using this mediator.
	 * 
	 * @param data
	 */
	public void talk(T data);

	/**
	 * This method allow to listen those objects which are using this mediator
	 * to listen when other object is talking using this mediator.
	 * 
	 * @return
	 */
	public T listen();

}
