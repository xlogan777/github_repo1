package com.algm_code;

import java.util.*;

public class Main 
{
	//testing
	public static void test0()
	{
		MathAlgms ma = new MathAlgms();
		
		ma.factorial(0);
		int x = ma.factorialRecursive(5);
		System.out.println(x);
		
		ma.fibonacci(2);
		x = ma.fibonacciRecursive(7);
		System.out.println(x);
		
		int [] ff = {1,2,3};
		
		boolean rv = ma.linearSearchRecusive(ff, 3, 0);
		rv = ma.linearSearchRecusive(ff, 4, 0);
		rv = ma.linearSearchRecusive(ff, 4, 10);
	}
	
	public static void test1()
	{
		System.out.println("Test1 ");
		String tmp = "Test";
		
		ArrayStack as = new ArrayStack(3);
		
		//testing clone
		try{
		as.clone();
		}
		catch(Exception e){e.printStackTrace();}
		
		ArrayQueue aq = new ArrayQueue(3);
		StackWithSllNode swssln = new StackWithSllNode();
		QueueWithSllNode qwssln = new QueueWithSllNode();
		
		for(int i = 0; i < 3; i++)
		{
			as.push(tmp+ (i+1));
			aq.enqueue(tmp+(i+1));
			swssln.push(tmp+(i+1)+"");
			qwssln.enqueue(tmp+(i+1)+"");
		}
		
		for(int i = 0; i < 3; i++)
		{
			System.out.println("Stack = "+as.pop());
			System.out.println("queue = "+aq.dequeue());
			System.out.println();
			System.out.println("poped this data = "+swssln.pop());
			System.out.println("dequeed this data = "+qwssln.dequeue());
		}
		
		as.pop();
		aq.dequeue();
		String tt = swssln.pop();
		if(tt == null)
			System.out.println("swssln is emtpy");
		
		tt = qwssln.dequeue();
		if(tt == null)
			System.out.println("qwssln is emtpy");
		
		for(int i = 0; i < 3; i++)
		{
			as.push(tmp+ (i+10));
			aq.enqueue(tmp+(i+10));
		}
	}
	
	public static void test2()
	{
		Sll sll = new Sll();
		sll.delete("sadf");
		
		sll.add("abc");
		sll.add("def");
		sll.add("ghi");
		sll.add("jkl");
		sll.printList();
		
		sll.find("asdfasdf");
		sll.find("jkl");
		
		sll.delete("oiuowerfu");
		sll.delete("abc");
		sll.printList();
		sll.delete("ghi");
		sll.delete("jkl");
		sll.printList();
		
		sll.insertAfter("def","jimy");
		sll.printList();
		sll.insertBefore("jimy", "testing");
		sll.printList();
		sll.add("jimy");
		sll.printList();
		sll.addToTail("adding to tail now...");
		sll.printList();
		
		sll.deleteMultipleElements("jimy");
		sll.printList();
		
		sll.add("ahha");
		sll.printList();
		sll.reverseList();
		sll.printList();
		sll.reverseList();
		sll.printList();
	}
	
	public static void test3()
	{
		Dll dll = new Dll();
		
		dll.add("abc", true);
		dll.add("def",true);
		dll.add("ghi",false);
		dll.add("jkl",false);
		dll.printList();
		
		dll.find("asdfasdf",true);
		dll.find("jkl",false);
		
		dll.delete("oiuowerfu",false);
		dll.delete("abc",true);
		dll.printList();
		dll.delete("ghi",true);
		dll.delete("jkl",false);
		dll.printList();
		
		dll.insertAfter("def","jimy",true);
		dll.insertAfter("def","jimies",false);
		dll.printList();
		dll.insertBefore("jimy", "testing",false);
		dll.insertBefore("def", "testing222",true);
		
		dll.printList();
		dll.add("jimy",true);
		dll.printList();
		dll.delete("jimy",true);
		dll.delete("jimy",true);
		dll.printList();
				
		dll.add("jimies", true);
		dll.add("jimies", true);
		dll.insertAfter("testing", "jimies", false);
		dll.printList();
		
		dll.deleteMultipleElements("jimies");
		dll.printList();		
	}
	
