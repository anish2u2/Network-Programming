package org.network.work.parallel.sender;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.worker.contracts.Work;

import net.iharder.Base64;

/**
 * This file will be used to send data to network.
 * @author Anish Singh
 *
 */
public class FileSenderWork implements Work, Init, Destroy {

	private InputStream inputStream;

	private OutputStream outputStream;

	private BufferedWriter writer;
	
	byte[] data = new byte[1024];
	
	private Object lock = new Object();

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public void work() {
		try {
			System.out.println("Starting work..");
			while (true) {
				synchronized (lock) {
					if (inputStream.read(data) == -1)
						break;
					writer.write(Base64.encodeBytes(data));
					writer.newLine();
					writer.flush();
				}
			}
			System.out.println("Work done..");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			System.out.println("Work done..");
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stopWork() {

	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
		this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
	}

}
