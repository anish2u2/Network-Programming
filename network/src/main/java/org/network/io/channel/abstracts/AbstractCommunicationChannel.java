package org.network.io.channel.abstracts;

import java.net.Socket;

import org.commons.contracts.Destroy;
import org.commons.contracts.Init;
import org.network.contracts.Channel;
import org.network.contracts.CommunicationChannel;
import org.network.contracts.Reader;
import org.network.contracts.Writer;
import org.network.io.reader.StreamReader;
import org.network.io.writer.StreamWriter;

public abstract class AbstractCommunicationChannel implements Channel, Init, Destroy {

	private Socket socket;

	@Override
	public CommunicationChannel create() {
		final Socket requestSocket = this.socket;
		socket = null;
		CommunicationChannel communicationChannel = new CommunicationChannel() {
			private Writer writer;
			private Reader reader;

			{
				init();
			}

			@Override
			public void setWriter(Writer writer) {
				this.writer = writer;
			}

			@Override
			public void setReader(Reader reader) {
				this.reader = reader;
			}

			@Override
			public Writer getWriter() {

				return this.writer;
			}

			@Override
			public Reader getReader() {

				return this.getReader();
			}

			@Override
			public void init() {
				try {
					writer = new StreamWriter(requestSocket.getOutputStream());
					reader = new StreamReader(requestSocket.getInputStream());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void destroy() {
				writer.destroy();
				writer = null;
				reader.destroy();
				reader = null;
			}

			@Override
			protected void finalize() throws Throwable {
				super.finalize();
				writer = null;
				reader = null;
			}

			@Override
			public boolean isCommunicationAlive() {
				return !requestSocket.isClosed();
			}
		};
		addCommunicationChannelForFutureGC(communicationChannel);
		return communicationChannel;
	}

	@Override
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void destroy() {
		socket = null;
	}

	protected abstract void addCommunicationChannelForFutureGC(CommunicationChannel channel);
}
