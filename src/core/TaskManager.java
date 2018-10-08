package core;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
/**
 * This class is responsible for main tasks operations that can be done to a tasks
 * Operations such as Add, Remove, Update and Sorting
 * The ToDol is a temporary container HashMap for doing the tasks operations on after reading and writing to a txt file.
 * @author TamerNoami
 * 
 */

public class TaskManager {
	Task task;
	Map<String, List<Task>> ToDol = new HashMap<String, List<Task>>(); // Create the HashMap only Once
	List<Task> listOfTask;
	SimpleDateFormat dateFormat;
	Date date;
	public final static String Date_format = "dd-MM-yyyy";
	

	
	/**
	 * Constructor to initiate the date Object
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public TaskManager() throws ClassNotFoundException, IOException, ParseException {
		// HashMap key consist of the project name
		// HashMap value is a list of instance of TaskList
	
		date = new Date();
		dateFormat = new SimpleDateFormat(Date_format);
		
	}

	/**
	 * Method to add the task to the HashMap
	 * @param project the task's project name TYPE String
	 * @param title the task's title TYPE String
	 * @param dd the task's DueDtae TYPE Date
	 * @param st the task's status TYPE boolean
	 * @throws ParseException
	 */
	public void AddTask(String project, String title, Date dd,boolean st) throws ParseException
	{
		Task task=new Task();
		task.setter(project, title, dd, st);

		for (Entry<String, List<Task>> s : ToDol.entrySet())
		
			if (!(ToDol.isEmpty()) && s.getKey().equalsIgnoreCase(project))
			{
				// Add the task to the ArrayList based on the project as a key If already exists
				s.getValue().add(task);
				return ;
			}
		listOfTask = new ArrayList<Task>();
		listOfTask.add(task);
		ToDol.put(project, listOfTask);
	}

	
			/**
			 * Method for removing the task based on the TaskNumber
			 * It deletes the project also once there is no tasks in the project
			 * @param TaskNumber is the reference for editing and deleting purposes TYPE INT
			 */
			public void RemoveTask(int TaskNumber)
			{	
				try
				{
				Iterator<Entry<String, List<Task>>> It = ToDol.entrySet().iterator();
					while(It.hasNext())
					{ 
						Entry<String, List<Task>> Itr = It.next();
						for(int i=0 ; i< Itr.getValue().size() ; i++ )
						{	
						Task task = Itr.getValue().get(i);
						if (task.getTaskNumber() == TaskNumber)
							{
						Itr.getValue().remove(i);
						System.out.println("Task has been removed from your list ");
						//return;
							}	
						} 
					if(Itr.getValue().size()==0)
						ToDol.remove(Itr.getKey());
					}
				}
				catch (ConcurrentModificationException e)
				{
					return;
				}
			}
			
			/**
			 * Method for editing the task status 
			 * @param TaskNumber is the reference for editing and deleting purposes TYPE INT
			 * @throws ParseException
			 */
			public void StatusEdit(int TaskNumber) throws ParseException
			{
				Iterator<Entry<String, List<Task>>> It = ToDol.entrySet().iterator();
				while(It.hasNext())
			{ 	Entry<String, List<Task>> Itr = It.next();
				for(int i=0 ; i< Itr.getValue().size() ; i++ )
				{	Task task = Itr.getValue().get(i);
					if (task.getTaskNumber() == TaskNumber)
				{	
					
					boolean status = task.getTaskStatus().equalsIgnoreCase("Done") ? false : true ;
					if(task.getTaskStatus().equalsIgnoreCase("Done"))
					System.out.println("Task status has been updated  from " +task.getTaskStatus() + " ==> " + "ToDO ");
					else
					System.out.println("Task status has been updated  from " +task.getTaskStatus() + " ==> " + " Done ");
					task.setter(task.getProjectName(), task.getTitle(), task.getDueDate(), status);
					return;
					}
				}
			}
			}

			
			
			/**
			 * Method for updating the task's details based on the TaskNumber
			 * @param TaskNumber is the reference for editing and deleting purposes TYPE INT
			 * @param project the task's project name TYPE String
			 * @param title the task's title TYPE String
			 * @param dd the task's DueDtae TYPE Date
			 * @param st the task's status TYPE boolean
			 * @throws ParseException
			 */
			public void update(int TaskNumber,String project, String title, Date dd,boolean st) throws ParseException
			{	
				Iterator<Entry<String, List<Task>>> It = ToDol.entrySet().iterator();
				while(It.hasNext())
				{ 	
				Entry<String, List<Task>> Itr = It.next();
				for(int i=0 ; i< Itr.getValue().size() ; i++ )
					{	
					Task task = Itr.getValue().get(i);
					if (task.getTaskNumber() == TaskNumber)
						{
					task.setter(project, title, dd, st);
					System.out.println("Task task has been edited ");
					break;
						}
					}
				}
				
			}
			
			
	
			/**
			 * Method to return number of ToDo and Done tasks for the main screen console
			 * @return Two values for the Done and ToDo tasks TYPE Array of INT
			 */
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
	
	
	 
	/**
	 * Method to validate the user entry for the task number for editing purposes to make sure only exists TaskNumber chosen
	 * @param TN the TaskNumber to check it 
	 * @return a valid TaskNumber TYPE INT
	 */
	public boolean TaskNumberCheck(int TN)
	{
		boolean found = false;
		Iterator<Entry<String, List<Task>>> It = ToDol.entrySet().iterator();
		while(It.hasNext())
		{ 	
			Entry<String, List<Task>> Itr = It.next();
		for(int i=0 ; i< Itr.getValue().size() ; i++ )
			{	
			Task task = Itr.getValue().get(i);
			if (task.getTaskNumber() == TN)
			found = true;
			}
		}
	return found;	
	}

	

	
	/**
	 * Create a temporary container ArrayList TemoList to store all of the values of the HashMap for file operations purposes
	 * @return
	 */
	public List<Task> MapToList()
	{
		return ToDol.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(ArrayList::new));
	}
	
	
	
	/**
	 * Method for sorting the task's date for displaying in a date order
	 * @return a List of Tasks ordered by a date TYPE LIST<TASK>
	 */
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

	
	/**
	 * Method to convert the Date from a String format ==> Date format
	 * @param dueDate the String date needed to be converted TYPE Date
	 * @return a Date TYPE Date
	 * @throws ParseException
	 */
	public Date StringToDate(String dueDate) throws ParseException {
		return dateFormat.parse(dueDate);
	}

	/**
	 * Method to convert the Date from a Date format ==> String format
	 * @param date the date in a date format needed to be converted to a String 
	 * @return a String for the date in a pre-defined format "dd-MM-yyyy"
	 */
	public String DateToString(Date date) {
		return dateFormat.format(date);
	}
	
	
		} // End of the TaskManager Class
		

