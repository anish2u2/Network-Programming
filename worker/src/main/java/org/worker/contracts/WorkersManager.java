package org.worker.contracts;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;

public interface WorkersManager extends Init, Destroy {

	public void assignWroker(Work work);

	public void assignWroker(Work work, String processName);

	public void releaseWorkers();

}
