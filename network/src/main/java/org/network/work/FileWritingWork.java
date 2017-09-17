package org.network.work;

import java.io.FileInputStream;
import java.io.InputStream;

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

	private int index = 0;

	@Override
	public void work() {
		byte[] buffer = new byte[default_buffer_size];
		try {

			while (fileInputStream.read(buffer) != -1 && !halt) {
				writer.write(index);
				writer.write(buffer);
				index++;
			}
			writer.write(-1);
			writer.close();
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
