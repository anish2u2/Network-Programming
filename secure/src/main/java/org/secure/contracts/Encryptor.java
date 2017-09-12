package org.secure.contracts;

public interface Encryptor extends SecureKey {

	public byte[] encrypt(byte[] buffer);

	public byte[] encrypt(String data);

	public String encryptToString(byte[] buffer);

	public String encryptToString(String data);

}
