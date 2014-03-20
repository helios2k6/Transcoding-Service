package com.nlogneg.transcodingService.utilities.system;

import org.puremvc.java.multicore.patterns.command.MacroCommand;

/**
 * Prints the help text and then exits the system
 * 
 * @author anjohnson
 * 
 */
public class PrintHelpAndExitMacroCommand extends MacroCommand
{

	@Override
	protected final void initializeMacroCommand()
	{
		this.addSubCommand(new PrintHelpCommand());
		this.addSubCommand(new ExitSystemCommand());
	}
}
