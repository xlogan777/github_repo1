package com.java_lang_exp;

public class MainJavaLangExp 
{
	public static void test0()
	{
		try
		{
			System.out.println("Test");		
			JsonP_Example json_example1 = new JsonP_Example();
			json_example1.parser();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		//test 0
		test0();
	}

}
