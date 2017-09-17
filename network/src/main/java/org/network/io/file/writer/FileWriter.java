package org.network.io.file.writer;

import java.io.File;

import org.network.io.abstracts.writer.AbstractFileWriter;
import org.network.work.FileWritingWork;
import org.process.batch.contracts.Process;

public class FileWriter extends AbstractFileWriter {

	private FileWritingWork fileWritingWork;

	private Process process;

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
			this.write("fileName:" + file.getName());
			process.setProcessName("File Writing Work");
			process.startProcess(fileWritingWork);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void writeFile(String fullyQualifiedFilePath) {

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
