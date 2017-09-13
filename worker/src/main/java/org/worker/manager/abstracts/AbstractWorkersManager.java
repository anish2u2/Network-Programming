package org.worker.manager.abstracts;

import org.pattern.contracts.creational.ObjectPooling;
import org.worker.contracts.Worker;

public class AbstractWorkersManager implements ObjectPooling<Worker> {

	public Worker fetch() {

		return null;
	}

	public void expire(Worker object) {

	}

}
