package org.network.work.parallel.reciever;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.worker.contracts.Work;
import org.worker.manager.WorkersManager;

import net.iharder.Base64;

public class FileRecieverWork implements Work, Init, Destroy {

	private InputStream inputStream;

	private BufferedReader reader;

	private OutputStream outputStream;

	private Object lock = new Object();

	private Object decodeLock = new Object();

	private String fileName;

	private CipherInputStream cipherInputStream;
	Cipher decrypt;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public void work() {
		try {
			System.out.println("Starting work.....");

			while (true) {
				synchronized (lock) {
					String line = reader.readLine();
					if (line == null)
						break;
					outputStream.write(Base64.decode(line));
					outputStream.flush();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			System.out.println("Work done..");
			// decodeFile();
			try {
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void stopWork() {

	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
		this.reader = new BufferedReader(new InputStreamReader(inputStream));

	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public void decodeFile() {

		WorkersManager.getInstance().assignWroker(new Work() {

			@Override
			public void work() {
				try {
					FileInputStream fis = new FileInputStream(new File(getFileName()));
					FileOutputStream fos = new FileOutputStream(
							new File(getFileName().replace(org.network.io.file.reader.FileReader.prefix, "")));
					byte[] data = new byte[1024];
					while (fis.read(data) != -1) {
						fos.write(Base64.decodeBase64(data));
					}
					fos.flush();
					fos.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void stopWork() {
				// TODO Auto-generated method stub

			}
		});
	}
}
