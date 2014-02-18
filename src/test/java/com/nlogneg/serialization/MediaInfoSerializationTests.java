package com.nlogneg.serialization;

import java.io.Serializable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@RunWith(JUnit4.class)
public class MediaInfoSerializationTests {
	
	private class Immutable implements Serializable{
		private final String a;
		
		public Immutable(String a){
			this.a = a;
		}
		
		public String getA(){
			return this.a;
		}
	}
	
	@Test
	public void deserializeImmutable(){
		XStream stream = new XStream(new DomDriver());
		String output = stream.toXML(new Immutable("hello"));
		Immutable i = (Immutable)stream.fromXML(output);
	}
}
