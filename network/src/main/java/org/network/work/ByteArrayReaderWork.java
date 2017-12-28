package org.network.work;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.contracts.ByteArrayReader;
import org.pattern.contracts.behavioral.Notifyer;
import org.worker.contracts.Work;

public class ByteArrayReaderWork implements Init, Work, Destroy, ByteArrayReader {

	private InputStream inputStream;

	private ByteArrayOutputStream byteArrayOutputStream;

	private int value;

	private OutputStream outputStream;

	private byte[] data = new byte[1024];

	private boolean haltWork;

	private Notifyer notifyer;

	private Object lock = new Object();

	@Override
	public void destroy() {

	}

	@Override
	public void work() {
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();
			while (readData() != -1) {

			}
			outputStream.close();
			/*
			 * while (readData() != -1) { }
			 */
			notifyer.notifyObject();
			org.logger.api.Logger.getInstance().info("Time tacken to complete:" + (Calendar.getInstance().getTimeInMillis() - startTime));
			org.logger.api.Logger.getInstance().info("Memory used:"
					+ ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000) + " M.B");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int readData() throws IOException {
		synchronized (lock) {
			value = inputStream.read(data);
			outputStream.write(data);
			outputStream.flush();
		}
		return value;

	}

	@Override
	public void stopWork() {
		haltWork = true;
	}

	@Override
	public void init() {

	}

	@Override
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;

	}

	@Override
	public void setByteArrayOutputStream(ByteArrayOutputStream arrayOutputStream) {
		this.byteArrayOutputStream = arrayOutputStream;
	}

	public void setNotifyer(Notifyer notifyer) {
		this.notifyer = notifyer;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
}
