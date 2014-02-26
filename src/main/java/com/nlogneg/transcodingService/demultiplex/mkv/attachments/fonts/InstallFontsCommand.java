package com.nlogneg.transcodingService.demultiplex.mkv.attachments.fonts;

import java.nio.file.Path;
import java.util.Collection;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

/**
 * Represents the installation of fonts depending on the OS
 * @author anjohnson
 *
 */
public abstract class InstallFontsCommand extends SimpleCommand{
	
	private static final Logger Log = LogManager.getLogger(InstallFontsCommand.class);
	
	public final void execute(INotification notification){
		Log.info("Installing fonts");
		
		Collection<Path> fontFiles = getFonts();
		installFonts(fontFiles);
		
		Log.info("Finished installing fonts");
	}

	private Collection<Path> getFonts() {
		ExtractedFontAttachmentsProxy proxy = (ExtractedFontAttachmentsProxy)getFacade().retrieveProxy(ExtractedFontAttachmentsProxy.PROXY_NAME);
		Collection<Path> fontFiles = proxy.getAllFonts();
		return fontFiles;
	}
	
	protected abstract void installFonts(Collection<Path> fontFiles);
}
