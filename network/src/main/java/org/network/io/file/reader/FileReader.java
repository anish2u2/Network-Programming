package org.network.io.file.reader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.network.file.Filehelper;
import org.network.io.abstracts.reader.AbstractFileReader;
import org.network.signal.IONotifyer;
import org.network.work.IOWork;
import org.network.work.parallel.reciever.FileRecieverWork;
import org.process.batch.contracts.Process;

public class FileReader extends AbstractFileReader {

	private String fileName;

	private Process process;

	private IOWork ioWork;


	{
		init();
	}

	@Override
	public void init() {

		super.init();
		process = new org.process.batch.action.Process();
		ioWork = new IOWork();
		ioWork.setIoNotifyer(new IONotifyer());

	}

	@Override
	public void readFile(String toFileLocation) {
		try {
			fileName = this.read().split(":")[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			File file = Filehelper.createFile(toFileLocation, fileName);
			FileOutputStream fos = new FileOutputStream(file);
			FileRecieverWork fileRecieverWork = new FileRecieverWork();
			fileRecieverWork.setInputStream(getInputStream());
			fileRecieverWork.setOutputStream(fos);
			fileRecieverWork.setFileName(file.getAbsolutePath());
			process.startProcess(fileRecieverWork);
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
