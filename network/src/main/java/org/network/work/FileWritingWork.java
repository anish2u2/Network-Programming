package org.network.work;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.commons.properties.ApplicationPropertyReader;
import org.network.contracts.Writer;
import org.worker.contracts.Work;

public class FileWritingWork implements Work, Init, Destroy {

	private Writer writer;

	private InputStream fileInputStream;

	private int default_buffer_size = 0;

	private boolean halt = false;

	// private int index = 0;
	{
		init();
	}

	private long startTime = 0;

	@Override
	public void work() {

		byte[] buffer = new byte[default_buffer_size];
		try {
			if (startTime == 0) {
				startTime = Calendar.getInstance().getTimeInMillis();
			}
			// int content = 0;
			while (fileInputStream.read(buffer) != -1 && !halt) {
				writer.write(new String(buffer, "UTF8"));
			}
			writer.write("/n");
			writer.close();
			halt = true;
			System.out.println("Work Done.");
			System.out.println(
					"Time taken:" + ((Calendar.getInstance().getTimeInMillis() - startTime) / 1000) + "seconds");
		} catch (Exception ex) {
			halt = true;
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	public void setFileInputStream(FileInputStream inputStream) {
		this.fileInputStream = inputStream;
	}

	@Override
	public void stopWork() {
		halt = true;
	}

	@Override
	public void destroy() {
		writer = null;
		fileInputStream = null;
	}

	@Override
	public void init() {
		default_buffer_size = Integer
				.parseInt(ApplicationPropertyReader.getInstance().getMessage("default.network.file.buffer.size"));
	}
}
