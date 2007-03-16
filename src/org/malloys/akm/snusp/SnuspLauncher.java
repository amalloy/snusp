package org.malloys.akm.snusp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SnuspLauncher
{
	/**
	 * @param args
	 * @throws  
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		SnuspProgram program = new SnuspProgram(new FileInputStream(args[0]));
		program.run();
	}

}
