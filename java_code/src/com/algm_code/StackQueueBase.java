package com.algm_code;

//base class for stacks and queues
public class StackQueueBase
{
	public StackQueueBase(int size)
	{
		dataArray = new String[size];
		count = 0;
	}
	
	public boolean isEmpty()
	{
		return (count == 0);
	}
	
	public boolean isFull()
	{
		return (count == dataArray.length);
	}
	
	public int NumElements() 
	{
		return count;
	}
		
	protected String dataArray[];
	protected int count;
}