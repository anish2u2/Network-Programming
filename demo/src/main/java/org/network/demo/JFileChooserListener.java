package org.network.demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class JFileChooserListener implements ActionListener {

	private JFrame component;

	private static Object lock = new Object();

	private List<String> files;

	public void actionPerformed(ActionEvent e) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setMultiSelectionEnabled(true);
		String actionCommand = e.getActionCommand();
		org.logger.api.Logger.getInstance().info("command:" + actionCommand);
		if ("ApproveSelection".equals(actionCommand)) {
			int odInt = fileChooser.showOpenDialog(((JFrame) component).getContentPane());
			File[] filesToBeSend = fileChooser.getSelectedFiles();
			if ((odInt == JFileChooser.APPROVE_OPTION) && filesToBeSend != null) {
				files = new ArrayList<String>();
				for (File file : filesToBeSend) {
					files.add(file.getAbsolutePath());
				}
			}
			synchronized (lock) {
				lock.notifyAll();
			}
		}
	}

	public void setFrame(JFrame frame) {
		this.component = frame;
	}

	public List<String> listOfSelectedFiles() {
		if (files != null)
			return files;
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return files;
		}
	}

}
