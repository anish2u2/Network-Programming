package org.network.io.file.reader;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.commons.contracts.Buffer;
import org.network.contracts.SynchronizedStreamReaderWrapper;
import org.network.contracts.SynchronizedStreamWriterWrapper;
import org.network.file.Filehelper;
import org.network.io.abstracts.reader.AbstractFileReader;
import org.network.io.abstracts.writer.ByteArrayOutputWriter;
import org.network.io.file.helper.StreamReaderWrapper;
import org.network.io.file.helper.StreamWriterWrapper;
import org.network.signal.IONotifyer;
import org.network.work.ByteArrayReaderWork;
import org.network.work.ByteArrayWriterWork;
import org.network.work.DemoWork;
import org.network.work.FileReadingWork;
import org.network.work.FileWritingWork;
import org.network.work.StringArrayReaderWork;
import org.network.work.StringArrayWriterWork;
import org.network.work.StringArrayWriterWork.WorkType;
import org.pattern.contracts.behavioral.Notifyer;
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

	private ByteArrayWriterWork byteArrayWriterWork;

	private ByteArrayReaderWork byteArrayReaderWork;

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
		buffer = new ConcurrentBuffer<String, String>().getBuffer("String");
		stringArrayWriterWork = new StringArrayWriterWork(WorkType.CLIENT);
		stringArrayReaderWork = new StringArrayReaderWork(org.network.work.StringArrayReaderWork.WorkType.CLIENT);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		Notifyer notifyer = new IONotifyer();
		byteArrayWriterWork = new ByteArrayWriterWork();
		byteArrayReaderWork = new ByteArrayReaderWork();
		byteArrayReaderWork.setByteArrayOutputStream(byteArrayOutputStream);
		byteArrayWriterWork.setByteArrayOutputStream(byteArrayOutputStream);
		byteArrayReaderWork.setNotifyer(notifyer);
		byteArrayWriterWork.setNotifyer(notifyer);
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
			 * process.startProcess(fileReadingWork); fileWritingWork.setOutputStream(fos);
			 * fileWritingWork.setSynchronizedWriterWrapper(streamWriterWrapper) ;
			 * process.setProcessName("Batch File Writing"); //
			 */
			// stringArrayReaderWork.setInputStream(getInputStream());
			// stringArrayWriterWork.setOutputStream(fos);
			byteArrayReaderWork.setInputStream(getInputStream());
			byteArrayWriterWork.setOutputStream(fos);
			// process.startProcess(stringArrayReaderWork);
			// process.startProcess(stringArrayWriterWork);
			// stringArrayReaderWork.work();
			// stringArrayWriterWork.work();
			Process process = new org.process.batch.action.Process();
			process.startProcess(byteArrayReaderWork);
			process.startProcess(byteArrayWriterWork);
			/*
			 * DemoWork demoWork = new DemoWork(); demoWork.setSignal(signal);
			 * demoWork.setInputStream(getInputStream()); demoWork.setOutputStream(fos);
			 * process.startProcess(demoWork);
			 */
			// signal.aquireSignal();
			// fileWritingWork.stopWork();
			// System.out.println("writer stopped.");
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
