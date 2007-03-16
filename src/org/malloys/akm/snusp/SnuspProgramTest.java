package org.malloys.akm.snusp;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;

public class SnuspProgramTest extends TestCase
{
	String code =
		"$=COPY==!/==?\\====!/>>=?\\<<#\n" +
         "<   -     |    -\n" + 
         "<   >     +    |\n" +
         "\\+>+/     \\=<<=/\n";


	public void testSnuspProgram() throws IOException
	{
		SnuspProgram program = new SnuspProgram(new StringReader(code));
		program.hashCode();
		fail("Not yet implemented");
	}

}
