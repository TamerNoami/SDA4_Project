package core;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TaskManagerTest {
	
	
	private TaskManager tmNoTasks;
	private TaskManager tmOneTask;
	private TaskManager tmThreeTasks;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tmNoTasks = new TaskManager();
		tmOneTask = new TaskManager();
		tmThreeTasks = new TaskManager();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddTaskOneTask() throws ParseException {
		addTaskToList();
		int[] x = tmNoTasks.StatusCount();
		assertEquals(1, x[0]+x[1]);
	}
	
	private void addTaskToList() throws ParseException{
		String project = "project1";
		String title = "title1";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("12-12-2012");
		boolean status = false;
		
		tmNoTasks.AddTask(project, title, dd, status);
		if (!findInTasks(project, title,dd, status, tmNoTasks.Sorting())) {
			fail("Task not found.");
		}
	}
	private boolean findInTasks(String project, String title, Date date, boolean status, List<Task> tasks) throws ParseException {
		for (Task t : tasks) {
			if (t.getProjectName().equals(project) && t.getDueDate().equals(date) &&
					(t.getTaskStatus().equalsIgnoreCase("ToDO") ? false : true) == status && t.getTitle().equals(title)) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void testAddTaskTwoTasks() throws ParseException {
		addTaskToList();
		
		String project = "project2";
		String title = "title2";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("12-10-2012");
		boolean status = false;
		
		tmNoTasks.AddTask(project, title, dd, status);
		int[] x = tmNoTasks.StatusCount();
		assertEquals(2, x[0]+x[1]);
		if (!findInTasks(project, title,dd, status, tmNoTasks.Sorting())) {
			fail("Task not found.");
		}
	}
	
}
