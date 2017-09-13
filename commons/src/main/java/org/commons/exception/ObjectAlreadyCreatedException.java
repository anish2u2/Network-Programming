package org.commons.exception;

public class ObjectAlreadyCreatedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 35332532231L;

	public ObjectAlreadyCreatedException() {
		super("Object Already Created.");
	}

}
