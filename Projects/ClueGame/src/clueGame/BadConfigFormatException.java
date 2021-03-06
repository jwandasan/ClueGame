package clueGame;

import java.util.*;
import java.io.*;

/**
 * BadConfigFormatException Class
 * 
 * @author Jeric Wandasan
 * @author Ethan Vijayabaskaran
 * 
 * Currently a class stub that will eventually detect bad format within input files.
 * 
 */

public class BadConfigFormatException extends Exception {
	private String outputFile = "logfile.txt";
	
	public BadConfigFormatException() throws FileNotFoundException {
		super("Error: Bad Config Format in file being read.");
		PrintWriter out = new PrintWriter(outputFile);
		out.println("Error: Bad Config Format in file being read.");
		out.close();
	}
	
	public BadConfigFormatException(String fileName) throws FileNotFoundException {
		super("Error: Bad Config Format in [" + fileName + "]");
		PrintWriter out = new PrintWriter(outputFile);
		out.println("Error: Bad Config Format in [" + fileName + "]");
		out.close();
	}
}
