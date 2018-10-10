package core;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;



public class TaskManagerTestAdding {

	private TaskManager tmTasks;
	private List<Task> taskList;
	

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
	
	
	@DisplayName("Adding Tasks Tests")
	
		@BeforeEach
		public void doStuff() throws ParseException, ClassNotFoundException, IOException {
			tmTasks = new TaskManager();
			taskList = new ArrayList<>();
			SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
			Task task1 = new Task();
			task1.setter("project", "title", dateFormat.parse("12-12-2012"), true);
			taskList.add(task1);
			Task task2 = new Task();
			task2.setter("project", "title2", dateFormat.parse("12-12-2014"), true);
			taskList.add(task1);
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
		

		
		
		@ParameterizedTest
		@ValueSource(ints = {1,2})
		@DisplayName( "Adding one or more tasks")
		public void testAddTasks(int num) throws ParseException {
			
			
			for(int i=0; i<num;i++) {
				Task myTask = taskList.get(i);
				String projectName = myTask.getProjectName();
				String title = myTask.getTitle();
				Date date = myTask.date;
				boolean st = myTask.getTaskStatus().equalsIgnoreCase("ToDO") ? false : true;
				tmTasks.AddTask(projectName, title, date, st);
			}

			
			int[] x = tmTasks.StatusCount();
			assertEquals(num, x[0] + x[1]);

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