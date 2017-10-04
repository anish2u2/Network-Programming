package org.worker.concurrent;

import org.commons.contracts.Buffer;

public class ConcurrentBuffer<T> implements org.worker.contracts.ConcurrentBuffer<T> {

	@SuppressWarnings("rawtypes")
	private static ConcurrentBuffer concurrentBuffer;

	private ConcurrentBuffer() {

	}

	@SuppressWarnings("unchecked")
	public Buffer<T> getBuffer(T type) {

		if (String.class.isAssignableFrom(type.getClass())) {
			return (Buffer<T>) new org.worker.concurrent.buffer.StringBuffer();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static ConcurrentBuffer getInstance() {
		if (concurrentBuffer == null) {
			concurrentBuffer = new ConcurrentBuffer();
		}
		return concurrentBuffer;
	}

}
