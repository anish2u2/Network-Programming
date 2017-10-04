package org.network.io.file.reader;

import java.io.FileOutputStream;
import java.io.InputStream;

import org.commons.contracts.Buffer;
import org.network.contracts.SynchronizedStreamReaderWrapper;
import org.network.contracts.SynchronizedStreamWriterWrapper;
import org.network.file.Filehelper;
import org.network.io.abstracts.reader.AbstractFileReader;
import org.network.io.file.helper.StreamReaderWrapper;
import org.network.io.file.helper.StreamWriterWrapper;
import org.network.work.DemoWork;
import org.network.work.FileReadingWork;
import org.network.work.FileWritingWork;
import org.network.work.StringArrayReaderWork;
import org.network.work.StringArrayWriterWork;
import org.network.work.StringArrayWriterWork.WorkType;
import org.pattern.contracts.behavioral.Signal;
import org.process.batch.contracts.Process;
import org.worker.concurrent.ConcurrentBuffer;

public class FileReader extends AbstractFileReader {

	private String fileName;

	private Process process;

	private FileReadingWork fileReadingWork;
	private FileWritingWork fileWritingWork;

	private Signal<Object> signal;

	private SynchronizedStreamReaderWrapper readerWrapper = new StreamReaderWrapper();

	private SynchronizedStreamWriterWrapper streamWriterWrapper = new StreamWriterWrapper();

	private StringArrayWriterWork stringArrayWriterWork;

	private StringArrayReaderWork stringArrayReaderWork;

	private Buffer<String> buffer;

	{
		init();
	}

	@Override
	public void init() {

		super.init();
		signal = new org.network.signal.Signal();
		process = new org.process.batch.action.Process();
		readerWrapper.setSynchronizedByteWriter(streamWriterWrapper);
		fileWritingWork = new FileWritingWork();
		fileReadingWork = new FileReadingWork();
		fileReadingWork.setSignal(signal);
		buffer = ConcurrentBuffer.getInstance().getBuffer(String.class);
		stringArrayWriterWork = new StringArrayWriterWork(WorkType.CLIENT);
		stringArrayReaderWork = new StringArrayReaderWork(org.network.work.StringArrayReaderWork.WorkType.CLIENT);
		stringArrayReaderWork.setBuffer(buffer);
		stringArrayWriterWork.setBuffer(buffer);
	}

	@Override
	public void readFile(String toFileLocation) {
		try {
			fileName = this.read().split(":")[1];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			FileOutputStream fos = new FileOutputStream(Filehelper.createFile(toFileLocation, fileName));
			/*
			 * readerWrapper.setInputStream(getInputStream());
			 * fileReadingWork.setSynchronizedReaderWrapper(readerWrapper);
			 * process.setProcessName("Batch File Reading");
			 * process.startProcess(fileReadingWork);
			 * fileWritingWork.setOutputStream(fos);
			 * fileWritingWork.setSynchronizedWriterWrapper(streamWriterWrapper)
			 * ; process.setProcessName("Batch File Writing");
			 */
			stringArrayReaderWork.setInputStream(getInputStream());
			stringArrayWriterWork.setOutputStream(fos);
			process.startProcess(stringArrayReaderWork);
			process.startProcess(stringArrayWriterWork); 
			/*
			 * DemoWork demoWork = new DemoWork(); demoWork.setSignal(signal);
			 * demoWork.setInputStream(getInputStream());
			 * demoWork.setOutputStream(fos); process.startProcess(demoWork);
			 */
			// signal.aquireSignal();
			// fileWritingWork.stopWork();
			System.out.println("writer stopped.");
			// System.out.println("Total time taken to finish reading:" +
			// demoWork.getWorkTime() + " seconds");

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
