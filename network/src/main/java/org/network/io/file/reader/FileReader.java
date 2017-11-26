package org.network.io.file.reader;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.network.file.Filehelper;
import org.network.io.abstracts.reader.AbstractFileReader;
import org.network.work.IOWork;
import org.process.batch.contracts.Process;

public class FileReader extends AbstractFileReader {

	private String fileName;

	private Process process;

	private IOWork ioWork;

	{
		init();
	}

	@Override
	public void init() {

		super.init();
		process = new org.process.batch.action.Process();
		ioWork = new IOWork();

	}

	@Override
	public void readFile(String toFileLocation) {
		try {
			fileName = this.read().split(":")[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileOutputStream fos = new FileOutputStream(Filehelper.createFile(toFileLocation, fileName));
			ioWork.setInputStream(getInputStream());
			ioWork.setOutputStream(fos);
			process.startProcess(ioWork);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public InputStream readFile() {
		try {
			return getInputStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void destroy() {
		super.destroy();
		process.destroy();
		process = null;
		fileName = null;
	}
}
