package org.network.demo;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;

import org.worker.contracts.Work;
import org.worker.manager.WorkersManager;

public class App {

	public static void main(String[] args) {

		Frame frame = new Frame();
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
		frame.setVisible(true);
		ActionButton server = new ActionButton();
		server.setActionCommand("SERVER");
		server.setText("SERVER");
		ActionButton client = new ActionButton();
		client.setText("CLIENT");
		client.setActionCommand("CLIENT");
		final EventListener listener = new EventListener();
		listener.setFrame(frame);
		client.addActionListener(listener);
		server.addActionListener(listener);
		frame.getContentPane().add(server);
		frame.getContentPane().add(client);
		frame.setBounds(200, 200, 300, 300);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				listener.destroy();
				System.exit(0);
			}
		});
		WorkersManager.getInstance().assignWroker(new Work() {

			public void work() {
				long totalMemory = Runtime.getRuntime().totalMemory();
				while (true) {
					System.out.println("Total Memory:" + (totalMemory / (1024 * 1024)) + " free memory:"
							+ (Runtime.getRuntime().freeMemory() / (1024 * 1024)) + " used meory:"
							+ ((totalMemory - Runtime.getRuntime().freeMemory()) / (1024 * 1024)));
					Runtime.getRuntime().gc();
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

			public void stopWork() {
				// TODO Auto-generated method stub

			}
		});
	}

	public static class ActionButton extends JButton {

	}
}