	public static void test4()
	{
		Bst bst = new Bst();
		
		bst.add(10);
		bst.add(20);
		bst.add(5);
		bst.add(8);
		bst.add(15);
		bst.add(3);
		bst.add(25);
		bst.add(1);
		bst.add(4);
		bst.add(30);
		
		bst.find(10);		
		bst.find(8);
		bst.find(1);
		bst.find(12);
		bst.find(15);
		
		BstNode root = bst.getRoot();
		
		//get the height of the tree
		int height = bst.heightOfTree(root);
		
		System.out.println("height of tree = "+height);
				
		StringBuilder sb = new StringBuilder();
		
		boolean found = bst.Dfs(root, 8);
		System.out.println("status for finding 8 via DFS is "+ (found ? "found" : "not found") );
		found = bst.Dfs(root, 30);
		System.out.println("status for finding via DFS 30 is "+ (found ? "found" : "not found") );
		found = bst.Dfs(root, 6);
		System.out.println("status for finding 6 via DFS is "+ (found ? "found" : "not found") );
		
		bst.Bfs(sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		System.out.println("Printing of BFS*************");
		
		bst.preOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		bst.postOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		System.out.println("Printing of removal of data*************");
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		
		bst.delete(25);
		bst.delete(30);
		bst.delete(20);
		
		bst.delete(1);		
		root = bst.getRoot();
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
				
		bst.delete(4);
		root = bst.getRoot();
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		
		bst.delete(20);
		root = bst.getRoot();
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		
		bst.delete(10);
		root = bst.getRoot();
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		
		bst.delete(25);
		root = bst.getRoot();
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		
		bst.delete(3);
		root = bst.getRoot();
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		
		bst.delete(5);
		root = bst.getRoot();
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		
		bst.delete(15);
		root = bst.getRoot();
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		
		bst.delete(30);
		root = bst.getRoot();
		bst.inOrder(root, sb);
		System.out.println(sb.toString());
		sb.delete(0, sb.length()-1);
		
		
		bst.delete(8);
		root = bst.getRoot();
		bst.inOrder(root, sb);
		System.out.println("last one = "+sb.toString());
		sb.delete(0, sb.length()-1);
		
		bst.delete(80);
		bst.add(80);
	}
	
	public static void test5()
	{
		ArraysAndStrings aaa = new ArraysAndStrings();
		boolean status = aaa.allUniqueChars2Loops("abc");
		System.out.println("abc is "+(status ? "unique" : "not unique"));
		status = aaa.allUniqueChars2Loops("j456j6");
		System.out.println("j456j6 is "+(status ? "unique" : "not unique"));
		
		status = aaa.allUniqueChars("abcdaj");
		System.out.println("abcdaj is with new function "+(status ? "unique" : "not unique"));
		status = aaa.allUniqueChars("6789");
		System.out.println("6789 is with new function "+(status ? "unique" : "not unique"));
		
		System.out.println("...........");
		
		status = aaa.allUniqueCharsXor("abcdaj");
		System.out.println("abcdaj with xor is with new function "+(status ? "unique" : "not unique"));
		
		status = aaa.allUniqueCharsXor("6789");
		System.out.println("6789 is with new function "+(status ? "unique" : "not unique"));
		
		System.out.println("...........");
		char [] input = {'a','b','c','d'};
		aaa.reverseStringWithSpace(input);
		System.out.println(input);
		aaa.reverseString(input);
		System.out.println(input);
		
		System.out.println("...........");
		
		String tt = "aaa";
		System.out.println(tt);
		aaa.removeAllDuplicates(tt.toCharArray());
		tt = "aabac";
		System.out.println(tt);
		aaa.removeAllDuplicates(tt.toCharArray());
		tt = "";
		aaa.removeAllDuplicates(tt.toCharArray());		
		tt = "zabe";
		System.out.println(tt);
		aaa.removeAllDuplicates(tt.toCharArray());
		tt = "aaabbb";
		System.out.println(tt);
		aaa.removeAllDuplicates(tt.toCharArray());
		
		System.out.println("...........");
		
		String xx1 = "hello";
		String xx2 = "hhllo";
		String xx3 = "lolhe";		
		aaa.isAnagram1(xx1, xx2);
		aaa.isAnagram2(xx1, xx2);
		aaa.isAnagram1(xx1, xx3);
		aaa.isAnagram2(xx1, xx3);
		
		System.out.println("...........");
		
		aaa.replaceSpacesWithInput("ab c ", "%20");
		aaa.replaceSpacesWithInput(" zbcX", "%20");
		aaa.replaceSpacesWithInput("cXj", "%20");
		
		int tmp [][] = {{1,2,3},{4,5,6,},{7,8,9}};
		
		aaa.rotate90ClockWise(tmp);
		
		int tmp2 [][] = {{1,2,3},
						 {4,5,6,}};
		//tmp2[0][1] = 0;
		tmp2[1][2] = 0;
		aaa.setRowsColsZero(tmp2);
		
		String s1 = "waterbottle";
		String s2 = "erbottlewat";
		aaa.isRotation(s1, s2);
		
		s2 = "erbtotlewat";
		aaa.isRotation(s1, s2);
		
		String tmp_str = "this,is,ny";
		aaa.removeSingleInputFromString(tmp_str.toCharArray(), ',');
		
		//both of these are valid email address
		aaa.validateEmailAddress("xlogan777@yahoo.com");		
		aaa.validateEmailAddress("jimmy.mena@gmail.com");
		aaa.validateEmailAddress("a@b.c");
		
		//not valid
		aaa.validateEmailAddress("@xlogan777yahoo.com");
		aaa.validateEmailAddress(".xlogan777@yahoocom");
		aaa.validateEmailAddress("xlogan777yahoo.com@");
		aaa.validateEmailAddress("xlogan777@yahoocom.");
		aaa.validateEmailAddress("xlogan777yahoocom");
		aaa.validateEmailAddress("xlogan@777@yahoo.com");
		aaa.validateEmailAddress("xlogan777@yahoocom");
		aaa.validateEmailAddress("xlogan777@.yahoo.com");
		
		aaa.isPalindrome("abcba");
		aaa.isPalindrome("abccba");
		aaa.isPalindrome("a");
		aaa.isPalindrome("abc");
		aaa.isPalindrome("abcdba");
		aaa.isPalindrome("");
		
		
		boolean value_test = aaa.isPalindromeRecusive("abcba");
		value_test = aaa.isPalindromeRecusive("abccba");
		value_test = aaa.isPalindromeRecusive("a");
		value_test = aaa.isPalindromeRecusive("abc");
		value_test = aaa.isPalindromeRecusive("abcdba");
		value_test = aaa.isPalindromeRecusive("");
		
		System.out.println("?????????????");
		aaa.printNumsInPyramid(22);
		
		System.out.println();
		
		aaa.capitalizeString(":...this is john, a he is here..hello the");
		aaa.capitalizeString("mynameisJimmy");
		aaa.capitalizeString("my-name-is-Jimmy is the.");
	}
	
	public static void test6()
	{
		SllAlgms sll_algms = new SllAlgms();
		Sll test1 = new Sll();
		
		test1.add("b");
		test1.add("b");
		test1.add("a");
		test1.add("c");
		test1.add("a");
		test1.add("d");
		test1.add("b");
		test1.add("d");
		test1.add("a");
		test1.add("a");
		sll_algms.deleteDuplicatesFromSllWithStorage2(test1);
		test1.fixListCount();
		test1.printList();
		
		System.out.println("printing data in reverse via recursion");
		test1.printListDataReverse(test1.getHeadNode());
		System.out.println();
		//sll_algms.deleteDuplicatesFromSllWithStorage(test1);
		System.out.println("showing data above.. dup removal with tmp storage using hashmap");
		
		Sll test2 = new Sll();
		test2.add("b");
		test2.add("b");
		test2.add("a");
		test2.add("c");
		test2.add("a");
		test2.add("d");
		test2.add("b");
		test2.add("d");
		test2.add("a");
		test2.add("a");
		
		sll_algms.deleteDuplicatesFromSll(test2);
		test2.fixListCount();
		sll_algms.findNthToLastElement(test2.getHeadNode(), test2.getSize());
		sll_algms.findNthToLastElement(test2.getHeadNode(), 3);
		sll_algms.findNthToLastElement(test2.getHeadNode(), 2);
		sll_algms.findNthToLastElement(test2.getHeadNode(), 1);
		sll_algms.findNthToLastElement(test2.getHeadNode(), 5);
		
		SllNode list1 = new SllNode("hello");
		list1.setNext(new SllNode("jimbo"));
		list1.getNext().setNext(new SllNode("wassa"));
		
		System.out.println("testing the middle of list algm");
		sll_algms.printList(list1);
		
		sll_algms.removeNodeFromMiddle(list1.getNext());
		sll_algms.printList(list1);
		
		//testing for circular Sll.		
		//1.empty list
		sll_algms.findBeginingOfCircularListFixed(null);
		
		//2. one node in list
		SllNode list2 = new SllNode("A");
		sll_algms.findBeginingOfCircularListFixed(list2);
		
		//3.one node with circular to itself
		list2.setNext(list2);
		sll_algms.findBeginingOfCircularListFixed(list2);
		
		//4. two node list that is not circular
		SllNode tmp1 = new SllNode("B");
		list2.setNext(tmp1);
		sll_algms.findBeginingOfCircularListFixed(list2);
		
		//5. two node in list that is circular
		tmp1.setNext(list2);
		sll_algms.findBeginingOfCircularListFixed(list2);
		
		//6. 3 nodes in list that is not circular
		SllNode tmp2 = new SllNode("C");
		tmp1.setNext(tmp2);
		sll_algms.findBeginingOfCircularListFixed(list2);
		
		//7. 3 nodes in list that is circular
		tmp2.setNext(tmp1);
		sll_algms.findBeginingOfCircularListFixed(list2);
		
		//8. 4 nodes in list that is not circular
		SllNode tmp3 = new SllNode("D");
		tmp2.setNext(tmp3);
		sll_algms.findBeginingOfCircularListFixed(list2);
		
		//9. 4 nodes with circular list
		tmp3.setNext(tmp1);
		sll_algms.findBeginingOfCircularListFixed(list2);
		
		//this is to test the adding of 2 lists containing numbers as the node values
		//and the head of the list holds the ones value, and next to head holds the 10's values...etc
		
		Sll new_list1 = new Sll();
		new_list1.addToTail("9");
		new_list1.addToTail("9");
		new_list1.addToTail("9");
		/*new_list1.addToTail("3");
		new_list1.addToTail("1");
		new_list1.addToTail("5");
		new_list1.addToTail("6");*/
				
		Sll new_list2 = new Sll();
		new_list2.addToTail("9");
		new_list2.addToTail("9");
		new_list2.addToTail("9");
		//new_list2.addToTail("9");
		//new_list2.addToTail("9");
		/*new_list2.addToTail("5");
		new_list2.addToTail("9");
		new_list2.addToTail("2");
		new_list2.addToTail("7");*/
		
		LinkedList ll = sll_algms.add2Sll(new_list1, new_list2);
		if(ll.size() > 0)
		{
			System.out.println("value of sum from the 2 lists = ");
			for(int i = 0; i < ll.size(); i++)
			{
				System.out.print(ll.get(i)+" ");
			}
			System.out.println();
		}		
	}
	
	public static void test7()
	{
		StackQueueAlgms sqalgms = new StackQueueAlgms();
		////////3.1
		boolean status = sqalgms.initializeMultiStacks(9, 3);
		
		if(status)
			System.out.println("successfully initialized single array with multi stack approach");
		else
		{
			System.out.println("failed to init multi stack mod value is not zero, must be evenly divisible");
			return;
		}
		
		String test1 = "test data ";
		int index = 0;
		for(int i = 0; i < 9; i++)
		{
			//push all data onto stacks
			index %= 3;
			sqalgms.pushToMultiStacks( (test1 + (i+1)), index+1);
			index++;
		}
		
		//try to overflow stack
		sqalgms.pushToMultiStacks( (test1 + 99), 2);
		sqalgms.pushToMultiStacks( (test1 + 99), 3);
		sqalgms.pushToMultiStacks( (test1 + 99), 1);
		
		//remove stack data
		index = 0;
		for(int i = 0; i < 9; i++)
		{
			//pop all data onto stacks
			index %= 3;
			String data = sqalgms.popToMultiStacks(index+1);
			index++;
			System.out.println("data removed from stack id = "+index+ " , data = "+data);
		}
		 
		sqalgms.popToMultiStacks(2);
		sqalgms.popToMultiStacks(3);		
		sqalgms.popToMultiStacks(1);
		
		
		
		
		////////////3.2
		int dataval = 50;
		sqalgms.initMinDataStack(5);
		for(int i = 0; i < 5; i++)
		{
			sqalgms.pushDataMinStack( dataval );
			dataval-= 10;
		}
		
		sqalgms.pushDataMinStack(98);
		int min_data = sqalgms.minDataOfStack();
		System.out.println("current min data = "+min_data);
		
		for(int i = 0; i < 5; i++)
		{
			int data = sqalgms.popDataMinStack();
			System.out.println("data contained in dataStack = "+data);
		}
		
		sqalgms.popDataMinStack();
		min_data = sqalgms.minDataOfStack();
		
				
		sqalgms.pushDataMinStack( 10 );
		sqalgms.pushDataMinStack( 5 );
		sqalgms.pushDataMinStack( 9 );
		sqalgms.pushDataMinStack( 5 );
		sqalgms.pushDataMinStack( 8 );
		
		sqalgms.minDataOfStack();
		sqalgms.popDataMinStack();
		sqalgms.popDataMinStack();
		sqalgms.minDataOfStack();
		sqalgms.popDataMinStack();
		sqalgms.popDataMinStack();
		sqalgms.minDataOfStack();
		sqalgms.popDataMinStack();
		
		
		/////////////3.3
		//this is setting the container limit to be 3 sub stacks and
		//setting the stack threshold to be 2
		System.out.println("................");
		System.out.println("set of stacks test");
		sqalgms.initSetOfStacks(3, 2);
		String set_of_stack_data = "set_of_stack id = ";
		
		for(int i = 0; i < 8; i++)
		{			
			sqalgms.setOfStackPush(set_of_stack_data + (i+1));
		}
		
		System.out.println();
		
		for(int i = 0; i < 8; i++)
		{			
			String data = sqalgms.setOfStackPop();
			
			//if(data != null)
				System.out.println(data);
		}
		
		//add data back to stack
		for(int i = 0; i < 8; i++)
		{			
			sqalgms.setOfStackPush(set_of_stack_data + (i+1));
		}
		
		//remove using popAt
		int idx = 0;
		for(int i = 0; i < 8; i++)
		{			
			sqalgms.setOfStackPopAt(idx+1);
			idx = (idx+1) % 3;
		}
		
		//pop will return curr ptr back to begining...
		sqalgms.setOfStackPop();
		sqalgms.setOfStackPop();
		
		
		//////////////// Implemented queue using 2 stacks
		System.out.println();
		System.out.println("!!!!!!!!!!!!!!!!!!");
		sqalgms.init2StackQueue();
		
		for(int i =0; i < 7; i++)
		{
			sqalgms.enqueue2Stacks("queue data "+(i+1));
		}
		System.out.println();
		for(int i =0; i < 8; i++)
		{
			String data22 = sqalgms.dequeue2Stacks();
			System.out.println("data in queue = "+data22);
		}
		
		//?????????????????????
		System.out.println();
		System.out.println("testing for 3.6");
		
		//create new stack to test
		Stack inputStack = new Stack();
		
		inputStack.push(3);
		System.out.println("data in stack = "+3);
		inputStack.push(1);
		System.out.println("data in stack = "+1);
		inputStack.push(2);
		System.out.println("data in stack = "+2);
				
		sqalgms.sortStackAscending(inputStack);
		
		System.out.print("SORTED data in stack = ");
		while(!inputStack.isEmpty())
		{
			int data = (int)inputStack.pop();
			
			System.out.print(data + " ");
		}
		System.out.println();
		
		System.out.println(".......data placed in acending order for testing");
		inputStack.push(1);
		inputStack.push(2);
		inputStack.push(3);
		sqalgms.sortStackAscending(inputStack);
		
		System.out.print("SORTED data in stack = ");
		while(!inputStack.isEmpty())
		{
			int data = (int)inputStack.pop();
			
			System.out.print(data + " ");
		}
		System.out.println();
		
		System.out.println(".......data placed in decending order for testing");
		inputStack.push(3);
		inputStack.push(2);
		inputStack.push(1);
		sqalgms.sortStackAscending(inputStack);		
		System.out.print("SORTED data in stack = ");
		while(!inputStack.isEmpty())
		{
			int data = (int)inputStack.pop();
			
			System.out.print(data + " ");
		}
		System.out.println();
				
		System.out.println(".......data all the same for testing");
		inputStack.push(3);
		inputStack.push(3);
		inputStack.push(3);
		sqalgms.sortStackAscending(inputStack);
		System.out.print("SORTED data in stack = ");
		while(!inputStack.isEmpty())
		{
			int data = (int)inputStack.pop();
			
			System.out.print(data + " ");
		}
		System.out.println();		
	}
	
	public static void test8()
	{
		ArrayList list = new ArrayList();
		Bst bst = new Bst();
		bst.add(10);
		bst.add(20);
		bst.add(50);
		TreesAndGraphAlgms taga = new TreesAndGraphAlgms();
		boolean status = taga.is_BT_A_BST(bst.getRoot(), list);
		if(status)
			System.out.println("first BT is a BST");
		
		BstNode tmp1 = new BstNode(10);
		BstNode tmp2 = new BstNode(5);
		BstNode tmp3 = new BstNode(20);
		tmp1.setLeftChild(tmp3);
		tmp1.setRightChild(tmp2);
		list.clear();
		
		status = taga.is_BT_A_BST(tmp1, list);
		if(!status)
			System.out.println("2nd BT is not a BST");
	}
		
	public static void main(String[] args) 
	{
	   //test0(); //math algms
	   //test1(); //basic stack and queue with arrays and LL implementations
	   test2(); //Sll class with test cases.
	   //test3(); //Dll class with test cases
	   //test4(); //BST class with test cases and algms
	   //test5(); //arrays and strings algm cases
	   //test6(); //linked list algms cases
	   //test7(); //stack and queue algms cases
	   //test8(); //trees and graph algms cases
	}
}
