package com.algm_code;

public class Dll 
{
	private DllNode head;
	private DllNode tail;
	
	public Dll()
	{
		head = tail = null;
	}
	
	public void add(String dataElement, boolean useHead)
	{
		DllNode tmp = new DllNode(dataElement);
		
		if(head == null)
		{
			head = tail = tmp;
		}
		else if(useHead)
		{
			tmp.setNext(head);
			head.setPrev(tmp);
			head = tmp;
		}
		else
		{
			tmp.setPrev(tail);
			tail.setNext(tmp);
			tail = tmp;
		}
	}
	
	public boolean find(String dataElement, boolean useHead)
	{
		DllNode tmp = null;
		boolean rv = false;
		
		if(useHead)
		   tmp = head;
		else
		   tmp = tail;
		
		while(tmp != null)
		{
			if(tmp.getData().equals(dataElement))
			{
				rv = true;
				break;
			}
			else if(useHead)
			{
				tmp = tmp.getNext();
			}
			else
			{
				tmp = tmp.getPrev();
			}
		}
		
		return rv;
	}
	
	public boolean delete(String dataElement, boolean useHead)
	{
		boolean rv = false;
		
		DllNode tmp = null;
		
		if(head != null && head.getData().equals(dataElement))
		{
			DllNode oldhead = head;
			head = head.getNext();
			head.setPrev(null);
			oldhead.setNext(null);
			oldhead.setPrev(null);
			oldhead = null;
			rv = true;
		}
		else if(tail != null && tail.getData().equals(dataElement))
		{
			DllNode oldtail = tail;
			tail = tail.getPrev();
			tail.setNext(null);
			oldtail.setNext(null);
			oldtail.setPrev(null);
			oldtail = null;
			rv = true;
		}
		else
		{		
			if(useHead)
				tmp = head.getNext();
			else
				tmp = tail.getPrev();
			
			while(tmp != null)
			{
				if(tmp.getData().equals(dataElement))
				{
					rv = true;
					DllNode prev_node = tmp.getPrev();
					DllNode next_node = tmp.getNext();
					prev_node.setNext(next_node);
					next_node.setPrev(prev_node);
					tmp.setNext(null);
					tmp.setPrev(null);
					tmp = null;
					break;
				}
				else if(useHead)
					tmp = tmp.getNext();
				else
					tmp = tmp.getPrev();				
			}
		}
		
		return rv;		
	}
	
	public void insertAfter(String dataElement, String newDataElement, boolean useHead)
	{
		DllNode tmp = null;
		
		if(tail != null && tail.getData().equals(dataElement))
		{
			DllNode new_node = new DllNode(newDataElement);
			new_node.setPrev(tail);
			tail.setNext(new_node);
			tail = new_node;			
		}
		else
		{		
			if(useHead)
				tmp = head;
			else
				tmp = tail.getPrev();
			
			while(tmp != null)
			{
				if(tmp.getData().equals(dataElement))
				{
					DllNode new_node = new DllNode(newDataElement);
					DllNode next_node = tmp.getNext();
					new_node.setNext(next_node);
					new_node.setPrev(tmp);
					tmp.setNext(new_node);
					next_node.setPrev(new_node);
					break;
					
				}
				else if(useHead)
					tmp = tmp.getNext();
				else
					tmp = tmp.getPrev();
			}
		}
		
	}
	
	public void insertBefore(String dataElement, String newDataElement, boolean useHead)
	{
		DllNode tmp = null;
		
		if(head != null && head.getData().equals(dataElement))
		{
			DllNode new_node = new DllNode(newDataElement);
			new_node.setNext(head);
			head.setPrev(new_node);
			head = new_node;
		}
		else
		{
			if(useHead)
				tmp = head.getNext();
			else
				tmp = tail;
			
			while(tmp != null)
			{
				if(tmp.getData().equals(dataElement))
				{
					DllNode new_node = new DllNode(newDataElement);
					DllNode prev_node = tmp.getPrev();
					new_node.setNext(tmp);
					new_node.setPrev(prev_node);
					prev_node.setNext(new_node);
					tmp.setPrev(new_node);
					break;
					
				}
				else if(useHead)
					tmp = tmp.getNext();
				else
					tmp = tmp.getPrev();
			}
		}
	}
	
	public void deleteMultipleElements(String dataElement)
	{
		DllNode curr = head;
		
		while(curr != null)
		{
			if(curr.getData().equals(dataElement))
			{
				if(curr == head)
				{
					DllNode tmp = curr.getNext();
					head = tmp;
					curr.setNext(null);
					curr.setPrev(null);
					head.setPrev(null);
					curr = head;
				}
				else if(curr == tail)
				{
					DllNode tmp = tail.getPrev();
					tail = tmp;
					curr.setNext(null);
					curr.setPrev(null);
					tail.setNext(null);
					curr = tail.getNext();					
				}
				else
				{
					DllNode prev_node = curr.getPrev();
					DllNode next_node = curr.getNext();
					curr.setNext(null);
					curr.setPrev(null);
					prev_node.setNext(next_node);
					next_node.setPrev(prev_node);
					curr = next_node;
				}			
			}
			else
				curr = curr.getNext();
		}
	}
	
	public void printList()
	{
		DllNode tmp = head;
		
		while(tmp != null)
		{
			System.out.println("DLinkedList value = "+tmp.getData());
			tmp = tmp.getNext();
		}
		System.out.println();
	}
}

