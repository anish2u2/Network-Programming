package org.worker.contracts;

import org.commons.contracts.Buffer;

public interface ConcurrentBuffer<T,K> {

	Buffer<T> getBuffer(K type);

}
