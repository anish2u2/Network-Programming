import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {

	public static final byte[] END = new byte[] { 'e', 'o', 'f' };

	public static boolean halt = false;

	public static void main(String args[]) {
		try {
			File file = new File("D:/ActiveMQ in Action.pdf");
			final FileInputStream inputStream = new FileInputStream(new File("D:/ActiveMQ in Action.pdf"));

			File dir = new File("D:/temp");
			if (!dir.exists())
				dir.mkdir();
			File fil = new File(dir, "ActiveMQ in Action.pdf");
			if (!fil.exists()) {
				fil.createNewFile();
			}
			final FileOutputStream fileOutputStream = new FileOutputStream(fil);
			long fileSize = file.length();
			final int rate = getThroughput(fileSize);
			System.out.println("Throughput:" + rate);

			long time = System.currentTimeMillis();
			final Signal signal = new Signal();
			Runnable runnable = new Runnable() {
				public void run() {
					byte[] buffer = new byte[1024];
					long time = System.currentTimeMillis();
					System.out.println("Starting Reading: " + Thread.currentThread().getName());
					try {
						while (inputStream.available() > 0) {
							byte[] buff = new byte[1024];
							inputStream.read(buff);
							signal.release(buff);
						}
						signal.release(END);
						halt = true;
						inputStream.close();
						System.out.println("Spent Time:" + (System.currentTimeMillis() - time));
					} catch (IOException e) {
						e.printStackTrace();
					}

					System.out.println("Reading Done: " + Thread.currentThread().getName());
				}
			};
			Runnable runTo = new Runnable() {

				@Override
				public void run() {
					try {
						Queue<byte[]> data = signal.acquir();
						byte[] EOF;
						while (!END.equals((EOF = data.poll()))) {
							if (EOF == null) {
								data = signal.acquir();
								EOF = data.poll();
								if (END.equals((EOF = data.poll()))) {
									break;
								}
							}
							fileOutputStream.write(EOF);
							fileOutputStream.flush();
						}
						fileOutputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};

			new Thread(runTo).start();
			for (int counter = 0; counter < 4; counter++) {
				new Thread(runnable, "Worker-" + counter).start();

			}

			/*
			 * GZIPInputStream gZipOutputStream = new GZIPInputStream(new
			 * FileInputStream( new File(
			 * "D:/movies/captain.underpants.the.first.epic.movie.2017.bdrip.x264-drones.mkv"
			 * ))); long counter = 0; byte[] buffer = new byte[1024 * 1024];
			 * while (gZipOutputStream.read() != -1) { counter++; }
			 * System.out.println("total read MB:" + (counter / (1024 * 1024)));
			 * gZipOutputStream.close();
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static class Signal {
		private Queue<byte[]> data = new LinkedList<byte[]>();

		private Object lock = new Object();

		public Queue<byte[]> acquir() {
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return data;
		}

		public void release(byte[] data) {
			synchronized (lock) {
				this.data.add(data);
				if (this.data.size() > 10) {
					lock.notify();
				}
			}
		}
	}

	public static int getThroughput(long size) {
		if (size % 2 == 0) {
			return getEven((int) (size / 2));
		} else {
			return getOdd((int) (size / 3));
		}

	}

	public static int getEven(int value) {
		while (value / 2 > 20480) {
			value = value / 2;
		}
		return value;
	}

	public static int getOdd(int value) {
		while (value / 3 > 20480) {
			value = value / 3;
		}
		return value;
	}

}
