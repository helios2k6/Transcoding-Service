package com.nlogneg.transcodingService.demultiplex.mkv.attachments;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public abstract class InstallFontsCommand extends SimpleCommand{
	
	private static final Logger Log = LogManager.getLogger(InstallFontsCommand.class);
	
	public final void execute(INotification notification){
		Log.info("Installing fonts");
		
	}
}
