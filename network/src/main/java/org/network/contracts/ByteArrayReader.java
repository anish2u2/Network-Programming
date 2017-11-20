package org.network.contracts;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public interface ByteArrayReader {

	void setInputStream(InputStream inputStream);

	void setByteArrayOutputStream(ByteArrayOutputStream arrayOutputStream);
}
