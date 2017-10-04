package org.worker.contracts;

import org.commons.contracts.Buffer;

public interface ConcurrentBuffer<T> {

	Buffer<T> getBuffer(T type);

}
