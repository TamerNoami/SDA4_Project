
/**
 * This class is going to deal with all user interfaces and display methods.
 * 
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserInterface {
	TaskManager taskManager= new TaskManager();
	ReadAndWrite RnW ;
	SimpleDateFormat dateFormat;
	Date date;
	final static String Date_format = "dd-MM-yyyy";

	public UserInterface () throws ClassNotFoundException, IOException, ParseException
	{
		RnW = new ReadAndWrite(taskManager);
		RnW.readFromFile();
		date = new Date();
		dateFormat = new SimpleDateFormat(Date_format);
		System.out.println("*************************************************");
		System.out.println("********* Welcome to ToDoly Application *********");
		System.out.println("*************************************************");
	}

	public void Display() throws ParseException, FileNotFoundException
	{
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

		

			int c = validate(1,4);
			
			switch (c) {
			case 1:
				
				System.out.println(">> (1) To show Tasks by date");
				System.out.println(">> (2) To show Tasks by project");
				System.out.println(">> (0) To return to main page");
				System.out.print(">> ");
				
				int p = validate(0,2);
				switch (p) {
				case 0:
					Display(); // Return to main display
					break;
				case 1:
					System.out.println("Show Tasks by date ** Display by Date");
					ShowByDate(false);
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
				System.out.println(" **** Add new task ****");
				
				
				System.out.println("(1) to add a task to exists project ");
				System.out.println("(2) to add a task with a new project ");
				System.out.println("(0) to return to previous page");
				System.out.print(">> ");
				String project = null;
				
				p = validate(0,2);
				switch (p) // ***********   Switch case for option (2)
				{
				case 0:
					//userInterface.Display();
					break;
				case 1:
				
					String projects[] = new String[taskManager.ToDol.size()];
					if (taskManager.ToDol.size() > 0) {

						int pn = 0; // a counter for the temp array of projects

						System.out.println("Exsist project/s   ");
						for (Entry<String, List<Task>> s : taskManager.ToDol.entrySet()) {
							projects[pn] = s.getKey(); // Store the projects into array to add task for already exists projects
							pn++;
							System.out.println("Project " + pn + " : " + s.getKey());
						}
					}
					// ***** take the project number and add it to the task
					System.out.print("Enter a project number you want to add a task to : ");
					int PN = validate(1,projects.length); 
					PN--; // reduce the index
					 project = projects[PN];
					 //in.nextLine(); // move the scanner
				case 2:
					
					
					if(p==2)
						{
						//in.nextLine();// move the scanner
						boolean found = true ;
					
						while(found)
							{	
								found = false;
								System.out.print("Enter Project Name : ");
								project = in.nextLine();
								
								for (Entry<String, List<Task>> s : taskManager.ToDol.entrySet())
								{
									
									if (s.getKey().equalsIgnoreCase(project))
										found = true;
								}
								if(found)
								System.out.println("Project name already exsits .... please check and re-enter ");
							}
						}
						
					
					System.out.print("Enter Task Title : ");
					String title = in.nextLine();
					System.out.print("Enter Task Due Date, use format dd-MM-yyyy  : ");
					Date dd = taskManager.StringToDate(ValDate());
					System.out.print("Enter Task Status : ");
					//String status = in.nextLine();
					String status=StatusCheck();
					boolean st = status.equalsIgnoreCase("ToDO") ? false : true;
					taskManager.AddTask(project,title,dd,st);
					System.out.println("Successful in adding the task ");	
					break;
					
				default:
					System.out.println("Invalid input ");
					Display();
				}
				
				//need to return to option 2
				Display();
				break;
			case 3:
				ShowByDate(true);
				System.out.println();
				System.out.println("(1) to edit a task ");
				System.out.println("(2) to mark a task as Done !  ");
				System.out.println("(3) to remove a task");
				System.out.println("(0) to return to previous page");
				System.out.print(">> ");
				p = validate(0,3);
				switch(p)
				{
				case 1:
					System.out.println("Enter task number you want to edit : ");
					p=in.nextInt();
					if(taskManager.TaskNumberCheck(p))
					edit(p);
					else
					System.out.println("No exsits task number ");
					break;
				case 2:
					System.out.println("Enter task number for the task you want to make it as Done : ");
					p=in.nextInt();
					if(taskManager.TaskNumberCheck(p))
					taskManager.StatusEdit(p);
					else
					System.out.println("No exsits task number ");	
					break; // Need to return
				case 3:
					System.out.print("Enter task number to remove : ");
					p=in.nextInt();
					if(taskManager.TaskNumberCheck(p))
					taskManager.RemoveTask(p);
					else
					System.out.print("Not exsits task number ");	
					break;
				default:
					System.out.println("Invalid input ");	
				}
				
				// need to return to previous screen
				Display();
				
		
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
			in.close();
		} // Try ends here
		

		

	

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
	/*
	public void showtask() throws ParseException
	{
		int taskStatus = 0;
		int TasksCount = 0;
		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("    Project name   			Due Date  		Status   	Title");
		System.out.println("--------------------------------------------------------------------------------");
		for (Entry<String, List<Task>> s : taskManager.ToDol.entrySet())
		{
			
			System.out.print("	" + s.getKey());
			
			for (int i = 0; i < s.getValue().size(); i++)
			{
				System.out.print("		" + taskManager.DateToString(s.getValue().get(i).getDueDate()));
				System.out.print("				" + s.getValue().get(i).getTaskStatus());
				System.out.println("						" + s.getValue().get(i).getTitle());
				// Task number for update method
				// System.out.println("Task Number :" + s.getValue().get(i).getTaskNumber());
				System.out.println("");
				
			}
		}
		
	}
	*/
	
	
	
	
	public void edit(int TaskNumber) throws ParseException
	{
		Scanner in = new Scanner(System.in);
		Iterator<Entry<String, List<Task>>> It = taskManager.ToDol.entrySet().iterator();
		while(It.hasNext())
			{ 	
			Entry<String, List<Task>> Itr = It.next();
				for(int i=0 ; i< Itr.getValue().size() ; i++ )
					{	
					Task task = Itr.getValue().get(i);
						if (task.getTaskNumber() == TaskNumber)
							{
								System.out.println("Exsits project name : **" + task.getProjectName()+ "**");
								System.out.println("Enter a new project name or press Enter to keep it same");
								String project = in.nextLine();
								
								System.out.println("Exsits title : **" + task.getTitle());
								System.out.println("Enter a new title or press Enter to keep it same"+ "**");
								String title = in.nextLine();
								
								System.out.println("Exsits title : **" + taskManager.DateToString(task.getDueDate())+ "**");
								System.out.println("Enter a new title or press Enter to keep it same");
								//String date = in.nextLine();
								String date =ValDate();
								boolean st = task.getTaskStatus().equalsIgnoreCase("ToDo") ? false : true;
								project = (project.length()==0) ? task.getProjectName() : project ;
								title = (title.length()==0) ? task.getTitle() : title ;
								date = (date.length()==0) ? taskManager.DateToString(task.getDueDate()) : date ;
								// If the project name changes ... I need to move to task in the HashMap
								if (project.equals( task.getProjectName()))
								{
								taskManager.update(TaskNumber, project,title,taskManager.StringToDate(date),st);
								return;
								}
								else
								{
									taskManager.AddTask(project, title, taskManager.StringToDate(date), st);
									taskManager.RemoveTask(TaskNumber);
									return;
								}
							}
				
			
					}
			}
	}
	
	
	public int validate(int a,int b) 
	{
		int c =0;
		try 
		{
			Scanner in = new Scanner(System.in);
			System.out.print("Pick an option number  " );
			 c = in.nextInt();
			while (!(c >= a && c <= b)) {
				System.out.println("Wrong option number,");
				System.out.print("Please enter the option number again between " + a + " and " + b + " >> ");
				c = in.nextInt();
			}
		}
		catch (InputMismatchException e)
		{
			System.out.println("Invalid Input ... please try again ");
			c = validate(a,b);
		}
			
			
		return c;
	}
	
	public String ValDate()
	{	 
		dateFormat.setLenient(false);
		String DateCheck = null ;
		boolean  isValidDate ;
		Scanner in = new Scanner(System.in);	
		try
		{
		do
		{	
			DateCheck = in.nextLine();
			date=taskManager.StringToDate(DateCheck.trim());
			isValidDate=true;
			//return DateCheck;
		}while(!isValidDate);

	    }
	        catch (ParseException e)
	        {
	        	System.out.println(e.getMessage());
	        	System.out.println(DateCheck + " is Invalid Date format, use dd-MM-yyyy format >>");
	            ValDate();
	            
	        }
		catch (NoSuchElementException e)
        {
            System.out.println(DateCheck + " is Invalid Date format, use dd-MM-yyyy format >>");
            ValDate();
            
        }
	        
	
		return DateCheck;
	    }
			
	
	
	
	public String StatusCheck()
	{
		String status =null;
		try 
		{
			Scanner in = new Scanner(System.in);
			status = in.nextLine();
			
			while (!(status.trim().equalsIgnoreCase("ToDo") || status.trim().equalsIgnoreCase("Done"))) {
				System.out.println("Wrong Status,");
				System.out.print("Please enter the status again as Done or ToDo : >> ");
				status = in.nextLine();
			} 
		}
		catch (InputMismatchException e)
		{
			System.out.println("Invalid Input ... please try again ");
			status = StatusCheck();
		}
		
		return status;
	}
	
	
	
	public void ShowByDate(boolean showT_number) throws ParseException
	{
		List<Task> list = taskManager.Sorting();

		for (Task t : list) {
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^");
			System.out.println("Due Date : " + taskManager.DateToString(t.getDueDate()));
			System.out.println("-----------------------");
			System.out.println("Project : " + t.getProjectName());
			System.out.println("Title : " + t.getTitle());
			System.out.println("Status : " + t.getTaskStatus());
			if (showT_number)
				System.out.println("Task Number : (" + t.getTaskNumber() + ")");
		}
	}

}
