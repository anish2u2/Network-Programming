package org.pattern.contracts.creational;

/**
 * This Interface represents the creation of concurrent similar /dependent
 * object.
 * 
 * @author Anish Singh
 *
 * @param <T>
 */
public interface AbstractFactory<T> {

	/**
	 * This method will create a T type of object and return it.
	 * 
	 * @return
	 */
	public T create();

}
