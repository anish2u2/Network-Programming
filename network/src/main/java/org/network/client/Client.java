package org.network.client;

import org.network.client.abstracts.AbstractClient;
import org.network.contracts.CommunicationChannel;

public class Client extends AbstractClient {

	@Override
	public CommunicationChannel getCommunicationChannel() {

		return getCommunication();
	}

}
