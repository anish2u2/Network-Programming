package org.pattern.contracts.creational;

/**
 * This interface will represents the ObjectPooling pattern which will be
 * responsible for managing T Type object.
 * 
 * @author Anish Singh
 *
 * @param <T>
 */
public interface ObjectPooling<T> {

	/**
	 * This method will return T type of object from the pool if pool is not
	 * having any object or Objects are busy then it will create new Object and
	 * return.
	 * 
	 * @return
	 */
	public T fetch();

	/**
	 * This method will expire the passed object immediately and remove from the
	 * pool.
	 * 
	 * @param object
	 */
	public void expire(T object);

}
