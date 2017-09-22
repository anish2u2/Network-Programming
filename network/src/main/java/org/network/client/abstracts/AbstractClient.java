package org.network.client.abstracts;

import java.net.Socket;

import org.commons.properties.ApplicationPropertyReader;
import org.network.contracts.Channel;
import org.network.contracts.Client;
import org.network.contracts.CommunicationChannel;
import org.network.io.channel.CommunicationChannelFactory;

public abstract class AbstractClient implements Client {

	private Socket clientSocket;

	protected CommunicationChannel communicationChannel;

	private Channel channel;

	private static int default_server_port = 0;
	{
		init();
	}

	@Override
	public void init() {

		default_server_port = Integer.parseInt(
				ApplicationPropertyReader.getInstance().getMessage("default.network.cmmunication.port.number"));
		channel = CommunicationChannelFactory.getInstance();
	}

	@Override
	public void destroy() {
		communicationChannel.destroy();
		clientSocket = null;
	}

	@Override
	public void connectTo(int port) {
		try {
			clientSocket = new Socket("localhost", port);
			channel.setSocket(clientSocket);
			communicationChannel = channel.create();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void connectTo(String address) {
		try {
			clientSocket = new Socket(address, default_server_port);
			channel.setSocket(clientSocket);
			communicationChannel = channel.create();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void connectTo(String address, int port) {
		try {
			clientSocket = new Socket(address, port);
			channel.setSocket(clientSocket);
			communicationChannel = channel.create();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected Channel getChannel() {
		return channel;
	}

	protected CommunicationChannel getCommunication() {
		return communicationChannel;
	}
}
