package org.commons.factory;

import org.commons.contracts.ListenerRegistrar;
import org.commons.contracts.Publisher;
import org.commons.exception.ObjectAlreadyCreatedException;
import org.commons.factory.abstracts.EventPublisher;

/**
 * 
 * @author Anish Singh
 *
 */
public class EventPublisherFactory extends EventPublisher implements org.commons.contracts.EventPublisherFactory {

	private static EventPublisherFactory eventPublisherFactory;

	private EventPublisherFactory() {
		if (eventPublisherFactory != null) {
			throw new ObjectAlreadyCreatedException();
		}
	}

	/**
	 * This method will return the factory class for {@ Publisher and
	 * ListenerRegistrar}
	 * 
	 * @return
	 */
	public static EventPublisherFactory getInstance() {
		if (eventPublisherFactory == null) {
			eventPublisherFactory = new EventPublisherFactory();
		}
		return eventPublisherFactory;
	}

	public Publisher getPublisher() {
		return this;
	}

	public ListenerRegistrar getListenerRegistrar() {
		return this;
	}

}
