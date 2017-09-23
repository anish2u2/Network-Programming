package org.network.io.file.writer;

import java.io.File;
import java.io.FileInputStream;

import org.network.contracts.SynchronizedStreamReaderWrapper;
import org.network.contracts.SynchronizedStreamWriterWrapper;
import org.network.io.abstracts.writer.AbstractFileWriter;
import org.network.io.file.helper.StreamReaderWrapper;
import org.network.io.file.helper.StreamWriterWrapper;
import org.network.work.FileReadingWork;
import org.network.work.FileWritingWork;
import org.pattern.contracts.behavioral.Signal;
import org.process.batch.contracts.Process;

public class FileWriter extends AbstractFileWriter {

	private FileReadingWork fileReadingWork;
	private FileWritingWork fileWritingWork;
	private SynchronizedStreamReaderWrapper readerWrapper = new StreamReaderWrapper();

	private SynchronizedStreamWriterWrapper streamWriterWrapper = new StreamWriterWrapper();

	private Signal<Object> signal;

	private Process process;

	{
		init();
	}

	@Override
	public void init() {
		super.init();
		fileWritingWork = new FileWritingWork();
		process = new org.process.batch.action.Process();
		fileWritingWork = new FileWritingWork();
		fileReadingWork = new FileReadingWork();
		fileReadingWork.setSignal(signal);
		fileReadingWork.setSynchronizedReaderWrapper(readerWrapper);
		signal = new org.network.signal.Signal();
	}

	@Override
	public void writeFile(File file) {
		try {
			readerWrapper.setInputStream(new FileInputStream(file));
			fileWritingWork.setSynchronizedWriterWrapper(streamWriterWrapper);
			fileWritingWork.setOutputStream(getOutputStream());
			this.write("fileName:" + file.getName());
			process.setProcessName("File Reading Work");
			process.startProcess(fileReadingWork);
			process.setProcessName("File Writing Work");
			process.startProcess(fileWritingWork);
			signal.aquireSignal();
			fileWritingWork.stopWork();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void writeFile(String fullyQualifiedFilePath) {
		try {
			File file = new File(fullyQualifiedFilePath);
			readerWrapper.setInputStream(new FileInputStream(file));
			fileWritingWork.setSynchronizedWriterWrapper(streamWriterWrapper);
			fileWritingWork.setOutputStream(getOutputStream());
			this.write("fileName:" + file.getName());
			process.setProcessName("File Reading Work");
			process.startProcess(fileReadingWork);
			process.setProcessName("File Writing Work");
			process.startProcess(fileWritingWork);
			signal.aquireSignal();
			fileWritingWork.stopWork();
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
