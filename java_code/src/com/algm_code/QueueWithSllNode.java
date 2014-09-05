package com.algm_code;

public class QueueWithSllNode 
{
	private SllNode head, tail;
	
	public QueueWithSllNode()
	{
		head = tail = null;
	}
	
	public void enqueue(String data)
	{
		SllNode tmp = new SllNode(data);
		
		if(head != tail)
		{
			tail.setNext(tmp);
			tail = tmp;
		}
		else
		{
			if(head == null)
			{
				head = tail = tmp;
			}
			else
			{
				tail.setNext(tmp);
				tail = tmp;
			}
		}

		System.out.println("adding new node Qwssln is = "+tail.getData());
	}
	
	public String dequeue()
	{
		String rv = null;
		
		if(head != tail)
		{
			rv = head.getData();
			SllNode tmp = head;
			head = head.getNext();
			tmp.setNext(null);
			tmp = null;
		}
		else//head and tail are equal, test if head not null
		{
			if(head != null)
			{			
				rv = head.getData();
				head = tail = null;
			}
		}
		
		return rv;
	}
}