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

class TaskManagerTestSorting {

private TaskManager tmTasks;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
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
		Date dd = dateFormat.parse("12-12-2018");
		boolean status = false;

		tmTasks.AddTask(project, title, dd, status);
		
	}

	private void addTaskToListTypeB() throws ParseException {
		String project = "project2";
		String title = "title2";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("12-12-2017");
		boolean status = true;

		tmTasks.AddTask(project, title, dd, status);
		
	}

		
	
	@Test
	public void testSortTaskTwoTasks() throws ParseException {
		addTaskToListTypeA();
		addTaskToListTypeB();
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		String date= dateFormat.format(tmTasks.Sorting().get(0).getDueDate());
		assertEquals(true,date.equals("12-12-2017") );
		date= dateFormat.format(tmTasks.Sorting().get(1).getDueDate());
		assertEquals(true,date.equals("12-12-2018") );
	}

	@Test
	public void testSortTaskthreeTasks() throws ParseException {
		addTaskToListTypeA();
		addTaskToListTypeB();

		String project = "project3";
		String title = "title3";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("12-11-2017");
		boolean status = false;
		tmTasks.AddTask(project, title, dd, status);
		String date= dateFormat.format(tmTasks.Sorting().get(0).getDueDate());
		assertEquals(true,date.equals("12-11-2017") );
		date= dateFormat.format(tmTasks.Sorting().get(1).getDueDate());
		assertEquals(true,date.equals("12-12-2017") );
		date= dateFormat.format(tmTasks.Sorting().get(2).getDueDate());
		assertEquals(true,date.equals("12-12-2018") );
		
	}
}
