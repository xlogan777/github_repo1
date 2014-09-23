package com.algm_code;

public class ArrayStack extends StackQueueBase implements Cloneable
{
	private int top;
	
	public ArrayStack(int size)
	{
		super(size);
		top = -1;
	}
	
	public boolean push(String element)
	{
		boolean ret_val = false;
		
		if(this.isFull())
			System.out.println("Stack is full");
		else
		{
			this.count++;
			this.top++;
			this.dataArray[top] = element;
			ret_val = true;
		}
		
		return ret_val;
	}
	
	public String pop()
	{
		String ret_val = null;
		
		if(this.isEmpty())
			System.out.println("stack is empty");
		else
		{
			ret_val = this.dataArray[top];
			this.dataArray[top] = null;//remove ref from array.
			this.count--;
			this.top--;
		}
		
		return ret_val;
	}
	
	//making my own version of clone, that is calling Object's clone function
	//but for this to work, then you need to have this class implement
	//the cloneable interface otherwise, calling super will yield an exception
	//calling super.clone, will always yield a shallow copy of the obj.
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
