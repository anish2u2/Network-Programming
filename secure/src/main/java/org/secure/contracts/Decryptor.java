package org.secure.contracts;


/**
 * 
 * @author Anish Singh
 *
 */
public interface Decryptor extends SecureKey {

	public byte[] decrypt(byte[] buffer);

	public byte[] decrypt(String data);

	public String decryptToString(byte[] buffer);

	public String decryptToString(String data);

}
