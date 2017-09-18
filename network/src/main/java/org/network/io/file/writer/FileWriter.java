package org.network.io.file.writer;

import java.io.File;
import java.io.FileInputStream;

import org.network.io.abstracts.writer.AbstractFileWriter;
import org.network.work.FileWritingWork;
import org.process.batch.contracts.Process;

public class FileWriter extends AbstractFileWriter {

	private FileWritingWork fileWritingWork;

	private Process process;

	{
		init();
	}

	@Override
	public void init() {
		super.init();
		fileWritingWork = new FileWritingWork();
		process = new org.process.batch.action.Process();
		fileWritingWork.setWriter(this);
	}

	@Override
	public void writeFile(File file) {
		try {
			fileWritingWork.setFileInputStream(new FileInputStream(file));
			this.write("fileName:" + file.getName());
			process.setProcessName("File Writing Work");
			process.startProcess(fileWritingWork);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void writeFile(String fullyQualifiedFilePath) {
		try {
			File file = new File(fullyQualifiedFilePath);
			this.write("fileName:" + file.getName());
			fileWritingWork.setFileInputStream(new FileInputStream(file));
			process.setProcessName("File Writing Work");
			process.startProcess(fileWritingWork);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		process.destroy();
		fileWritingWork.destroy();
		fileWritingWork = null;
		process = null;
	}

}
