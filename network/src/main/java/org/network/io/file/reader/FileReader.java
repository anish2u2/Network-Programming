package org.network.io.file.reader;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.network.file.Filehelper;
import org.network.io.abstracts.reader.AbstractFileReader;
import org.network.signal.IONotifyer;
import org.network.work.ByteArrayReaderWork;
import org.network.work.ByteArrayWriterWork;
import org.pattern.contracts.behavioral.Notifyer;
import org.process.batch.contracts.Process;
import org.worker.manager.WorkersManager;

public class FileReader extends AbstractFileReader {

	private String fileName;

	private Process process;

	private ByteArrayWriterWork byteArrayWriterWork;

	private ByteArrayReaderWork byteArrayReaderWork;

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
	public void readFile(String toFileLocation) {
		try {
			fileName = this.read().split(":")[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			FileOutputStream fos = new FileOutputStream(Filehelper.createFile(toFileLocation, fileName));
			byteArrayReaderWork.setInputStream(getInputStream());
			byteArrayWriterWork.setOutputStream(fos);
			byteArrayReaderWork.setOutputStream(fos);
			process.startProcess(byteArrayReaderWork);
			//WorkersManager.getInstance().assignWroker(byteArrayReaderWork);
			// Process process = new org.process.batch.action.Process();
			// process.startProcess(byteArrayReaderWork);
			// process.startProcess(byteArrayWriterWork);
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
		process.destroy();
		process = null;
		fileName = null;
	}
}
