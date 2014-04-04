package com.nlogneg.transcodingService.request.server.protocol;

import com.nlogneg.transcodingService.request.server.protocol.MediaRequestProtocol.State;

/**
 * Set the initial protocol state
 * @author anjohnson
 *
 */
public final class InitializeProtocolAction implements MediaRequestProtocolVisitor{

	@Override
	public void visit(MediaRequestProtocol protocol) {
		protocol.setCurrentState(State.WAITING);
		protocol.setRequest(null);
	}

}
