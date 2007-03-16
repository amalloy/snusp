package org.malloys.akm.snusp;

public enum SnuspInstruction
{
	NOOP, LEFT, RIGHT, ADD, SUB, LURD, DLUR, SKIP, SKIP_ZERO, CALL, RETURN, BEGIN, READ, WRITE;

	public static SnuspInstruction get(char c)
	{
		switch (c) {
		case '<':
			return LEFT;
		case '>':
			return RIGHT;
		case '+':
			return ADD;
		case '-':
			return SUB;
		case '\\':
			return LURD;
		case '/':
			return DLUR;
		case '!':
			return SKIP;
		case '?':
			return SKIP_ZERO;
		case '@':
			return CALL;
		case '#':
			return RETURN;
		case '$':
			return BEGIN;
		case ',':
			return READ;
		case '.':
			return WRITE;
		default:
			return NOOP;
		}
	}
}
