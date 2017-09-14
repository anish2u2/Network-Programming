import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

	public static void main(String args[]) {
		try {
			final Object obj = new Object();
			System.out.println(Runtime.getRuntime().availableProcessors());
			final FileInputStream inputStream = new FileInputStream(
					new File("D:/movies/captain.underpants.the.first.epic.movie.2017.bdrip.x264-drones.mkv"));

			Runnable runnable = new Runnable() {
				public void run() {
					byte[] buffer = new byte[1024 * 1024];
					long time = System.currentTimeMillis();
					System.out.println("Starting Reading: " + Thread.currentThread().getName());
					try {
						while (inputStream.read(buffer) != -1) {

						}
						System.out.println("Spent Time:" + (System.currentTimeMillis() - time));
						synchronized (obj) {
							obj.notify();
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					System.out.println("Reading Done: " + Thread.currentThread().getName());
				}
			};
			for (int counter = 0; counter < 4; counter++) {
				new Thread(runnable).start();
			}
			synchronized (obj) {
				obj.wait();
				inputStream.close();
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

	public class FileInputStreamLoc extends FileInputStream {

		public FileInputStreamLoc(File file) throws FileNotFoundException {
			super(file);
		}

	}

}
