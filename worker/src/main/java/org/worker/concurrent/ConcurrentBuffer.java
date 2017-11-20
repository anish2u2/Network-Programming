package org.worker.concurrent;

import org.commons.contracts.Buffer;

public class ConcurrentBuffer<T,K> implements org.worker.contracts.ConcurrentBuffer<T,K> {

	@SuppressWarnings("rawtypes")
	private static ConcurrentBuffer concurrentBuffer;

	

	@SuppressWarnings("unchecked")
	public Buffer<T> getBuffer(K type) {
		System.out.println("class Name:"+type.getClass().getClass());
		System.out.println("class Name:"+type.getClass().getClass());
		if ("String".equals(type)) {
			System.out.println("String class is assignable..");
			return (Buffer<T>) new org.worker.concurrent.buffer.StringBuffer();
			
		}
		System.out.println("String class is not assingable.");
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
