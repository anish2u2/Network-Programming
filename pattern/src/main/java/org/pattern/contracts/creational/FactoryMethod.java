package org.pattern.contracts.creational;

/**
 * This interface is a representation of the factory pattern.
 * 
 * @author Anish Singh
 *
 * @param <T>
 */
public interface FactoryMethod<T> {

	/**
	 * This method will create T type of object. and return the Object.
	 * 
	 * @return
	 */
	public T create();

}
