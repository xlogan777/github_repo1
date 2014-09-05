package com.algm_code;

public class MathAlgms 
{
	public MathAlgms(){}
	
	public int factorialRecursive(int num)
	{
		if(num == 0 || num == 1)
			return 1;
		else
			return (num * factorialRecursive(num-1));
	}
	
	public int fibonacciRecursive(int num)
	{
		if(num == 1 || num == 2)
			return 1;
		else
			return ( fibonacciRecursive(num-2) + fibonacciRecursive(num-1) );
	}
	
	//computes the factorial of a number
	public void factorial(int num)
	{
		int ans = 1;
		
		for(int i = num; i > 0; i--)
			ans *= i;
		
		System.out.println(num+"!"+" = "+ ans);
	}
	
	//prints the number in the fibonacci seq.
	public void fibonacci(int num)
	{
		int val = 0;
		
		//handle both the first and second num
		//in fib seq at 1.
		if(num == 1 || num == 2)
			val = 1;
		else
		{
			int a = 1;
			int b = 1;
			
			//start with 2 numbers less in loop since
			//we account for 1 and 2.
			for(int i = 0; i < num-2; i++)
			{
				val = a+b;
				a = b;
				b = val;				
			}
		}
		
		System.out.println(num+"th fibonacci num = "+val);
	}
	
	/*
	 * recursive linear search. This will run in O(n) runtime.
	 * testing the concept of recursion for simple search algm.
	 */
	public boolean linearSearchRecusive(int [] input, int item, int index)
	{
		boolean rv = true;
		
		//if we reached end of array or outside, them we didnt find it.
		if(index >= input.length)
		{
			rv = false;
		}
		//if item doesnt match array loc index item, continue to recurse.
		else if( input[index] != item)
		{
			//increase index and try further.
			rv = linearSearchRecusive(input, item, ++index);
		}
		
		//return val.
		return rv;
	}
	
}
