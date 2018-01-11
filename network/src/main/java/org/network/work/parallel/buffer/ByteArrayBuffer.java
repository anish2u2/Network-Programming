package org.network.work.parallel.buffer;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public class ByteArrayBuffer implements Init, Destroy {

	private Queue<byte[]> byteArrayQueue = new ConcurrentLinkedQueue<byte[]>();
	private boolean flag;

	public void closeBuffer() {
		flag = true;
	}

	public boolean isBufferClosed() {
		return flag;
	}

	@Override
	public void init() {

	}

	public void write(byte[] data) {
		byteArrayQueue.add(data);// Arrays.copyOf(data, data.length)
	}

	public byte[] getBytes() {

		return byteArrayQueue.poll();
	}

	@Override
	public void destroy() {

	}

}
