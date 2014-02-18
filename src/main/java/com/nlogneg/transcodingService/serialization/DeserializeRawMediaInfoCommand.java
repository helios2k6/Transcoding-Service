package com.nlogneg.transcodingService.serialization;

import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.thoughtworks.xstream.XStream;

public class DeserializeRawMediaInfoCommand extends SimpleCommand{
	private final XStream deserializer;
	
	public DeserializeRawMediaInfoCommand(XStream deserializer){
		this.deserializer = deserializer;
	}
	
	public DeserializeRawMediaInfoCommand(){
		throw new NotImplementedException();
	}
}
