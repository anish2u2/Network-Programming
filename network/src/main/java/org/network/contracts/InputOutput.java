package org.network.contracts;

import java.io.InputStream;
import java.io.OutputStream;

public interface InputOutput {

	void setInputStream(InputStream inputStream);

	void setOutputStream(OutputStream outputStream);

	OutputStream getOutputStream();

	InputStream getInputStream();
}
