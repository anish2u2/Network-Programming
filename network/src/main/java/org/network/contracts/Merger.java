package org.network.contracts;

import java.io.OutputStream;

/**
 * The implementor of this interface is responsible for merging the files from
 * temp directory.
 * 
 * @author Anish Singh
 *
 */
public interface Merger {

	/**
	 * This method will allow you to merge multiple files into a single file.
	 * 
	 * @param mergeToStream
	 * @param fileName
	 * @param index
	 */
	public void mergeFile(OutputStream mergeToStream, String fileName, long index);

}
