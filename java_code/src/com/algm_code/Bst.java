package com.algm_code;
import java.util.LinkedList;

public class Bst
{
	private BstNode root;
	
	public Bst()
	{
		root = null;
	}
	
	public BstNode getRoot(){return this.root;}
	
	public boolean add(int dataElement)
	{
		boolean rv = true;
		
		if(root == null)
		{
			BstNode tmp = new BstNode(dataElement);
			root = tmp;
		}
		else
		{
			addNode(root, dataElement);
		}
		
		return rv;
	}
	
	private boolean addNode(BstNode node, int dataElement)
	{
		boolean rv = false;

		if(node != null)
		{
			if(node.getData() == dataElement)
			{
				System.out.println("Data exists in tree...disregard.");
			}
			else if(dataElement < node.getData())
			{
				if(node.getLeftChild() == null)
				{
					BstNode tmp = new BstNode(dataElement);
					node.setLeftChild(tmp);
					rv = true;
				}
				else
					addNode(node.getLeftChild(), dataElement);
			}
			else
			{
				if(node.getRightChild() == null)
				{
					BstNode tmp = new BstNode(dataElement);
					node.setRightChild(tmp);
					rv = true;
				}
				else
					addNode(node.getRightChild(), dataElement);
			}
		}
		
		return rv;
	}
	
	public boolean find(int dataElement)
	{
		boolean rv = false;
		
		rv = findNode(root, dataElement);
		
		return rv;
	}
	
	private boolean findNode(BstNode node, int dataElement)
	{
		boolean rv = false;
		
		if(node == null)
			rv = false;
		else if(node.getData() == dataElement)
		{
			rv = true;
			System.out.println("found the element in the tree");
		}
		else if(dataElement < node.getData())
			rv = findNode(node.getLeftChild(), dataElement);
		else
			rv = findNode(node.getRightChild(), dataElement);
		
		return rv;
	}
	
	public boolean delete(int dataElement)
	{
		boolean rv = false;
		
		if(root != null )
		{
			//fix root node for special case, and make new root node
			if(root.getData() == dataElement)
			{
				rv = true;
				BstNode min_node = findMinNode(root.getRightChild());

				if(min_node != null)
				{
					root.setData(min_node.getData());
				
					//remove the min node from this right subtree
					deleteNode(root.getData(), root, root.getRightChild());
				}
				else
				{
					root = root.getLeftChild();//make new root
				}
			}
			else if(dataElement < root.getData())
			{
				rv = deleteNode(dataElement, root, root.getLeftChild());
			}
			else
			{
				rv = deleteNode(dataElement, root, root.getRightChild());
			}
		}
		
		return rv;		
	}
	
	public BstNode findMinNode(BstNode node)
	{
		BstNode tmp = null;
		
		while(node != null)
		{
			tmp = node;
			node = node.getLeftChild();
		}
		
		return tmp;
	}
	
	public boolean deleteNode(int dataElement, BstNode parent, BstNode node)
	{
		boolean rv = false;
		
		//parent is not empty
		if(node != null)
		{
			if(node.getData() == dataElement)
			{	
				rv = true;
				
				//have a leaf condition
				if(node.getLeftChild() == null && node.getRightChild() == null)
				{
					//fix left child link from parent
					if(parent.getLeftChild() == node)
					{
						parent.setLeftChild(null);
					}
					//fix right child link from parent
					else
					{
						parent.setRightChild(null);
					}
					
					node = null;
				}
				//one sub tree, with right side being null
				else if(node.getLeftChild() != null && node.getRightChild() == null)
				{
					//fix parent link for left node.
					if(parent.getLeftChild() == node)
					{
						parent.setLeftChild(node.getLeftChild());			
					}
					else//fix parent link for right node.
					{
						parent.setRightChild(node.getLeftChild());
					}
					
					node.setLeftChild(null);
					node = null;
				}
				//one sub tree, with left side being null
				else if(node.getLeftChild() == null && node.getRightChild() != null)
				{
					//fix parent link for left node.
					if(parent.getLeftChild() == node)
					{
						parent.setLeftChild(node.getRightChild());			
					}
					else//fix parent link for right node.
					{
						parent.setRightChild(node.getRightChild());
					}
										
					node.setRightChild(null);
					node = null;
				}
				//node has 2 children, special case
				else
				{
					//find min node from right subtree, and update deleting node with this min value item
					BstNode min_node = findMinNode(node.getRightChild());
					
					if(min_node != null)
					{
						node.setData(min_node.getData());
					
						//remove the min node from this right subtree
						rv = deleteNode(node.getData(), node, node.getRightChild());
					}
				}
			}
			else if(dataElement < node.getData())
			{
				rv = deleteNode(dataElement, node, node.getLeftChild());
			}
			else
			{
				rv = deleteNode(dataElement, node, node.getRightChild());
			}
		}
		
		return rv;		
	}
	
	/*left, root, right*/
	public void inOrder(BstNode node, StringBuilder output)
	{
		if(node == null)
			return;
		
		inOrder(node.getLeftChild(), output);
		output.append(node.getData());
		output.append(" ");
		inOrder(node.getRightChild(), output);
	}
	
	/*left, right, root*/
	public void postOrder(BstNode node, StringBuilder output)
	{
		if(node == null)
			return;
		
		postOrder(node.getLeftChild(), output);
		postOrder(node.getRightChild(), output);
		output.append(node.getData());
		output.append(" ");
	}
	
	/*root, left, right*/
	public void preOrder(BstNode node, StringBuilder output)
	{
		if(node == null)
			return;
		
		output.append(node.getData());
		output.append(" ");
		preOrder(node.getLeftChild(), output);
		preOrder(node.getRightChild(), output);
	}
	
	public void Bfs(StringBuilder output)
	{
		LinkedList<BstNode> local_queue = new LinkedList<BstNode>();
		
		//add root to the queue
		local_queue.add(root);
		
		while(!local_queue.isEmpty())
		{
			//remove and save away data
			BstNode tmp = local_queue.remove();
			output.append(tmp.getData()+" ");
			
			if(tmp.getLeftChild() != null)
				local_queue.add(tmp.getLeftChild());
			
			if(tmp.getRightChild() != null)
				local_queue.add(tmp.getRightChild());
		}
	}
	
	public boolean Dfs(BstNode node, int dataElement)
	{
		boolean rv = false;
		
		if(node != null)
		{
			if(node.getData() == dataElement)
			{
				rv = true;
			}
			
			if(rv == false)
				rv = Dfs(node.getLeftChild(), dataElement);
			
			if(rv == false)
				rv = Dfs(node.getRightChild(), dataElement);
		}
		
		return rv;
	}
}
