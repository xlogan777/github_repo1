package com.algm_code;

import java.util.*;

public class SllAlgms 
{
	public SllAlgms()
	{
		
	}
	
	public void printList(SllNode head)
	{
		SllNode tmp = head;
		
		while(tmp != null)
		{
			System.out.println("List value = "+tmp.getData());
			tmp = tmp.getNext();
		}
		System.out.println();
	}
	
	/*
	 * O(n) runtime, space is O(n)
	 */
	//2.1
	public void deleteDuplicatesFromSllWithStorage2(Sll inputSll)
	{
		System.out.println("remove dups with hashmap as lookup table");
		inputSll.printList();
		//use hashmap to save knowledge of seen stuff
		//key is data at node, boolean to keep track if seen or not already
		HashMap<String,Boolean> hm = new HashMap<String,Boolean>();
		
		SllNode prev = null;
		SllNode curr = inputSll.getHeadNode();
		
		while(curr != null)
		{
			Boolean tmp = hm.get(curr.getData());
			//already exits
			if(tmp != null && tmp.booleanValue())
			{
				//found data, remove entry
				prev.setNext(curr.getNext());
				curr.setNext(null);
				curr = prev.getNext();
			}
			else
			{
				//udpate boolean value to true here.
				hm.put(curr.getData(), new Boolean(true));
				prev = curr;
				curr = curr.getNext();
			}
		}
		
		inputSll.printList();
	}
	
	/*
	 * this will remove all the duplicates from a SLL
	 * using tmp storage.
	 * O(n^2) runtime, O(n) space needed for storage of non duplicate data SLL
	 */
	//2.1
	public void deleteDuplicatesFromSllWithStorage(Sll inputSll)
	{
		inputSll.printList();
		
		SllNode curr = inputSll.getHeadNode();
		
		//new sll without any duplicate data
		
		Sll new_sll = new Sll();
		
		while(curr != null)
		{
			String data = curr.getData();
			
			//does a search in the new list to check if the data element exists			
			boolean found_in_new_list = new_sll.find(data);
			
			//if it doesnt exist then it get added to the new list, otherwise just go to next node in main
			//list
			if(!found_in_new_list)
			{
				new_sll.addToTail(data);
			}
			
			curr = curr.getNext();
		}
		
		new_sll.printList();
	}

	/*
	 * this will remove all the duplicates from a SLL w/o a tmp storage
	 * O(n^2) runtime
	 */
	//2.1
	public void deleteDuplicatesFromSll(Sll inputSll)
	{
		//before the removal off dups 
		inputSll.printList();		
		
		SllNode curr = inputSll.getHeadNode();
		
		while(curr != null)
		{
			String curr_data = curr.getData();
			
			SllNode next_curr = curr.getNext();
			SllNode prev = curr;
			
			while(next_curr != null)
			{
				//next node that matches to curr data
				if(curr_data.equals(next_curr.getData()))
				{
					prev.setNext(next_curr.getNext());
					next_curr.setNext(null);
					next_curr = prev.getNext();
				}
				else
				{
					prev = next_curr;
					next_curr = next_curr.getNext();
				}
			}
			
			//get next current node
			curr = curr.getNext();
		}
		
		//after the removal of dups.
		inputSll.printList();
	}
	
	/*
	 * O(n) runtime.
	 */
	//2.2
	public void findNthToLastElement(SllNode head, int len)
	{
		SllNode curr = head;
		int index = 0;
		
		while(curr != null)
		{
			index++;
			
			if(index == len-1)
			{				
				System.out.println("this is the nth to last element = "+ curr.getData());
				break;
			}
			else if(len-1==0)
			{
				System.out.println("only 1 element in the list..nothing before it.");
				break;
			}
			
			curr = curr.getNext();
		}
	}
	
	/*
	 * O(1) runtime.
	 */
	//2.3
	public void removeNodeFromMiddle(SllNode node)
	{
		if(node.getNext() != null)
		{
			String data_del = node.getData();
			//get next node
			SllNode next = node.getNext();
			//copy next node in node data..having 2 copies of data from next node.
			node.setData(next.getData());
			
			//fix the links
			node.setNext(next.getNext());
			next.setNext(null);
			//set next node to null
			next = null;
			System.out.println("removing this data element from middle of list = "+data_del);
		}
		else
			System.out.println("the node passed in doesnt have a valid next node, its next node is null");
	}
	
