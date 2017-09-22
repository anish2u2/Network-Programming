import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

public class MultipleThreadWriting {

	private static long startingTIime;

	public static void main(String args[]) {
		try {
			memoryUtilization();
			FileInputStream inputStream = new FileInputStream(new File("D:/ActiveMQ in Action.pdf"));
			File dir = new File("D:/temp");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(new File(dir, "ActiveMQ in Action.pdf"));
			reader.setInputStream(inputStream);
			Thread readerThread = new Thread(getInputStreamReaderJob(inputStream), "Reader-Job");
			Thread writerThread = new Thread(getWriterRunnable(fileOutputStream), "Writer-Job");
			readerThread.start();
			writerThread.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static SynchronizedInputStreamReader reader = new SynchronizedInputStreamReader();
	public static BAOS byteArrayOutputStream = new BAOS();

	public static boolean halt = false;

	/*
	 * public static Signal signal = new Signal();
	 * 
	 * public static class Signal {
	 * 
	 * private Object lock = new Object();
	 * 
	 * public void aquireLock() { try { synchronized (lock) { lock.wait(); } }
	 * catch (Exception ex) { ex.printStackTrace(); } }
	 * 
	 * public void releaseLock() { try { synchronized (lock) { lock.notifyAll();
	 * } } catch (Exception ex) { ex.printStackTrace(); } }
	 * 
	 * }
	 */

	public static Runnable getInputStreamReaderJob(final InputStream inputStream) {
		return new Runnable() {
			private long time;

			@Override
			public void run() {
				try {
					time = Calendar.getInstance().getTimeInMillis();
					// startingTIime = Calendar.getInstance().getTimeInMillis();
					while (true) {
						// signal.aquireLock();
						if (reader.read() == -1) {
							break;
						}
					}
					System.out.println(
							"Time tacken to read the stream :" + (Calendar.getInstance().getTimeInMillis() - time));
					halt = true;
				} catch (Exception ex) {
					halt = true;
					ex.printStackTrace();
				}
			}
		};
	}

	public static class BAOS {

		private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		private Object lock = new Object();

		public BAOS() {
			super();
		}

		public BAOS(int size) {
			// super(size);
		}

		public void writeToStream(byte[] buffer) throws Exception {
			synchronized (lock) {
				byteArrayOutputStream.write(buffer, 0, buffer.length);
			}

		}

		public byte[] getBuffer() {
			synchronized (lock) {
				byte[] buffer = byteArrayOutputStream.toByteArray();
				this.byteArrayOutputStream = new ByteArrayOutputStream();
				return buffer;
			}

		}

	}

	public static class SynchronizedInputStreamReader {

		private InputStream inputStream;
		int value;
		byte[] buffer = new byte[1048576];

		public int read() throws Exception {
			value = inputStream.read(buffer);
			byteArrayOutputStream.writeToStream(buffer);
			return value;
		}

		public void setInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}

	}

	public static Runnable getWriterRunnable(final OutputStream outputStream) {
		return new Runnable() {
			private long time;

			@Override
			public void run() {
				try {
					time = Calendar.getInstance().getTimeInMillis();
					while (!halt) {
						byte[] buffer = byteArrayOutputStream.getBuffer();
						if (buffer != null) {
							outputStream.write(buffer);
							outputStream.flush();
						}
					}
					System.out.println(
							"Time tacken to write the stream :" + (Calendar.getInstance().getTimeInMillis() - time));
					outputStream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				Runtime.getRuntime().gc();
			}

		};
	}

	public static void memoryUtilization() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				long totalMemo = Runtime.getRuntime().totalMemory();
				long mb = 1024 * 1024;
				boolean isGCCalled = false;
				while (true) {
					System.out.println(
							"Total Memo:" + (totalMemo / mb) + " Free Memo:" + (Runtime.getRuntime().freeMemory() / mb)
									+ " Utilized Memo:" + ((totalMemo - Runtime.getRuntime().freeMemory()) / mb));
					try {
						Thread.sleep(1000);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}, "Memory-Utilization").start();
	}

}
