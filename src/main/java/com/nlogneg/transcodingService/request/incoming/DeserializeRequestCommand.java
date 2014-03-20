package com.nlogneg.transcodingService.request.incoming;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;
import org.puremvc.java.multicore.patterns.facade.Facade;

import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;

/**
 * Represents the deserialization of a request
 * 
 * @author anjohnson
 * 
 */
public class DeserializeRequestCommand extends SimpleCommand
{

	private static final Logger Log = LogManager
			.getLogger(DeserializeRequestCommand.class);

	private final XStream deserializer;

	/**
	 * Constructs a new deserialize request command given the XStream
	 * deserializer
	 * 
	 * @param deserializer
	 *            The deserializer
	 */
	public DeserializeRequestCommand(final XStream deserializer)
	{
		this.deserializer = deserializer;
	}

	/**
	 * Constructs the default deserialize request command with the default
	 * XStream deserializer
	 */
	public DeserializeRequestCommand()
	{
		this.deserializer = SerializerFactory
				.generateDefaultRequestSerializer();
	}

	public void Execute(final INotification notification)
	{
		final Facade facade = this.getFacade();
		final SerializedRequestProxy serializedRequestProxy = (SerializedRequestProxy) facade
				.retrieveProxy(SerializedRequestProxy.PROXY_NAME);
		final Optional<String> request = serializedRequestProxy
				.getNextSerializedRequest();

		if (request.isNone())
		{
			Log.info("No requests to deserialize");
			return;
		}

		try
		{
			final Request deserializedRequest = (Request) this.deserializer
					.fromXML(request.getValue());
			final RequestProxy requestProxy = (RequestProxy) facade
					.retrieveProxy(RequestProxy.PROXY_NAME);
			requestProxy.addRequest(deserializedRequest);
		} catch (final XStreamException ex)
		{
			Log.error("Unable to deserialize request", ex);
			return;
		}
	}
}
