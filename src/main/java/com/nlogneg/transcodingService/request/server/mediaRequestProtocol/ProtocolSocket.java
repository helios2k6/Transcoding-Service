package com.nlogneg.transcodingService.request.server.mediaRequestProtocol;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.codec.binary.Base64;

import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.utilities.Optional;
import com.nlogneg.transcodingService.utilities.SerializerFactory;
import com.thoughtworks.xstream.XStream;

/**
 * Represents the connection between a client and the server and offers
 * functionality that allows the client and server to communicate
 * @author Andrew
 *
 */
public final class ProtocolSocket implements Closeable, AutoCloseable
{
	public enum State
	{
		WAITING,
		REQUEST,
		KEEP_ALIVE,
		RESPONSE,
		CLOSED,
		ERROR
	}
	
	private static final String MESSAGE_START_IDENTIFIER = "::BEGIN_MESSAGE::";
	private static final String MESSAGE_END_IDENTIFIER = "::END_MESSAGE::";
	private static final XStream Serializer = SerializerFactory.getMediaRequestProtocolMessageSerializer();
	
	private final Socket socket;
	private State state = State.WAITING;
	
	/**
	 * Constructs a new MediaRequestProtocolSocket
	 * @param socket
	 */
	public ProtocolSocket(Socket socket)
	{
		this.socket = socket;
	}
	
	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException
	{
		socket.close();
	}
	
	/**
	 * Sends an Acknowledgment message
	 */
	public void sendAcknowledgment()
	{
		
	}
	
	/**
	 * Sends the KEEPALIVE message
	 */
	public void sendKeepAlive()
	{
	}
	
	/**
	 * Sends an error message to the client
	 * @param reason
	 */
	public void sendError(String reason)
	{
	}
	
	/**
	 * Sends the close message to the client
	 */
	public void sendClose()
	{
	}
	
	/**
	 * Reads the Acknowledgment message from the client or times out. 
	 * @return true if the client sent an acknowledgment. false otherwise
	 */
	public boolean readClientAcknowledgment()
	{
		return false;
	}
	
	/**
	 * Reads the Request sent by a client
	 * @return
	 */
	public Optional<Request> readClientRequest()
	{
		return Optional.none();
	}
	
	/**
	 * Sends a payload to the client. The string must be formatted in BASE64 using RFC 2045
	 * @param payload
	 * @throws IOException When the underlying Socket throws 
	 */
	public void sendMessage(String type, String payload) throws IOException
	{
		Message message = constructMessage(type, payload);
		String serializedMessage = constructSerializedMessage(message);
		
		OutputStream stream = socket.getOutputStream();
		stream.write(serializedMessage.getBytes());
	}
	
	/**
	 * Sends a raw byte payload to the client, which will automatically be formatted in BASE64
	 * @param payload
	 * @throws IOException When the underlying Socket throws
	 */
	public void sendMessage(String type, byte[] payload) throws IOException
	{
		String encodedString = Base64.encodeBase64String(payload);
		sendMessage(type, encodedString);
	}
	
	/**
	 * Gets the state of the Protocol Socket
	 * @return
	 */
	public State getState()
	{
		return this.state;
	}
	
	/**
	 * Sets the state of the Protocol Socket
	 * @param state
	 */
	public void setState(State state)
	{
		this.state = state;
	}
	
	private static Message constructMessage(String type, String payload)
	{
		Message message = new Message();
		
		Header header = new Header();
		header.setType(type);
		
		message.setHeader(header);
		message.setPayload(payload);
		
		return message;
	}
	
	private static String constructSerializedMessage(Message message)
	{
		return MESSAGE_START_IDENTIFIER + Serializer.toXML(message) + MESSAGE_END_IDENTIFIER;
	}
}
