package core;

/**
 * This class is going to deal with all user interfaces and display methods.
 * It deals with instance from TaskManager to use it's methods and fields
 * ToDol is the HashMap that stores all the tasks
 * 
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;

import readWrite.ReadAndWrite;

public class UserInterface {
	TaskManager taskManager = new TaskManager();
	ReadAndWrite RnW;
	SimpleDateFormat dateFormat;
	Date date;
	final static String Date_format = "dd-MM-yyyy";
	final static String Format1 = "| %-10s | %-20s | %-6s | %-70s |%n";
	final static String Format2 = "| %-10s | %-20s | %-6s | %-70s | %-11d |%n";
	final static String Format3 = "| %-10s | %-6s | %-70s |%n";
	final static String Format4 = "| %-4d | %-20s |%n";
	final static String pattern1 = "\\d{2}-\\d{2}-\\d{4}";
	final static String pattern2 = "\\d{2}-\\d{1}-\\d{4}";
	final static String pattern3 = "\\d{1}-\\d{2}-\\d{4}";
	final static String pattern4 = "\\d{1}-\\d{1}-\\d{4}";
	

	/**
	 * The class constructor that read the tasks from the file into the HashMap
	 * after creating an instance of the TaskManager
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public UserInterface() throws ClassNotFoundException, IOException, ParseException {
		RnW = new ReadAndWrite(taskManager);
		RnW.readFromFile();
		date = new Date();
		dateFormat = new SimpleDateFormat(Date_format);
		System.out.println("*************************************************");
		System.out.println("********* Welcome to ToDoly Application *********");
		System.out.println("*************************************************");
	}

	/**
	 * The main screen method for the user interface that deals with the user first
	 * choices
	 * 
	 * @throws ParseException        for the date printing
	 * @throws FileNotFoundException for reading from the file
	 */
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

		int c = validate(1, 4);
		// The main switch case for the main console screen
		switch (c) {
		case 1:
			// Calling the method responsible for Option 1 for displaying
			UserInterfaceDisplayOption();
			break;
		case 2:
			System.out.println(" **** Add new task ****");
			// Calling the method responsible for Option 2 for Adding tasks
			UserInterfaceAddOption();
			break;
		case 3:
			// Calling the method responsible for Option 3 for updating and deleting tasks
			UserInterfaceEditOption();
			break;
		case 4: //
			
			SaveAndExit();
			// Calling the method responsible for writing the task from the HashMap to the
			// txt file as an ArrayList
			// before closing the App
			
		default:
		{
			break;
		}

		}
		in.close();
	} // End of Display method

	/**
	 * Method for Option 1 for the Displaying options in the main console screen
	 * 
	 * @throws ParseException
	 */
	public void UserInterfaceDisplayOption() throws FileNotFoundException, ParseException {
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println(">> (1) To show Tasks by date");
		System.out.println(">> (2) To show Tasks by project");
		System.out.println(">> (0) To return to main page");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.print(">> ");
		int p = validate(0, 2);
		// Switch case for the sub menu for the Option 1 Adding
		switch (p) {
		case 0:
			Display(); // Return to main display
			break;
		case 1:
			System.out.println("Show Tasks by date ** Display by Date");
			// Calling the method responsible for displaying the task Date wise
			ShowByDate(false);
			UserInterfaceDisplayOption(); // To return to sub menu 1
			break;
		case 2:
			System.out.println("Show Tasks by project ** Display by project");
			// Calling the method responsible for displaying the task filtered by project
			ShowTasksByProject();
			UserInterfaceDisplayOption();// To return to sub menu 1
			break;
		default:
			System.out.println("Invalid input ");
			UserInterfaceDisplayOption();// To return to sub menu 1
		}
	} // End of Option 1 method

	/**
	 * Method for Option 2 for Adding options in the main console screen
	 * 
	 * @throws ParseException for the date printing
	 * 
	 */
	public void UserInterfaceAddOption() throws ParseException, FileNotFoundException {
		System.out.println("(1) to add a task to exists project ");
		System.out.println("(2) to add a task with a new project ");
		System.out.println("(0) to return to previous page");
		System.out.print(">> ");
		String project = null;

		int p = validate(0, 2);
		switch (p) // *********** Switch case for option (2)
		{
		case 0:
			Display(); // return to main screen
			break;
		case 1:
			// Assign the project name to a variable and pass it to case 2 to for adding
			project = projectnames("add");
		case 2:
			Scanner sc = new Scanner(System.in);
			if (p == 2) {
				boolean found = true; // To check the project name when adding new task with new project
				while (found) {
					found = false;
					System.out.print("Enter Project Name : >> ");
					project = sc.nextLine();

					for (Entry<String, List<Task>> s : taskManager.ToDol.entrySet()) {

						if (s.getKey().equalsIgnoreCase(project))
							found = true;
					}
					if (found)
						System.out.println("Project name already exsits .... please check and re-enter ");
				}
			}

			System.out.print("Enter Task Title : >> ");
			String title = sc.nextLine();

			Date dd = taskManager.StringToDate(ValDate());
			System.out.print("Enter Task Status : >> ");

			String status = StatusCheck(); // Validate the user input for task status
			boolean st = status.equalsIgnoreCase("ToDO") ? false : true;
			taskManager.AddTask(project, title, dd, st);
			System.out.println("\n^^ Successful in adding the task ^^\n");
			UserInterfaceAddOption();
			break;

		default:
			System.out.println("Invalid input ");
			UserInterfaceAddOption(); // To return to sub menu 2
		}

	} // End of Option 2 method

	/**
	 * Method for Option 3 for Editing options in the main console screen
	 * 
	 * @throws ParseException for the date printing
	 */
	public void UserInterfaceEditOption() throws ParseException, FileNotFoundException {
		Scanner in = new Scanner(System.in);
		System.out.println();
		System.out.println("(1) to edit a task ");
		System.out.println("(2) to change a task status  ");
		System.out.println("(3) to remove a task");
		System.out.println("(0) to return to previous page");
		System.out.print(">> ");
		int p = validate(0, 3);
		// Switch Case for Option 3 for the editing
		switch (p) {
		case 0:
			Display();
		case 1:

			ShowByDate(true);// Display the tasks along with TaskNumber for editing purposes
			System.out.println("Enter task number you want to edit : >> ");
				p=validateEntryInt();
				//p = in.nextInt();
				
			// Check the existence of the task number
			if (taskManager.TaskNumberCheck(p))
				edit(p);
			else
				System.out.println("No exsits task number ");
			UserInterfaceEditOption();// To return to sub menu 3
			break;
		case 2:
			ShowByDate(true);// Display the tasks along with TaskNumber for editing purposes
			System.out.print("Enter task number for the task you want to change it's status : >> ");
			p = validateEntryInt();
			// Check the existence of the task number
			if (taskManager.TaskNumberCheck(p))
				taskManager.StatusEdit(p);
			else
				System.out.println("No exsits task number ");
			UserInterfaceEditOption();// To return to sub menu 3
			break;
		case 3:
			ShowByDate(true);// Display the tasks along with TaskNumber for editing purposes
			System.out.print("Enter task number to remove : >> ");
			p = validateEntryInt();
			// Check the existence of the task number
			if (taskManager.TaskNumberCheck(p))
				{taskManager.RemoveTask(p);
				System.out.println("\nTask has been removed from your list \n");}
			else
				System.out.println("No exsits task number ");
			UserInterfaceEditOption();// To return to sub menu 3
			break;
		default:
			System.out.println("Invalid input ");
			UserInterfaceEditOption();// To return to sub menu 3 if error caught
		}

	} // End of Option 3 method

	/**
	 * Method for Option 3 for Exiting and Saving options in the main console screen
	 *
	 * @throws FileNotFoundException ParseException for the date writing in the file
	 */
	public void SaveAndExit() throws FileNotFoundException, ParseException {
		Scanner in = new Scanner(System.in);
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println("(1) to save and exit ");
		System.out.println("(2) to exit without saving ");
		System.out.println("(0) to return to previous page");
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		int p = validate(0, 2);
		switch (p) // *********** Switch case for option (4)
		{
		case 0:
			Display(); // return to main screen
			break;
		case 1: // Write in the file before exit
			System.out.println("Save and Quit");
			RnW.writeToFile(taskManager.MapToList());
			in.close();
			System.exit(0);
		case 2:
			System.out.println("Quit");
			in.close();
			System.exit(0); // Exit with writing to the file
			break;

		default:
			System.out.println("Invalid input ");
			SaveAndExit(); // To return to sub menu 4
		}
	}// End of Option 4 method
	
	
	/**
	 * Method for showing the task filtered by the project
	 * 
	 * @throws ParseException for the date printing
	 */
	public void ShowTasksByProject() throws ParseException {
		String project = projectnames("show"); // Do display projects and add specific message for the user
		for (Entry<String, List<Task>> s : taskManager.ToDol.entrySet()) {

			if (s.getKey().equalsIgnoreCase(project)) {
				System.out.println("\n");
				System.out.format("-----------------------------%n");
				System.out.format("** Project name ** : " + s.getKey() + "%n");
				System.out.format("-----------------------------%n");

				System.out.println(" (dd-MM-yyyy)");
				System.out.format(
						"+------------+--------+------------------------------------------------------------------------+%n");
				System.out.format("|   DueDate  | Status |  Title	  							       |%n");
				System.out.format(
						"+------------+--------+------------------------------------------------------------------------+%n");

				for (int i = 0; i < s.getValue().size(); i++) {
					System.out.format(Format3, taskManager.DateToString(s.getValue().get(i).getDueDate()),
							s.getValue().get(i).getTaskStatus(), s.getValue().get(i).getTitle());
					System.out.println(
							"-----------------------------------------------------------------------------------------------");

				}
			}
		}
	}

	/**
	 * Method responsible for the Option 1 in the Sub menu for editing It takes the
	 * user input and send it to the update method in the TaskManager
	 * 
	 * @param TaskNumber is the task number the user want to edit
	 * @throws ParseException for the date printing
	 */
	public void edit(int TaskNumber) throws ParseException {
		Scanner in = new Scanner(System.in);
		Iterator<Entry<String, List<Task>>> It = taskManager.ToDol.entrySet().iterator();
		while (It.hasNext())
		{
			Entry<String, List<Task>> Itr = It.next();
			for (int i = 0; i < Itr.getValue().size(); i++)
			{
				Task task = Itr.getValue().get(i);
				if (task.getTaskNumber() == TaskNumber)
				{
					System.out.println("Exsits project name : **" + task.getProjectName() + "**");
					System.out.println("Enter a new project name or press Enter to keep it same >> ");
					String project = in.nextLine();

					System.out.println("Exsits title : **" + task.getTitle()+ "**");
					System.out.println("Enter a new title or press Enter to keep it same >> ");
					String title = in.nextLine();

					System.out.println("Exsits DueDate : **" + taskManager.DateToString(task.getDueDate()) + "**");
					System.out.println("Enter a new DueDate or press Enter to keep it same >> ");
					String date = in.nextLine();

					if (date.length() == 0)
						date = taskManager.DateToString(task.getDueDate());
					else
						date = ValDate(date);

					boolean st = task.getTaskStatus().equalsIgnoreCase("ToDo") ? false : true;
					project = (project.length() == 0) ? task.getProjectName() : project;
					title = (title.length() == 0) ? task.getTitle() : title;

					// If the project name changes ... I need to move to task in the HashMap
					if (project.equalsIgnoreCase(task.getProjectName()))
						{
						taskManager.update(TaskNumber, project, title, taskManager.StringToDate(date), st);
						System.out.println("\n^^ Task task has been edited ^^\n");
						return;
						}
					else
						{
						taskManager.AddTask(project, title, taskManager.StringToDate(date), st);
						taskManager.RemoveTask(TaskNumber);
						System.out.println("\n^^ Task task has been edited ^^\n");
						return;
						}
				}
			}
		}
	} // End of edit method

	
	/**
	 * Method for user to validate user entry when asked for Int
	 * @return the number chosen by the user TYPE INT
	 */
	public int validateEntryInt() {
		int num = 0;
		try {
			Scanner sc = new Scanner(System.in);
			num = sc.nextInt();
			return num;
		} catch (Exception e) {
			// validateEntryInt();
			System.out.println("Invalid input,try again");
}
		return validateEntryInt();
	}
	
	
	/**
	 * Method to validate the user entry for the menu choices
	 * 
	 * @param the FROM number that the user can choice TYPE INT
	 * @param the TO number that the user can choice TYPE INT
	 * @return The valid choice that only accepted in the displayed menu type INT
	 */
	public int validate(int a, int b) {
		int c = 0;
		try {
			Scanner in = new Scanner(System.in);
			System.out.print("Pick an option number >>  ");

			c = in.nextInt();
			while (!(c >= a && c <= b)) {

				System.out.println("Wrong option number,");
				System.out.print("Please enter the option number again between " + a + " and " + b + " >> ");
				c = in.nextInt();
			}
		}

		catch (InputMismatchException e) {
			System.out.println("Invalid Input ... please try again ");
			c = validate(a, b);
		}
		return c;
	}// End of validate method

	/**
	 * Method to take the date from user and validate it
	 * 
	 * @return a valid date TYPE String
	 */
	public String ValDate() {
		dateFormat.setLenient(false);
		String DateCheck = null;
		// boolean isValidDate = false;
		Scanner in = new Scanner(System.in);

		try {
			do {
				
				System.out.print("Enter Task Due Date, use format dd-MM-yyyy : >> ");
				DateCheck = in.nextLine();
				date = taskManager.StringToDate(DateCheck.trim());
			} while (!DateCheck.trim().matches(pattern1) && !DateCheck.trim().matches(pattern2) && !DateCheck.trim().matches(pattern3) && !DateCheck.trim().matches(pattern4));

		} catch (ParseException e) {
			System.out.println(DateCheck + " is Invalid Date format, use dd-MM-yyyy format >>");
			ValDate();
		} catch (NoSuchElementException e) {
			System.out.println(DateCheck + " is Invalid Date format, use dd-MM-yyyy format >>");
			ValDate();

		}

		return taskManager.DateToString(date);
	}

	/**
	 * OVERLOAD Method to validate the user entry for the date
	 * 
	 * @param dd is the date that user entered and want to be validated else the
	 *           user will enter again until validate
	 * @return a valid date TYPE String
	 */
	public String ValDate(String dd) {
		dateFormat.setLenient(false);
		String DateCheck = dd;
		// boolean isValidDate = false;
		Scanner in = new Scanner(System.in);

		try {
			while (!DateCheck.trim().matches(pattern1) && !DateCheck.trim().matches(pattern2) && !DateCheck.trim().matches(pattern3) && !DateCheck.trim().matches(pattern4))
				{
				System.out.print(DateCheck + " is Invalid Date format, use dd-MM-yyyy format >>");
				DateCheck = in.nextLine();
				}
			date = taskManager.StringToDate(DateCheck.trim());
		} catch (ParseException e) {
			System.out.println(DateCheck + " is Invalid Date format, use dd-MM-yyyy format >>");
			ValDate();
		} catch (NoSuchElementException e) {
			System.out.println(DateCheck + " is Invalid Date format, use dd-MM-yyyy format >>");
			ValDate();

		}

		return taskManager.DateToString(date);
	}

	/**
	 * Method to show the existence projects for different purposes and take the
	 * user choice for which project
	 * 
	 * @param MSG a String word for specifying the method calling purpose
	 * @return a project name TYPE String
	 */
	public String projectnames(String MSG) {

		String projects[] = new String[taskManager.ToDol.size()];
		if (taskManager.ToDol.size() > 0) {

			int pn = 0; // a counter for the temp array of projects

			System.out.println("Exsist project/s   ");
			System.out.println("\n");
			System.out.format("+------+----------------------+%n");
			System.out.format("|  ID  |  Project 	      |%n");
			System.out.format("+------+----------------------+%n");
			for (Entry<String, List<Task>> s : taskManager.ToDol.entrySet()) {
				projects[pn] = s.getKey(); // Store the projects into array to add task for already exists projects
				pn++;
				System.out.format(Format4, pn, CheckDisplayLenght(s.getKey(), 20));
				System.out.println("-----------------------------");
			}
			System.out.println("\n");
		}
		// ***** take the project number and add it to the task
		System.out.print("Enter a project number you want to " + MSG + " a task to : >> ");
		int PN = validate(1, projects.length);
		PN--; // reduce the index

		return projects[PN];

	}

	/**
	 * Method to validate the user entry for the task status User can only enter
	 * Done or ToDo .
	 * 
	 * @return a valid status TYPE String
	 */
	public String StatusCheck() {
		String status = null;
		try {
			Scanner in = new Scanner(System.in);
			status = in.nextLine();

			while (!(status.trim().equalsIgnoreCase("ToDo") || status.trim().equalsIgnoreCase("Done"))) {
				System.out.println("Wrong Status,");
				System.out.print("Please enter the status again as Done or ToDo : >> ");
				status = in.nextLine();
			}
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input ... please try again ");
			status = StatusCheck();
		}

		return status;
	}

	
	/**
	 * Method for controlling the text length in display screens
	 * 
	 * @param text is the text needed to be controlled
	 * @param max  is the maximum allowed length for the text else print ...
	 * @return the text after editing for displaying if the length is greater that
	 *         the max length allowed TYPE String
	 */
	public String CheckDisplayLenght(String text, int max) {
		if (text.length() > max)
			return text.substring(0, max - 3) + "...";
		return text;
	}

	/**
	 * Method to Display the project date wise
	 * 
	 * @param showT_number a boolean variable if True display the tasks with the
	 *                     TaskNaumber for editing purposes else display the tasks
	 *                     without showing the TaskNaumber
	 * @throws ParseException for the date printing
	 */
	public void ShowByDate(boolean showT_number) throws ParseException {
		List<Task> list = taskManager.Sorting();
		if (!showT_number) {
			System.out.println("\n");
			System.out.println(" (dd-MM-yyyy)");
			System.out.format(
					"+------------+----------------------+--------+------------------------------------------------------------------------+%n");
			System.out.format(
					"|   DueDate  | Project              | Status |  Title 	  							 			     |%n");
			System.out.format(
					"+------------+----------------------+--------+------------------------------------------------------------------------+%n");
			for (Task t : list) {
				System.out.format(Format1, taskManager.DateToString(t.getDueDate()),
						CheckDisplayLenght(t.getProjectName(), 20), t.getTaskStatus(), t.getTitle());
				System.out.println(
						"----------------------------------------------------------------------------------------------------------------------");
			}
		} else {
			System.out.println("\n");
			System.out.println(" (dd-MM-yyyy)");
			System.out.format(
					"+------------+----------------------+--------+------------------------------------------------------------------------+--------------%n");
			System.out.format(
					"|   DueDate  | Project              | Status |  Title   							      | Task Number |%n");
			System.out.format(
					"+------------+----------------------+--------+------------------------------------------------------------------------+--------------%n");
			for (Task t : list) {
				System.out.format(Format2, taskManager.DateToString(t.getDueDate()),
						CheckDisplayLenght(t.getProjectName(), 20), t.getTaskStatus(), t.getTitle(), t.getTaskNumber());
				System.out.println(
						"-------------------------------------------------------------------------------------------------------------------------------------");
			}
		}

	}

}
