package com.algm_code;

import java.util.*;

public class StackQueueAlgms 
{
//3.1
	//max len of the stacks
	private int stack_max_len;
	
	//make array to hold 3 stacks evenly
	private String [] array_stack;
	
	//array to be used as the num elements per stack
	private int [] stack_count;
	
	//array to track the stack ptr to add or delete data.
	private int [] stack_ptrs;
	
	//must be evenly divisible by 3
	public boolean initializeMultiStacks(int len_of_array_stacks, int number_of_stacks)
	{
		boolean rv = false;
		
		if( (len_of_array_stacks % number_of_stacks) == 0)
		{
			array_stack = new String [len_of_array_stacks];//buffer size
			stack_count = new int [number_of_stacks];//number of ptrs for each stack
			stack_ptrs = new int [number_of_stacks];
			
			//this will be the max count for each stack
			stack_max_len = array_stack.length / stack_count.length;
			
			//initialize the stack ptrs to the correct starting pt
			stack_ptrs[0] = -1;
			for(int i = 1; i < stack_ptrs.length; i++)
			{
				stack_ptrs[i] = stack_ptrs[i-1] + stack_max_len;
			}
			
			rv = true;
		}
		
		return rv;
	}
	
	public StackQueueAlgms(){}
	
	/*
	 * O(1) runtime for adding data to a multi stack, based on an stack id
	 */
//3.1
	public void pushToMultiStacks(String data, int stack_id)
	{
		//check to see if the stack count for that stack id has reached full.
		if(stack_count[stack_id-1] == stack_max_len)
		{
			System.out.println("Stack id = "+ stack_id + " is full");
		}
		else
		{
			//increase stack count
			stack_count[stack_id-1]++;
			
			//increase stack index before saving the data
			stack_ptrs[stack_id-1]++;
			
			//get index
			int index = stack_ptrs[stack_id-1];
			
			//save data in array for that index
			array_stack[index] = data;
		}
	}
	
	/*
	 * O(1) runtime for removing data to a multi stack for that stack id
	 */
//3.1
	public String popToMultiStacks(int stack_id)
	{
		String rv = null;
		
		if(stack_count[stack_id-1] == 0)
		{
			System.out.println("Stack id = "+ stack_id + " is empty");
		}
		else
		{
			//decrease count for that stack id
			stack_count[stack_id-1]--;

			//get stack ptr index
			int index = stack_ptrs[stack_id-1];

			//save the data to return back to user
			rv = array_stack[index];
			
			//decrease the stack ptr for the stack id			
			stack_ptrs[stack_id-1]--;
		}
		
		return rv;
	}

	
//3.2
	/*
	 * O(1) runtime, space is O(2n), which is O(n)
	 */
	private int dataStack [];
	private int minDataIndexStack[];
	private int topData,topMinData;
	int count_for_data;
	
	public void initMinDataStack(int size_of_Stack)
	{
		dataStack = new int[size_of_Stack];
		minDataIndexStack = new int [size_of_Stack];
		topData = topMinData = -1;
		count_for_data = 0;
	}
	
	/*
	 * O(1) runtime
	 */
	public void pushDataMinStack(int data)
	{
		//check to make sure we dont exceed the array limit.
		if(count_for_data != dataStack.length)
		{
			//save data to data stack and increase topData ptr.
			dataStack[++topData] = data;
			
			count_for_data++;
			
			//if the first time, then make the first location of data stack in data location
			if(count_for_data == 1)
			{
				//increase top val
				topMinData++;
				
				//save index of smallest value to min data stack location 0
				minDataIndexStack[topMinData] = topMinData;
			}
			//determine if new data is smaller than current
			else 
			{
				//get smallest index from min index stack
				int smallest_index = minDataIndexStack[topMinData];
				
				//check to see if new data is smaller than 
				//location at smallest index..if so then add to min index stack
				if(data <= dataStack[smallest_index])
				{
					topMinData++;
					
					//save min index on min data stack
					minDataIndexStack[topMinData] = topData;
				}
			}
		}
		else
			System.out.println("min stack is full");
	}
	
