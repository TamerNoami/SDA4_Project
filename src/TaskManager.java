import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TaskManager {
	Task task;
	Map<String, List<Task>> ToDol = new HashMap<String, List<Task>>();
	List<Task> listOfTask;
	SimpleDateFormat dateFormat;
	Date date;
	final static String Date_format = "dd-MM-yyyy";
	

	// Constructor to create the HashMap
	public TaskManager() throws ClassNotFoundException, IOException, ParseException {
		// HashMap key consist of the project name
		// HashMap value is a list of instance of TaskList
	
		date = new Date();
		dateFormat = new SimpleDateFormat(Date_format);
	}


	public void AddTask(String project, String title, Date dd,boolean st) throws ParseException
	{
		Task task=new Task();
		task.setter(project, title, dd, st);

		for (Entry<String, List<Task>> s : ToDol.entrySet())
		
			if (!(ToDol.isEmpty()) && s.getKey().equalsIgnoreCase(project))
			{
				// Add the task to the ArrayList based on the project as a key If already exists
				s.getValue().add(task);
				//System.out.println("Successful in adding the task to exsits project ");
				//userInterface.Display();
				return ;
			}
			
		listOfTask = new ArrayList<Task>();
		listOfTask.add(task);
		ToDol.put(project, listOfTask);
		//System.out.println("Successful in adding the task to a new project ");
		//userInterface.Display();
	}

	// Add task method 2 to add elements from the txt file
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
			
			public void StatusEdit(int TaskNumber) throws ParseException
			{
				Iterator<Entry<String, List<Task>>> It = ToDol.entrySet().iterator();
				while(It.hasNext())
			{ 	Entry<String, List<Task>> Itr = It.next();
				for(int i=0 ; i< Itr.getValue().size() ; i++ )
				{	Task task = Itr.getValue().get(i);
					if (task.getTaskNumber() == TaskNumber)
				{	
					if(task.getTaskStatus().equalsIgnoreCase("Done"))
					{
					System.out.println("Task status already Done ");
					return;
					}
					else
					{
					task.setter(task.getProjectName(), task.getTitle(), task.getDueDate(), true);
					System.out.println("Task status has been updated ");
					return;
					}
				}
			}
			}
			}
			
			
			
			public void update(int TaskNumber,String project, String title, Date dd,boolean st) throws ParseException
			{	
				Iterator<Entry<String, List<Task>>> It = ToDol.entrySet().iterator();
				while(It.hasNext())
			{ 	Entry<String, List<Task>> Itr = It.next();
				for(int i=0 ; i< Itr.getValue().size() ; i++ )
				{	Task task = Itr.getValue().get(i);
					if (task.getTaskNumber() == TaskNumber)
					{
					task.setter(project, title, dd, st);
					System.out.println("Task task has been edited ");
					break;
					}
						
					
				}
			}
				
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
	
	public boolean TaskNumberCheck(int TN)
	{
		boolean found = false;
		Iterator<Entry<String, List<Task>>> It = ToDol.entrySet().iterator();
		while(It.hasNext())
	{ 	Entry<String, List<Task>> Itr = It.next();
		for(int i=0 ; i< Itr.getValue().size() ; i++ )
		{	Task task = Itr.getValue().get(i);
			if (task.getTaskNumber() == TN)
			found = true;
			
	}}
	return found;	
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

	

	public Date StringToDate(String dueDate) throws ParseException {
		return dateFormat.parse(dueDate);
	}

	public String DateToString(Date date) {
		return dateFormat.format(date);
	}
	
	
	
		
		}
		// *******************************************************