	/*
	 * O(n) runtime, even though it is 2 list, you are going over them at the same time 
	 */
	//2.4
	public LinkedList add2Sll(Sll list1, Sll list2)
	{
		LinkedList rv = new LinkedList();		
		
		SllNode tmp = null;
		
		SllNode c1 = list1.getHeadNode();
		list1.printList();
		
		SllNode c2 = list2.getHeadNode();
		list2.printList();
		
		//correctly make tmp either the head of list 1 or list 2
		if(c1 != null)
			tmp = c1;
		else if(c2 != null)
			tmp = c2;
		
		//used to keep track of the carry over.
		int val2 = 0;
		
		while(tmp != null)
		{			
			//both nodes contain data to add
			if(c1 != null && c2 != null)
			{
				int sum_val1_val2 = Integer.parseInt(c1.getData()) + Integer.parseInt(c2.getData()) + val2;
				val2 = 0;
				
				if(sum_val1_val2 < 10)
				{
					//add to list, the sum
					rv.add(sum_val1_val2);
				}
				else //if(sum_val1_val2  10)
				{
					//add to list the mod of the sum
					int val1 = sum_val1_val2 % 10;
					rv.add(val1);
					
					//save the carry over from addition to next sum
					val2 = sum_val1_val2 / 10;
				}
				
				//increment c1 and c2
				c1 = c1.getNext();
				c2 = c2.getNext();
				
				//assign tmp to c1 for control of while loop
				if(c1 != null)
					tmp = c1;
				else if(c2 != null)
					tmp = c2;
				else
				{
					//only add it to the return list if the carry over is > 0
					if(val2 > 0)
						rv.add(val2);
					
					tmp = null;//reached end of both loops at the same time
				}
			}
			//check if left side is not null
			else if(c1 != null && c2 == null)
			{
				int sum = Integer.parseInt(c1.getData())+val2;
				val2 = 0;
				
				//deal with carry over and add a portion to list now
				if(sum < 10)
					rv.add(sum);
				else
				{
					rv.add(sum % 10);
					val2 = sum / 10;
				}

				c1 = c1.getNext();
				tmp = c1;
				
				//add to list the carry over if we are at the end of this list
				if(tmp == null && val2 > 0)
					rv.add(val2);
					
			}
			//check of right side is not null
			else if(c1 == null && c2 != null)
			{
				int sum = Integer.parseInt(c2.getData()) + val2;
				val2 = 0;
				
				//deal with carry over and add a portion to list now
				if(sum < 10)
					rv.add(sum);
				else
				{
					rv.add(sum % 10);
					val2 = sum / 10;
				}

				c2 = c2.getNext();
				tmp = c2;
				
				//add to list the carry over if we are at the end of this list
				if(tmp == null && val2>0)
					rv.add(val2);
			}
		}
		
		return rv;
	}
	
	/*
	 * this algorithm helps to determine if the Sll is circular, but doesnt properly
	 * return the start of the loop for a circular Sll.
	 * O(n) runtime.
	 */
	//2.5
	public SllNode findBeginingOfCircularList(SllNode head)
	{
		SllNode rv = null;
		
		if(head != null)
		{
			//have p1 point to the first element in list
			SllNode p1 = head;
			
			//this is a single node list that is good, since its next points to null
			//no circular list
			//if(p1.getNext() == null)
				//return rv;
			if(p1.getNext() != null)
			{
				//have p2 point 2 elements away from p1
				SllNode p2 = p1.getNext().getNext();				
				
				while(true)
				{
					//this will check to see if p2 is null, on a 2 element list
					//or check if its 3 or more elements in the list, then we have a good list
					//by checking p2 being null or the next node of p2 being null
					if(p2 == null || p2.getNext() == null)
						//return rv;
						break;
					
					//update p1 and p2
					else if(p1 != p2)
					{
						p1 = p1.getNext();
						p2 = p2.getNext().getNext();
					}
					else
					{
						rv = p1;
						//return rv;
						break;
					}
				}
			}
		}
		
		if(rv != null)
			System.out.println("CIRCULAR LIST..this is the value of the node that begins the circular list = "+rv.getData());
		else
			System.out.println("NOT CIRCULAR LIST..this was not a circular list..normal or empty Sll.");
		
		return rv;
	}
	
	/*
	 * O(n) runtime.
	 */
	//2.5
	public SllNode findBeginingOfCircularListFixed(SllNode head)
	{
		SllNode rv = null;
		
		if(head != null)
		{
			SllNode p1 = head;
			SllNode p2 = p1;
			
			//find the meeting point of p1,p2 in the circular list
			//if this list is circular
			while(p2 != null && p2.getNext() != null)
			{
				p1 = p1.getNext();
				p2 = p2.getNext().getNext();
				
				//this is the meeting point in the circular list
				if(p1 == p2)
					break;
			}
			
			//check to see if, p2.next is not null, to now find the starting point of the loop.
			if(p2!= null && p2.getNext() != null)
			{
				//set p1 back to head of list for the purpose of
				//meeting up with p2 at the same traversal update rate.
				p1 = head;
				
				//once they meet, that is the start of the loop in the circular list
				while(p1 != p2)
				{
					p1 = p1.getNext();
					p2 = p2.getNext();
				}
				
				//start of loop is p1
				rv = p1;
			}
		}
		
		if(rv != null)
			System.out.println("CIRCULAR LIST..this is the value of the node that begins the circular list = "+rv.getData());
		else
			System.out.println("NOT CIRCULAR LIST..this was not a circular list..normal or empty Sll.");
		
		return rv;
	}	
}