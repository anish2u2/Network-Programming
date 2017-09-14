import java.io.File;
import java.io.FileInputStream;

public class ALtMain {

	public static void main(String[] args) {
		try {
			final FileInputStream inputStream = new FileInputStream(
					new File("D:/movies/captain.underpants.the.first.epic.movie.2017.bdrip.x264-drones.mkv"));
			byte[] buffer = new byte[1024 * 1024];
			long time = System.currentTimeMillis();
			while (inputStream.read(buffer) != -1) {

			}
			System.out.println("Spent Time:" + (System.currentTimeMillis() - time));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
