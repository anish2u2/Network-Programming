package org.worker.concurrent.buffer;

import org.commons.contracts.Buffer;
import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public class StringBuffer implements Buffer<String>, Init, Destroy {

	private Object lock;

	private StringBuilder builder;

	public void push(String data) {

		synchronized (lock) {
			builder.append(data);
		}

	}

	public String pull() {
		synchronized (lock) {
			String readedData = builder.toString();
			builder.delete(0, builder.length());
			return readedData;
		}
	}

	public void destroy() {
		lock = null;
		builder.delete(0, builder.length());
		builder = null;
	}

	public void init() {
		lock = new Object();
		builder = new StringBuilder();
	}

	public void close() {
		synchronized (lock) {
			builder.append("/n");
		}
	}

}
