package com.algm_code;

public class SllNode 
{
	private String dataElement;
	private SllNode nextNode;
	
	public SllNode(String dataElement)
	{
		this.dataElement = dataElement;
		this.nextNode = null;
	}
	
	public void setNext(SllNode node)
	{
		this.nextNode = node;
	}
	
	public SllNode getNext()
	{
		return this.nextNode;
	}
	
	public String getData()
	{
		return dataElement;
	}
	
	public void setData(String data)
	{
		this.dataElement = data;
	}
}
