package org.worker.manager;

import org.worker.contracts.Work;
import org.worker.manager.abstracts.AbstractWorkersManager;

public class WorkersManager extends AbstractWorkersManager implements org.worker.contracts.WorkersManager {

	private static WorkersManager manager;

	private WorkersManager() {

	}

	public void init() {

	}

	public void destroy() {

	}

	public static org.worker.contracts.WorkersManager getInstance() {
		if (manager == null)
			manager = new WorkersManager();
		return manager;
	}

	public void assignWroker(Work work) {

	}

	public void releaseWorkers() {

	}

}
