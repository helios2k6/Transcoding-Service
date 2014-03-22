package com.nlogneg.demultiplex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJob;
import com.nlogneg.transcodingService.utilities.Optional;

import com.nlogneg.transcodingService.demultiplex.DemultiplexJobFactory;

@RunWith(JUnit4.class)
public class TestDemultiplexFactory
{

	@Test
	public void createDemultiplexJob()
	{
		Optional<? extends DemultiplexJob> job = DemultiplexJobFactory.tryCreateDemultiplexJob(null, null);
		
	}
}
