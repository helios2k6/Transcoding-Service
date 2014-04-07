package com.nlogneg.transcodingService.request.server.mediaRequestProtocol;

import com.nlogneg.transcodingService.request.incoming.Request;
import com.nlogneg.transcodingService.request.server.mediaRequestProtocol.ProtocolSocket.State;
import com.nlogneg.transcodingService.utilities.Optional;

/**
 * Reads the client transaction
 * @author Andrew
 *
 */
public final class ReadClientRequestTransaction implements ProtocolTransaction
{
	private Optional<Request> request;
	
	/* (non-Javadoc)
	 * @see com.nlogneg.transcodingService.request.server.mediaRequestProtocol.ProtocolTransaction#enactTransaction(com.nlogneg.transcodingService.request.server.mediaRequestProtocol.ProtocolSocket)
	 */
	@Override
	public void enactTransaction(ProtocolSocket socket)
	{
		if(socket.getState() != State.WAITING)
		{
			throw new InvalidProtocolSocketStateException("State was not 'WAITING'. It was: " + socket.getState());
		}
		
		request = socket.readClientRequest();
		socket.setState(State.KEEP_ALIVE);
	}

	/**
	 * @return the request
	 */
	public Optional<Request> getRequest()
	{
		return request;
	}
}