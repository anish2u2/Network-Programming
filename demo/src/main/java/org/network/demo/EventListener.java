package org.network.demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;

import org.commons.contracts.Destroy;
import org.network.client.factory.ClientFactory;
import org.network.contracts.Client;
import org.network.contracts.CommunicationChannel;
import org.network.contracts.FileReader;
import org.network.contracts.FileWriter;
import org.worker.contracts.Work;
import org.worker.manager.WorkersManager;

public class EventListener implements ActionListener, Destroy {

	private JFrame frame;

	private ServerMaintainer server;

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if ("SERVER".equals(command)) {
			server = new ServerMaintainer();
			server.startServer();

			System.out.println("GOt the channel");
			FileChooser fileChooser = new FileChooser();
			final JFileChooserListener jfChooserListener = new JFileChooserListener();
			jfChooserListener.setFrame(frame);
			fileChooser.addActionListener(jfChooserListener);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(fileChooser);
			WorkersManager.getInstance().assignWroker(new Work() {

				public void work() {
					while (true) {
						final CommunicationChannel channel = server.getChannel();
						System.out.println("Waiting to select file");
						List<String> files = jfChooserListener.listOfSelectedFiles();
						System.out.println("File selected.");
						for (String str : files) {
							System.out.println("File:" + str);
							FileWriter writer = new org.network.io.file.writer.FileWriter();
							writer.setOutputStream(channel.getWriter().getOutputStream());
							writer.writeFile(str);
						}
					}
				}

				public void stopWork() {
					// TODO Auto-generated method stub

				}
			});
		} else if ("CLIENT".equals(command)) {
			Client client = ClientFactory.getInstance().create();
			client.connectTo(9889);
			CommunicationChannel communicationChannel = client.getCommunicationChannel();
			FileReader fileReader = new org.network.io.file.reader.FileReader();
			fileReader.setInputStream(communicationChannel.getReader().getInputStream());
			fileReader.readFile("D:/temp");
		}
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void destroy() {
		server.destroy();
	}

}
