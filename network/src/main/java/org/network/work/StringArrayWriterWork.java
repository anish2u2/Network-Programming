package org.network.work;

import java.io.OutputStream;

import org.commons.contracts.Buffer;
import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.contracts.ConcurrentWriter;
import org.worker.contracts.Work;

public class StringArrayWriterWork implements Init, Work, Destroy {

	private Buffer buffer;

	public enum WorkType {
		SERVER, CLIENT
	}

	private OutputStream outputStream;

	private boolean halt;

	ConcurrentWriter writer;

	private WorkType type;

	public StringArrayWriterWork(WorkType type) {
		this.type = type;
		init();
	}

	@Override
	public void destroy() {

	}

	@Override
	public void work() {
		writer.setBuffer(buffer);
		writer.setOutputStream(outputStream);
		try {
			while (!halt) {
				if (writer.writer() == -1) {
					halt = true;
					break;
				}
			}
			outputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void stopWork() {
		halt = true;
	}

	@Override
	public void init() {
		writer = type.equals(WorkType.CLIENT) ? new org.network.client.io.helper.ConcurrentWriter()
				: new org.network.server.io.helper.ConcurrentWriter();
	}

	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

}
