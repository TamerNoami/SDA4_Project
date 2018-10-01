
/**
 * This class is going to deal with all user interfaces and display methods.
 * 
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

public class UserInterface {
	TaskManager taskManager= new TaskManager();
	ReadAndWrite RnW ;

	public UserInterface () throws ClassNotFoundException, IOException, ParseException
	{
		RnW = new ReadAndWrite(taskManager);
		RnW.readFromFile();
		System.out.println("*************************************************");
		System.out.println("********* Welcome to ToDoly Application *********");
		System.out.println("*************************************************");
	}

	public void Display() throws ParseException, FileNotFoundException {
		int SC[] = taskManager.StatusCount();
		System.out.println("-------------------------------------------------");
		System.out.println("		 ToDoly Application ");
		System.out.println(">> You have " + SC[0] + " tasks todo and " + SC[1] + " tasks are done!");
		System.out.println(">> Pick an option:");
		System.out.println(">> (1) Show Task List (by date or project)");
		System.out.println(">> (2) Add New Task");
		System.out.println(">> (3) Edit Task (update, mark as done, remove)");
		System.out.println(">> (4) Save and Quit");
		System.out.println("-------------------------------------------------");
		System.out.print(">> ");
		Scanner in = new Scanner(System.in);

		try {

			int c = in.nextInt();
			while (!(c >= 1 && c <= 4)) {
				System.out.println("Wrong option number,");
				System.out.println("Please enter the option number again :");
				c = in.nextInt();
			}

			switch (c) {
			case 1:

				System.out.println(">> (1) To show Tasks by date");
				System.out.println(">> (2) To show Tasks by project");
				System.out.println(">> (0) To return to main page");
				System.out.print(">> ");
				int p = in.nextInt();
				while (!(p >= 0 && p <= 2)) {
					System.out.println("Wrong option number,");
					System.out.println("Please enter the option number again :");
					p = in.nextInt();
				}

				switch (p) {
				case 0:
					Display(); // Return to main display
					break;
				case 1:
					System.out.println("Show Tasks by date ** Display by Date");
					taskManager.ShowByDate(false);
					Display();
					break;
				case 2:
					System.out.println("Show Tasks by project ** Display by project");
					showtask();
					Display();
					break;
				default:
					System.out.println("Invalid input ");
					Display();
				}

				break;
			case 2:
				System.out.println(" **** Add New Task ****");
				taskManager.AddTask();
				Display();
				break;
			case 3:
				//Update();
				System.out.println("Edit Task");
				break;
			case 4: // 
				System.out.println("Save and Quit");
				RnW.writeToFile(taskManager.MapToList());
				//System.exit(0);
				//break;
			default: {
				break;
			}

			}
		} // Try ends here
		catch (InputMismatchException e)

		{
			System.out.println("Invalid Input ... please try again ");
			Display();
		}

		in.close();

	} // End if display method

	public void showtask() throws ParseException
	{
		int taskStatus = 0;
		int TasksCount = 0;

		for (Entry<String, List<Task>> s : taskManager.ToDol.entrySet())
		{
			System.out.println("-----------------------------");
			System.out.println("**Project name** : " + s.getKey());
			System.out.println("-----------------------------");
			for (int i = 0; i < s.getValue().size(); i++)
			{
				
				System.out.println("Title :" + s.getValue().get(i).getTitle());
				System.out.println("Due Date :" + taskManager.DateToString(s.getValue().get(i).getDueDate()));
				System.out.println("Status :" + s.getValue().get(i).getTaskStatus());
				// Task number for update method
				// System.out.println("Task Number :" + s.getValue().get(i).getTaskNumber());
				System.out.println("+++++++++++++++++");
				
			}
		}
		
	}

}
