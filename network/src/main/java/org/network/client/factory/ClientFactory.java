package org.network.client.factory;

import java.util.HashSet;
import java.util.Set;

import org.commons.exception.ObjectAlreadyCreatedException;
import org.network.client.abstracts.AbstractClientFactory;
import org.network.contracts.Client;

public class ClientFactory extends AbstractClientFactory {

	private Set<Client> clients;

	private static ClientFactory clientFactory;

	{
		init();
	}

	private ClientFactory() {
		if (clientFactory != null) {
			throw new ObjectAlreadyCreatedException();
		}
	}

	public static org.network.contracts.ClientFactory getInstance() {
		if (clientFactory == null) {
			clientFactory = new ClientFactory();
		}
		return clientFactory;
	}

	@Override
	public void init() {
		super.init();
		clients = new HashSet<Client>();
	}

	@Override
	protected void addClient(Client client) {
		clients.add(client);
	}

	@Override
	public void destroy() {
		super.destroy();

		for (Client client : clients) {
			client.destroy();
		}
		clients.clear();
		clients = null;

	}

}
