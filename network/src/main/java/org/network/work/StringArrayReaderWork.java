package org.network.work;

import java.io.InputStream;

import org.commons.contracts.Buffer;
import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.contracts.ConcurrentReader;
import org.worker.concurrent.ConcurrentBuffer;
import org.worker.contracts.Work;

public class StringArrayReaderWork implements Init, Work, Destroy {

	public enum WorkType {
		SERVER, CLIENT
	}

	Buffer<String> buffer;

	private WorkType type;

	private boolean halt;

	private InputStream inputStream;

	ConcurrentReader reader;

	public StringArrayReaderWork(WorkType type) {
		this.type = type;
		init();
	}

	@Override
	public void destroy() {
		buffer.destroy();
	}

	@Override
	public void work() {
		reader.setBuffer(buffer);
		reader.setInputStream(inputStream);
		try {
			while (!halt) {
				if (reader.read() == -1) {
					halt = true;
					break;
				}
			}
			inputStream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void stopWork() {
		halt = true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		reader = type.equals(WorkType.CLIENT) ? new org.network.client.io.helper.ConcurrentReader()
				: new org.network.server.io.helper.ConcurrentReader();
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}

}
