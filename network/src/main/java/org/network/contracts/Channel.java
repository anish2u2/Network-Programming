package org.network.contracts;

import java.net.Socket;

import org.pattern.contracts.creational.FactoryMethod;

public interface Channel extends FactoryMethod<CommunicationChannel> {

	public void setSocket(Socket socket);

}
