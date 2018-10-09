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

public class TaskManagerTestAdding {

	private TaskManager tmTasks;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tmTasks = new TaskManager();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	

	private void addTaskToListTypeA() throws ParseException {
		String project = "project1";
		String title = "title1";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("12-12-2012");
		boolean status = false;

		tmTasks.AddTask(project, title, dd, status);
		if (!findInTasks(project, title, dd, status, tmTasks.Sorting())) {
			fail("Task not found.");
		}
	}

	private void addTaskToListTypeB() throws ParseException {
		String project = "project2";
		String title = "title2";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("12-12-2017");
		boolean status = true;

		tmTasks.AddTask(project, title, dd, status);
		if (!findInTasks(project, title, dd, status, tmTasks.Sorting())) {
			fail("Task not found.");
		}
	}

	private boolean findInTasks(String project, String title, Date date, boolean status, List<Task> tasks)
			throws ParseException {
		for (Task t : tasks) {
			if (t.getProjectName().equals(project) && t.getDueDate().equals(date)
					&& (t.getTaskStatus().equalsIgnoreCase("ToDO") ? false : true) == status
					&& t.getTitle().equals(title)) {
				return true;
			}
		}
		return false;
	}
	
	
	@Test
	public void testAddTaskOneTask() throws ParseException {
		addTaskToListTypeA();
		int[] x = tmTasks.StatusCount();
		assertEquals(1, x[0] + x[1]);
	}
	
	
	
	@Test
	public void testAddTaskTwoTasks() throws ParseException {
		addTaskToListTypeA();
		
		String project = "project2";
		String title = "title2";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("12-10-2012");
		boolean status = false;

		tmTasks.AddTask(project, title, dd, status);
		int[] x = tmTasks.StatusCount();
		assertEquals(2, x[0] + x[1]);
		if (!findInTasks(project, title, dd, status, tmTasks.Sorting())) {
			fail("Task not found.");
		}
	}

	@Test
	public void testAddTaskthreeTasks() throws ParseException {
		addTaskToListTypeA();
		addTaskToListTypeB();

		String project = "project3";
		String title = "title3";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("12-11-2012");
		boolean status = false;

		tmTasks.AddTask(project, title, dd, status);
		int[] x = tmTasks.StatusCount();
		assertEquals(3, x[0] + x[1]);
		if (!findInTasks(project, title, dd, status, tmTasks.Sorting())) {
			fail("Task not found.");
		}
	}
}