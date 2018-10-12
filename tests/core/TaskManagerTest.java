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

public class TaskManagerTest {

	private TaskManager tmTasks;
	private List<Task> testCases;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {

		tmTasks = new TaskManager();
		testCases = new ArrayList<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Task task1 = new Task();
		task1.setter("project", "title", dateFormat.parse("02-12-2018"), false);
		testCases.add(task1);
		Task task2 = new Task();
		task2.setter("project", "title2", dateFormat.parse("22-11-2018"), true);
		testCases.add(task2);
		Task task3 = new Task();
		task3.setter("project", "title3", dateFormat.parse("14-01-2018"), true);
		testCases.add(task3);
	}

	@Before
	public void initiatetest() throws ParseException, ClassNotFoundException, IOException {
		tmTasks = new TaskManager();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	private Task getTaskTestHelper(int titleIndex, String dayInt, String monthInt, String yearInt)
			throws ParseException {
		Task task = new Task();
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse(dayInt + "-" + monthInt + "-" + yearInt);
		task.setter("project", "title" + titleIndex, dd, true);
		return task;
	}

	private Task getTaskTestHelper(int titleIndex) throws ParseException {

		return getTaskTestHelper(titleIndex, "22", "03", "2018");
	}

	
	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 50, 100, 1000})
	@DisplayName("Adding one or more tasks")
	public void testAddTasks(int num) throws ParseException {

		for (int i = 0; i < num; i++) {
			Task myTask = getTaskTestHelper(i);
			String projectName = myTask.getProjectName();
			String title = myTask.getTitle();
			Date date = myTask.date;
			boolean st = myTask.getTaskStatus().equalsIgnoreCase("ToDO") ? false : true;
			tmTasks.AddTask(projectName, title, date, st);
			//System.out.println("Added the task: " + myTask);
		}

		int[] x = tmTasks.StatusCount();
		assertEquals(num, x[0] + x[1]);

	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 50, 100, 500})
	@DisplayName("Removing one or more tasks")
	public void testRemoveTaskOneTask(int num) throws ParseException
	{
		for (int i = 0; i < num; i++) {
		Task myTask = getTaskTestHelper(i);
		String projectName = myTask.getProjectName();
		String title = myTask.getTitle();
		Date date = myTask.date;
		boolean st = myTask.getTaskStatus().equalsIgnoreCase("ToDO") ? false : true;
		int taskId = tmTasks.AddTask(projectName, title, date, st);
		int[] countBeforeRemove = tmTasks.StatusCount();
		tmTasks.RemoveTask(taskId);
		int[] countAfterRemove = tmTasks.StatusCount();
		int actual =countAfterRemove[0]+countAfterRemove[1]; 
		int expected = (countBeforeRemove[0]+countBeforeRemove[1])-1;
		assertEquals(expected,actual );
	
	}

	}

	@ParameterizedTest
	@ValueSource(ints = { 2 })
	@DisplayName("Sort Tow tasks")
	public void testSortTaskTwoTasks(int num) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		for(int i=0; i<num;i++) {
			Task myTask = testCases.get(i);
			String projectName = myTask.getProjectName();
			String title = myTask.getTitle();
			Date DueDate = myTask.getDueDate();
			System.out.println(DueDate);//***
			boolean st = myTask.getTaskStatus().equalsIgnoreCase("ToDO") ? false : true;
			tmTasks.AddTask(projectName, title, DueDate, st);
		}
	
		String date = dateFormat.format(tmTasks.Sorting().get(0).getDueDate());
		System.out.println(date);//***
		assertEquals(true, date.equals("22-11-2018"));
		date = dateFormat.format(tmTasks.Sorting().get(1).getDueDate());
		assertEquals(true, date.equals("02-12-2018"));
	}

	@ParameterizedTest
	@ValueSource(ints = { 3 })
	@DisplayName("Sort three tasks")
	public void testSortTaskthreeTasks(int num) throws ParseException {
		for(int i=0; i<num;i++) {
			Task myTask = testCases.get(i);
			String projectName = myTask.getProjectName();
			String title = myTask.getTitle();
			Date DueDate = myTask.getDueDate();
			System.out.println(DueDate);
			boolean st = myTask.getTaskStatus().equalsIgnoreCase("ToDO") ? false : true;
			tmTasks.AddTask(projectName, title, DueDate, st);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		String date = dateFormat.format(tmTasks.Sorting().get(0).getDueDate());
		assertEquals(true, date.equals("14-01-2018"));
		date = dateFormat.format(tmTasks.Sorting().get(1).getDueDate());
		assertEquals(true, date.equals("22-11-2018"));
		date = dateFormat.format(tmTasks.Sorting().get(2).getDueDate());
		assertEquals(true, date.equals("02-12-2018"));

	}
	
	
}