package com.nlogneg.transcodingService.request.server.protocol;

import com.nlogneg.transcodingService.request.incoming.Request;

/**
 * Represents the state of the Media Request Protocol
 * @author anjohnson
 *
 */
public final class MediaRequestProtocol {
	
	/**
	 * The state of the MediaRequestProtocol
	 * @author anjohnson
	 *
	 */
	public enum State
	{
		WAITING,
		RECEIVED_REQUEST_SUCCESS,
		RECEIVED_REQUEST_FAILURE,
		RESPONDED_ACCEPT,
		RESPONDED_REJECT,
	}
	
	private State currentState;
	private Request request;
	
	/**
	 * @return the currentState
	 */
	public State getCurrentState() {
		return currentState;
	}
	
	/**
	 * @param currentState the currentState to set
	 */
	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
	
	/**
	 * @return the request
	 */
	public Request getRequest() {
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public void accept(MediaRequestProtocolVisitor visitor)
	{
		visitor.visit(this);
	}
}
