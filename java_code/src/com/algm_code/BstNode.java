package com.algm_code;

public class BstNode 
{
	private int dataElement;
	private BstNode leftChild;
	private BstNode rightChild;
	
	public BstNode(int dataElement)
	{
		this.dataElement = dataElement;
		this.leftChild = null;
		this.rightChild = null;
	}
	
	public void setData(int dataElement)
	{
		this.dataElement = dataElement;
	}
	
	public void setLeftChild(BstNode node)
	{
		this.leftChild = node;
	}
	
	public void setRightChild(BstNode node)
	{
		this.rightChild = node;
	}
		
	public BstNode getLeftChild()
	{
		return this.leftChild;
	}
	
	public BstNode getRightChild()
	{
		return this.rightChild;
	}
		
	public int getData()
	{
		return this.dataElement;
	}
}
