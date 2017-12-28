package org.worker.concurrent.buffer;

import org.commons.contracts.Buffer;
import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public class StringBuffer implements Buffer<String>, Init, Destroy {

	private Object lock;
	
	private boolean isClosed=false;
	
	private StringBuilder builder;
	
	{
		init();
	}

	public void push(String data) {
		//org.logger.api.Logger.getInstance().info(data);
		synchronized (lock) {
			builder.append(data);
			lock.notifyAll();
		}

	}

	public String pull() {
		try {
		synchronized (lock) {
			String readedData = builder.toString();
			//org.logger.api.Logger.getInstance().info(readedData);
			if(readedData==null) {
				lock.wait();
				readedData = builder.toString();
			}
			builder.delete(0, builder.length());
			return readedData;
		}}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
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
		isClosed=true;
	}
	public boolean isBufferClosed() {
		
		return isClosed;
	}

}
