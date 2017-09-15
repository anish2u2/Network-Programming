package org.worker.contracts;

public interface WorkersMonitor {

	public void removeFromPool(Worker worker);

	public void addToPool(Worker worker);

	public void setPoolSize(int size);

	public void releaseWorkers();
}
