package org.process.batch.contracts;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.worker.contracts.Work;

/**
 * This interface represents a Process which allow to handle a process consist
 * of multi-threaded task.
 * 
 * @author Anish Singh
 *
 */
public interface Process extends Init, Destroy {

	/**
	 * This method allow you to set the process name.
	 * 
	 * @param processName
	 */
	public void setProcessName(String processName);

	/**
	 * This method will allow you accomplish your work with its best
	 * multi-thread effort.
	 * 
	 * @param work
	 */
	public void startProcess(Work work);

	/**
	 * This method will stop the process work whatever assigned to this process.
	 */
	public void stopProcess();

}
