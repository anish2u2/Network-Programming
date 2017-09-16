package org.network.contracts;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface Client extends Init, Destroy {

	public void connectTo(int port);

	public void connectTo(String address);

	public void connectTo(String address, int port);

	public CommunicationChannel getCommunicationChannel();

}
