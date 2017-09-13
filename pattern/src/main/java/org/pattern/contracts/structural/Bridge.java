package org.pattern.contracts.structural;

import org.pattern.contracts.connection.Connection;
import org.pattern.contracts.connection.Wire;

/**
 * This interface represents the bridge communication between two object which
 * are implementor of {@link Wire}
 * 
 * @author Anish Singh
 *
 * @param <T>
 *            first param should implement {@ Wire}
 * @param <O>
 *            second param should implement {@ Wire}
 */
public interface Bridge<T extends Wire, O extends Wire> {

	/**
	 * This method will create bridge between two {@ Wire} Objects and return
	 * the {@ Connection} object which is responsible for making the connection
	 * on/off.
	 * 
	 * @param firstObject
	 * @param secondObject
	 * @return
	 */
	public Connection createBridge(Wire firstObject, Wire secondObject);

	/**
	 * This method will disconnect the bridge.
	 */
	public void disconnectBridge();

}
