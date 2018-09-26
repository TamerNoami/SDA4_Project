/**
 * An application for "To Do List"  
 * In this version I'm taking information already fed in the main class for proceesing
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
		
		// Below only for test to put information
		Task tlist1 = new Task();
		tlist1.setter("Work", "Designe the classes",taskManager.StringToDate("20-09-2018"), false);
		taskManager.AddTask("Work", tlist1,taskManager.ToDol);
		
		Task tlist2 = new Task();
		tlist2.setter("Family", "Fix the electircity", taskManager.StringToDate("22-09-2018"), true);
		taskManager.AddTask("Family", tlist2,taskManager.ToDol);
		
		Task tlist3 = new Task();
		tlist3.setter("Work", "Write The code", taskManager.StringToDate("19-09-2017"), false);
		taskManager.AddTask("WOrk", tlist3,taskManager.ToDol);
		
		Task tlist4 = new Task();
		tlist4.setter("Personal", "Go to the doctor", taskManager.StringToDate("27-09-2019"), false);
		taskManager.AddTask("Personal", tlist4,taskManager.ToDol);
		
		
		Task tlist5 = new Task();
		tlist5.setter("Personal", "Gamming night", taskManager.StringToDate("29-09-2018"), false);
		taskManager.AddTask("Personal", tlist5,taskManager.ToDol);
		
		// End of test input data
		
		//**** Below I try to call the methods to check it's  **
		
		//taskManager.showtask(taskManager.ToDol);
		//System.out.println(taskManager.MapToList(taskManager.ToDol));
		System.out.println(taskManager.Sorting(taskManager.MapToList(taskManager.ToDol)));
		RnW.writeToFile(taskManager.ToDol);
		//RnW.readFromFile();
	}

}
