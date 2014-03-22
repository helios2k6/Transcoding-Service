package com.nlogneg.transcodingService.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class PathConverter implements Converter
{

	@Override
	public boolean canConvert(final Class arg0)
	{
		return arg0 == Path.class;
	}

	@Override
	public void marshal(
			final Object arg0,
			final HierarchicalStreamWriter arg1,
			final MarshallingContext arg2)
	{

		throw new NotImplementedException();
	}

	@Override
	public Object unmarshal(
			final HierarchicalStreamReader reader,
			final UnmarshallingContext context)
	{
		final String filePath = reader.getValue();
		return Paths.get(filePath);
	}

}
