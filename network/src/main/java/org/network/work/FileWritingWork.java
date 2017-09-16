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

	private String fileName;

	private InputStream fileInputStream;

	private int default_buffer_size = 0;

	private boolean halt = false;

	@Override
	public void work() {
		byte[] buffer = new byte[default_buffer_size];
		try {
			int index = 0;
			writer.write("fileName:" + fileName);
			while (fileInputStream.read(buffer) != -1 && !halt) {
				writer.write(index);
				writer.write(buffer);
				index++;
			}
		} catch (Exception ex) {
			halt = true;
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

	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void init() {
		default_buffer_size = Integer
				.parseInt(ApplicationPropertyReader.getInstance().getMessage("default.network.file.buffer.size"));
	}
}
