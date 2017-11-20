package org.network.contracts;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public interface ByteArrayWriter {

	void setOutputStream(OutputStream outputStream);

	void setByteArrayOutputStream(ByteArrayOutputStream arrayOutputStream);

}
