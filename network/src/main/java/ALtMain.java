import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ALtMain {

	public static int throughput = 1024;

	public static void main(String[] args) {
		try {
			File file = new File(
					"D:/Music & Videos/video Songs/Hollywood/Album/babe Rexa/Bebe Rexha - I Got You [Official Music Video] - YouTube[via torchbrowser.com] (1).mp4");
			final FileInputStream inputStream = new FileInputStream(new File(
					"D:/Music & Videos/video Songs/Hollywood/Album/babe Rexa/Bebe Rexha - I Got You [Official Music Video] - YouTube[via torchbrowser.com] (1).mp4"));

			File dir = new File("D:/temp");
			if (!dir.exists())
				dir.mkdir();
			File fil = new File(dir, "Bebe Rexha - I Got You [Official Music Video] - YouTube[via torchbrowser.com] (1).mp4");
			if (!fil.exists()) {
				fil.createNewFile();
			}
			final FileOutputStream fileOutputStream = new FileOutputStream(fil);
			long fileSize = file.length();
			int rate = getThroughput(fileSize);
			org.logger.api.Logger.getInstance().info("Throughput:" + rate);
			byte[] buffer = new byte[rate];
			long time = System.currentTimeMillis();
			while (inputStream.read(buffer) != -1) {
				fileOutputStream.write(buffer);
				fileOutputStream.flush();
			}
			fileOutputStream.close();
			org.logger.api.Logger.getInstance().info("Spent Time:" + (System.currentTimeMillis() - time));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static class FIleInpStream extends InputStream {

		private boolean flage = true;
		int value = 0;

		public boolean isEOFNotReached() {
			return value != -1;
		}

		@Override
		public int read() throws IOException {
			// TODO Auto-generated method stub
			return 0;
		}

		// public int readByte(byte[] b) throws IOException {
		// return value = read(b);
		// }

		/*
		 * @Override public int read(byte[] b, int off, int len) throws
		 * IOException { return value = super.read(b, off, len); }
		 */

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
