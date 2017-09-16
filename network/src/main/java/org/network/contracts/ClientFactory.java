package org.network.contracts;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.pattern.contracts.creational.FactoryMethod;

public interface ClientFactory extends FactoryMethod<Client>, Init, Destroy {

}
