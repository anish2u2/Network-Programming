package org.network.io.channel;

import org.commons.exception.ObjectAlreadyCreatedException;
import org.network.contracts.Channel;
import org.network.io.channel.abstracts.AbstractCommunicationChannel;

public class CommunicationChannelFactory extends AbstractCommunicationChannel {

	private static CommunicationChannelFactory instance;

	@Override
	public void init() {

	}

	private CommunicationChannelFactory() {
		if (instance != null) {
			throw new ObjectAlreadyCreatedException();
		}
	}

	public static Channel getInstance() {
		if (instance == null) {
			instance = new CommunicationChannelFactory();
		}
		return instance;
	}

	@Override
	public void destroy() {

	}

}
