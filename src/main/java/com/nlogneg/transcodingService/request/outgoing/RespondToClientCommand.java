package com.nlogneg.transcodingService.request.outgoing;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.thoughtworks.xstream.XStream;

/**
 * Responses to a client with the specified acknowledgment and closes the socket
 * 
 * @author anjohnson
 * 
 */
public final class RespondToClientCommand extends SimpleCommand
{
	private static final Logger Log = LogManager
			.getLogger(RespondToClientCommand.ResponseTuple.class);

	public final class ResponseTuple
	{
		private final Acknowledgement acknowledgement;
		private final Socket socket;

		/**
		 * @param acknowledgement
		 * @param socket
		 */
		public ResponseTuple(final Acknowledgement acknowledgement,
				final Socket socket)
		{
			this.acknowledgement = acknowledgement;
			this.socket = socket;
		}
	}

	@Override
	public void execute(final INotification notification)
	{
		final ResponseTuple responseTuple = (ResponseTuple) notification
				.getBody();

		final Socket socket = responseTuple.socket;
		final Acknowledgement ack = responseTuple.acknowledgement;

		final XStream serializer = SerializerFactory
				.getAcknowledgementSerializer();

		final String serializedResponse = serializer.toXML(ack);

		try (PrintWriter writer = new PrintWriter(socket.getOutputStream(),
				true);)
		{
			writer.print(serializedResponse);
		} catch (final IOException e)
		{
			Log.error("Could not send response back to client.", e);
		} finally
		{
			try
			{
				socket.close();
			} catch (final IOException e)
			{
				Log.error("Could not close socket.", e);
			}
		}
	}
}
