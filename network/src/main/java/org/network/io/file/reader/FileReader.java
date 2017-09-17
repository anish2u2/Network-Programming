package org.network.io.file.reader;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.network.contracts.Merger;
import org.network.file.FileMerger;
import org.network.file.Filehelper;
import org.network.io.abstracts.reader.AbstractFileReader;
import org.network.work.FileReadingWork;
import org.pattern.contracts.behavioral.Signal;
import org.process.batch.contracts.Process;

public class FileReader extends AbstractFileReader {

	private String fileName;

	private Process process;

	private FileReadingWork fileReadingWork;

	private Merger mereger;

	private Signal<Object> signal;

	{
		init();
	}

	@Override
	public void init() {

		super.init();
		signal = new org.network.signal.Signal();
		process = new org.process.batch.action.Process();

		mereger = new FileMerger();
	}

	@Override
	public void readFile(String toFileLocation) {
		try {
			fileReadingWork = new FileReadingWork();
			fileReadingWork.setReader(this);
			fileName = this.read().split(":")[1];
			process.setProcessName("Batch File Reading");
			fileReadingWork.setFileName(fileName);
			fileReadingWork.setFileReadingSignal(signal);
			process.startProcess(fileReadingWork);
			signal.aquireSignal();
			mereger.mergeFile(new FileOutputStream(Filehelper.createFile(toFileLocation, fileName)), fileName,
					fileReadingWork.getIndex());
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
		mereger = null;
		fileReadingWork.destroy();
		fileReadingWork = null;
		process.destroy();
		process = null;
		fileName = null;
		signal.releaseSignal(null);
		signal = null;
	}
}
