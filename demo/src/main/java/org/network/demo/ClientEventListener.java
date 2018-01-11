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

public class ClientEventListener implements ActionListener, Destroy {
	private JFrame frame;

	private ServerMaintainer server;

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		/*
		 * if ("SERVER".equals(command)) { server = new ServerMaintainer();
		 * server.startServer();
		 * 
		 * org.logger.api.Logger.getInstance().info("GOt the channel");
		 * FileChooser fileChooser = new FileChooser(); final
		 * JFileChooserListener jfChooserListener = new JFileChooserListener();
		 * jfChooserListener.setFrame(frame);
		 * fileChooser.addActionListener(jfChooserListener);
		 * frame.getContentPane().removeAll();
		 * frame.getContentPane().add(fileChooser);
		 * WorkersManager.getInstance().assignWroker(new Work() {
		 * 
		 * public void work() { while (true) { final CommunicationChannel
		 * channel = server.getChannel();
		 * org.logger.api.Logger.getInstance().info("Waiting to select file");
		 * List<String> files = jfChooserListener.listOfSelectedFiles();
		 * org.logger.api.Logger.getInstance().info("File selected."); for
		 * (String str : files) {
		 * org.logger.api.Logger.getInstance().info("File:" + str); FileWriter
		 * writer = new org.network.io.file.writer.FileWriter();
		 * writer.setOutputStream(channel.getWriter().getOutputStream());
		 * writer.writeFile(str); } } }
		 * 
		 * public void stopWork() { // TODO Auto-generated method stub
		 * 
		 * } }); } else
		 */ if ("CLIENT".equals(command)) {
			final Client client = ClientFactory.getInstance().create();
			client.connectTo("192.168.43.1", 9889);

			// FileReader fileReader = new
			// org.network.io.file.reader.FileReader();
			// fileReader.setInputStream(communicationChannel.getReader().getInputStream());
			// fileReader.readFile("D:/temp");

			org.logger.api.Logger.getInstance().info("GOt the channel");
			FileChooser fileChooser = new FileChooser();
			final JFileChooserListener jfChooserListener = new JFileChooserListener();
			jfChooserListener.setFrame(frame);
			fileChooser.addActionListener(jfChooserListener);
			frame.getContentPane().removeAll();
			frame.getContentPane().add(fileChooser);
			WorkersManager.getInstance().assignWroker(new Work() {

				public void work() {
					//while (true) {
						try {
							// final CommunicationChannel channel =
							// server.getChannel();
							System.out.println("Starting :");
							CommunicationChannel communicationChannel = client.getCommunicationChannel();
							
							System.out.println("acquiring reader");
							String line = communicationChannel.getReader().read();
							System.out.println("got data :" + line);
							if ("GOT_CONNECTION_ACKNOWLEDGE".equals(line)){
								System.out.println("acquiring writer");
								communicationChannel.getWriter().write("GOT_ACKNOWLEDGE_CLIENT");
								communicationChannel.getWriter().getOutputStream().flush();
								//communicationChannel.getWriter().write("GOT_ACKNOWLEDGE_CLIENT");
							org.logger.api.Logger.getInstance().info("Waiting to select file");
							List<String> files = jfChooserListener.listOfSelectedFiles();
							if("START_FILE_SENDING".equals(communicationChannel.getReader().read())){
							org.logger.api.Logger.getInstance().info("File selected.");
							for (String str : files) {
								org.logger.api.Logger.getInstance().info("File:" + str);
								FileWriter writer = new org.network.io.file.writer.FileWriter();
								writer.setOutputStream(communicationChannel.getWriter().getOutputStream());
								writer.writeFile(str);
							}
							}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					//}
				}

				public void stopWork() {
					// TODO Auto-generated method stub

				}
			});
		}
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void destroy() {
		server.destroy();
	}
}
