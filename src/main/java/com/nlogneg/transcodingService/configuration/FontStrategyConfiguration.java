package com.nlogneg.transcodingService.configuration;

import java.nio.file.Path;

/**
 * Represents the font strategy to use
 * @author anjohnson
 *
 */
public final class FontStrategyConfiguration{
	public enum FontInstallationStrategy{
		InstallToFolder,
		LeaveInEncodingDirectory,
		DoNothing,
	}
	
	private final Path fontFolder;
	private final FontInstallationStrategy fontInstallationStrategy;
	private final String[] commandsToInstallFonts;
	
	/**
	 * Represents a font installation strategy
	 * @param fontFolder
	 * @param fontInstallationStrategy
	 * @param commandsToInstallFonts
	 */
	public FontStrategyConfiguration(Path fontFolder,
			FontInstallationStrategy fontInstallationStrategy,
			String[] commandsToInstallFonts) {
		super();
		this.fontFolder = fontFolder;
		this.fontInstallationStrategy = fontInstallationStrategy;
		this.commandsToInstallFonts = commandsToInstallFonts;
	}

	/**
	 * @return the fontFolder
	 */
	public Path getFontFolder() {
		return fontFolder;
	}

	/**
	 * @return the fontInstallationStrategy
	 */
	public FontInstallationStrategy getFontInstallationStrategy() {
		return fontInstallationStrategy;
	}

	/**
	 * @return the commandsToInstallFonts
	 */
	public String[] getCommandsToInstallFonts() {
		return commandsToInstallFonts;
	}
	
	
}
