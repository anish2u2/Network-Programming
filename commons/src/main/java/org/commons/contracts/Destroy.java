package org.commons.contracts;

/**
 * This interface represents the destroy method of implementor class.
 * 
 * 
 * @author Anish Singh
 *
 */
public interface Destroy {

	/**
	 * This method will allow you to release all the resources hold by the
	 * implementor class before destroying the bean.
	 */
	public void destroy();

}
