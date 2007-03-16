package org.malloys.akm.snusp;

import java.util.Stack;


public class SnuspState
{
	public SnuspState(SnuspInstructionPointer ip, int dataPointer)
	{
		this.ip = ip;
		this.dataPointer = dataPointer;
	}

	int getDataPointer()
	{
		return dataPointer;
	}

	SnuspInstructionPointer getInstructionPointer()
	{
		return ip;
	}

	void moveDataPointer(boolean left) throws ArrayIndexOutOfBoundsException
	{
		if (dataPointer == 0 && left)
			throw new ArrayIndexOutOfBoundsException("Read head can't move left");
		dataPointer += left ? -1 : 1;
	}

	void setInstructionPointer(SnuspInstructionPointer p)
	{
		ip = p;
	}
	
	public void pushState()
	{
		stack.push(ip.clone());
	}
	
	public void popState()
	{
		ip = stack.pop();
	}
	
	public boolean canPop()
	{
		return !stack.isEmpty();
	}

	private int dataPointer;

	private SnuspInstructionPointer ip;
	
	private Stack<SnuspInstructionPointer> stack = new Stack<SnuspInstructionPointer>();
}
