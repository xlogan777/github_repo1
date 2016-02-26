package com.algm_code;

//test
public class ArrayQueue extends StackQueueBase 
{
	private int head, tail;
	
	public ArrayQueue(int size)
	{
		super(size);
		head = tail = 0;
	}
	
	public boolean enqueue(String element)
	{
		boolean rv = false;
		
		if(this.isFull())
			System.out.println("queue is empty");
		else
		{			
			this.dataArray[tail] = element;
			tail = (tail+1) % this.dataArray.length;
			rv = true;
			this.count++;
		}
		
		return rv;
	}
	
	public String dequeue()
	{
		String rv = null;
		
		if(this.isEmpty())
			System.out.println("queue is emtpy");
		else
		{
			rv = this.dataArray[head];
			this.dataArray[head] = null;
			head = (head+1) % this.dataArray.length;
			this.count--;
		}
		
		return rv;
	}
}
