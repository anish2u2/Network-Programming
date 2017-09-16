package org.network.contracts;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface CommunicationChannel extends Init, Destroy {

	public void setWriter(Writer writer);

	public void setReader(Reader reader);

	public Writer getWriter();

	public Reader getReader();

	public boolean isCommunicationAlive();

}
