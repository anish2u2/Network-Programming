package org.network.server;

import java.net.ServerSocket;
import java.net.Socket;

import org.commons.contracts.Destroy;
import org.network.contracts.Channel;
import org.network.contracts.CommunicationChannel;
import org.network.io.channel.CommunicationChannelFactory;
import org.network.server.abstracts.AbstractServer;
import org.network.signal.IncommingSignal;
import org.pattern.contracts.behavioral.Signal;
import org.worker.contracts.Work;

public class Server extends AbstractServer {

	private Signal<CommunicationChannel> signal;
	private Channel channel;
	private final ServerCommand serverCommand = new ServerCommand();

	{
		init();
	}

	@Override
	public void init() {
		super.init();
		signal = IncommingSignal.getInstance();
		channel = CommunicationChannelFactory.getInstance();
	}

	@Override
	protected void initRequest(final ServerSocket serverSocket) {
		org.worker.manager.WorkersManager.getInstance().assignWroker(new Work() {

			@Override
			public void work() {
				while (!serverCommand.isStopServerCommandExecuted()) {
					try {
						Socket socket = serverSocket.accept();
						channel.setSocket(socket);
						signal.releaseSignal(channel.create());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});
	}

	@Override
	public void destroy() {
		super.destroy();
		((Destroy) signal).destroy();
		((Destroy) channel).destroy();
	}

	@Override
	public void stopServer() {
		serverCommand.setStopServerCommandExecuted(true);
	}

	private static class ServerCommand {
		private boolean stopServerCommandExecuted;

		public boolean isStopServerCommandExecuted() {
			return stopServerCommandExecuted;
		}

		public void setStopServerCommandExecuted(boolean isStopServerCommandExecuted) {
			this.stopServerCommandExecuted = isStopServerCommandExecuted;
		}

	}

}
