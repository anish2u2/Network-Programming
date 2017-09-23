package org.network.io.file.reader;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.network.contracts.SynchronizedStreamReaderWrapper;
import org.network.contracts.SynchronizedStreamWriterWrapper;
import org.network.file.Filehelper;
import org.network.io.abstracts.reader.AbstractFileReader;
import org.network.io.file.helper.StreamReaderWrapper;
import org.network.io.file.helper.StreamWriterWrapper;
import org.network.work.FileReadingWork;
import org.network.work.FileWritingWork;
import org.pattern.contracts.behavioral.Signal;
import org.process.batch.contracts.Process;

public class FileReader extends AbstractFileReader {

	private String fileName;

	private Process process;

	private FileReadingWork fileReadingWork;
	private FileWritingWork fileWritingWork;

	private Signal<Object> signal;

	private SynchronizedStreamReaderWrapper readerWrapper = new StreamReaderWrapper();

	private SynchronizedStreamWriterWrapper streamWriterWrapper = new StreamWriterWrapper();

	{
		init();
	}

	@Override
	public void init() {

		super.init();
		signal = new org.network.signal.Signal();
		process = new org.process.batch.action.Process();
		readerWrapper.setInputStream(getInputStream());
		readerWrapper.setSynchronizedByteWriter(streamWriterWrapper);
		fileWritingWork = new FileWritingWork();
		fileReadingWork = new FileReadingWork();
	}

	@Override
	public void readFile(String toFileLocation) {
		try {

			fileReadingWork.setSynchronizedReaderWrapper(readerWrapper);
			fileName = this.read().split(":")[1];
			process.setProcessName("Batch File Reading");
			process.startProcess(fileReadingWork);
			fileWritingWork.setOutputStream(new FileOutputStream(Filehelper.createFile(toFileLocation, fileName)));
			fileWritingWork.setSynchronizedWriterWrapper(streamWriterWrapper);
			process.setProcessName("Batch File Writing");
			process.startProcess(fileWritingWork);
			signal.aquireSignal();
			fileWritingWork.stopWork();
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
		fileReadingWork.destroy();
		fileReadingWork = null;
		process.destroy();
		process = null;
		fileName = null;
		signal.releaseSignal(null);
		signal = null;
	}
}
