package org.network.contracts;

public interface CommunicationChannel {

	public void setWriter(Writer writer);

	public void setReader(Reader reader);

	public Writer getWriter();

	public Reader getReader();

}
