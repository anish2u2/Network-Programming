package org.network.server.abstracts;

import java.net.InetAddress;
import java.net.ServerSocket;

import org.commons.properties.ApplicationPropertyReader;
import org.network.contracts.Server;

public abstract class AbstractServer implements Server {

	private ServerSocket serverSocket;

	private static int default_port = 0;

	private static String temp_file_location = "";

	private static String log_file_location = "";

	private static int client_socket_connection_per_request = 0;

	{
		init();
	}

	@Override
	public void init() {
		default_port = Integer.parseInt(
				ApplicationPropertyReader.getInstance().getMessage("default.network.cmmunication.port.number"));
		temp_file_location = ApplicationPropertyReader.getInstance().getMessage("default.network.temp.folder.location");
		log_file_location = ApplicationPropertyReader.getInstance().getMessage("default.network.log.folder.location");
		client_socket_connection_per_request = Integer.parseInt(
				ApplicationPropertyReader.getInstance().getMessage("default.opened.client.socket.size.per.connection"));
		System.out.println("Default_Port:" + default_port);
	}

	@Override
	public void destroy() {

	}

	@Override
	public String startServer() {
		try {
			serverSocket = new ServerSocket(default_port);
			initRequest(serverSocket);
			return serverSocket.getInetAddress().getHostAddress();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public String startServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			initRequest(serverSocket);
			return serverSocket.getInetAddress().getHostAddress();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public String startServer(String address, int port, int numberOfConnectionToConsume) {
		try {
			serverSocket = new ServerSocket(port, numberOfConnectionToConsume, InetAddress.getByName(address));
			initRequest(serverSocket);
			return serverSocket.getInetAddress().getHostAddress();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	protected abstract void initRequest(final ServerSocket serverSocket);
}
