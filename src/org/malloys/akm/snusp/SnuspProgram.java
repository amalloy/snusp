package org.malloys.akm.snusp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.malloys.akm.util.Pair;

public class SnuspProgram
{
	private List<List<SnuspInstruction>> codeSpace;

	public static final int MAX_DATA_SPACE = 65536;

	private byte[] dataSpace = new byte[MAX_DATA_SPACE];

	private List<SnuspState> threads = new LinkedList<SnuspState>();

	private Pair<Integer, Integer> dimensions;

	public SnuspProgram(Reader code) throws IOException
	{
		parse(code);
		SnuspInstructionPointer ip = new SnuspInstructionPointer(Direction.RIGHT,
				discoverStart());

		threads.add(new SnuspState(ip, 0));
	}

	public SnuspInstruction getInstruction(Pair<Integer, Integer> pos)
	{
		if (pos.y < 0 || pos.y >= dimensions.y || pos.x < 0
				|| pos.x >= dimensions.x) {
			throw new IllegalArgumentException("Out of range");
		}
		List<SnuspInstruction> row = codeSpace.get(pos.y);
		if (pos.x >= row.size()) {
			return SnuspInstruction.NOOP;
		}
		return row.get(pos.x);
	}

	public void tick()
	{
		List<SnuspState> deadThreads = new LinkedList<SnuspState>();
		for (SnuspState state : threads) {
			try {
				performInstruction(state);
			} catch (DeadThreadException e) {
				deadThreads.add(state);
			}
		}
		
		for(SnuspState s: deadThreads) {
			threads.remove(s);
		}
	}

	private void performInstruction(SnuspState thread)
	{
		switch (getInstruction(thread.getInstructionPointer().getPosition())) {
		case NOOP:
		case BEGIN:
			break;
		case LEFT:
			thread.moveDataPointer(true);
			break;
		case RIGHT:
			thread.moveDataPointer(false);
			break;
		case ADD:
			dataSpace[thread.getDataPointer()]++;
			break;
		case SUB:
			dataSpace[thread.getDataPointer()]--;
			break;
		case LURD:
			thread.getInstructionPointer().doLurd();
			break;
		case DLUR:
			thread.getInstructionPointer().doDlur();
			break;
		case SKIP:
			thread.getInstructionPointer().advance();
			break;
		case SKIP_ZERO:
			if (dataSpace[thread.getDataPointer()] == 0) {
				thread.getInstructionPointer().advance();
			}
			break;
		case CALL:
			thread.pushState();
			break;
		case RETURN:
			if (!thread.canPop()) {
				throw new DeadThreadException();
			}
			thread.popState();
			thread.getInstructionPointer().advance();
			break;
		case READ:
			try {
				dataSpace[thread.getDataPointer()] = (byte)System.in.read();
			} catch (IOException e) {
				dataSpace[thread.getDataPointer()] = 0;
			}
			break;
		case WRITE:
			System.out.write(dataSpace[thread.getDataPointer()]);
			System.out.flush();
			break;
		}

		thread.getInstructionPointer().advance();
	}

	public SnuspProgram(InputStream code) throws IOException
	{
		this(new InputStreamReader(code));
	}

	private Pair<Integer, Integer> discoverStart()
	{
		int y = 0;
		for (List<SnuspInstruction> row : codeSpace) {
			int x = 0;
			for (SnuspInstruction cell : row) {
				if (cell == SnuspInstruction.BEGIN) {
					return new Pair<Integer, Integer>(x, y);
				}
				x++;
			}
			y++;
		}

		return new Pair<Integer, Integer>();
	}

	private void parse(Reader text) throws IOException
	{
		BufferedReader in = new BufferedReader(text);
		codeSpace = new ArrayList<List<SnuspInstruction>>();
		String line;
		while ((line = in.readLine()) != null) {
			List<SnuspInstruction> curr = new ArrayList<SnuspInstruction>();
			codeSpace.add(curr);
			for (int i = 0; i < line.length(); i++) {
				curr.add(SnuspInstruction.get(line.charAt(i)));
			}
		}

		dimensions = new Pair<Integer, Integer>();
		dimensions.y = codeSpace.size();
		dimensions.x = 0;
		for (List<SnuspInstruction> row : codeSpace) {
			if (row.size() > dimensions.x) {
				dimensions.x = row.size();
			}
		}
	}

	public void run()
	{
		while (!threads.isEmpty()) {
			tick();
		}
	}
}
