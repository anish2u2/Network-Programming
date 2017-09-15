package org.worker.manager;

import org.worker.contracts.Work;
import org.worker.contracts.Worker;
import org.worker.manager.abstracts.AbstractWorkersManager;

public class WorkersManager extends AbstractWorkersManager implements org.worker.contracts.WorkersManager {

	private static WorkersManager manager;

	private WorkersManager() {

	}

	public static org.worker.contracts.WorkersManager getInstance() {
		if (manager == null)
			manager = new WorkersManager();
		return manager;
	}

	public void assignWroker(Work work) {
		fetch().doWork(work);
	}

	public void assignWroker(Work work, String processName) {
		fetch().doWork(work);
	}

	@Override
	protected Worker getWorker() {
		return new org.worker.factory.Worker();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() {
		super.init();
	}

}
