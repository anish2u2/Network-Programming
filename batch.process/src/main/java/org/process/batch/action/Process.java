package org.process.batch.action;

import org.process.batch.action.abstracts.AbstractProcess;
import org.worker.contracts.Work;

public class Process extends AbstractProcess {

	private String processName;

	{
		init();
	}

	@Override
	public void init() {
		processName = "Process";
		super.init();
	}

	@Override
	public void destroy() {

		super.destroy();
	}

	public void startProcess(Work work) {
		for (int counter = 0; counter < getAvailableProcessors(); counter++) {
			getWorkAssigner().assignWroker(work);
		}
	}

	public void stopProcess() {

	}

	public void setProcessName(String processName) {
		this.processName = processName;

	}

}
