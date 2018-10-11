package core;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskTest {
	private Task task;
	SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		task=new Task();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	private void addTaskForSetterTaskOne() throws ParseException {
		String project = "Project 1";
		String title = "title 1";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("21-07-2018");
		boolean status = false;

		task.setter(project, title, dd, status);
		
	}
	
	private void addTaskForSetterTaskTwo() throws ParseException {
		String project = "Project 2";
		String title = "title 2";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TaskManager.Date_format);
		Date dd = dateFormat.parse("21-07-2019");
		boolean status = true;

		task.setter(project, title, dd, status);
		
	}
	
	@DisplayName("Test If task is not null")
	@Test
	void TestTask()
	{
		assertNotNull(task);
	}
	
	
	@DisplayName("Test the setter method")
	@Test
	void testSetter() throws ParseException {
		String expectedProject="Project 1";
		String expectedTitle="title 1";
		Date expectedDD = dateFormat.parse("21-07-2018");
		boolean expectedStatus = false;
		task.setter(expectedProject, expectedTitle, expectedDD, expectedStatus);
		assertEquals(expectedProject, task.getProjectName());
		assertEquals(expectedTitle, task.getTitle());
		assertEquals(0, expectedDD.compareTo(task.getDueDate()));
		boolean ActualStatus = task.getTaskStatus().equals("Done")? true : false;
		assertEquals(expectedStatus, ActualStatus);
	}

	@DisplayName("Test the Get project method")
	@Test
	void testGetProjectName() throws ParseException {
		addTaskForSetterTaskOne();
		assertEquals("Project 1",task.getProjectName());
		addTaskForSetterTaskTwo();
		assertEquals("Project 2",task.getProjectName());

	}
	@DisplayName("Test the Get title method")
	@Test
	void testGetTitle() throws ParseException {
		addTaskForSetterTaskOne();
		assertEquals("title 1",task.getTitle());
		addTaskForSetterTaskTwo();
		assertEquals("title 2",task.getTitle());
	}
	
	@DisplayName("Test the Get DueDate method")
	@Test
	void testGetDueDate() throws ParseException {
		addTaskForSetterTaskOne();
		Date expectedDD =dateFormat.parse("21-07-2018");
		assertEquals(0, expectedDD.compareTo(task.getDueDate()));
		addTaskForSetterTaskTwo();
		Date expectedDD2 =dateFormat.parse("21-07-2019");
		assertEquals(0, expectedDD2.compareTo(task.getDueDate()));
	}

	@DisplayName("Test the Get Status method")
	@Test
	void testGetTaskStatus() throws ParseException {
		addTaskForSetterTaskOne();
		assertEquals(false,task.getTaskStatus().equals("Done")?true:false);
		addTaskForSetterTaskTwo();
		assertEquals(true,task.getTaskStatus().equals("Done")?true:false);
	}


}
