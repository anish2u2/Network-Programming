package org.worker.factory.abstracts;

import org.worker.contracts.Work;
import org.worker.contracts.Worker;

public abstract class AbstractWorker extends Thread implements Worker {

	private static int numberOfWorkers = 0;

	public boolean isWorking;

	private Work work;

	{
		init();
	}

	public void init() {
		setName("Worker-" + numberOfWorkers);
		numberOfWorkers++;
	}

	public void destroy() {
		numberOfWorkers--;
		work = null;
	}

	public boolean isWorking() {

		return this.isWorking;
	}

	protected void setWork(Work work) {
		this.work = work;
	}

	public void setWorkerName(String workerName) {
		this.setName(workerName);
	}

	protected Work getWork() {
		return work;
	}

	@Override
	public void run() {
		try {
			isWorking = true;
			work.work();
			isWorking = false;
		} catch (Exception ex) {
			destroy();
			ex.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}
}
