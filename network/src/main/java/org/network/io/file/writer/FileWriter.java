package org.network.io.file.writer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;

import org.commons.contracts.Buffer;
import org.network.contracts.SynchronizedStreamReaderWrapper;
import org.network.contracts.SynchronizedStreamWriterWrapper;
import org.network.io.abstracts.writer.AbstractFileWriter;
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
import org.network.work.StringArrayReaderWork.WorkType;
import org.network.work.StringArrayWriterWork;
import org.pattern.contracts.behavioral.Notifyer;
import org.pattern.contracts.behavioral.Signal;
import org.process.batch.contracts.Process;
import org.worker.concurrent.ConcurrentBuffer;

public class FileWriter extends AbstractFileWriter {

	private FileReadingWork fileReadingWork;
	private FileWritingWork fileWritingWork;
	private SynchronizedStreamReaderWrapper readerWrapper = new StreamReaderWrapper();

	private SynchronizedStreamWriterWrapper streamWriterWrapper = new StreamWriterWrapper();

	private StringArrayReaderWork stringArrayReaderWork;

	private Signal<Object> signal;

	private Buffer<String> buffer;

	private StringArrayWriterWork stringArrayWriterWork;

	private ByteArrayWriterWork byteArrayWriterWork;

	private ByteArrayReaderWork byteArrayReaderWork;

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
		fileReadingWork.setSynchronizedReaderWrapper(readerWrapper);
		readerWrapper.setSynchronizedByteWriter(streamWriterWrapper);
		signal = new org.network.signal.Signal();
		fileReadingWork.setSignal(signal);
		buffer = new ConcurrentBuffer<String, String>().getBuffer("String");
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
	public void writeFile(File file) {
		try {
			stringArrayReaderWork.setInputStream(new FileInputStream(file));
			// fileWritingWork.setSynchronizedWriterWrapper(streamWriterWrapper);
			// fileWritingWork.setOutputStream(getOutputStream());
			this.write("fileName:" + file.getName());
			// stringArrayWriterWork.setOutputStream(getOutputStream());
			byteArrayReaderWork.setInputStream(new FileInputStream(file));
			byteArrayWriterWork.setOutputStream(getOutputStream());
			// process.setProcessName("File Reading Work");
			// process.startProcess(stringArrayReaderWork);
			// process.setProcessName("File Writing Work");
			// process.startProcess(stringArrayWriterWork);
			process = new org.process.batch.action.Process();
			process.startProcess(byteArrayReaderWork);
			process.startProcess(byteArrayWriterWork);
			// signal.aquireSignal();
			// fileWritingWork.stopWork();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void writeFile(String fullyQualifiedFilePath) {
		try {
			File file = new File(fullyQualifiedFilePath);
			// stringArrayReaderWork.setInputStream(new FileInputStream(file));
			// fileWritingWork.setSynchronizedWriterWrapper(streamWriterWrapper);
			// fileWritingWork.setOutputStream(getOutputStream());
			this.write("fileName:" + file.getName());
			byteArrayReaderWork.setInputStream(new FileInputStream(file));
			byteArrayWriterWork.setOutputStream(getOutputStream());
			// process.setProcessName("File Reading Work");
			// process.startProcess(stringArrayReaderWork);
			// process.setProcessName("File Writing Work");
			// process.startProcess(stringArrayWriterWork);
			process = new org.process.batch.action.Process();
			process.startProcess(byteArrayReaderWork);
			process.startProcess(byteArrayWriterWork);
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
