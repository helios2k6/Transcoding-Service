package com.nlogneg.transcodingService.demultiplex.fonts;

import com.nlogneg.transcodingService.utilities.system.SystemUtilities;
import com.nlogneg.transcodingService.utilities.system.SystemUtilities.OperatingSystem;

/**
 * A factory class that creates font installers based on the system
 * @author Andrew
 *
 */
public final class FontInstallerFactory{
	
	/**
	 * Creates a Font Installer for the given operating system
	 * @return
	 */
	public static FontInstaller createFontInstaller(){
		OperatingSystem operatingSystem = SystemUtilities.getOperatingSystem();
		switch(operatingSystem){
		case UnixLike:
			return new UnixFontInstaller();
		case Windows:
			return new WindowsFontInstaller();
		default:
			throw new RuntimeException("Unknown Operating System. Can't make font installer.");
		}
	}
}
