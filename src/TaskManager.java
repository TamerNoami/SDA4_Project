import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class TaskManager extends Task
	{
	Map<String,List<Task> > ToDol;
	ReadAndWrite RnW ;
	
	//Constructor to create the HashMap
	public TaskManager() throws FileNotFoundException
	{
		// HashMap key consist of the project name
		// HashMap value is a list of instance of tasklist
		ToDol = new HashMap<String,List<Task>>();
		RnW = new ReadAndWrite();
		
	}
	
	List<Task> listOfTask;//= new ArrayList<TaskList>();
	
	// Add task method 
	public void AddTask(String project , Task task , Map<String,List<Task>> Task )
	  {
		for(Entry<String, List<Task>> s : Task.entrySet())
			{	
			if(!(Task.isEmpty()) && s.getKey().equalsIgnoreCase(project))
			{
				// Add the task to the Arraylist based on the project as a key If already exists
				s.getValue().add(task);
				return;
			}
			}
			// Adding the new project in the HashMap a long with the value
			listOfTask= new ArrayList<Task>();
			listOfTask.add(task);
			Task.put(project,listOfTask );
			} 
	//*******************************************************
	
	// Method to print the tasks from the HashMap
	public void showtask(Map<String,List<Task>> map) throws ParseException 
		{	
		int taskStatus=0;
		int TasksCount=0;
		
		for(Entry<String, List<Task>> s : map.entrySet())
		{
			System.out.println("-----------------------------");
			System.out.println("**Project name** : " + s.getKey());
			System.out.println("-----------------------------");
			for(int i=0; i<s.getValue().size();i++)
			{
				//System.out.println("Project :" + s.getValue().get(i).getProjectName());	
				System.out.println("Title :" + s.getValue().get(i).getTitle());
				System.out.println("Due Date :" + DateToString(s.getValue().get(i).getDueDate()));	
				System.out.println("Status :" + s.getValue().get(i).getTaskStatus());
				//Task number
				System.out.println("Task Number :" + s.getValue().get(i).getTaskNumber());	
				System.out.println("+++++++++++++++++");
				if((s.getValue().get(i).getTaskStatus().equals("Done")))
				taskStatus++;
				
			}
			TasksCount+=s.getValue().size();
		}
		System.out.println("You have  " + (TasksCount - taskStatus) +" tasks todo and " + taskStatus +" tasks are done !");
	}
	
	//***********************************************
	
	
	// Create a temporary container ArrayList TemoList to store all of the values of the HashMaP
	
	public List<Task> MapToList(Map<String,List<Task>> map)
	{
		List<Task> TempList=new ArrayList<>();
		for(List<Task> s : map.values())
		{
			for(Task t: s)
			{
				TempList.add(t);
			}
		}
		return TempList;
	}
	
	
	public List<Task> Sorting(List<Task> list)
	{
		 Collections.sort(list, new Comparator<Task>() 
		 {
	            public int compare(Task t1, Task t2)
	            { 
	             try {
					return   t1.getDueDate().compareTo(t2.getDueDate());
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
				return 0;
	            }
	
		 });
	
	return list;
	
	}

	}
	


