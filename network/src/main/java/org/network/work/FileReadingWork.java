package org.network.work;

import java.io.File;
import java.io.FileOutputStream;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.commons.properties.ApplicationPropertyReader;
import org.network.contracts.Reader;
import org.network.data.BufferSegment;
import org.pattern.contracts.behavioral.Signal;
import org.worker.contracts.Work;
import org.worker.manager.WorkersManager;

public class FileReadingWork implements Work, Init, Destroy {

	private boolean halt;

	private Reader reader;

	private int default_buffer_size = 0;

	private String tempDirectory;

	private BufferAvailableSignal bufferAvailableSignal;

	private boolean haltTempFileWriter;

	private long index = 0;

	private String fileName;

	private Signal<Object> readingNotifier;

	@Override
	public void work() {
		try {
			File temp = new File(tempDirectory);
			if (!temp.exists()) {
				temp.mkdirs();
			}

			// String fileName = reader.read().split(":")[1];
			byte[] buffer = new byte[default_buffer_size];
			index = reader.readLong();
			while (reader.read(buffer) != -1 && !halt) {
				bufferAvailableSignal.releaseSignal(new Data(fileName + index, buffer));
				index = reader.readLong();
			}
			haltTempFileWriter = true;
			readingNotifier.releaseSignal(null);
		} catch (Exception ex) {
			haltTempFileWriter = true;
			bufferAvailableSignal.releaseSignal(new Data("null", BufferSegment.SEGMENT_END));
			readingNotifier.releaseSignal(null);
			ex.printStackTrace();

		}
	}

	@Override
	public void stopWork() {
		halt = false;
	}

	@Override
	public void destroy() {
		bufferAvailableSignal.destroy();
		reader = null;
		tempDirectory = null;
	}

	@Override
	public void init() {
		bufferAvailableSignal = new BufferAvailableSignal();
		default_buffer_size = Integer
				.parseInt(ApplicationPropertyReader.getInstance().getMessage("default.network.file.buffer.size"));
		tempDirectory = ApplicationPropertyReader.getInstance().getMessage("default.network.temp.folder.location");
		startWritingToTempFile();
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	private static class BufferAvailableSignal implements Signal<BufferSegment>, Init, Destroy {

		private Object lock;

		private BufferSegment buffer;

		@Override
		public BufferSegment aquireSignal() {
			try {
				synchronized (lock) {
					lock.wait();
					BufferSegment data = this.buffer;
					buffer = null;
					return data;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
		}

		@Override
		public void releaseSignal(BufferSegment data) {
			synchronized (lock) {
				this.buffer = data;
				lock.notifyAll();
			}
		}

		@Override
		public void destroy() {
			lock = null;
		}

		@Override
		public void init() {
			lock = new Object();
		}

	}

	public void startWritingToTempFile() {
		WorkersManager.getInstance().assignWroker(new Work() {

			@Override
			public void work() {
				try {
					while (!haltTempFileWriter) {
						BufferSegment buffer = bufferAvailableSignal.aquireSignal();
						File file = new File(tempDirectory, buffer.getFileName());
						file.createNewFile();
						FileOutputStream fileOutputStream = new FileOutputStream(file);
						fileOutputStream.write(buffer.getBuffer());
						fileOutputStream.flush();
						fileOutputStream.close();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void stopWork() {
				haltTempFileWriter = true;
			}
		});
	}

	private static final class Data implements BufferSegment {

		private byte[] buffer;

		private String fileName;

		public Data(String fileName, byte[] buffer) {
			this.fileName = fileName;
			this.buffer = buffer;
		}

		public byte[] getBuffer() {
			return buffer;
		}

		@Override
		public String getFileName() {
			return fileName;
		}

	}

	public void setFileReadingSignal(Signal<Object> signal) {
		readingNotifier = signal;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getIndex() {
		return index;
	}
}
