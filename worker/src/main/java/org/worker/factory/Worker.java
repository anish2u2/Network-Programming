package org.worker.factory;

import org.worker.contracts.Work;
import org.worker.factory.abstracts.AbstractWorker;

public class Worker extends AbstractWorker {

	public void doWork(Work work) {
		setWork(work);
		start();
	}

	public void doWork(Work work, boolean backGroundWorker) {
		this.setDaemon(backGroundWorker);
		setWork(work);
		start();
	}

}
