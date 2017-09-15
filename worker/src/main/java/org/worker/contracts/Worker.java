package org.worker.contracts;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface Worker extends Init, Destroy {

	public void doWork(Work work);

	public void doWork(Work work, boolean backGroundWorker);

	public boolean isWorking();

	public void setWorkerName(String workerName);
}
