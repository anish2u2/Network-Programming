package org.process.batch.action.abstracts;

import org.process.batch.contracts.Process;
import org.worker.contracts.WorkersManager;

public abstract class AbstractProcess implements Process {

	private WorkersManager manager;

	public void init() {
		manager = org.worker.manager.WorkersManager.getInstance();

	}

	public void destroy() {
		manager = null;
	}

	protected int getAvailableProcessors() {
		return Runtime.getRuntime().availableProcessors();
	}

	protected WorkersManager getWorkAssigner() {
		return manager;
	}
}
