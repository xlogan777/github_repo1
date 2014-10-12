package com.java_lang_exp;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class JsonP_ExampleTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("before class");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("after class");
	}

	@Test
	public void testParser() throws Exception {
		System.out.println("Hello testing here");
		assertEquals(1,1);
		
	}
	
	@Test
	public void testadding() throws Exception{ 
		
		JsonP_Example jp = new JsonP_Example();
		int sum = jp.adding(10, 20);
		if(sum < 30)
			throw new Exception("sum didnt work");
	}

}
