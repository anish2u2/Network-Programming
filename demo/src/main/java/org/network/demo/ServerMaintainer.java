package org.network.demo;

import org.commons.contracts.Destroy;
import org.network.contracts.CommunicationChannel;
import org.network.contracts.Server;

public class ServerMaintainer implements Destroy {

	private Server server;

	{
		server = new org.network.server.Server();
	}

	public void startServer() {
		System.out.println(server.startServer());
	}

	public void stopServer() {
		server.stopServer();
	}

	public void destroy() {
		server.destroy();
	}

	public CommunicationChannel getChannel() {
		System.out.println("Waiting for the communication channel.");
		return server.getCommunicationChannel();
	}
}
