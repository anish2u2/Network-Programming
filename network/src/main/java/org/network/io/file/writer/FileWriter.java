package org.network.io.file.writer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.network.io.abstracts.writer.AbstractFileWriter;
import org.network.signal.IONotifyer;
import org.network.work.ByteArrayReaderWork;
import org.network.work.ByteArrayWriterWork;
import org.pattern.contracts.behavioral.Notifyer;
import org.process.batch.contracts.Process;
import org.worker.manager.WorkersManager;

public class FileWriter extends AbstractFileWriter {

	private ByteArrayWriterWork byteArrayWriterWork;

	private ByteArrayReaderWork byteArrayReaderWork;

	private Process process;

	{
		init();
	}

	@Override
	public void init() {
		super.init();

		process = new org.process.batch.action.Process();
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
			this.write("fileName:" + file.getName());

			byteArrayReaderWork.setInputStream(new FileInputStream(file));
			byteArrayWriterWork.setOutputStream(getOutputStream());
			byteArrayReaderWork.setOutputStream(getOutputStream());
			process.startProcess(byteArrayReaderWork);
			// WorkersManager.getInstance().assignWroker(byteArrayReaderWork);
			// process = new org.process.batch.action.Process();
			// process.startProcess(byteArrayReaderWork);
			// process.startProcess(byteArrayWriterWork);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void writeFile(String fullyQualifiedFilePath) {
		try {
			File file = new File(fullyQualifiedFilePath);

			this.write("fileName:" + file.getName());
			byteArrayReaderWork.setInputStream(new FileInputStream(file));
			byteArrayWriterWork.setOutputStream(getOutputStream());
			byteArrayReaderWork.setOutputStream(getOutputStream());
			process.startProcess(byteArrayReaderWork);
			// WorkersManager.getInstance().assignWroker(byteArrayReaderWork);
			// process = new org.process.batch.action.Process();
			// process.startProcess(byteArrayReaderWork);
			// process.startProcess(byteArrayWriterWork);
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
