package org.worker.manager.abstracts;

import java.util.LinkedList;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.commons.properties.ApplicationPropertyReader;
import org.pattern.contracts.creational.ObjectPooling;
import org.worker.contracts.Worker;

public abstract class AbstractWorkersManager implements ObjectPooling<Worker>, Init, Destroy {

	private static final WorkersMonitor MONITOR = new WorkersMonitor();

	private int MAX_POOL_SIZE = 0;
	{
		init();
	}

	public Worker fetch() {
		MONITOR.releaseWorkers();
		if (MAX_POOL_SIZE < (MONITOR.POOL_SIZE + 1)) {
			throw new RuntimeException(
					ApplicationPropertyReader.getInstance().getMessage("The Max limit of Workers pool is exceeded"));
		}
		Worker worker = getWorker();
		return worker;
	}

	public void expire(Worker object) {
		MONITOR.removeFromPool(object);
	}

	public void destroy() {
		MONITOR.destroy();
	}

	public void init() {
		int size = Integer.parseInt(ApplicationPropertyReader.getInstance().getMessage("default.worker.pool.size"));
		org.logger.api.Logger.getInstance().info("Default workers pool size:" + size);
		MONITOR.setPoolSize(size);
		MAX_POOL_SIZE = Integer.parseInt(ApplicationPropertyReader.getInstance().getMessage("max.worker.pool.size"));
	}

	protected abstract Worker getWorker();

	private static class WorkersMonitor implements Init, Destroy, org.worker.contracts.WorkersMonitor {
		private LinkedList<Worker> workers;

		private int POOL_SIZE = 0;

		public WorkersMonitor() {

		}

		{
			init();
		}

		public void destroy() {
			org.logger.api.Logger.getInstance().info("Destroying Workers Monitor.");
			for (Worker worker : workers) {
				worker.destroy();
				POOL_SIZE--;
			}
			workers.clear();
			workers = null;
		}

		public void init() {
			workers = new LinkedList<Worker>();
		}

		public void removeFromPool(Worker worker) {
			if (workers.remove(worker)) {
				POOL_SIZE--;
			}
		}

		public void addToPool(Worker worker) {
			this.workers.add(worker);
		}

		public void setPoolSize(int size) {
			POOL_SIZE = size;

		}

		@Override
		protected void finalize() throws Throwable {
			destroy();
			super.finalize();
		}

		public void releaseWorkers() {
			org.logger.api.Logger.getInstance().info("Releasing all workers.");
			for (Worker worker : workers) {
				if (worker.isWorking()) {
					worker.destroy();
					POOL_SIZE--;
				}
			}
			if (POOL_SIZE > 5)
				workers.clear();
			org.logger.api.Logger.getInstance().info("All workers are released.");
		}

	}

	public void releaseWorkers() {
		MONITOR.releaseWorkers();
	}

	@Override
	protected void finalize() throws Throwable {
		destroy();
		super.finalize();
	}

}
