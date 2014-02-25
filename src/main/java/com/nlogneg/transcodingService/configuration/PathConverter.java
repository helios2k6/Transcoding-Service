package com.nlogneg.transcodingService.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PathConverter implements Converter{

	@Override
	public boolean canConvert(Class arg0) {
		return arg0 == Path.class;
	}

	@Override
	public void marshal(
			Object arg0, 
			HierarchicalStreamWriter arg1,
			MarshallingContext arg2){
		
		throw new NotImplementedException();
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context){
		String filePath = reader.getValue();
		return Paths.get(filePath);
	}

}
