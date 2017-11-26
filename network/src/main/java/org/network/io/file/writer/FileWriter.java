package org.network.io.file.writer;

import java.io.File;
import java.io.FileInputStream;

import org.network.io.abstracts.writer.AbstractFileWriter;
import org.network.work.IOWork;
import org.process.batch.contracts.Process;

public class FileWriter extends AbstractFileWriter {



	private IOWork ioWork;

	private Process process;

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
	public void writeFile(File file) {
		try {
			this.write("fileName:" + file.getName());

			ioWork.setInputStream(new FileInputStream(file));
			ioWork.setOutputStream(getOutputStream());
			process.startProcess(ioWork);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void writeFile(String fullyQualifiedFilePath) {
		try {
			File file = new File(fullyQualifiedFilePath);

			this.write("fileName:" + file.getName());
			ioWork.setInputStream(new FileInputStream(file));
			ioWork.setOutputStream(getOutputStream());
			process.startProcess(ioWork);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		process.destroy();
		process = null;
	}

}
