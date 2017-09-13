package org.commons.contracts;

public interface EventPublisherFactory {

	public Publisher getPublisher();

	public ListenerRegistrar getListenerRegistrar();

}
