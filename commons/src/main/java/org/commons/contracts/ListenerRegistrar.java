package org.commons.contracts;

/**
 * This interface represents the registration of the listeners.
 * 
 * @author Anish Singh
 *
 */
public interface ListenerRegistrar {

	/**
	 * This method allow to registered listener passed as parameter.
	 * 
	 * @param listener
	 */
	public void registerListener(Listener listener);

}
