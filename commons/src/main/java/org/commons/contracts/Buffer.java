package org.commons.contracts;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface Buffer<T> extends Init, Destroy {

	public void push(T data);

	public T pull();

	public void close();

}
