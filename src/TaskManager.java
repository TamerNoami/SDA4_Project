import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class TaskManager {
	Task task;
	Map<String, List<Task>> ToDol;
	ReadAndWrite RnW;
	List<Task> listOfTask;
	SimpleDateFormat dateFormat;
	Date date;
	UserInterface userInterface;

	// Constructor to create the HashMap
	public TaskManager() throws FileNotFoundException {
		// HashMap key consist of the project name
		// HashMap value is a list of instance of TaskList
		ToDol = new HashMap<String, List<Task>>();
		RnW = new ReadAndWrite();
		userInterface = new UserInterface(this);
		date = new Date();
		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	}

	public void AddTask() throws ParseException {

		System.out.println("(1) To add task to exists project (2) To create a new project)");
		System.out.println("Or (0) To return to previous page");
		System.out.print(">> ");
		Scanner in = new Scanner(System.in);
		int p = in.nextInt();
		while (!(p >= 0 && p <= 2)) {
			System.out.println("Wrong option number,");
			System.out.print("Please enter the option number again :");
			p = in.nextInt();
		}
		in.nextLine();
		task = new Task();
		switch (p) {
		case 0:
			userInterface.Display();
			break;
		case 1:
			String projects[] = new String[ToDol.size()];
			if (ToDol.size() > 0) {

				int pn = 0; // a counter for the temp array of projects

				System.out.println("Exsist project/s   ");
				for (Entry<String, List<Task>> s : ToDol.entrySet()) {
					projects[pn] = s.getKey(); // Store the projects into array to add task for already exists projects
					pn++;
					System.out.println("Project " + pn + " : " + s.getKey());
				}
			}
			// ***** take the project number and add it to the task
			System.out.print("Enter a project number you want to add a task to : ");
			int PN = in.nextInt();
			PN--; // reduce the index
			while( !(PN >= 0 && PN <= projects.length))
			{
				System.out.print("Invalid choice ... please try again");
				PN = in.nextInt();
			}
			Add(projects[PN]);
			break;
		case 2:
			Add();
			break;
		default:
			System.out.println("Invalid input ");
			AddTask();
		}
	}

	public void Add() throws ParseException
	{
		Scanner in = new Scanner(System.in);
		dateFormat.setLenient(false);
		System.out.print("Enter Project Name : ");
		String project = in.nextLine();
		boolean found = false ;
		for (Entry<String, List<Task>> s : ToDol.entrySet())
		{
			if (s.getKey().equalsIgnoreCase(project))
			{
				 found = true;
			} 
		}
		if(found)
		{
			System.out.println("Project name already exsits .... please check and re-enter ");
			Add();
		}
		else
		{
			Add(project);
		}
	}

	public void Add(String project_name) throws ParseException
	{
		Scanner in = new Scanner(System.in);
		dateFormat.setLenient(false);
		String project = project_name;
		System.out.print("Enter Task Title : ");
		String title = in.nextLine();
		System.out.print("Enter Task Due Date, use format dd-MM-yyyy  : ");
		Date dd = StringToDate(in.nextLine());
		System.out.print("Enter Task Status : ");
		String status = in.nextLine();
		boolean st = status.equalsIgnoreCase("ToDO") ? false : true;
		task.setter(project, title, dd, st);

		for (Entry<String, List<Task>> s : ToDol.entrySet())
		{
			if (!(ToDol.isEmpty()) && s.getKey().equalsIgnoreCase(project_name))
			{
				// Add the task to the ArrayList based on the project as a key If already exists
				s.getValue().add(task);
				System.out.println("Successful in adding the task ");
				userInterface.Display();
			}
		}
		listOfTask = new ArrayList<Task>();
		listOfTask.add(task);
		ToDol.put(project, listOfTask);
		System.out.println("Successful in adding the task ");
		userInterface.Display();
	}

	// Method to return number of ToDo and Done tasks

	public int[] StatusCount()
	{
		int[] status_count = new int[2];
		int taskStatus = 0;
		int TasksCount = 0;

		for (Entry<String, List<Task>> s : ToDol.entrySet())
		{
			for (int i = 0; i < s.getValue().size(); i++)
			{
				if ((s.getValue().get(i).getTaskStatus().equals("Done")))
					taskStatus++;
			}
			TasksCount += s.getValue().size();
		}
		status_count[0] = TasksCount - taskStatus; // tasks ToDo
		status_count[1] = taskStatus; // tasks are Done
		return status_count; // Return the count of ToDo and Done tasks
	}

	// ***********************************************

	// Create a temporary container ArrayList TemoList to store all of the values of
	// the HashMaP

	public List<Task> MapToList()
	{
		List<Task> TempList = new ArrayList<>();
		
		for (List<Task> s : ToDol.values())
		{
			for (Task t : s)
			{
				TempList.add(t);
			}
		}
		return TempList;
	}

	public List<Task> Sorting()
	{
		List<Task> list = MapToList();
		Collections.sort(list, new Comparator<Task>()
		{
			public int compare(Task t1, Task t2) {
				try
				{
					return t1.getDueDate().compareTo(t2.getDueDate());
				} 
				
				catch (ParseException e)
				{
					e.printStackTrace();
				}
				return 0;
			}});
		return list;
	}

	public void ShowByDate(boolean showT_number) throws ParseException {
		List<Task> list = Sorting();

		for (Task t : list) {
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^");
			System.out.println("Due Date : " + DateToString(t.getDueDate()));
			System.out.println("-----------------------");
			System.out.println("Project : " + t.getProjectName());
			System.out.println("Title : " + t.getTitle());
			System.out.println("Status : " + t.getTaskStatus());
			if (showT_number)
				System.out.println("Task Number : (" + t.getTaskNumber() + ")");
		}
	}

	public Date StringToDate(String dueDate) throws ParseException {
		return dateFormat.parse(dueDate);
	}

	public String DateToString(Date date) {
		return dateFormat.format(date);
	}
	// Add task method 2 to add elements already exists for ^testing purpose only
		public void AddTask2(String project, Task task)
		{
			for (Entry<String, List<Task>> s : ToDol.entrySet())
			{
				if (!(ToDol.isEmpty()) && s.getKey().equalsIgnoreCase(project))
				{
					// Add the task to the ArrayList based on the project as a key If already exists
					s.getValue().add(task);
					return;
				}
			}
			// Adding the new project in the HashMap a long with the value
			listOfTask = new ArrayList<Task>();
			listOfTask.add(task);
			ToDol.put(project, listOfTask);
		}

		// *******************************************************
}
