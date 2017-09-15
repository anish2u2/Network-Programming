package org.network.server.abstracts;

import java.net.ServerSocket;
import java.net.Socket;

import org.commons.properties.ApplicationPropertyReader;
import org.network.contracts.Server;

public abstract class AbstractServer implements Server {

	private ServerSocket serverSocket;

	private static int default_port = 0;

	private static String temp_file_location = "";

	private static String log_file_location = "";

	private static int client_socket_connection_per_request = 0;

	@Override
	public void init() {
		default_port = Integer.parseInt(
				ApplicationPropertyReader.getInstance().getMessage("default.network.cmmunication.port.number"));
		temp_file_location = ApplicationPropertyReader.getInstance().getMessage("default.network.temp.folder.location");
		log_file_location = ApplicationPropertyReader.getInstance().getMessage("default.network.log.folder.location");
		client_socket_connection_per_request = Integer.parseInt(
				ApplicationPropertyReader.getInstance().getMessage("default.opened.client.socket.size.per.connection"));
	}

	@Override
	public void destroy() {

	}

	@Override
	public String startServer() {

		return null;
	}

	@Override
	public String startServer(int port) {

		return null;
	}

	@Override
	public String startServer(String address, int port, int numberOfConnectionToConsume) {

		return null;
	}

	@Override
	public void stopServer() {

	}

	protected abstract void initRequest(Socket socket);
}
