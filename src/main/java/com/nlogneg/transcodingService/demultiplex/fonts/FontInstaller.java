package com.nlogneg.transcodingService.demultiplex.fonts;

import java.nio.file.Path;
import java.util.Collection;

/**
 * Represents a class that can install fonts on a particular system
 * @author Andrew
 *
 */
public interface FontInstaller{
	public boolean installFonts(Collection<Path> fonts, Path fontFolder);
}
