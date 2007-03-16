package org.malloys.akm.util;

public class Pair<X, Y> implements Cloneable
{
	public Pair(X x, Y y)
	{
		this.x = x; this.y = y;
	}
	
	public Pair()
	{
	}

	public Pair<X, Y> clone()
	{
		return new Pair<X, Y>(x, y);
	}
	public X x;
	public Y y;
}
