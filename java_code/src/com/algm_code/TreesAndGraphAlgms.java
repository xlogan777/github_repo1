package com.algm_code;
import java.util.ArrayList;


public class TreesAndGraphAlgms 
{	
	public TreesAndGraphAlgms()
	{}
	
	/*
	 * O(n) runtime with O(n) space. this will
	 * traverse a BT with inOrder traversal, and 
	 * save all the nodes in a list. then iterate over
	 * the list and make sure that the bst property is good
	 * by making sure the list is sorted in acending order.
	 */
	public boolean is_BT_A_BST(BstNode bstNode, ArrayList list)
	{
		boolean rv = true;
		
		inorderWithList(bstNode, list);
		
		int size = list.size();
		
		for(int i = 0; i < size-1; i++)
		{
			int a = (int)list.get(i);
			int b = (int)list.get(i+1);
			
			//check to see if left side is greater than right
			if( a > b)
			{
				//this bt is not a bst
				rv = false;
				break;
			}
		}
		
		return rv;
	}
	
	/*
	 * O(n) runtime, O(n) space, since it is doing an inorder traversal
	 * and saving the node being visited to the list
	 */
	private void inorderWithList(BstNode bstNode, ArrayList list)	
	{
		if(bstNode == null)
			return;
		
		inorderWithList(bstNode.getLeftChild(), list);
		list.add(bstNode.getData());
		inorderWithList(bstNode.getRightChild(), list);
	}

}
