package com.algm_code;

public class Sll 
{
	private SllNode head;
	private int count;
	
	public Sll()
	{
		head = null;
		count = 0;
	}
		
	public SllNode getHeadNode()
	{
		return this.head;
	}
	
	public int getSize()
	{
		return this.count;
	}
	
	public void add(String dataElement)
	{
		SllNode tmp = new SllNode(dataElement);
		
		if(head == null)
		{
			head = tmp;
		}
		else
		{
			tmp.setNext(head);
			head = tmp;
		}
		
		count++;
	}
	
	public boolean find(String dataElement)
	{
		boolean rv = false;
		
		SllNode tmp = head;
		
		while(tmp != null)
		{
			if(tmp.getData().equals(dataElement))
			{
				System.out.println("Found data element in list");
				rv = true;
				break;
			}
			
			tmp = tmp.getNext();
		}
		return rv;
	}
	
	public boolean delete(String dataElement)
	{
		boolean rv = false;
		
		if(head != null && head.getData().equals(dataElement))
		{
			SllNode tmp = head;
			head = head.getNext();
			tmp.setNext(null);
			tmp = null;
			rv = true;
		}
		else
		{
			SllNode prev = head;
			SllNode tmp = head.getNext();
			
			while(tmp != null)
			{
				if(tmp.getData().equals(dataElement))
				{
					prev.setNext(tmp.getNext()); 
					tmp.setNext(null);
					System.out.println("Deleting from SLL"+dataElement);
					tmp = null;
					rv = true;
					break;
				}
				
				prev = tmp;
				tmp = tmp.getNext();
			}
		}
		
		if(rv)
			count--;
		
		return rv;
	}
	
	public void insertAfter(String dataElement, String newDataElement)
	{
		SllNode tmp = head;
		
		while(tmp != null)
		{
			if(tmp.getData().equals(dataElement))
			{
				System.out.println("inserting node after...");
				SllNode tmp2 = new SllNode(newDataElement);
				tmp2.setNext(tmp.getNext());
				tmp.setNext(tmp2);
				count++;
				break;
			}
			
			tmp = tmp.getNext();
		}
	}
	
	public void insertBefore(String dataElement, String newDataElement)
	{
		if(head != null && head.getData().equals(dataElement))
		{
			SllNode tmp = new SllNode(newDataElement);
			tmp.setNext(head);
			head = tmp;
		}
		else
		{
			SllNode prev = head;
			SllNode tmp = head.getNext();
			
			while(tmp != null)
			{
				if(tmp.getData().equals(dataElement))
				{
					SllNode tmp2 = new SllNode(newDataElement);
					tmp2.setNext(tmp);
					prev.setNext(tmp2);
					count++;
					break;
				}
				prev = tmp;
				tmp = tmp.getNext();
			}
		}
	}
	
	public void deleteMultipleElements(String dataElement)
	{
		SllNode prev = null;
		SllNode curr = head;
		
		while(curr != null)
		{
			if(curr.getData().equals(dataElement))
			{
				if(head == curr)
				{
					head = head.getNext();
					curr.setNext(null);
					curr = head;
					count--;
				}
				else
				{
					prev.setNext(curr.getNext());
					curr.setNext(null);
					curr = prev.getNext();
					count--;
				}				
			}
			else
			{
			   prev = curr;
			   curr = curr.getNext();			   
			}
		}
	}
	
	public void reverseList()
	{
		SllNode curr = head;
		head = null;
		
		while(curr != null)
		{
			SllNode tmp = curr;
			curr = curr.getNext();
			tmp.setNext(head);
			head = tmp;			
		}		
	}
	
	public void addToTail(String dataElement)
	{
		SllNode curr = head;
		
		//head was null
		if(curr == null)
			this.add(dataElement);
		else
		{
			while(curr.getNext() != null)
			{
				curr = curr.getNext();
			}
			
			SllNode new_node = new SllNode(dataElement);		
			curr.setNext(new_node);
			count++;
		}
	}
	
	public void fixListCount()
	{
		this.count = 0;
		SllNode curr = head;
		while(curr != null)
		{
			count++;
			curr = curr.getNext();
		}
	}
	
	public void printList()
	{
		SllNode tmp = head;
		
		System.out.print("List value = ");
		while(tmp != null)
		{
			System.out.print(tmp.getData()+" ");
			tmp = tmp.getNext();
		}
		System.out.println();
	}
	
	public void printListDataReverse(SllNode node)
	{
		if(node.getNext() != null)
		{			
			this.printListDataReverse(node.getNext());
		}
		
		System.out.print(node.getData()+" ");		
	}
}
