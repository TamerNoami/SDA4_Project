package core;
/**
 * This class is the Task class.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Task {
	/**
	 * The fields for the task are as follow String project to store the project
	 * name String title to store the task title or description Date for the dueDate
	 * of the task Boolean status for the task status and return a String of "ToDO"
	 * or "Done" TaskNumber is a number assigned to all tasks for the updating
	 * process T_counter is to create the unique number for the task
	 */
	private String project;
	private String title;
	private Date dueDate;
	private boolean status;
	private int TaskNumber = 0;
	public static int T_counter = 0;

	

	SimpleDateFormat dateFormat;
	Date date = new Date();;

	/**
	 * Constructor to initial status of the task list to ToDo
	 */
	public Task() {
		// Initiate all the task status to ToDO
		this.status = false;
		TaskNumber = T_counter;
		dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	}

	/**
	 * A setter method for setting the task date into the private variables
	 * 
	 * @param project String for the project name
	 * @param title   String title to store the task title or description
	 * @param dueDate String for the DueDate
	 * @param status  Boolean for the task status
	 */
	public void setter(String project, String title, Date dueDate, boolean status) {

		this.project = project;
		this.title = title;
		this.dueDate = dueDate;
		this.status = status;
		T_counter++;
		TaskNumber = T_counter;
	}

	/**
	 * Getter method for the task's project number
	 * 
	 * @return the Project name
	 */
	public String getProjectName() {
		return project;
	}

	/**
	 * Getter method for the task's title
	 * 
	 * @return the title of the task
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter method for the task's DueDate
	 * 
	 * @return the Due'date TYPE Date
	 * @throws ParseException
	 */
	public Date getDueDate() throws ParseException {
		return dueDate;
	}

	/**
	 * Getter method for the task's Status
	 * 
	 * @return the status of the tasks TYPE String
	 */
	public String getTaskStatus() {
		if (status)
			return "Done";
		else
			return "ToDo";
	}

	/**
	 * Getter method for the task's Id number for editing purposes
	 * 
	 * @return the task Id number as a reference
	 */
	public int getTaskNumber() {

		return TaskNumber;
	}

	/**
	 * Override the method toString for specified String format
	 * 
	 * @return a String format TYPE String
	 */
	@Override
	public String toString() {
		return "\n" + project + "\n" + title + "\n" + dateFormat.format(dueDate) + "\n" + status + "\n" + TaskNumber
				+ "\n";

	}

	/**
	 * Getter for the task ID for the editing
	 * 
	 * @return TaskNumber TYPE INT
	 */
	public int getId() {
		// TODO Auto-generated method stub
		return TaskNumber;
	}

}
