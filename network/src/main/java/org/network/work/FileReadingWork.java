package org.network.work;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

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

	private long startTime = 0;

	private Signal<Object> readingNotifier;

	private Signal<Object> fileNameRecieved;

	{
		init();
	}

	private FileOutputStream outputStream;

	@Override
	public void work() {
		if (startTime == 0) {
			startTime = Calendar.getInstance().getTimeInMillis();
		}
		try {
			File temp = new File(tempDirectory);
			if (!temp.exists()) {
				temp.mkdirs();
			}
			fileNameRecieved.releaseSignal(fileName);
			// String fileName = reader.read().split(":")[1];
			byte[] buffer = new byte[default_buffer_size];
			// index = reader.readLong();
			// InputStream stream = reader.getInputStream();
			String content = reader.read();
			while (!"/n".equals(content) && !halt) {
				// System.out.println("Got Buffre:" + buffer);
				outputStream.write(content.getBytes("UTF8"));
				outputStream.flush();
				// bufferAvailableSignal.releaseSignal(new Data(null, buffer));
			}
			outputStream.close();
			halt = true;
			haltTempFileWriter = true;
			readingNotifier.releaseSignal(null);
			System.out.println(
					"Time taken:" + ((Calendar.getInstance().getTimeInMillis() - startTime) / 1000) + "seconds");
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
		bufferAvailableSignal = null;
		reader = null;
		tempDirectory = null;
		((Destroy) fileNameRecieved).destroy();
		fileNameRecieved = null;
		reader = null;
		readingNotifier = null;
	}

	@Override
	public void init() {
		bufferAvailableSignal = new BufferAvailableSignal();
		default_buffer_size = Integer
				.parseInt(ApplicationPropertyReader.getInstance().getMessage("default.network.file.buffer.size"));
		tempDirectory = ApplicationPropertyReader.getInstance().getMessage("default.network.temp.folder.location");
		fileNameRecieved = new org.network.signal.Signal();
		// startWritingToTempFile();
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	private static class BufferAvailableSignal implements Signal<BufferSegment>, Init, Destroy {

		private Object lock;

		private BufferSegment buffer;
		{
			init();
		}

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
				String fileName = (String) fileNameRecieved.aquireSignal();
				File file = new File(tempDirectory, fileName);
				try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
					while (!haltTempFileWriter) {
						System.out.println("Waiting for signal.");
						BufferSegment buffer = bufferAvailableSignal.aquireSignal();
						// if (!BufferSegment.SEGMENT_END.equals(buffer)) {
						fileOutputStream.write(buffer.getBuffer());
						fileOutputStream.flush();
						System.out.println("Writen to file.");
						/*
						 * System.out.println("-"); } else { System.out.println(
						 * "End of data."); }
						 */

					}
					System.out.println("File written in directory");
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
		try {
			outputStream = new FileOutputStream(new File(tempDirectory, fileName));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public long getIndex() {
		return index;
	}
}
