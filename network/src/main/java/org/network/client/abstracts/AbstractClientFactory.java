package org.network.client.abstracts;

import org.network.contracts.Client;
import org.network.contracts.ClientFactory;

public abstract class AbstractClientFactory implements ClientFactory {

	@Override
	public Client create() {
		Client client = new org.network.client.Client();
		addClient(client);
		return client;
	}

	@Override
	public void init() {
	}

	@Override
	public void destroy() {
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		destroy();
	}

	protected abstract void addClient(Client client);
}
