package com.debal.java.test;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class SerializationTest implements Externalizable{
	private String key;
	
	public SerializationTest(String key) {
		this.key = key;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readExternal(ObjectInput arg0) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeExternal(ObjectOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
