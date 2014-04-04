package com.nlogneg.transcodingService.request.server.protocol;

import java.io.OutputStream;

public final class SendResponseAction implements MediaRequestProtocolVisitor{

	private final OutputStream outputStream;
	
	public SendResponseAction(OutputStream outputStream)
	{
		this.outputStream = outputStream;
	}
	
	@Override
	public void visit(MediaRequestProtocol protocol) {
		// TODO Auto-generated method stub
		
	}

}
