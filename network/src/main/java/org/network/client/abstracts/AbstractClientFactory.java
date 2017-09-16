package org.network.client.abstracts;

import org.network.contracts.Client;
import org.network.contracts.ClientFactory;

public abstract class AbstractClientFactory implements ClientFactory {

	@Override
	public Client create() {

		return null;
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
