package main;
import core.UserInterface;

/**
 * An application for "To Do List"  
 * In this version I'm taking information already fed in the main class for proceeding
 * @author Tamer
 * @version 1.0.0
 *
 */

public class ToDoly {
	/**
	 * Main method basically has create an instance of the UserInterface class and call the main Display method.
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		
		
		
		UserInterface userInterface=new UserInterface();
		
		userInterface.Display();
		
	}

}
