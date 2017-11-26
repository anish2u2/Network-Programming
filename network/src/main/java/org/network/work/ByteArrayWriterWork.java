package org.network.work;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.contracts.ByteArrayWriter;
import org.pattern.contracts.behavioral.Notifyer;
import org.worker.contracts.Work;

public class ByteArrayWriterWork implements ByteArrayWriter, Destroy, Init, Work {

	private OutputStream outputStream;

	private ByteArrayOutputStream byteArrayOutputStream;

	private boolean haltWork;

	private Notifyer notifyer;

	private boolean isStreamClosed;

	private int startCounter;

	private int endCounter;

	@Override
	public void work() {
		try {
			long startTime = Calendar.getInstance().getTimeInMillis();
			byte[] byts = null;
			startCounter++;
			while (!haltWork) {
				synchronized (outputStream) {
					synchronized (byteArrayOutputStream) {
						byts = byteArrayOutputStream.toByteArray();
						byteArrayOutputStream.reset();
					}
					if (byts != null && byts.length > 0)
						outputStream.write(byts);
				}
				outputStream.flush();
				if (notifyer.isNotified()) {
					break;
				}
			}
			endCounter++;
			System.out.println("Time tacken to complete:" + (Calendar.getInstance().getTimeInMillis() - startTime));
			System.out.println("Memory used:"
					+ ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000) + " M.B");
			System.out.println("isStreamClosed: " + isStreamClosed + " StratCounter:" + startCounter + " endCounter:"
					+ endCounter);
			if (!isStreamClosed && startCounter == endCounter) {
				synchronized (outputStream) {
					outputStream.close();
					isStreamClosed = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			isStreamClosed = true;
		}
	}

	@Override
	public void stopWork() {
		haltWork = true;
	}

	@Override
	public void init() {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public void setByteArrayOutputStream(ByteArrayOutputStream arrayOutputStream) {
		this.byteArrayOutputStream = arrayOutputStream;

	}

	public void setNotifyer(Notifyer notifyer) {
		this.notifyer = notifyer;
	}

}