	/*
	 * O(1) runtime
	 */
	public int popDataMinStack()
	{
		int rv = -1;
		
		//prevent to go outside the array
		if(count_for_data != 0)
		{
			//remove data from stack
			rv = dataStack[topData--];
			//decrease amt of elements here
			count_for_data--;
			
			//get min index from min index stack
			int index = minDataIndexStack[topMinData];
			
			//test to see if data removed from data stack matches 
			//what the min_index in the data stack is..if so then
			if(rv == dataStack[index])
			{
				//remove the index from stack, and store bogus data
				minDataIndexStack[topMinData--] = -999;
			}
		}
		else
			System.out.println("min stack is emtpy");
		
		return rv;
	}
	
	/*
	 * O(1) runtime
	 */
	public int minDataOfStack()
	{
		int rv = -999;
		
		if(topMinData > -1)
		{
			int index = minDataIndexStack[topMinData];
			rv = dataStack[index];
			System.out.println("min data at dataArray = "+rv);
		}
		else
			System.out.println("nothing in the min data stack as min value");
		
		return rv;
	}	
	
//3.3

	//data to hold limits and range of sub stack
	private int threshold_of_stack;
	
	//this will hold the stack objs for each sub stack in the array list
	private ArrayList<Stack<String>> arrayListStackContainer;
	
	//pointer showing which stack is being used.
	private int subStackPtr = 0;
	
	public void initSetOfStacks(int stack_container_limit, int threshold_of_substack)
	{
		//set internal data
		this.threshold_of_stack = threshold_of_substack;
		
		//create container with limit and create substacks within this container
		arrayListStackContainer = new ArrayList<Stack<String>>(stack_container_limit);
		
		for(int i = 0; i <stack_container_limit; i++ )
			arrayListStackContainer.add(new Stack<String>());		
	}
	
	/*
	 * O(1) to push data to sub stacks
	 */
	public void setOfStackPush(String data)
	{
		Stack <String> tmp = arrayListStackContainer.get(subStackPtr);
					
		//check if stack is not full, if so add data to this stack
		if(tmp.size() < threshold_of_stack)
		{
			tmp.push(data);
			System.out.println("pushed this data onto sub stack id = "+ (subStackPtr)+" data = "+data);
		}
		//stack is full, not check if index is at the end of the list
		else
		{
			//check to see if you are at the end of the container
			if(subStackPtr == arrayListStackContainer.size()-1)
			{
				System.out.println("completely full stack");
			}
			else
			{
				subStackPtr++;
				tmp = arrayListStackContainer.get(subStackPtr);
				tmp.push(data);
				System.out.println("pushed this data onto sub stack id = "+ (subStackPtr)+" data = "+data);
			}
		}
	}
	
	/*
	 * O(1) to pop data from sub stacks except if removed data using popAt
	 * then it becomes O(n) to fix the curr stack ptr for the sub stacks
	 */
	public String setOfStackPop()
	{
		String data = null;
	
		Stack <String> tmp = arrayListStackContainer.get(subStackPtr);
		
		//if that current stack is not empty then remove data and return
		if(!tmp.empty())
		{
			data = tmp.pop();
		}
		else
		{
			if(subStackPtr == 0)
			{
				System.out.println("completely emtpy stack");
			}
			else
			{
				//decrease the stack ptr until we find a stack that has data in it.
				while(tmp.empty())
				{
					//check to see if u reach zero, if so, then we have emtpy stack and at zero location
					if(subStackPtr == 0)
					{
						System.out.println("completely emtpy stack, and at zero");
						break;
					}
					
					subStackPtr--;
					tmp = arrayListStackContainer.get(subStackPtr);
				}
				
				//check if we left loop because stack was not empty
				if(!tmp.empty())
					data = tmp.pop();
			}
		}
				
		return data;
	}
	
	/*
	 * O(1) to remove data from sub stack using popAt., this will 
	 * need to account for a rollover fix for all the sub stacks
	 * implement later.
	 */
	public String setOfStackPopAt(int index)
	{
		String data = null;
		
		int internal_index = index-1;
		
		//check to make sure within boundary of array list index.
		if(internal_index >= 0 && internal_index < arrayListStackContainer.size())
		{
			if(!arrayListStackContainer.get(internal_index).empty())
			{
				data = arrayListStackContainer.get(internal_index).pop();
				this.rollOverSubStackData(internal_index);
			}
			else
				System.out.println("no data for stack id = "+index);
		}
		else
		{
			System.out.println("not within the array list range");
		}
		
		return data;
	}
	
