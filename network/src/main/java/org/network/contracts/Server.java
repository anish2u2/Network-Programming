package org.network.contracts;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface Server extends Init, Destroy {

	public String startServer();

	public String startServer(int port);

	public String startServer(String address, int port, int numberOfConnectionToConsume);

	public void stopServer();

}
