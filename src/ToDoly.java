/**
 * An application for "To Do List"  
 * In this version I'm taking information already fed in the main class for proceeding
 * @author Tamer
 * @version 1.0.0
 *
 */

public class ToDoly {
	/**
	 * Main method basically has input information and call the methods 
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		
		// Create an instance of the TaskManager class where all tasks apply there
		TaskManager taskManager = new TaskManager();
		
		// Create an instance if the ReadAndWrite class where I can read and write to the file through
		ReadAndWrite RnW = new ReadAndWrite();
		
		UserInterface userInterface=new UserInterface(taskManager);
		
		
		// Below only for test to put information
		Task tlist1 = new Task();
		tlist1.setter("Work", "Desgine the classes",taskManager.StringToDate("50-09-2018"), false);
		taskManager.AddTask2("Work", tlist1);
		
		Task tlist2 = new Task();
		tlist2.setter("Family", "Fix the electircity", taskManager.StringToDate("22-09-2020"), true);
		taskManager.AddTask2("Family", tlist2);
		
		Task tlist3 = new Task();
		tlist3.setter("Work", "Write The code", taskManager.StringToDate("19-09-2017"), false);
		taskManager.AddTask2("WOrk", tlist3);
		
		Task tlist4 = new Task();
		tlist4.setter("Personal", "Go to the doctor", taskManager.StringToDate("27-09-2019"), false);
		taskManager.AddTask2("Personal", tlist4);
		
		
		Task tlist5 = new Task();
		tlist5.setter("Personal", "Gaming night", taskManager.StringToDate("29-09-2018"), false);
		taskManager.AddTask2("Personal", tlist5);
		
		// End of test input data
		
		//**** Below I try to call the methods to check it's  **
		
		
		
		//taskManager.showtask2();
		userInterface.Display();
		//System.out.println(taskManager.MapToList(taskManager.ToDol));
		// Display by date
		//taskManager.ShowByDate(true);
		//System.out.println(taskManager.Sorting());
		//RnW.writeToFile(taskManager.ToDol);
		//RnW.readFromFile();
	}

}