	//this will actually fix the sub stacks to have data
	//be removed from the next sub stack to this stack.
	// popAt(1), need to get bottom of stack data from sub stack 2
	//and put it to stack 1, and stack 3 bottom, needs to put data
	//onto stack 2 and so on. needs to fill gaps of data onto all substacks
	public void rollOverSubStackData(int index)
	{
		
	}
	
/////3.5
	//implement my queue class with 2 stacks.
	
	//stacks to hold data for the queue implementation
	private Stack<String> dataStack1;
	private Stack<String> dataStack2;
	
	//initialize the queue data structure
	public void init2StackQueue()
	{
		dataStack1 = new Stack<String>();
		dataStack2 = new Stack<String>();
	}
	
	//push data onto stack 1
	/*
	 * O(1)
	 */
	public void enqueue2Stacks(String data)
	{
		//enqueue data onto stack
		dataStack1.push(data);
		System.out.println("enqueued this data = "+data);
	}
	
	//remove data from stack 2 first, if there is no data
	//on stack 2, then remove data from stack 1, into stack 2
	//and pop data from stack 2;
	/*
	 * O(1) if data is in stack 2, worst case is O(n) to push to stack 2 from
	 * stack 1.
	 */
	public String dequeue2Stacks()
	{
		String rv = null;
		
		//if there is data in stack 2, then just return data
		if(!dataStack2.isEmpty())
			rv = dataStack2.pop();
		else
		{
			//emtpy stack 1 into stack 2
			while(!dataStack1.isEmpty())
			{
				String tmp = dataStack1.pop();
				dataStack2.push(tmp);
			}
			
			//check to see if there was data in stack 2, if so remove it.
			if(!dataStack2.isEmpty())
				rv = dataStack2.pop();
		}
		
		return rv;
	}
	
//3.6

	/*
	 * O(n^2) runtime with space O(n) space as tmp storage 
	 * this functions takes in a stack with int data types
	 */
	public void sortStackAscending(Stack inputStack) 
	{
		//create second stack for storage
		Stack new_stack = new Stack();
		
		//loop until input stack is emtpy
		while(!inputStack.isEmpty()) 
		{
			//remove entry from input stack
			int tmp = (int)inputStack.pop();
			
			//loop until the second stack is not empty
			//and the second stack top value is > the tmp
			//this will help with sorting in acending order.
			while(!new_stack.isEmpty() && (int)new_stack.peek() > tmp) 
			{				
				//remove the data from the second stack and
				//place it in the input stack again
				int data2 = (int)new_stack.pop();
				inputStack.push(data2);
			}
			//save tmp to second stack since it is greater than all 
			//in first stack
			new_stack.push(tmp);
		}
		
		//copy data back to first stack
		while(!new_stack.isEmpty())
			inputStack.push(new_stack.pop());
	}
	
	/*
	 * O(n^2) runtime with space O(2n) ~ O(n) space as tmp storage 
	 * this functions takes in a stack with int data types
	 */
	public void sortStackAscending2(Stack inputStack)
	{
		System.out.println("sort stack ver 2");
		Stack tmp1 = new Stack();
		Stack tmp2 = new Stack();
		
		int max_num = -1;
		
		//copy all data to tmp1 stack
		while(!inputStack.isEmpty())
			tmp1.push(inputStack.pop());
		
		while(!tmp1.isEmpty())
		{
			//loop to find max, and save it
			while(!tmp1.isEmpty())
			{
				int data = (int)tmp1.pop();
				
				if(data > max_num)
					max_num = data;
				
				//save to tmp2
				tmp2.push(data);
			}
			
			//loop to save into input stack the max number
			//how every many times it exists in the tmp2 stack
			//and not have it be included in tmp1
			while(!tmp2.isEmpty())
			{
				int data = (int)tmp2.pop();
				
				//save that data into the input stack
				if(data == max_num)
				{
					inputStack.push(data);
				}
				else//save data into tmp1 stack
				{
					tmp1.push(data);
				}
			}
			
			//reset max num, to be used next time.
			max_num = -1;
		}		
	}	
}

