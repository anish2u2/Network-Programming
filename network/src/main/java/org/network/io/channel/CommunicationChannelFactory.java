package org.network.io.channel;

import java.util.HashSet;
import java.util.Set;

import org.commons.exception.ObjectAlreadyCreatedException;
import org.network.contracts.Channel;
import org.network.contracts.CommunicationChannel;
import org.network.io.channel.abstracts.AbstractCommunicationChannel;
import org.worker.contracts.Work;
import org.worker.manager.WorkersManager;

public class CommunicationChannelFactory extends AbstractCommunicationChannel {

	private static CommunicationChannelFactory instance;

	private Set<CommunicationChannel> setOfCreatedCommunication;

	{
		init();
	}

	@Override
	public void init() {
		setOfCreatedCommunication = new HashSet<CommunicationChannel>();
		startCommunicationChannelCleanerWorker();
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
		for (CommunicationChannel communicationChannel : setOfCreatedCommunication) {
			communicationChannel.destroy();
		}
		setOfCreatedCommunication.clear();
		setOfCreatedCommunication = null;
		instance = null;
	}

	private void startCommunicationChannelCleanerWorker() {
		WorkersManager.getInstance().assignWroker(new Work() {

			@Override
			public void work() {
				while (setOfCreatedCommunication != null) {
					try {
						for (CommunicationChannel communicationChannel : setOfCreatedCommunication) {
							if (!communicationChannel.isCommunicationAlive()) {
								communicationChannel.destroy();
							}
						}
						Runtime.getRuntime().gc();
						Thread.sleep(10000);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}

			@Override
			public void stopWork() {
				setOfCreatedCommunication.clear();
				setOfCreatedCommunication = null;
			}
		});
	}

	@Override
	protected void addCommunicationChannelForFutureGC(CommunicationChannel channel) {
		this.setOfCreatedCommunication.add(channel);
	}

}
