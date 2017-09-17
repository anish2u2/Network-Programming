package org.network.data;

public interface BufferSegment {

	public byte[] SEGMENT_END = new byte[] { 'e', 'o', 'f' };

	public String getFileName();

	public byte[] getBuffer();

}
