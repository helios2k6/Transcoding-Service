package com.nlogneg.transcodingService.utilities.system;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

/**
 * Calls System.Exit with the return code
 * @author anjohnson
 *
 */
public class ExitSystemCommand extends SimpleCommand{
	
	@Override
	public void execute(INotification notification){
		Object body = notification.getBody();
		if(body != null && body instanceof Integer){
			Integer returnCode = (Integer)body;
			System.exit(returnCode);
		}
		
		System.exit(0);
	}
}
