package com.algm_code;

public class DllNode 
{
	private DllNode prevNode;
	private DllNode nextNode;
	private String dataElement;
	
	public DllNode(String dataElement)
	{
		this.dataElement = dataElement;
		this.nextNode = null;
		this.prevNode = null;
	}
	
	public void setPrev(DllNode node)
	{
		this.prevNode = node;
	}
	
	public DllNode getPrev()
	{
		return this.prevNode;
	}
	
	public void setNext(DllNode node)
	{
		this.nextNode = node;
	}
	
	public DllNode getNext()
	{
		return this.nextNode;
	}
	
	public String getData()
	{
		return this.dataElement;
	}
	
	public void setData(String data)
	{
		this.dataElement = data;
	}
}
