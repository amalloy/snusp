package org.malloys.akm.snusp;

import org.malloys.akm.util.Pair;

public class SnuspInstructionPointer implements Cloneable
{
	public SnuspInstructionPointer(Direction direction,
			Pair<Integer, Integer> position)
	{
		this.direction = direction;
		this.position = position;
	}
	
	public void doLurd()
	{
		direction =
			direction == Direction.LEFT ? Direction.UP :
			direction == Direction.UP ? Direction.LEFT :
			direction == Direction.DOWN ? Direction.RIGHT :
			Direction.DOWN;
	}
	
	public void doDlur()
	{
		direction =
			direction == Direction.LEFT ? Direction.DOWN :
			direction == Direction.DOWN ? Direction.LEFT :
			direction == Direction.UP ? Direction.RIGHT :
			Direction.UP;
	}
	
	public SnuspInstructionPointer clone()
	{
		try {
			SnuspInstructionPointer result = (SnuspInstructionPointer)super.clone();
			result.position = position.clone();
			return result;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

	public Direction getDirection()
	{
		return direction;
	}

	public Pair<Integer, Integer> getPosition()
	{
		return position;
	}

	public void setDirection(Direction direction)
	{
		this.direction = direction;
	}

	public void setPosition(Pair<Integer, Integer> position)
	{
		this.position = position;
	}
	
	/**
	 * Move the instruction pointer one unit in the current direction, regardless
	 * of the content of the current instruction. Should only be called after the
	 * instruction has been handled.
	 */
	public void advance()
	{
		if (direction == Direction.UP)
			position.y--;
		else if (direction == Direction.LEFT)
			position.x--;
		else if (direction == Direction.RIGHT)
			position.x++;
		else if (direction == Direction.DOWN)
			position.y++;
	}
	
	Direction direction;

	Pair<Integer, Integer> position;
}
