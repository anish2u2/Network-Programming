package org.network.work;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.work.parallel.ReaderWork;
import org.network.work.parallel.WriterWork;
import org.network.work.parallel.buffer.ByteArrayBuffer;
import org.pattern.contracts.behavioral.Notifyer;
import org.worker.contracts.Work;
import org.worker.manager.WorkersManager;

public class IOWork implements Init, Work, Destroy {

	private InputStream inputStream;

	private int value;

	private OutputStream outputStream;

	private byte[] data = new byte[1024];

	private Object lock = new Object();

	private Notifyer notifyer;

	private int threadCounter;

	@Override
	public void destroy() {
		synchronized (lock) {
			lock = null;
		}
		data = null;
		outputStream = null;
		inputStream = null;
	}

	@Override
	public void work() {
		try {
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			org.logger.api.Logger.getInstance().info("Starting work.");
			byte[] data = new byte[1024];
			while (dataInputStream.read(data) != -1) {
				dataOutputStream.write(data, 0, data.length);
			}
			outputStream.flush();
			org.logger.api.Logger.getInstance().info("Work Done.");
			outputStream.close();
			/*
			 * ByteArrayBuffer buffer = new ByteArrayBuffer(); ReaderWork
			 * readerWork = new ReaderWork(); WriterWork writerWork = new
			 * WriterWork(); readerWork.setInputStream(inputStream);
			 * readerWork.setByteArrayBuffer(buffer);
			 * writerWork.setBuffer(buffer);
			 * writerWork.setOutputStream(outputStream);
			 * WorkersManager.getInstance().assignWroker(readerWork);
			 * WorkersManager.getInstance().assignWroker(writerWork);
			 */
			/*
			 * long startTime = Calendar.getInstance().getTimeInMillis();
			 * threadCounter++; while (readData() != -1) {
			 * 
			 * } notifyer.notifyObject(); threadCounter--; if (threadCounter ==
			 * 0) outputStream.close();
			 * org.logger.api.Logger.getInstance().info(
			 * "Time tacken to complete:" +
			 * (Calendar.getInstance().getTimeInMillis() - startTime));
			 * org.logger.api.Logger.getInstance().info("Memory used:" +
			 * ((Runtime.getRuntime().totalMemory() -
			 * Runtime.getRuntime().freeMemory()) / 1000000) + " M.B");
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int readData() throws IOException {
		if (notifyer.isNotified()) {
			return -1;
		}
		synchronized (lock) {
			value = inputStream.read(data);
			outputStream.write(data);
			outputStream.flush();
		}
		return value;

	}

	@Override
	public void stopWork() {
		notifyer.notifyObject();
	}

	@Override
	public void init() {

	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;

	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
		// this.dataOutputStream = new DataOutputStream(outputStream);
	}

	public Notifyer getIoNotifyer() {
		return notifyer;
	}

	public void setIoNotifyer(Notifyer ioNotifyer) {
		this.notifyer = ioNotifyer;
	}

}
