package com.algm_code;

public class StackWithSllNode 
{
	private SllNode top = null;
	
	public StackWithSllNode()
	{
		
	}
	
	public void push(String data)
	{
		SllNode node = new SllNode(data);
		node.setNext(top);
		top = node;
		System.out.println("pushed = "+top.getData());
	}
	
	public String pop()
	{
		String rv = null;
		
		if(top != null)
		{
			rv = top.getData();
			top = top.getNext();
		}
		
		return rv;
	}
}
