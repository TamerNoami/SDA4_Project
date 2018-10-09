package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Task;
import core.TaskManager;

class TaskManagerTestRemove {

	private TaskManager tmNoTasks;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tmNoTasks = new TaskManager();
		
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

		tmNoTasks.AddTask(project, title, dd, status);
		if (!findInTasks(project, title, dd, status, tmNoTasks.Sorting())) {
			fail("Task not found.");
		}
	}

	private void addTaskToListTypeB() throws ParseException {
		String project = "project2";
		String title = "title2";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("12-12-2017");
		boolean status = true;

		tmNoTasks.AddTask(project, title, dd, status);
		if (!findInTasks(project, title, dd, status, tmNoTasks.Sorting())) {
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
	public void testRemoveTaskOneTask(int tn) throws ParseException
	{
		addTaskToListTypeA();
		int TN =tmNoTasks.MapToList().get(tmNoTasks.Sorting().size()).getTaskNumber();
		int[] x = tmNoTasks.StatusCount();
		assertEquals(1, x[0] + x[1]);
		tmNoTasks.RemoveTask(TN);
		 x = tmNoTasks.StatusCount();
		assertEquals(0, x[0] + x[1]);
	
	}


}