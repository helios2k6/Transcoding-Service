package com.nlogneg.transcodingService.request.server.mediaRequestProtocol;

/**
 * A transaction upon a ProtocolSocket
 * @author Andrew
 *
 */
public interface ProtocolTransaction
{
	/**
	 * Enacts a transaction upon the ProtocolSocket
	 * @param socket The socket
	 */
	public void enactTransaction(ProtocolSocket socket);
}
